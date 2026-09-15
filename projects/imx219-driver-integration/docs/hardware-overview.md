# Hardware Overview – IMX219 Camera on Jetson Nano

## Objective
This document reviews the hardware components involved in the integration of the Raspberry Pi Camera Module v2 (Sony IMX219) with the NVIDIA Jetson Nano. The goal is to understand the sensor interfaces, physical connections, and signal mapping required for correct operation.

---

## Camera Sensor: Sony IMX219

The Raspberry Pi Camera Module v2 is based on the Sony IMX219 CMOS image sensor. According to the IMX219 datasheet provided by Arducam, the sensor exposes two main interfaces:

### Control Interface
* **Protocol:** I2C
* **Purpose:**
  * Sensor configuration
  * Register access (gain, exposure, mode selection)
  * Device identification (Model ID)

**Note:** The I2C interface is mandatory for sensor initialization and must be operational before any video data can be captured.

### Data Interface
* **Protocol:** MIPI CSI-2
* **Configuration:** 2 data lanes + 1 clock lane
* **Purpose:** High-speed pixel data transmission

---

## Raspberry Pi Camera Module v2 Schematic Review
The official Raspberry Pi documentation provides the schematics for the Camera Module v2. The module connects to the host system using a 15-pin FFC connector.

### Key Signals on the 15-pin Connector
| Pin | Signal | Description |
|------------|--------|-------|
| 1 | GND | Ground |
| 2 | CAM_D0_P | MIPI CSI-2 Data Lane 0 (+) |
| 3 | CAM_D0_N | MIPI CSI-2 Data Lane 0 (−) |
| 4 | GND | Ground |
| 5 | CAM_D1_P| MIPI CSI-2 Data Lane 1 (+) |
| 6 | CAM_D1_N| MIPI CSI-2 Data Lane 1 (-) |
| 7 | GND | Ground |
| 8 | CAM_CLK_P | MIPI CSI-2 Clock (+) |
| 9 | CAM_CLK_N | MIPI CSI-2 Clock (-) |
| 10 | GND | Ground |
| 11 | CAM0_PWDN | Camera enable/reset |
| 12 | MCLK | Master clock |
| 13 | SCL | I2C SCL |
| 14 | SDA | I2C SDA|
| 15 | CAM_3V3| Power |

These signals are routed through the FFC cable directly to the Jetson Nano CSI camera connector.

---

## Jetson Nano Camera Interface
According to the NVIDIA Jetson Nano and Jetson Xavier NX Camera Design Guide, the Jetson Nano exposes a 15-pin MIPI CSI-2 connector, physically compatible with the Raspberry Pi camera cable.

### Interface Characteristics
* MIPI CSI-2 for video data
* I2C for sensor control
* Dedicated GPIO for camera enable/reset

### Signal Naming (Jetson Perspective)
On the Jetson Nano, camera signals are typically named:

* `CSI_D0_P / CSI_D0_N`
* `CSI_D1_P / CSI_D1_N`
* `CSI_CLK_P / CSI_CLK_N`
* `CAM_I2C_SDA / CAM_I2C_SCL`
* `CAM_EN`

These signals map directly to those found in the Raspberry Pi Camera Module schematic, confirming electrical compatibility.

---

## Summary
* IMX219 uses I2C for control and MIPI CSI-2 for data
* Raspberry Pi Camera Module v2 is pin-compatible with Jetson Nano
* Proper GPIO and I2C configuration is required before the sensor appears on the bus
* Understanding the hardware interface is essential before debugging software or driver issues

---

## References
* [Sony IMX219 Datasheet](https://www.opensourceinstruments.com/Electronics/Data/IMX219PQ.pdf)
* [Raspberry Pi Camera Module v2 Schematic](https://datasheets.raspberrypi.com/camera/camera-module-2-schematics.pdf)
* [NVIDIA Jetson Nano and Jetson Xavier NX Camera Design Guide](https://developer.nvidia.com/embedded/downloads#?search=NVIDIA%20Jetson%20Nano%20and%20Jetson%20Xavier%20NX%20Camera%20Design%20Guide)
