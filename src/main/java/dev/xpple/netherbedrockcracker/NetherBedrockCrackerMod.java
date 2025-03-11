package dev.xpple.netherbedrockcracker;

import com.mojang.brigadier.CommandDispatcher;
import dev.xpple.netherbedrockcracker.command.commands.CrackCommand;
import dev.xpple.netherbedrockcracker.command.commands.SourceCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class NetherBedrockCrackerMod implements ClientModInitializer {

    public static final String MOD_ID = "netherbedrockcracker";

    static {
        String libraryName = System.mapLibraryName("libbedrockcracker");
        String extension = FilenameUtils.getExtension(libraryName);
        libraryName = "libbedrockcracker" + '.' + extension;
        URL libraryPath = NetherBedrockCrackerMod.class.getClassLoader().getResource(libraryName);
        Path libcubiomes;
        try {
            libcubiomes = Files.createTempFile("libbedrockcracker", '.' + extension);
            IOUtils.copy(libraryPath, libcubiomes.toFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.load(libcubiomes.toAbsolutePath().toString());
    }

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(NetherBedrockCrackerMod::registerCommands);
    }

    private static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext buildContext) {
        CrackCommand.register(dispatcher);
        SourceCommand.register(dispatcher);
    }
}
