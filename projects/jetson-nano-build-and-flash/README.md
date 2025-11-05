# Jetson Nano Build and Flash
Bring-up and kernel rebuild process for the **NVIDIA Jetson Nano 4GB Developer Kit** using **SDK Manager**, **Docker** and **cross-compilation** with the Linaro GCC toolchain.

![Embedded system](docs/embedded-system.JPG)

## Overview
- Flashing Jetson Nano OS via SDK Manager (Docker container).
- Cross-compiling the Linux 4.9-tegra kernel using Linaro GCC 7.3.1.
- Building and installing kernel modules and device trees.
- Serial debugging over UART.

## Technologies
| Category | Tools / Components |
|-----------|--------------------|
| Hardware | Jetson Nano 4GB Dev Kit |
| OS / Kernel | Linux for Tegra (L4T 32.7.3) |
| Toolchain | GCC Linaro 7.3.1 aarch64-linux-gnu |
| SDK / Flash | NVIDIA SDK Manager (Docker) |
| Utilities | Bash, Docker, Minicom (UART) |
## Structure
`jetson-nano-build-and-flash/`  
├── `config/` – Environment and serial configs   
├── [`docs/`](docs/README.md) – Logs, UART captures, results  
├── `scripts/` – Flashing and build automation     
└── `README.md`

## Tags
`embedded-linux` • `jetson-nano` • `kernel-build` • `sdkmanager` • `cross-compilation` • `uart` • `docker`  
**Status:** Finished
