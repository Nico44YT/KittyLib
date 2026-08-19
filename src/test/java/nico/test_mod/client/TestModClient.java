package nico.test_mod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionTypes;
import nico.kittylib.api.client.renderer.ModelRegistrationEvent;
import nico.kittylib.api.client.renderer.obj.UnbakedObjModel;
import nico.test_mod.TestMod;

import java.util.function.Supplier;

public class TestModClient implements ClientModInitializer {
    public static final Supplier<UnbakedObjModel> UNBAKED_MODEL = UnbakedObjModel.get(TestMod.id("cube"));
    public static final Identifier TEXTURE = Identifier.of("test_mod", "textures/block/cube.png");
    public static final Identifier TEXTURE_NORMAL = Identifier.of("test_mod", "textures/block/cube_n.png");

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.BEFORE_ENTITIES.register((context) -> {
            UnbakedObjModel unbakedObjModel = UNBAKED_MODEL.get();

            MatrixStack matrices = context.matrixStack();
            Camera camera = context.camera();

            matrices.push();

            // Translate to world position (0, 0, 0)
            matrices.translate(
                    -camera.getPos().x,
                    -camera.getPos().y,
                    -camera.getPos().z
            );

            matrices.translate(0.5, 0, 0.5);

            unbakedObjModel.render(context.consumers(), TEXTURE, TEXTURE_NORMAL,
                    matrices, 0xFF_FF_FF_FF,
                    WorldRenderer.getLightmapCoordinates(context.world(), BlockPos.ORIGIN),
                    OverlayTexture.DEFAULT_UV
            );

            matrices.pop();
        });
    }
}
