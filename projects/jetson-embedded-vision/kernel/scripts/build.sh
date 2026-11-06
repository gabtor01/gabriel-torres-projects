#!/usr/bin/env bash
# Configures the IMX219 driver as a loadable kernel module, builds the kernel 
# modules and Device Trees, and installs the generated modules

set -euo pipefail

mkdir -p "$TEGRA_KERNEL_OUT" "$TEGRA_MODULES_OUT"

cd "$L4T/sources"

CONFIG_FILE="$TEGRA_KERNEL_OUT/.config"

# Configure IMX219 as a loadable kernel module
sed -i '/^CONFIG_VIDEO_IMX219=/d' "$CONFIG_FILE"
sed -i '/^# CONFIG_VIDEO_IMX219 is not set$/d' "$CONFIG_FILE"
echo "CONFIG_VIDEO_IMX219=m" >> "$CONFIG_FILE"

# Update kernel configuration
make -C kernel/kernel-4.9/ \
    ARCH=arm64 \
    O="$TEGRA_KERNEL_OUT" \
    LOCALVERSION=-tegra \
    CROSS_COMPILE="${TOOLCHAIN_PREFIX}" \
    olddefconfig

# Build kernel modules
make -C kernel/kernel-4.9/ \
    ARCH=arm64 \
    O="$TEGRA_KERNEL_OUT" \
    LOCALVERSION=-tegra \
    CROSS_COMPILE="${TOOLCHAIN_PREFIX}" \
    -j"$(nproc)" \
    modules

# Build Device Trees
make -C kernel/kernel-4.9/ \
    ARCH=arm64 \
    O="$TEGRA_KERNEL_OUT" \
    LOCALVERSION=-tegra \
    CROSS_COMPILE="${TOOLCHAIN_PREFIX}" \
    -j"$(nproc)" \
    dtbs

# Install kernel modules
make -C kernel/kernel-4.9/ \
    ARCH=arm64 \
    O="$TEGRA_KERNEL_OUT" \
    LOCALVERSION=-tegra \
    CROSS_COMPILE="${TOOLCHAIN_PREFIX}" \
    INSTALL_MOD_PATH="$TEGRA_MODULES_OUT" \
    modules_install

echo "Build completed successfully."