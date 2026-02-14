package nico.kittylib.api.client.renderer.item;

import net.minecraft.item.Item;
import nico.kittylib.internal.client.ImplementedTooltipRendererRegistry;

public interface KittyLibTooltipRendererRegistry {
    static KittyLibTooltipRendererRegistry getInstance() {
        return ImplementedTooltipRendererRegistry.get();
    }

    void registerTooltipRenderer(Item item, KittyLibTooltipRenderer.Factory renderer);
    void deregisterTooltipRenderer(Item item);
}
