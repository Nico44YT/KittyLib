package nico.kittylib.internal.client.injections;

import net.minecraft.client.render.model.json.Transformation;

public interface JsonModelTransformationInjections {
    default Transformation kittylib$inverse() {
        throw new UnsupportedOperationException();
    }
}
