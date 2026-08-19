package nico.kittylib.api.client.renderer;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public interface ModelRegistrationEvent {
    @ApiStatus.Internal
    final int DEFAULT_PRIORITY = 1000;
    @ApiStatus.Internal
    final List<Entry> entries = new LinkedList<>();

    static void register(Consumer<List<Identifier>> modelList) {
        entries.add(new Entry(DEFAULT_PRIORITY, modelList));
    }

    static void register(int priority, Consumer<List<Identifier>> modelList) {
        entries.add(new Entry(priority, modelList));
    }

    @ApiStatus.Internal
    record Entry(int priority, Consumer<List<Identifier>> modelList) {

    }
}
