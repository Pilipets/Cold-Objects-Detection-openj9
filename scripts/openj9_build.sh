#!/bin/bash

set -e -u -o pipefail


usage_str="\
Usage: ${0} jdk_dir jdk_ver [-c|--configure] [-d|--debug]"

function usage()
{
	echo "${usage_str}" 1>&2
	exit 1
}


if (( $# < 2 )); then usage; fi

jdk_dir=$(readlink -f "${1}")
jdk_ver="${2}"
configure=false
debug=false

for arg in "${@:3}"; do
	case "${arg}" in
		"-c" | "--configure" )
			configure=true
			;;
		"-d" | "--debug" )
			debug=true
			;;
		*)
			usage
			;;
	esac
done


jdk_src_dir="${jdk_dir}/openj9-openjdk-jdk${jdk_ver}"

if [[ "${debug}" == true ]]; then
	debug_level="slowdebug"
else
	debug_level="release"
fi

build_dir="${jdk_src_dir}/build/${debug_level}"

export CC=gcc-7 CXX=g++-7

if [[ "${configure}" == true || ! -d "${build_dir}" ]]; then
	rm -rf "${build_dir}"
	pushd "${jdk_src_dir}"

	bash "./configure" --enable-ccache --enable-jitserver --with-cmake \
	                   --with-boot-jdk="${jdk_dir}/bootjdk${jdk_ver}" \
	                   --with-conf-name="${debug_level}" \
	                   --with-debug-level="${debug_level}" \
	                   --with-native-debug-symbols=internal
	rm -f "a.out"

	popd #"${jdk_src_dir}"
fi


extra_cflags=("-ggdb3")

if [[ "${debug}" == true ]]; then
	extra_cflags+=("-Og" "-fno-inline")
fi

#  "-DJ9VM_GC_ALWAYS_CALL_OBJECT_ACCESS_BARRIER=1"

cmake_args=(
	"-DCMAKE_VERBOSE_MAKEFILE=ON"
	"-DOMR_PLATFORM_C_COMPILE_OPTIONS=\"${extra_cflags[@]}\""
	"-DOMR_PLATFORM_CXX_COMPILE_OPTIONS=\"${extra_cflags[@]}\""
	"-DOMR_SEPARATE_DEBUG_INFO=OFF"
)

export EXTRA_CMAKE_ARGS="${cmake_args[@]}"

make  -C "${jdk_src_dir}" CONF="${debug_level}" all