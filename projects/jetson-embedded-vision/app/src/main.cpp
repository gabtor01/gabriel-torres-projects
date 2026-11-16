#include <iostream>
#include <gst/gst.h>
#include <string>
#include <iomanip>

// Report capture performance
static void on_fps_measurement(
  GstElement*,          // Signal emisor
  gdouble fps,          // Actual FPS 
  gdouble,              // Discarded frames
  gdouble average_fps,
  gpointer){
  std::cout << std::fixed << std::setprecision(3) 
            << "Current FPS: " << fps 
            << std::fixed << std::setprecision(3) 
            << " | Average FPS: " << average_fps << std::endl;
}

int main(int argc, char* argv[]){
  // Initialize GStreamer
  gst_init(&argc, &argv);

  // Number of frames to capture
  int num_buffers = 600;

  if (argc > 1){
    try {
      num_buffers = std::stoi(argv[1]);
    } catch(...){
      std::cerr << "Invalid buffer count." << std::endl;
      return 1;
    }
  }
  
  if (num_buffers <= 0){
    std::cerr << "Buffer count must be positive." << std::endl;
    return 1;
  }
  
  // Camera pipeline structure
  std::string pipeline_description = 
    "nvarguscamerasrc sensor-id=0 num-buffers=" +
    std::to_string(num_buffers) +
    " ! video/x-raw(memory:NVMM),"
    "width=1024,height=768,framerate=60/1"
    " ! fpsdisplaysink name=fps "
    "video-sink=fakesink "
    "text-overlay=false "
    "signal-fps-measurements=true "
    "sync=false";

  GError* error = nullptr;

  std::cout << "Pipeline: " << pipeline_description << std::endl; // Check built pipeline

  GstElement* pipeline = gst_parse_launch(
    pipeline_description.c_str(), &error);

  if (error != nullptr){
    std::cerr << "Pipeline error: " << error->message << std::endl;
    g_error_free(error);
    if (pipeline != nullptr){
      gst_object_unref(pipeline);
    }
    return 1;
  }
  
  if (pipeline == nullptr){
    std::cerr << "Failed to create pipeline." << std::endl;
    return 1;
  }
  
  // Get the fps measurement element
  GstElement* fps_sink = gst_bin_get_by_name(GST_BIN(pipeline), "fps");

  if (fps_sink == nullptr){
    std::cerr << "FPS element not found." << std::endl;
    gst_object_unref(pipeline);
    return 1;
  }
  
  g_signal_connect(
    fps_sink,
    "fps-measurements",
    G_CALLBACK(on_fps_measurement),
    nullptr);
  
  gst_object_unref(fps_sink);
  
  // Get the pipeline message bus
  GstBus* bus = gst_element_get_bus(pipeline);
  
  std::cout << "Starting camera capture..." << std::endl;

  if (gst_element_set_state(
    pipeline, GST_STATE_PLAYING) == GST_STATE_CHANGE_FAILURE){
    std::cerr << "Failed to start pipeline." << std::endl;
    gst_object_unref(bus);
    gst_element_set_state(pipeline, GST_STATE_NULL);
    gst_object_unref(pipeline);
    return 1;
  }

  // Wait untill capture completion or an error
  GstMessage* message = gst_bus_timed_pop_filtered(
    bus,
    GST_CLOCK_TIME_NONE,
    static_cast<GstMessageType>(GST_MESSAGE_ERROR | GST_MESSAGE_EOS));

  int exit_code = 0;

  if (message != nullptr){
    switch (GST_MESSAGE_TYPE(message)){
      case GST_MESSAGE_EOS:{
        std::cout << "Capture completed successfully." << std::endl;
        break;
      }
      case GST_MESSAGE_ERROR:{
        GError* gst_error = nullptr;
        gchar* debug_info = nullptr;

        gst_message_parse_error(message, &gst_error, &debug_info);

        std::cerr << "Gstreamer error: " << gst_error->message << std::endl;

        g_error_free(gst_error);
        g_free(debug_info);

        exit_code = 1;
        break;
      }
      default:
        break;
    }
    gst_message_unref(message);
  }
  
  // Release resources
  std::cout << "Stopping pipeline..." << std::endl;
  GstStateChangeReturn result = gst_element_set_state(pipeline, GST_STATE_NULL);

  GstState state;
  GstState pending;

  gst_element_get_state(
      pipeline,
      &state,
      &pending,
      GST_CLOCK_TIME_NONE);

  std::cout << "Pipeline state: "
            << gst_element_state_get_name(state)
            << std::endl;

  if (result == GST_STATE_CHANGE_FAILURE ||
      state != GST_STATE_NULL) {
      std::cerr << "Pipeline shutdown failed." << std::endl;
      exit_code = 1;
  }

  gst_object_unref(bus);
  gst_object_unref(pipeline);

  return exit_code;
}
