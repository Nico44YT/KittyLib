package nico.kittylib.api.util;

import net.minecraft.util.Identifier;

public interface KittyLibIdentifierResolvable {
    default Identifier kittylib$getId() {
        return null;
    }
}
