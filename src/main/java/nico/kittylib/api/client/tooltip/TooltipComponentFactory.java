package nico.kittylib.api.client.tooltip;

import net.minecraft.client.gui.tooltip.TooltipComponent;

public interface TooltipComponentFactory<T> {
    TooltipComponent createTooltipComponent(T data);
}
