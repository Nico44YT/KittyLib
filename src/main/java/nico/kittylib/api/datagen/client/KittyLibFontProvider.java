package nico.kittylib.api.datagen.client;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class KittyLibFontProvider implements DataProvider {
    protected final DataOutput.PathResolver fontFolderPathResolver;
    protected final String id;
    protected final Map<Identifier, JsonObject> fontFiles;

    public KittyLibFontProvider(String id, FabricDataOutput output) {
        this.id = id;
        this.fontFolderPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "");
        this.fontFiles = new HashMap<>();
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        var array = fontFiles.entrySet().stream().map(entry -> {
            return DataProvider.writeToPath(writer, entry.getValue(), fontFolderPathResolver.resolve(entry.getKey().withPath(path -> "fonts/" + path), "json"));
        }).toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(array);
    }

    @Override
    public String getName() {
        return "kittylib:font/" + id;
    }

    public static class FontFileBuilder {

    }
}
