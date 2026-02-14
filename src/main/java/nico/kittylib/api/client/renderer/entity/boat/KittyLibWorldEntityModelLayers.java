package nico.kittylib.api.client.renderer.entity.boat;

import nico.kittylib.api.entity.boat.KittyLibBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class KittyLibWorldEntityModelLayers {
    public static EntityModelLayer createRaft(KittyLibBoatEntity.KittyLibBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "raft/" + type.id().getPath()), "main");
    }

    public static EntityModelLayer createChestRaft(KittyLibBoatEntity.KittyLibBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "chest_raft/" + type.id().getPath()), "main");
    }

    public static EntityModelLayer createBoat(KittyLibBoatEntity.KittyLibBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "boat/" + type.id().getPath()), "main");
    }

    public static EntityModelLayer createChestBoat(KittyLibBoatEntity.KittyLibBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "chest_boat/" + type.id().getPath()), "main");
    }

    private static EntityModelLayer create(Identifier id, String layer) {
        return new EntityModelLayer(id, layer);
    }
}