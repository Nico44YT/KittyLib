package nico.kittylib.api.block.hanging_sign;

import net.minecraft.block.HangingSignBlock;
import net.minecraft.block.WoodType;

public class KittyLibHangingSignBlock extends HangingSignBlock implements KittyLibHangingSign {

    public KittyLibHangingSignBlock(Settings settings, WoodType woodType) {
        super(settings, woodType);
    }
}