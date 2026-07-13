package nico.kittylib.api.client.renderer;

public record UVRegion(double minU, double minV, double maxU, double maxV) {
    public static UVRegion of(double minU, double minV, double maxU, double maxV) {
        return new UVRegion(minU, minV, maxU, maxV);
    }

    public static UVRegion of(double textureSize, double startX, double startY, double endX, double endY) {
        return UVRegion.of(
                startX / textureSize,
                startY / textureSize,
                endX / textureSize,
                endY / textureSize
        );
    }

    public static UVRegion ofSized(double textureSize, double x, double y, double sizeX, double sizeY) {
        return of(textureSize, x, y, x + sizeX, y + sizeY);
    }

    public UVRegion move(double x, double y) {
        return UVRegion.of(
                minU + x,
                minV + y,
                maxU + x,
                maxV + y
        );
    }

    public UVRegion move(double textureSize, double x, double y) {
        return move(x / textureSize, y / textureSize);
    }

    public UVRegion scale(double scaleX, double scaleY) {
        return UVRegion.of(
                minU * scaleX,
                minV * scaleY,
                maxU * scaleX,
                maxV * scaleY
        );
    }

    public UVRegion scale(double textureSize, double scaleX, double scaleY) {
        return scale(scaleX / textureSize, scaleY / textureSize);
    }

    public UVRegion scaleFromTextureCenter(double scaleX, double scaleY) {
        return move(0.5, 0.5).scale(scaleX, scaleY).move(-0.5, -0.5);
    }

    public UVRegion scaleFromRegionCenter(double scaleX, double scaleY) {
        double centerU = (minU + maxU) * 0.5;
        double centerV = (minV + maxV) * 0.5;

        return UVRegion.of(
                centerU + (minU - centerU) * scaleX,
                centerV + (minV - centerV) * scaleY,
                centerU + (maxU - centerU) * scaleX,
                centerV + (maxV - centerV) * scaleY
        );
    }

    public UVRegion inset(double amountX, double amountY) {
        return UVRegion.of(
                minU + amountX,
                minV + amountY,
                maxU - amountX,
                maxV - amountY
        );
    }

    public UVRegion inset(double textureSize, double amountX, double amountY) {
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

    public double width() {
        return maxU - minU;
    }

    public double height() {
        return maxV - minV;
    }

    public double centerU() {
        return (minU + maxU) * 0.5;
    }

    public double centerV() {
        return (minV + maxV) * 0.5;
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
