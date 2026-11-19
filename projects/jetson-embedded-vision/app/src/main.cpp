#include "CameraPipeline.hpp"
#include "StreamingConfig.hpp"

#include <arpa/inet.h>
#include <exception>
#include <iostream>
#include <string>

int main(int argc, char* argv[]){
  if (argc != 3){
    std::cerr << "Usage: " << argv[0]
              << " <number_buffers> <receiver_IPv4>\n";
    return 1;
  }

  int num_buffers = 0;

  try {
    const std::string buffer_arg = argv[1];
    std::size_t consumed = 0;

    num_buffers = std::stoi(buffer_arg, &consumed);

    if (consumed != buffer_arg.size() || num_buffers <= 0){
      std::cerr << "Buffer count must be a positive integer.\n";
      return 1;
    }
  }
  catch(const std::exception&){
    std::cerr << "Invalid buffer count.\n";
    return 1;
  }

  const std::string receiver_ip = argv[2];
  
  in_addr address{};

  if (inet_pton(AF_INET, receiver_ip.c_str(), &address) != 1){
    std::cerr << "Invalid receiver IPv4 address.\n";
    return 1;
  }

  StreamingConfig config;
  config.num_buffers = num_buffers;
  config.receiver_ip = receiver_ip;

  // Initialize GStreamer
  gst_init(&argc, &argv);

  // Create and initialize the camera pipeline
  CameraPipeline camera(config);

  if (!camera.initialize()){
    return 1;
  }

  // Run the pipeline
  return camera.run() ? 0 : 1;
}
