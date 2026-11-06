# IMX219 Custom 1024 × 768 @ 60 FPS Capture Mode

## Objective

Add a 1024 × 768 @ 60 FPS capture mode to the Sony IMX219 driver on the Jetson Nano. The goal was to obtain a smaller RAW frame at 60 FPS while retaining control over the sensor configuration rather than resizing an existing output in userspace.

## Sensor Configuration

The mode was implemented in the IMX219 register tables using a cropped sensor region, with binning disabled to preserve one-to-one pixel sampling.

| Setting | Configuration | Reason |
|---|---|---|
| Output | 1024 × 768 | Target capture resolution |
| Frame rate | 60 FPS | Target capture rate |
| Pixel format | RAW10 | Native sensor output |
| Crop window | 1024 × 768 | Produce the requested resolution at the sensor |
| Binning | Disabled | Avoid combining neighboring pixels |

The crop coordinates and timing registers are defined in `imx219_mode_tbls.h` and should be reviewed together: output dimensions alone do not determine the achievable frame rate.

## Driver and Device Tree Integration

- Added the mode's register sequence to `imx219_mode_tbls.h` and registered it in the driver's mode table.
- Added the corresponding mode entry in the camera Device Tree, including resolution, pixel format, and timing metadata.
- Rebuilt and deployed the updated kernel components, then verified mode discovery through NVIDIA Argus.

The driver register table programs the sensor, the Device Tree describes the mode to the Tegra camera stack. Both must agree.

## Image Orientation

Applied the required vertical flip and adjusted the Bayer interpretation accordingly. Verified image orientation and colors visually through GStreamer. The flip implementation is covered in [imx219-driver-integration](/projects/imx219-driver-integration/docs/bayer-pattern-correction.md).

## Validation

Used `nvarguscamerasrc` to validate the mode through the same Argus/GStreamer path intended for the application. `fakesink` removes display and encoding work from the capture test.

```bash
gst-launch-1.0 -e \
    nvarguscamerasrc sensor-id=0 num-buffers=18000 ! \
    'video/x-raw(memory:NVMM),width=1024,height=768,framerate=60/1' ! \
    fpsdisplaysink video-sink=fakesink text-overlay=false silent=false sync=false
```

| Check | Observed result |
|---|---|
| Argus mode discovery | 1024 × 768, nominal 59.999999 FPS |
| Selected mode | Sensor mode 5 |
| Orientation and colors | Visually verified |
| Sustained capture | 18,000 buffers requested; EOS and successful Argus cleanup |
| GStreamer execution time | 300.21 s |
| Approximate throughput | 59.96 FPS |

The throughput was computed as 18,000 / 300.21 s. This is an estimate, not a measurement of frame timestamps or proof that no frames were dropped. Periodic `fpsdisplaysink` measurements were not present in the saved output.

Direct V4L2 streaming was also attempted but stalled waiting for buffers. Since the target application uses Argus, sustained validation proceeded through GStreamer; the V4L2 issue remains separate from this result.

As a conclusion for this stage of the project, the custom mode is recognized and selected by Argus, produces the expected image, and completes a five-minute capture. Frame-interval accuracy and CSI/VI-level frame loss were not independently characterized.