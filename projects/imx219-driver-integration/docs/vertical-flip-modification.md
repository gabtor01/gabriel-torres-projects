# Vertical Image Flip Modification – IMX219 Driver

## Objective

This section documents the modification of the Sony IMX219 camera driver to perform a vertical image flip for the `1640x1232 @ 30 FPS` capture mode on the NVIDIA Jetson Nano.

The goal is to demonstrate low-level customization of sensor behavior by modifying its register configuration and validating the result through video capture.

---

## Relevant Source File

### `imx219_mode_tbls.h`

The IMX219 driver uses mode-specific register tables to configure the sensor.

The `1640x1232 @ 30 FPS` mode is defined in:

```c
static imx219_reg imx219_mode_1640x1232_30fps[] = {
    ...
};
```

---

## Flip Control Register

According to the IMX219 datasheet, image orientation is controlled by register `0x0172` (`IMG_ORIENTATION_A`).

![IMX219 Datasheet Image Orientation Registers](images/image-orientation-register.png)

The relevant bits are:

* Bit 0: horizontal mirror
* Bit 1: vertical flip

Therefore, enabling only the vertical flip requires:

```text
0x0172 = 0x02
```

---

## Implementing the Vertical Flip

The following register entry was added to the `1640x1232 @ 30 FPS` mode:

```c
{0x0170, 0x01},
{0x0171, 0x01},
{0x0172, 0x02}, /* Vertical flip */
{0x0174, 0x01},
{0x0175, 0x01},
```

The remaining mode configuration was left unchanged.

---

## Rebuilding and Validation

After modifying the register table, the IMX219 kernel module was recompiled and deployed to the Jetson Nano.

The modified driver was loaded and the camera was tested using the `1640x1232 @ 30 FPS` GStreamer capture pipeline.

### Before and After

The following captures show the camera output before and after applying the vertical flip register modification.

| Before Modification                           | After Modification                           |
| --------------------------------------------- | -------------------------------------------- |
| **Original orientation**                      | **Vertical flip enabled**                    |
| [▶ View video](docs/videos/capture_2026-09-17_12-10-31.mp4) | [▶ View video](results/video/after-flip.mp4) |

The resulting video exhibited a **vertical flip** relative to the original orientation, confirming that the register-level modification was applied successfully.

---

## Summary

* IMX219 image orientation is controlled by register `0x0172`.
* Bit 1 enables vertical flipping.
* `0x0172 = 0x02` was added to the `1640x1232 @ 30 FPS` mode.
* The modification was validated through real video capture.

---

## References

* Sony IMX219 Datasheet
* GStreamer Documentation
