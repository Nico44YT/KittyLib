package nico.kittylib.api.client.renderer;

public record UVRegion(float minU, float minV, float maxU, float maxV) {
    public static UVRegion of(float minU, float minV, float maxU, float maxV) {
        return new UVRegion(minU, minV, maxU, maxV);
    }

    public static UVRegion of(float textureSize, float startX, float startY, float endX, float endY) {
        return UVRegion.of(
                startX / textureSize,
                startY / textureSize,
                endX / textureSize,
                endY / textureSize
        );
    }

    public static UVRegion ofSized(float textureSize, float x, float y, float sizeX, float sizeY) {
        return of(textureSize, x, y, x + sizeX, y + sizeY);
    }

    public UVRegion move(float x, float y) {
        return UVRegion.of(
                minU + x,
                minV + y,
                maxU + x,
                maxV + y
        );
    }

    public UVRegion move(float textureSize, float x, float y) {
        return move(x / textureSize, y / textureSize);
    }

    public UVRegion scale(float scaleX, float scaleY) {
        return UVRegion.of(
                minU * scaleX,
                minV * scaleY,
                maxU * scaleX,
                maxV * scaleY
        );
    }

    public UVRegion scale(float textureSize, float scaleX, float scaleY) {
        return scale(scaleX / textureSize, scaleY / textureSize);
    }

    public UVRegion scaleFromTextureCenter(float scaleX, float scaleY) {
        return move(0.5f, 0.5f).scale(scaleX, scaleY).move(-0.5f, -0.5f);
    }

    public UVRegion scaleFromRegionCenter(float scaleX, float scaleY) {
        float centerU = (minU + maxU) * 0.5f;
        float centerV = (minV + maxV) * 0.5f;

        return UVRegion.of(
                centerU + (minU - centerU) * scaleX,
                centerV + (minV - centerV) * scaleY,
                centerU + (maxU - centerU) * scaleX,
                centerV + (maxV - centerV) * scaleY
        );
    }

    public UVRegion inset(float amountX, float amountY) {
        return UVRegion.of(
                minU + amountX,
                minV + amountY,
                maxU - amountX,
                maxV - amountY
        );
    }

    public UVRegion inset(float textureSize, float amountX, float amountY) {
        return inset(amountX / textureSize, amountY / textureSize);
    }

    public UVRegion flipU() {
        return UVRegion.of(
                1 - maxU,
                minV,
                1 - minU,
                maxV
        );
    }

    public UVRegion flipV() {
        return UVRegion.of(
                minU,
                1 - maxV,
                maxU,
                1 - minV
        );
    }

    public float width() {
        return maxU - minU;
    }

    public float height() {
        return maxV - minV;
    }

    public float centerU() {
        return (minU + maxU) * 0.5f;
    }

    public float centerV() {
        return (minV + maxV) * 0.5f;
    }

    public UVRegion normalize() {
        return UVRegion.of(
                Math.min(minU, maxU),
                Math.min(minV, maxV),
                Math.max(minU, maxU),
                Math.max(minV, maxV)
        );
    }
}
