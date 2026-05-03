package nico.kittylib.mixin.client.open_format;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.JsonHelper;
import nico.kittylib.api.KittyLibConstants;
import nico.kittylib.internal.client.open_format.KittyLibJsonUnbakedModelDeserializer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Collectors;

@Mixin(JsonUnbakedModel.class)
public abstract class JsonUnbakedModelMixin {
    @Shadow
    public String id;


    /*
    /// T is of type JsonUnbakedModel
    @SuppressWarnings("unchecked")
    @WrapOperation(method = "deserialize(Ljava/io/Reader;)Lnet/minecraft/client/render/model/json/JsonUnbakedModel;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/JsonHelper;deserialize(Lcom/google/gson/Gson;Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;"))
    private static <T> T kittyLib$deserializeModel(Gson gson, Reader reader, Class<T> type, Operation<T> original) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        JsonElement jsonElement = JsonParser.parseString(bufferedReader.lines().collect(Collectors.joining()));

        if (kittylib$checkFormat(jsonElement)) {
            return (T)KittyLibJsonUnbakedModelDeserializer.deserialize(jsonElement.toString());
        }

        return original.call(gson, new BufferedReader(reader), type);
    }
*/

    @Shadow
    @Final
    private static Gson GSON;

    @Inject(method = "deserialize(Ljava/io/Reader;)Lnet/minecraft/client/render/model/json/JsonUnbakedModel;", at = @At("HEAD"), cancellable = true)
    private static void liby$deserializeModel(Reader reader, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        JsonElement jsonElement = JsonParser.parseString(bufferedReader.lines().collect(Collectors.joining()));

        if (kittylib$checkFormat(jsonElement)) {
            cir.setReturnValue(KittyLibJsonUnbakedModelDeserializer.deserialize(jsonElement.toString()));
            return;
        }

        cir.setReturnValue(JsonHelper.deserialize(GSON, jsonElement.toString(), JsonUnbakedModel.class));
    }


    @Inject(method = "deserialize(Ljava/lang/String;)Lnet/minecraft/client/render/model/json/JsonUnbakedModel;", at = @At("HEAD"), cancellable = true)
    private static void liby$deserializeModel(String json, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        JsonElement jsonElement = JsonParser.parseString(json);

        System.out.println(jsonElement);

        if (kittylib$checkFormat(jsonElement)) {
            cir.setReturnValue(KittyLibJsonUnbakedModelDeserializer.deserialize(jsonElement.toString()));
            return;
        }
    }

    @Unique
    private static boolean kittylib$checkFormat(JsonElement jsonElement) {
        if (jsonElement instanceof JsonObject jsonObject) {
            if (jsonObject.has("format_version") && jsonObject.get("format_version").isJsonPrimitive() && jsonObject.get("format_version").getAsJsonPrimitive().isString()) {
                String format = jsonObject.get("format_version").getAsString();
                return format.equals(KittyLibConstants.OPEN_FORMAT);
            }
        }

        return false;
    }
}