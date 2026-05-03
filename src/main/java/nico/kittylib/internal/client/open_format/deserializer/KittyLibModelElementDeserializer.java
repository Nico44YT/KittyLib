package nico.kittylib.internal.client.open_format.deserializer;

import com.google.gson.*;
import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelRotation;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;
import nico.kittylib.internal.client.open_format.KittyLibFreeFormRotation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.lang.reflect.Type;
import java.util.Map;

public class KittyLibModelElementDeserializer extends ModelElement.Deserializer {
    public KittyLibModelElementDeserializer() {
        super();
    }

    @Override
    public ModelElement deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject elementObject = jsonElement.getAsJsonObject();
        Vector3f from = this.deserializeVec3f(elementObject, "from");
        Vector3f to = this.deserializeVec3f(elementObject, "to");
        Map<Direction, ModelElementFace> facesMap = this.deserializeFacesValidating(jsonDeserializationContext, elementObject);

        ModelRotation modelRotation = this.KittyLib$deserializeRotation(elementObject);

        boolean shade = !elementObject.has("shade") || elementObject.get("shade").getAsBoolean();

        return new ModelElement(from, to, facesMap, modelRotation, shade);
    }

    @Nullable
    public ModelRotation KittyLib$deserializeRotation(JsonObject object) {
        ModelRotation modelRotation = new ModelRotation(new Vector3f(), null, 0, false);
        if (object.has("rotation")) {
            //Just don't ask why it wants the full path idk
            KittyLibFreeFormRotation KittyLibFreeFormRotation = nico.kittylib.internal.client.open_format.KittyLibFreeFormRotation.deserializeRotation(object.getAsJsonObject());

            KittyLibFreeFormRotation.getOrigin().mul(1/16f);

            modelRotation = new ModelRotation(new Vector3f(), null, 0, false);
            modelRotation.kittyLib$setFreeFormRotation(KittyLibFreeFormRotation);
        }

        return modelRotation;
    }

    private Vector3f deserializeVec3f(JsonObject object, String name) {
        JsonArray jsonArray = JsonHelper.getArray(object, name);
        if (jsonArray.size() != 3) {
            throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonArray.size());
        } else {
            float[] fs = new float[3];

            for(int i = 0; i < fs.length; ++i) {
                fs[i] = JsonHelper.asFloat(jsonArray.get(i), name + "[" + i + "]");
            }

            return new Vector3f(fs[0], fs[1], fs[2]);
        }
    }

    private Map<Direction, ModelElementFace> deserializeFacesValidating(JsonDeserializationContext context, JsonObject object) {
        Map<Direction, ModelElementFace> map = this.deserializeFaces(context, object);
        if (map.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
        } else {
            return map;
        }
    }

}