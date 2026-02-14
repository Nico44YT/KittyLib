package nico.kittylib.api.block.sign;

import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;

public class KittyLibSignBlock extends SignBlock implements KittyLibSign {

    public KittyLibSignBlock(Settings settings, WoodType woodType) {
        super(settings, woodType);
    }
}