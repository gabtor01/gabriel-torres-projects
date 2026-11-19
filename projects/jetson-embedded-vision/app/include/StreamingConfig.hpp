#pragma once

#include <string>

// Settings shared by the camera capture and UDP streaming pipeline
struct StreamingConfig{
  int sensor_id = 0;
  int width = 1024;
  int height = 768;
  int fps = 60;
  int num_buffers = 18000;
  int bitrate = 40000000;
  int port = 5000;
  std::string receiver_ip;
};
