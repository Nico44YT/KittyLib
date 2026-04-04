package nico.kittylib.api.block.interactable;

import net.minecraft.util.math.Vec3d;

public class InteractableRadiusEntry extends InteractableEntry {

    private final Vec3d centralPos;
    private final double radius;

    public InteractableRadiusEntry(Vec3d centralPos, double radius, InteractableAction action) {
        super(action);

        this.centralPos = centralPos;
        this.radius = radius;
    }

    @Override
    public boolean canInteract(Vec3d hitPos) {
        return hitPos.distanceTo(this.centralPos) <= this.radius;
    }
}
