package nico.test_mod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import nico.kittylib.api.client.renderer.obj.KittyLibObjModel;
import nico.test_mod.TestMod;

import java.util.function.Supplier;

public class TestModClient implements ClientModInitializer {

    public static final Supplier<KittyLibObjModel> MODEL = KittyLibObjModel.get(TestMod.id("models/kitty_lib_obj/monkey.obj"));
    public static final Identifier TEXTURE = Identifier.of("test_mod", "textures/block/monkey.png");

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.BEFORE_ENTITIES.register((context) -> {
            KittyLibObjModel model = MODEL.get();
            if (model == null) return;

            MatrixStack matrices = context.matrixStack();
            VertexConsumerProvider consumers = context.consumers();

            Camera camera = context.camera();

            matrices.push();

            // Translate to world position (0, 0, 0)
            matrices.translate(
                    -camera.getPos().x,
                    -camera.getPos().y,
                    -camera.getPos().z
            );

            int size = 3;

            for(int x = 0;x<size;x++) {
                for(int y = 0;y<size;y++) {
                    for(int z = 0;z<size;z++) {
                        matrices.push();

                        matrices.translate(x, y, z);

                        model.render(
                                consumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE)),
                                matrices,
                                0xF000F0,
                                OverlayTexture.DEFAULT_UV
                        );

                        matrices.pop();
                    }
                }
            }

            matrices.pop();
        });
    }
}
