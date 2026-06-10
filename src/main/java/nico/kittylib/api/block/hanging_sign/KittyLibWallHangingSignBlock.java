package nico.kittylib.api.block.hanging_sign;

import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.block.WoodType;

public class KittyLibWallHangingSignBlock extends WallHangingSignBlock implements KittyLibHangingSign {

    public KittyLibWallHangingSignBlock(WoodType woodType, Settings settings) {
        super(woodType, settings);
    }
}