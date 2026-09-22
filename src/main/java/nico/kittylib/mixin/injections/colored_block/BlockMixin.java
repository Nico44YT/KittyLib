package nico.kittylib.mixin.injections.colored_block;

import net.minecraft.block.Block;
import net.minecraft.util.DyeColor;
import nico.kittylib.api.color.DyeColored;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements DyeColored {
    @Override
    public DyeColor getColor() {
        return null;
    }
}
