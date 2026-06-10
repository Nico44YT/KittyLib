package nico.kittylib.api.block.sign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;

public class KittyLibWallSignBlock extends WallSignBlock implements KittyLibSign {

    public KittyLibWallSignBlock(WoodType woodType, Settings settings) {
        super(woodType, settings);
    }
}