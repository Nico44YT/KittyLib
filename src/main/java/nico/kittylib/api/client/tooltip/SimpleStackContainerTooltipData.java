package nico.kittylib.api.client.tooltip;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record SimpleStackContainerTooltipData(ItemStack stack, @Nullable Identifier overlay) implements TooltipData, TooltipComponentFactory<SimpleStackContainerTooltipData> {
    @Override
    public TooltipComponent createTooltipComponent(SimpleStackContainerTooltipData data) {
        return new SimpleStackContainerTooltip(data, overlay);
    }
}
