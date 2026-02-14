package nico.kittylib.api.block.hanging_sign;

import nico.kittylib.api.util.KittyLibIdentifier;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public interface KittyLibHangingSign {
    default Identifier getTexture() {
        KittyLibIdentifier id = KittyLibIdentifier.tryParseOrDefault(getWoodType().name(), Identifier.DEFAULT_NAMESPACE);
        return Identifier.of(id.getNamespace(), "entity/sign/" + id.getPath());
    }

    WoodType getWoodType();
}