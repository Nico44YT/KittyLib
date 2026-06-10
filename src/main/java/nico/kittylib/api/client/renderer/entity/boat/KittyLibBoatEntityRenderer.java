package nico.kittylib.api.client.renderer.entity.boat;

import nico.kittylib.api.entity.boat.KittyLibBoat;
import nico.kittylib.api.entity.boat.KittyLibBoatEntity;
import nico.kittylib.api.entity.boat.KittyLibChestBoatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class KittyLibBoatEntityRenderer extends BoatEntityRenderer {

    public KittyLibBoatEntityRenderer(EntityRendererFactory.Context ctx, boolean chest) {
        super(ctx, chest);
    }

    public static Identifier getTexture(KittyLibBoatEntity.KittyLibBoatType type, boolean chest) {
        String namespace = type.id().getNamespace();
        String path = "textures/entity/";
        String file = type.id().getPath();
        String chestStr = chest ? "chest_boat/" : "boat/";

        return Identifier.tryParse(namespace,path + chestStr + file + ".png");
    }

    @Override
    public Identifier getTexture(BoatEntity boatEntity) {
        if(boatEntity instanceof KittyLibBoat boat) {
            return getTexture(boat.getBoatVariant(), boat instanceof KittyLibChestBoatEntity);
        }
        throw new RuntimeException("[KittyLib] BoatEntityRenderer registered for non KittyLibBoat" + boatEntity.getClass());
    }

    public CompositeEntityModel<BoatEntity> createModel(EntityRendererFactory.Context context, KittyLibBoat boat, boolean chest) {
        EntityModelLayer entityModelLayer = chest ? EntityModelLayers.createChestBoat(BoatEntity.Type.OAK) : EntityModelLayers.createBoat(BoatEntity.Type.OAK);
        ModelPart modelPart = context.getPart(entityModelLayer);
        if (boat.getBoatVariant().raft()) {
            return chest ? new ChestRaftEntityModel(modelPart) : new RaftEntityModel(modelPart);
        } else {
            return chest ? new ChestBoatEntityModel(modelPart) : new BoatEntityModel(modelPart);
        }
    }
}