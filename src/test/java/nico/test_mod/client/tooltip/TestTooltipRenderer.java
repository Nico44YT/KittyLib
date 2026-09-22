package nico.test_mod.client.tooltip;

import net.minecraft.item.ItemStack;
import nico.kittylib.api.client.renderer.KittyLibDrawContext;
import nico.kittylib.api.client.renderer.item.KittyLibTooltipRenderer;

public class TestTooltipRenderer implements KittyLibTooltipRenderer {
    public TestTooltipRenderer() {

    }

    @Override
    public void render(ItemStack stack, KittyLibDrawContext context, int x, int y, int width, int height, int z) {
        context.fill(x, y, x + width, y + height, 0xFF_FF_FF_FF);
    }
}
