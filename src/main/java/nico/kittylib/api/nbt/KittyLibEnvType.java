package nico.kittylib.api.nbt;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public enum KittyLibEnvType {
    CLIENT(EnvType.CLIENT),
    SERVER(EnvType.SERVER),
    COMMON(null);

    final EnvType envType;
    KittyLibEnvType(EnvType envType)  {
        this.envType = envType;
    }

    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }
}
