package nico.kittylib.internal.client.open_format;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import nico.kittylib.api.KittyLibConstants;

import java.lang.reflect.Type;

public class LibyModelVariantDeserializer extends ModelVariant.Deserializer {

    @Override
    public ModelVariant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Identifier identifier = this.deserializeModel(jsonObject);

        KittyLibConstants.kittyLibModelRotationUsers.add(identifier);

        KittyLibFreeFormRotation modelRotation = KittyLibFreeFormRotation.deserializeRotation(jsonObject);

        boolean uvLock = JsonHelper.getBoolean(jsonObject, "uvlock", false);
        int weight = this.deserializeWeight(jsonObject);

        return new ModelVariant(identifier, modelRotation.getAffineTransformation(), uvLock, weight);
    }
}