package nico.kittylib.api.screenshake;

import nico.kittylib.api.nbt.NbtConvertible;
import nico.kittylib.api.util.KittyLibEasing;
import net.minecraft.nbt.NbtCompound;

public class KittyLibScreenshakeInstance implements NbtConvertible {
    public int progress;
    public int MAX_DURATION;

    public float intensityX, intensityY, intensityZ;
    public KittyLibEasing intensityCurveStartEasing;
    public KittyLibEasing intensityCurveEndEasing;

    private KittyLibScreenshakeInstance(int duration) {
        this.MAX_DURATION = duration;
        this.progress = 0;

        this.intensityX = 1;
        this.intensityY = 1;
        this.intensityZ = 1;

        this.intensityCurveStartEasing = KittyLibEasing.LINEAR;
        this.intensityCurveEndEasing = KittyLibEasing.LINEAR;
    }

    public KittyLibScreenshakeInstance setIntensity(float intensity) {
        return this.setIntensity(intensity, intensity, intensity);
    }

    public KittyLibScreenshakeInstance setIntensity(float x, float y, float z) {
        this.intensityX = x;
        this.intensityY = y;
        this.intensityZ = z;
        return this;
    }

    @Override
    public void writeNbt(NbtCompound nbtCompound) {
        nbtCompound.putInt("progress", this.progress);
        nbtCompound.putInt("duration", this.MAX_DURATION);

        nbtCompound.putFloat("intensity_x", this.intensityX);
        nbtCompound.putFloat("intensity_y", this.intensityY);
        nbtCompound.putFloat("intensity_z", this.intensityZ);

        nbtCompound.putString("easing_start", this.intensityCurveStartEasing.name);
        nbtCompound.putString("easing_end", this.intensityCurveEndEasing.name);
    }

    @Override
    public void readNbt(NbtCompound nbtCompound) {
        this.progress = nbtCompound.getInt("progress");
        this.MAX_DURATION = nbtCompound.getInt("duration");

        this.intensityX = nbtCompound.getFloat("intensity_x");
        this.intensityY = nbtCompound.getFloat("intensity_y");
        this.intensityZ = nbtCompound.getFloat("intensity_z");

        this.intensityCurveStartEasing = KittyLibEasing.valueOf(nbtCompound.getString("easing_start"));
        this.intensityCurveEndEasing = KittyLibEasing.valueOf(nbtCompound.getString("easing_end"));
    }

    public NbtCompound toNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        writeNbt(nbtCompound);
        return nbtCompound;
    }

    public static KittyLibScreenshakeInstance fromNbt(NbtCompound nbtCompound) {
        KittyLibScreenshakeInstance instance = new KittyLibScreenshakeInstance(0);
        instance.readNbt(nbtCompound);
        return instance;
    }
}
