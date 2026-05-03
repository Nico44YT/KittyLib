package nico.kittylib.internal.client.open_format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.render.model.json.*;
import net.minecraft.util.JsonHelper;
import nico.kittylib.internal.client.open_format.deserializer.*;

import java.io.Reader;
import java.io.StringReader;

public class KittyLibJsonUnbakedModelDeserializer extends JsonUnbakedModel.Deserializer {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter((JsonUnbakedModel.class), new KittyLibJsonUnbakedModelDeserializer())
            .registerTypeAdapter((ModelElement.class), new KittyLibModelElementDeserializer())
            .registerTypeAdapter((ModelElementFace.class), new KittyLibModelElementFaceDeserializer())
            .registerTypeAdapter((ModelElementTexture.class), new KittyLibModelElementTextureDeserializer())
            .registerTypeAdapter((Transformation.class), new KittyLibTransformationDeserializer())
            .registerTypeAdapter((ModelTransformation.class), new KittyLibModelTransformationDeserializer())
            .registerTypeAdapter((ModelOverride.class), new KittyLibModelOverrideDeserializer())
            .create();

    public static JsonUnbakedModel deserialize(Reader input) {
        return JsonHelper.deserialize(GSON, input, JsonUnbakedModel.class);
    }

    public static JsonUnbakedModel deserialize(String json) {
        return KittyLibJsonUnbakedModelDeserializer.deserialize(new StringReader(json));
    }

    public KittyLibJsonUnbakedModelDeserializer() {
        super();
    }
}