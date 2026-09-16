# I2C Validation – IMX219 Camera on Jetson Nano

## Objective

The purpose of this stage is to validate low-level communication between the NVIDIA Jetson Nano and the Sony IMX219 camera sensor using the I2C bus. This is necessary as the camera driver relies on I2C access to configure the sensor before any video data can be streamed over MIPI CSI-2.

---

## Hardware Setup

* NVIDIA Jetson Nano Developer Kit
* Raspberry Pi Camera Module v2 (Sony IMX219)
* 15-pin FFC camera cable

Special care was taken to correctly orient the FFC cable to avoid power or signal shorts. The camera was connected with the Jetson Nano powered off.

---

## Expected I2C Bus and Slave Address

Based on the Jetson Nano Camera Design Guide and the IMX219 datasheet:

* **Expected I2C bus:** Camera I2C bus routed through the CSI connector (via I2C mux)
* **IMX219 slave address:** `0x10` (7-bit address)

The camera should appear on the I2C bus once it is powered and released from reset.

---

## Installing I2C Tools

To inspect the I2C bus from user space, the `i2c-tools` package was installed:

```bash
sudo apt update
sudo apt install i2c-tools
```

---

## Initial I2C Scan

The following command was used to scan the expected I2C bus:

```bash
sudo i2cdetect -r -y <bus_number>
```

### Initial Result

* The IMX219 did not appear at address `0x10`
* The bus scan returned no responsive device at the expected address

This behavior indicates that the sensor was either not powered, held in reset or not yet enabled by a control GPIO.

---

## Why the Camera May Not Appear on I2C

Reviewing the Raspberry Pi Camera Module v2 schematic reveals that:

* The IMX219 requires an **enable/reset GPIO** to be asserted
* Power rails alone are insufficient to activate the sensor

On the Jetson Nano, this GPIO must be configured and driven high before I2C communication becomes possible.

---

## Identifying the Camera GPIO (Pinmux)

The NVIDIA Pinmux configuration files were reviewed to identify the GPIO associated with the camera enable signal.

* **Pinmux label:** `CAM0_PWDN`

The exact label depends on BSP version and is used internally by the kernel and must be translated into a Linux GPIO number.

---

## Calculating the GPIO Number

The file `tegra-gpio.h` from the Jetson kernel sources provides the formula to convert a GPIO label into a numeric GPIO identifier.

```C
#define TEGRA_GPIO_PORT_S 18

#define TEGRA_GPIO(port, offset) \
        ((TEGRA_GPIO_PORT_##port * 8) + offset)
```

| Jetson Nano Signal Name | GPIO        |
|------------------------|-------------|
| CAM0_PWDN              | GPIO3_PS.07 |

Using the port and offset information defined in the Pinmux configuration, the corresponding GPIO number was calculated as follows ```(18*8)+7=151```.

---

## Enabling the Camera via GPIO

Once the GPIO number was known, the following sequence was executed to enable the camera:

```bash
sudo su
echo <GPIO_NUM> > /sys/class/gpio/export
echo out > /sys/class/gpio/gpio<GPIO_NUM>/direction
echo 1 > /sys/class/gpio/gpio<GPIO_NUM>/value
exit
```

This sequence:

* Exports the GPIO
* Configures it as an output
* Drives it high to release the camera from reset

---

## I2C Scan After GPIO Enable

After enabling the camera, the I2C scan command was executed again:

```bash
sudo i2cdetect -r -y <bus_number>
```

### Result

* The IMX219 appeared at address **0x10**
* Successful acknowledgment confirms proper power and control sequencing

This validates that the camera sensor is correctly connected and responsive on the I2C bus.

---

## Reading Camera Registers

The `i2c-tools` package also allows direct register access. To verify sensor identity, the **Model ID register** was read using the command confirmed with the course instructor.

Example command:

```bash
sudo i2cget -y <bus_number> 0x10 <register_address>
```

### Result

* The returned value matched the expected IMX219 Model ID
* This confirms successful low-level communication with the sensor

---

## Summary

* Initial I2C scan failed due to camera enable GPIO not being asserted
* Proper GPIO configuration is required before I2C communication is possible
* After enabling the GPIO, the IMX219 appeared at address `0x10`
* Direct register access confirmed correct sensor identification

This step validates the electrical connection, power sequencing, and control interface required for successful driver operation.

---

## References

* [Sony IMX219 Datasheet](https://www.opensourceinstruments.com/Electronics/Data/IMX219PQ.pdf)
* [Raspberry Pi Camera Module v2 Schematic](https://datasheets.raspberrypi.com/camera/camera-module-2-schematics.pdf)
* [NVIDIA Jetson Nano and Jetson Xavier NX Camera Design Guide](https://developer.nvidia.com/embedded/downloads#?search=NVIDIA%20Jetson%20Nano%20and%20Jetson%20Xavier%20NX%20Camera%20Design%20Guide)
* [Jetson Nano Pinmux](https://developer.nvidia.com/embedded/downloads#?search=jetson%20nano%20pinmux)  
* NVIDIA Jetson Linux Kernel Sources
