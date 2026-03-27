package nico.kittylib.api.client.renderer.obj;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import nico.kittylib.KittyLibMain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class KittyLibObjResourceLoader implements IdentifiableResourceReloadListener {
    private static KittyLibObjResourceLoader instance;
    private final HashMap<Identifier, KittyLibObjModel> modelsMap = new HashMap<>();
    private final Identifier identifier;

    public KittyLibObjResourceLoader(Identifier id) {
        this.identifier = id;
        instance = this;

        KittyLibMain.LOGGER.info("[KittyLib] Created OBJ Resource Loader");
    }

    public synchronized static KittyLibObjResourceLoader get() {
        return instance;
    }

    public HashMap<Identifier, KittyLibObjModel> getMap() {
        return modelsMap;
    }


    @Override
    public Identifier getFabricId() {
        return this.identifier;
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        try {
            KittyLibMain.LOGGER.info("[KittyLib-Obj] Starting to load models.");

            CompletableFuture<HashMap<Identifier, KittyLibObjModel>> objModelMapFuture = CompletableFuture.supplyAsync(() -> {
                String startingPath = "models/kitty_lib_obj";
                HashMap<Identifier, KittyLibObjModel> libyObjModelHashMap = new HashMap<>();

                Map<Identifier, List<Resource>> files = manager.findAllResources(startingPath, Objects::nonNull);
                KittyLibMain.LOGGER.info("[KittyLib-Obj] Collected {} files", files.size());

                files.forEach((fileId, fileResourceList) -> {
                    if (fileId.getPath().startsWith(startingPath) && fileId.getPath().endsWith(".obj")) {
                        fileResourceList.forEach(fileResource -> {
                            libyObjModelHashMap.put(fileId, new KittyLibObjModel(fileId, fileResource));

                            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                                KittyLibMain.LOGGER.info("[KittyLib-Obj] loaded \"{}\"", fileId);
                            }
                        });
                    }
                });

                return libyObjModelHashMap;
            });

            return objModelMapFuture.thenCompose(synchronizer::whenPrepared).thenAcceptAsync(prepareData -> {
                if (prepareData != null) modelsMap.putAll(prepareData);

                KittyLibMain.LOGGER.info("[KittyLib-Obj] Put all obj models into the map.");
            });
        } catch (Exception e) {
            KittyLibMain.LOGGER.error("[KittyLib-Obj] An error occurred trying to load obj models");
            e.printStackTrace();
            return null;
        }
    }
}