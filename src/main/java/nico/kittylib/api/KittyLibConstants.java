package nico.kittylib.api;

import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class KittyLibConstants {
    public static final List<Identifier> kittyLibModelRotationUsers = new LinkedList<>();

    public static final String OPEN_FORMAT = "kittylib:open_format";
    public static final Identifier OPEN_FORMAT_ID = Identifier.tryParse(OPEN_FORMAT);
}
