cmake_minimum_required(VERSION 3.10)

project(jetson_vision LANGUAGES CXX)

# C++17
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find GStreamer dependencies
find_package(PkgConfig REQUIRED)

pkg_check_modules(GSTREAMER REQUIRED IMPORTED_TARGET
    gstreamer-1.0
    gstreamer-base-1.0
)

# Build application
add_executable(jetson_vision
    src/main.cpp
)

# Link GStreamer libraries
target_link_libraries(jetson_vision PRIVATE
    PkgConfig::GSTREAMER
)

# Search paths for transitive ARM64 shared libraries
if(CMAKE_CROSSCOMPILING)
    set(SYSROOT_LIB_DIRS
        "${CMAKE_SYSROOT}/lib/aarch64-linux-gnu"
        "${CMAKE_SYSROOT}/usr/lib/aarch64-linux-gnu"
        "${CMAKE_SYSROOT}/lib"
        "${CMAKE_SYSROOT}/usr/lib"
    )

    foreach(LIB_DIR ${SYSROOT_LIB_DIRS})
        set_property(TARGET jetson_vision APPEND_STRING PROPERTY
            LINK_FLAGS " -Wl,-rpath-link,${LIB_DIR}"
        )
    endforeach()
endif()
