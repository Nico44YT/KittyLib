package nico.kittylib.api.block.interactable;

import net.minecraft.util.math.Vec3d;

public class InteractableZoneEntry extends InteractableEntry {

    private final Vec3d corner1;
    private final Vec3d corner2;

    protected InteractableZoneEntry(Vec3d corner1, Vec3d corner2, InteractableAction action) {
        super(action);

        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    @Override
    public boolean canInteract(Vec3d hitPos) {
        return corner1.getX() <= hitPos.getX() && corner2.getX() >= hitPos.getX() &&
                corner1.getZ() <= hitPos.getZ() && corner2.getZ() >= hitPos.getZ() &&
                corner1.getY() <= hitPos.getY() && corner2.getY() >= hitPos.getY();
    }
}
