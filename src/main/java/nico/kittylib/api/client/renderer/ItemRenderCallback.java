package nico.kittylib.api.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public interface ItemRenderCallback {
    /**
     *
     * @param stack the item stack
     * @return A render callback
     */
    @Environment(EnvType.CLIENT)
    RenderCallback getRenderCallback(ItemStack stack);

    @FunctionalInterface
    interface RenderCallback {
        /**
         *
         * @param stack
         * @param renderMode
         * @param leftHanded
         * @param matrices
         * @param vertexConsumers
         * @param light
         * @param overlay
         * @param model
         */
        void render(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);
    }
}