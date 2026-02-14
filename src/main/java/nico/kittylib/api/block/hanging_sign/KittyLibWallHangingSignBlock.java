package nico.kittylib.api.block.hanging_sign;

import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.block.WoodType;

public class KittyLibWallHangingSignBlock extends WallHangingSignBlock implements KittyLibHangingSign {

    public KittyLibWallHangingSignBlock(Settings settings, WoodType woodType) {
        super(settings, woodType);
    }
}