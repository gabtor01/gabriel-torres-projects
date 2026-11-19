#pragma once

#include <gst/gst.h>

#include "StreamingConfig.hpp"

class CameraPipeline{
public:
  explicit CameraPipeline(const StreamingConfig& config);
  ~CameraPipeline();

  // GstElement and GstBus use reference counting I must not copy their owner
  CameraPipeline(const CameraPipeline&) = delete;
  CameraPipeline& operator=(const CameraPipeline&) = delete;
  
  bool initialize();
  bool run();

private:
  bool stop();
  
  StreamingConfig config_;
  GstElement* pipeline_ = nullptr;
  GstBus* bus_ = nullptr;
  bool stopped_ = false;
};
