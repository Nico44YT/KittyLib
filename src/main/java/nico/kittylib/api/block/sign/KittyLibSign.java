package nico.kittylib.api.block.sign;

import nico.kittylib.api.util.KittyLibIdentifier;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public interface KittyLibSign {
    default Identifier getTexture() {
        KittyLibIdentifier id = KittyLibIdentifier.tryParseOrDefault(getWoodType().name(), Identifier.DEFAULT_NAMESPACE);
        return Identifier.tryParse(id.getNamespace(), "entity/sign/" + id.getPath());
    }

    WoodType getWoodType();
}