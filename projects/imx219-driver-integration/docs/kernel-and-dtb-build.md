# Kernel and Device Tree Build – IMX219 on Jetson Nano

## Objective

This stage documents the process of rebuilding the Linux kernel, device tree, and camera driver modules on the NVIDIA Jetson Nano platform. The main goal is to compile the IMX219 camera driver as an external kernel module, allowing explicit control over when the driver is loaded and enabling easier experimentation and debugging.

---

## Development Environment

* **Platform:** NVIDIA Jetson Nano
* **SoC:** NVIDIA Tegra210
* **OS / BSP:** NVIDIA Jetson Linux (L4T)
* **Kernel:** Linux kernel provided by NVIDIA BSP

Kernel sources were obtained directly from the NVIDIA Jetson Linux BSP to ensure version compatibility with the running system.

---

## Modifying Kernel Configuration

By default, the IMX219 driver is built into the kernel image. To enable external module loading, the kernel configuration was modified:

* The IMX219 driver was changed from **built-in (`=y`)** to **module (`=m`)**

This modification allows the driver to be inserted and removed dynamically using `insmod` and `rmmod`.

Kernel configuration was updated using standard kernel configuration tools, ensuring that all dependencies remained enabled.

---

## Rebuilding Kernel, Device Tree, and Modules

After updating the kernel configuration, the following components were rebuilt:

* Linux kernel image (`Image`)
* Device tree blobs (`.dtb`)
* Kernel modules (`.ko` files)

This ensures that:

* The modified configuration is reflected in the running kernel
* The camera device tree matches the driver expectations
* The IMX219 driver is available as a loadable module

---

## Backing Up Existing System Files

Before deploying the newly built files, backups were created on the Jetson Nano:

* **Device tree blob** currently in use
* **Kernel image** currently in use

The active device tree filename was identified using:

```bash
cat /proc/device-tree/nvidia,dtsfilename
```

This step is critical to ensure system recovery in case of boot failures.

---

## Deploying the New Kernel and Device Tree

The newly built kernel image and device tree blob were copied to the `/boot` directory of the Jetson Nano.

The bootloader configuration file was then updated:

```text
/boot/extlinux/extlinux.conf
```

An explicit reference to the new device tree was added:

```text
FDT /boot/<DTB_name>
```

This forces the bootloader to load the updated device tree instead of the default one.

---

## Verifying Kernel and Device Tree Usage

After rebooting the system, the following checks were performed:

* Kernel build timestamp
* Device tree build timestamp

This verification confirms that the Jetson Nano is running the newly compiled kernel and device tree files.

---

## Installing Kernel Modules

The directory containing the newly built kernel modules was compressed and transferred to the Jetson Nano.

On the target system:

* The existing `/lib/modules` directory was backed up
* The new modules were extracted into `/lib/modules`

This step ensures that module versioning matches the running kernel image.

---

## Preparing the IMX219 Module for Manual Insertion

To allow manual control of the camera driver loading sequence, the IMX219 module was relocated:

```text
From /lib/modules/4.9.337-tegra/kernel/drivers/media/i2c/imx219.ko (In the host)  
to   /home/nvidia/imx219.ko                                        (In the target)
```

The system was rebooted to ensure the module was not automatically loaded during startup.

---

## Inserting the IMX219 Driver Module

After reboot, the presence of video devices was checked:

```bash
ls /dev/video*
```

Initially, no video devices were present.

The IMX219 driver module was then manually inserted:

```bash
sudo insmod /home/nvidia/imx219.ko
```

### Result

* The module loaded successfully
* A video device node `/dev/video0` was created

This confirms that:

* The kernel module was correctly built
* The device tree configuration matches the driver
* The camera sensor was successfully probed by the driver

---

## Summary

* The IMX219 driver was successfully converted into an external kernel module
* Kernel, device tree, and modules were rebuilt and deployed
* Bootloader configuration was updated to use the new device tree
* Manual insertion of the driver resulted in successful camera detection

This stage establishes a flexible development setup suitable for driver debugging and feature experimentation.

---

## References

* [NVIDIA Jetson Linux Developer Guide](https://docs.nvidia.com/jetson/archives/r38.2/DeveloperGuide/)
* [NVIDIA Jetson Nano Camera Design Guide](https://developer.nvidia.com/embedded/downloads#?search=NVIDIA%20Jetson%20Nano%20and%20Jetson%20Xavier%20NX%20Camera%20Design%20Guide)
* [Linux Kernel Documentation](https://www.kernel.org/doc/html/latest/)
