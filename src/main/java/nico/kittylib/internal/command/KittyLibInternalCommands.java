package nico.kittylib.internal.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class KittyLibInternalCommands {
    public static void registerAll(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) dispatcher.register(NbtCommand.create());
    }
}
