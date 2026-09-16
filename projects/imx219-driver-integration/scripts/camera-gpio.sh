#!/usr/bin/env bash
# Enable the IMX219 camera through its power-down GPIO
# Jetson Nano B01: GPIO 151 (CAM0_PWDN / GPIO3_PS.07)

set -euo pipefail

GPIO=151
GPIO_PATH="/sys/class/gpio/gpio${GPIO}"

if [[ ! -d "$GPIO_PATH" ]]; then
    echo "Exporting GPIO $GPIO..."
    echo "$GPIO" | sudo tee /sys/class/gpio/export >/dev/null
fi

echo "Configuring GPIO $GPIO as output..."
echo out | sudo tee "$GPIO_PATH/direction" >/dev/null

echo "Setting GPIO $GPIO HIGH..."
echo 1 | sudo tee "$GPIO_PATH/value" >/dev/null

echo
echo "GPIO $GPIO enabled."
echo "Current value: $(cat "$GPIO_PATH/value")"