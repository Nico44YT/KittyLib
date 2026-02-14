package nico.kittylib.api.util;

import net.minecraft.util.Identifier;

public class KittyLibIdentifier extends Identifier {
    public KittyLibIdentifier(String id) {
        super(id);
    }

    public KittyLibIdentifier(String namespace, String path) {
        super(namespace, path);
    }

    public KittyLibIdentifier(Identifier id) {
        this(id.getNamespace(), id.getPath());
    }

    public static Identifier of(Identifier id) {
        return new KittyLibIdentifier(id);
    }

    public static KittyLibIdentifier of(String namespace, String path) {
        return new KittyLibIdentifier(namespace, path);
    }

    public static KittyLibIdentifier ofVanilla(String path) {
        return new KittyLibIdentifier(Identifier.DEFAULT_NAMESPACE, path);
    }

    public static KittyLibIdentifier ofRealms(String path) {
        return new KittyLibIdentifier(Identifier.REALMS_NAMESPACE, path);
    }

    public static KittyLibIdentifier tryParseOrDefault(String id, String defaultNamespace) {
        String[] parts = id.split(":");
        if(parts.length >= 2) return KittyLibIdentifier.of(parts[0], parts[1]);
        return KittyLibIdentifier.of(defaultNamespace, parts[0]);
    }

    public KittyLibIdentifier append(String namespace, String path) {
        return new KittyLibIdentifier(this.getNamespace() + namespace, this.getPath() + path);
    }

    public KittyLibIdentifier appendPath(String path) {
        return new KittyLibIdentifier(this.getNamespace(), this.getPath() + path);
    }

    public KittyLibIdentifier appendNamespace(String namespace) {
        return new KittyLibIdentifier(this.getNamespace() + namespace, this.getPath());
    }

    public KittyLibIdentifier prepend(String namespace, String path) {
        return new KittyLibIdentifier(namespace + this.getNamespace(), path + this.getPath());
    }

    public KittyLibIdentifier prependPath(String path) {
        return new KittyLibIdentifier(this.getNamespace(), path + this.getPath());
    }

    public KittyLibIdentifier prependNamespace(String namespace) {
        return new KittyLibIdentifier(namespace + this.getNamespace(), this.getPath());
    }

    public Identifier toId() {
        return this;
    }
}