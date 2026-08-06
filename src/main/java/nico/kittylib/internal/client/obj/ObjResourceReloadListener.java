package nico.kittylib.internal.client.obj;

import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import nico.kittylib.KittyLibMain;
import nico.kittylib.api.client.renderer.obj.BakedObjModel;
import nico.kittylib.api.client.renderer.obj.UnbakedObjModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class ObjResourceReloadListener implements SimpleResourceReloadListener<Map<Identifier, UnbakedObjModel>> {
    public static final Map<Identifier, UnbakedObjModel> UNBAKED_MODEL_MAP = new HashMap<>();
    private static final String startingPath = "models/kitty_lib_obj";

    @Override
    public CompletableFuture<Map<Identifier, UnbakedObjModel>> load(ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            long start = System.nanoTime();
            KittyLibMain.LOGGER.info("[KittyLib-Obj] Starting to load obj models.");

            profiler.push("kittylib:collecting_obj_models");
            Map<Identifier, UnbakedObjModel> unbakedObjModelMap = new HashMap<>();
            Map<Identifier, Resource> resourceMap = resourceManager.findResources(startingPath, fileId -> fileId != null && fileId.getPath().endsWith(".obj"));

            Set<String> modIds = resourceMap.keySet().stream().map(Identifier::getNamespace).collect(Collectors.toSet());
            String str = modIds.stream().collect(Collectors.joining(", "));
            profiler.swap("kittylib:reading_obj_models");

            resourceMap.forEach((fileId, resource) -> {
                Identifier cleanId = fileId.withPath(path -> path.substring(startingPath.length() + 1, path.length() - ".obj".length()));
                float[] vertexData = ObjDeserializer.toVertexData(fileId, resource);
                UnbakedObjModel unbakedObjModel = new UnbakedObjModel(cleanId, vertexData);
                unbakedObjModelMap.put(cleanId, unbakedObjModel);
            });
            profiler.pop();

            long elapsedNs = System.nanoTime() - start;
            KittyLibMain.LOGGER.info("[KittyLib-Obj] Loaded {} obj models in {} ms, across {} mods. ({})", resourceMap.size(), String.format("%.2f", elapsedNs / 1_000_000.0), modIds.size(), str);

            return unbakedObjModelMap;
        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Map<Identifier, UnbakedObjModel> modelMap, ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            UNBAKED_MODEL_MAP.clear();
            UNBAKED_MODEL_MAP.putAll(modelMap);
            KittyLibMain.LOGGER.info("[KittyLib-Obj] Loaded all unbaked obj models.");

            BakedObjModel.BAKED_MODELS.clear();

            UNBAKED_MODEL_MAP.forEach((modelId, model) -> {
                ObjBaker.bake(model, BakedObjModel.BAKED_MODELS);
            });

        }, executor);
    }

    @Override
    public Identifier getFabricId() {
        return KittyLibMain.id("obj_resource_reload_listener");
    }
}
