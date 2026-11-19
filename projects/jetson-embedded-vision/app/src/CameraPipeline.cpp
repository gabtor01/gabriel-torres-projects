#include "CameraPipeline.hpp"
#include <iostream>
#include <sstream>

CameraPipeline::CameraPipeline(const StreamingConfig& config) : config_(config){
}

CameraPipeline::~CameraPipeline(){
  // Elements to NULL before releasing resources
  stop();

  if (bus_ != nullptr){
    gst_object_unref(bus_);
  }

  if (pipeline_ != nullptr){
    gst_object_unref(pipeline_);
  }
}

bool CameraPipeline::initialize(){
  if (pipeline_ != nullptr){
    std::cerr << "Pipeline is already initialize.\n";
    return false;
  }
  
  std::ostringstream description;

  description
    << "nvarguscamerasrc sensor-id=" << config_.sensor_id
    << " num-buffers=" << config_.num_buffers
    << " ! video/x-raw(memory:NVMM),"
    << "width=" << config_.width
    << ",height=" << config_.height
    << ",framerate=" << config_.fps << "/1"
    << " ! nvv4l2h264enc bitrate=" << config_.bitrate
    << " insert-sps-pps=true"
    << " ! h264parse"
    << " ! rtph264pay pt=96 config-interval=1"
    << " ! udpsink host=" << config_.receiver_ip
    << " port=" << config_.port << " sync=false";

  const std::string pipeline_description = description.str();
  
  GError* error = nullptr;

  pipeline_ = gst_parse_launch(pipeline_description.c_str(), &error);

  if (error != nullptr || pipeline_ == nullptr){
    std::cerr << "Failed to create pipeline: "
              << (error != nullptr ? error->message : "unknown error\n");
              
    if (error != nullptr){
      g_error_free(error);
    }
    
    if (pipeline_ != nullptr){
      gst_element_set_state(pipeline_,GST_STATE_NULL);
      gst_object_unref(pipeline_);
      pipeline_ = nullptr;
    }

    return false;
  }
  
  bus_ = gst_element_get_bus(pipeline_);

  if (bus_ == nullptr){
    std::cerr << "Failed to access the gstreamer bus.\n";
    return false;
  }

  std::cout << "Pipeline: " << pipeline_description << '\n';

  return true;
}

bool CameraPipeline::run(){

  if (pipeline_ == nullptr || bus_ == nullptr){
    std::cerr << "Pipeline is not initialized.\n";
    return false;
  }
  
  std::cout << "Starting camera streaming...\n";

  if (gst_element_set_state(pipeline_, GST_STATE_PLAYING) 
    == GST_STATE_CHANGE_FAILURE){
    
    std::cerr << "Failed to start pipeline.\n";
    stop();
    return false;
  }
  
  GstMessage* message = gst_bus_timed_pop_filtered(
    bus_,
    GST_CLOCK_TIME_NONE,
    static_cast<GstMessageType>(
      GST_MESSAGE_EOS | GST_MESSAGE_ERROR));

  bool success = false;

  if (message != nullptr){
    switch (GST_MESSAGE_TYPE(message)){
      
      case GST_MESSAGE_EOS:{
        std::cout << "Streaming completed successfully.\n";
        success = true;
        break;
      }
      case GST_MESSAGE_ERROR:{
        GError* error = nullptr;
        gchar* debug_info = nullptr;

        gst_message_parse_error(message, &error, &debug_info);
        
        std::cerr << "GStreamer error: "
                  << (error != nullptr ? error->message : "unknown error\n");

        if (debug_info != nullptr){
          std::cerr << "Details: " << debug_info << '\n';
        }

        if (error != nullptr){
          g_error_free(error);
        }

        g_free(debug_info);
        break;
      }
      default:{
        break;
      }
      
    gst_message_unref(message);
    }   
  }
  else {
    std::cerr << "No completion message received.\n";
  }
  const bool sutdown_ok = stop();
  return success && sutdown_ok;
}

bool CameraPipeline::stop(){
  if (pipeline_ == nullptr || stopped_){
    return true;
  }

  const GstStateChangeReturn result = 
    gst_element_set_state(pipeline_, GST_STATE_NULL);

  if (result == GST_STATE_CHANGE_FAILURE){
    std::cerr << "Failed to stop pipeline.\n";
    return false;
  }
  
  GstState state = GST_STATE_VOID_PENDING;
  
  const GstStateChangeReturn wait_result =
    gst_element_get_state(pipeline_,
                          &state,
                          nullptr,
                          GST_CLOCK_TIME_NONE);

  if (wait_result == GST_STATE_CHANGE_FAILURE || state != GST_STATE_NULL){
    std::cerr << "Pipeline did not reach NULL state.\n";
    return false;
  }

  stopped_ = true;
  
  std::cout << "Pipeline state: "
            << gst_element_state_get_name(state) << '\n';

  return true;
}
