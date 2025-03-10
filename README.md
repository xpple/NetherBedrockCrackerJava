# Nether Bedrock Cracker Java
This project provides Java bindings for the [Nether Bedrock Cracker](https://github.com/19MisterX98/Nether_Bedrock_Cracker) by [19MisterX98](https://github.com/19MisterX98) written in Rust. 

## Building from source
The same [instructions for SeedMapper](https://github.com/xpple/SeedMapper?tab=readme-ov-file#building-the-mod-locally), largely apply here too. However instead of compiling a C library, here you are compiling a Rust library to a shared library. Do not forget to use `--release` when building. Then in order to use [jextract](https://github.com/openjdk/jextract), I used [cbindgen](https://github.com/mozilla/cbindgen) to generate the C header file.

I intentionally did not include the binary file in the repository. You _must_ build it yourself. See also [SeedMapper's buildscript](https://github.com/xpple/SeedMapper/blob/master/.github/workflows/build.yml) for an example on how to automate this.
