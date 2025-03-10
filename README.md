Always use the latest (stable) version!

# Nether Bedrock Cracker
This Minecraft Fabric mod is a wrapper around the Rust library [Nether Bedrock Cracker](https://github.com/19MisterX98/Nether_Bedrock_Cracker) by [19MisterX98](https://github.com/19MisterX98). Any and all credits for the cracking part go to him. For the cracking the seed in the Overworld, consider using [SeedCrackerX](https://github.com/19MisterX98/SeedcrackerX) instead.

## Installation
1. Install the [Fabric Loader](https://fabricmc.net/use/).
2. Download the [Fabric API](https://minecraft.curseforge.com/projects/fabric/) and move it to your mods folder
    - Linux/Windows: `.minecraft/mods`.
    - Mac: `minecraft/mods`.
3. Download Nether Bedrock Cracker from the [releases page](https://modrinth.com/mod/netherbedrockcracker/versions/) and move it to your mods folder.

## IMPORTANT
You need to have Java 23 installed to use this mod. I recommend to get Java 23 from [adoptium.net](https://adoptium.net/temurin/releases/?version=23). Next, configure your Minecraft launcher to use this release of Java.
- Vanilla launcher: Go to `Installations` -> `Edit` -> `More options` -> `Java executable`.
- MultiMC: Go to `Edit Instance` -> `Settings` -> `Java` -> `Java Installation`.
- PrismLauncher: Go to `Settings` -> `Java` -> `Java Runtime` -> `Auto-Detect...`.
    - Do not forget to enable "Skip Java compatibility checks".

If you run into issues, contact your launcher's support.

## Commands
The mod comes with two commands. The most important command is `/nbc:crack`.

### Crack command
Usage: `/nbc:crack [<threads>] [<bedrockgeneration>] [<outputmode>]`.

The `threads` parameter controls how many threads are going to be used for the cracking. Using more threads will speed up the computation. By default, the number of available threads will be used.

The `bedrockgeneration` parameter controls the type of bedrock generation. Two different types exist, because there used to be a bug in Paper that would cause wrong bedrock generation. The options are `NORMAL` and `PAPER1_18`. See [19MisterX98/Nether_Bedrock_Cracker](https://github.com/19MisterX98/Nether_Bedrock_Cracker?tab=readme-ov-file#papermc-servers) for more information on which one to use. By default, `NORMAL` is used.

The `outputmode` parameter controls the output type of the seed. The options are `WORLD_SEED` and `STRUCTURE_SEED`. Also for this, see [19MisterX98/Nether_Bedrock_Cracker](https://github.com/19MisterX98/Nether_Bedrock_Cracker?tab=readme-ov-file#user-specified-seeds) for more information. By default, `WORLD_SEED` is used, but do try `STRUCTURE_SEED` if the former does not work.

### Source command
Usage: `/nbc:source (run)|(as <entity>)|(positioned <position>)|(rotated <rotation>)|(in <dimension>)`.

This command is largely borrowed from [SeedMapper](https://github.com/xpple/SeedMapper). Basically, it allows you to modify the source from which the command is executed. A common use-case is changing the position from which the command is executed, or forcing the dimension when the server is using an unknown world name.

## Building from source
The same [instructions for SeedMapper](https://github.com/xpple/SeedMapper?tab=readme-ov-file#building-the-mod-locally), largely apply here too. However instead of compiling a C library, here you are compiling a Rust library to a shared library. Do not forget to use `--release` when building. Then in order to use [jextract](https://github.com/openjdk/jextract), I used [cbindgen](https://github.com/mozilla/cbindgen) to generate the C header file.
