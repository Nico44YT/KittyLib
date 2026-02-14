package nico.kittylib.internal.client;

import net.minecraft.item.Item;
import nico.kittylib.api.client.renderer.item.KittyLibTooltipRenderer;
import nico.kittylib.api.client.renderer.item.KittyLibTooltipRendererRegistry;

import java.util.HashMap;
import java.util.Map;

public class ImplementedTooltipRendererRegistry implements KittyLibTooltipRendererRegistry {
    private static ImplementedTooltipRendererRegistry INSTANCE;
    public static ImplementedTooltipRendererRegistry get() {
        if (INSTANCE == null) INSTANCE = new ImplementedTooltipRendererRegistry();
        return INSTANCE;
    }

    private final Map<Item, KittyLibTooltipRenderer> tooltipRenderers = new HashMap<>();

    public void registerTooltipRenderer(Item item, KittyLibTooltipRenderer.Factory renderer) {
        tooltipRenderers.put(item, renderer.apply());
    }

    public void deregisterTooltipRenderer(Item item) {
        tooltipRenderers.remove(item);
    }

    public static Map<Item, KittyLibTooltipRenderer> getTooltipRenderers() {
        return get().tooltipRenderers;
    }
}
