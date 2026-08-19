package nico.kittylib.api.block.hanging_sign;

import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public interface KittyLibHangingSign {
    default Identifier getTexture() {
        Identifier id = Identifier.tryParse(getWoodType().name());
        if (id == null) id = Identifier.tryParse(Identifier.DEFAULT_NAMESPACE + ":" + getWoodType().name());
        return Identifier.of(id.getNamespace(), "entity/sign/" + id.getPath());
    }

    WoodType getWoodType();
}