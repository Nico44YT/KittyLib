package nico.kittylib.mixin.littable_block;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(DispenserBlock.class)
public interface DispenserBlockAccessor {
    @Accessor("BEHAVIORS")
    static Map<Item, DispenserBehavior> kittylib$getBehaviors() {
        return null;
    }
}
