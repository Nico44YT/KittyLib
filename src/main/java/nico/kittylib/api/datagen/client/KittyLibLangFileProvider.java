package nico.kittylib.api.datagen.client;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public abstract class KittyLibLangFileProvider implements DataProvider {
    protected final DataOutput.PathResolver langFolderResolver;
    protected final String id;

    protected final Map<String, LanguageKeyCollector> collectors;

    public KittyLibLangFileProvider(String id, FabricDataOutput output) {
        this.id = id;
        this.langFolderResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "lang/");
        this.collectors = new LinkedHashMap<>();
    }

    public LanguageKeyCollector create(String languageId) {
        return this.collectors.computeIfAbsent(languageId, LanguageKeyCollector::of);
    }

    public LanguageKeyCollector createDefault() {
        return this.collectors.computeIfAbsent(LanguageManager.DEFAULT_LANGUAGE_CODE, LanguageKeyCollector::of);
    }

    public abstract void collect();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        collect();

        List<CompletableFuture<?>> futures = new ArrayList<>();

        this.collectors.forEach((languageId, collector) -> {
            futures.add(DataProvider.writeToPath(writer, collector.toJson(), langFolderResolver.resolve(Identifier.of(id, languageId), "kittylib.json")));
        });

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "kittlib:lang/" + id;
    }

    public static class LanguageKeyCollector {
        final String id;
        final Map<String, String> keys;

        private LanguageKeyCollector(String languageId) {
            this.id = languageId;
            this.keys = new LinkedHashMap<>();
        }

        public static LanguageKeyCollector of(String languageId) {
            return new LanguageKeyCollector(languageId);
        }

        public static LanguageKeyCollector ofDefault() {
            return new LanguageKeyCollector(LanguageManager.DEFAULT_LANGUAGE_CODE);
        }

        public LanguageKeyCollector put(String key, String value) {
            keys.put(key, value);
            return this;
        }

        public Map<String, String> collect() {
            return keys;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            for (Map.Entry<String, String> entry : this.keys.entrySet()) {
                json.addProperty(entry.getKey(), entry.getValue());
            }

            return json;
        }
    }
}
