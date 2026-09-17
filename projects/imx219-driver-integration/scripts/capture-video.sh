#!/usr/bin/env bash
# Capture IMX219 video using NVIDIA Argus and GStreamer
# Usage:
#   ./capture-video.sh
#   ./capture-video.sh <output_file>

set -euo pipefail

if ! command -v gst-launch-1.0 >/dev/null 2>&1; then
    echo "Error: gst-launch-1.0 was not found."
    exit 1
fi

if [[ $# -gt 1 ]]; then
    echo "Usage: $0 [output_file]"
    exit 1
fi

if [[ $# -eq 1 ]]; then
    OUTPUT="$1"
else
    OUTPUT="capture_$(date +%Y-%m-%d_%H-%M-%S).mp4"
fi

echo "Starting IMX219 video capture..."
echo "Output: $OUTPUT"
echo "Press Ctrl+C to stop."

gst-launch-1.0 -e \
    nvarguscamerasrc \
    ! 'video/x-raw(memory:NVMM),width=1640,height=1232,format=NV12,framerate=30/1' \
    ! nvv4l2h264enc bitrate=8000000 \
    ! h264parse \
    ! qtmux \
    ! filesink location="$OUTPUT"