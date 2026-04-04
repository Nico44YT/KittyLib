package nico.kittylib.api.block.interactable;

import net.minecraft.util.math.Vec3d;

public abstract class InteractableEntry {

    private final InteractableAction action;

    protected InteractableEntry(InteractableAction action) {
        this.action = action;
    }

    public InteractableAction getAction() {
        return this.action;
    }

    public abstract boolean canInteract(Vec3d hitPos);
}
