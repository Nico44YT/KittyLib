package nico.kittylib.api.client.renderer.obj;

import net.minecraft.resource.Resource;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import nico.kittylib.api.client.renderer.KittyLibFace;
import nico.kittylib.api.client.renderer.KittyLibTriangleData;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class KittyLibObjDeserializer {
    public static List<KittyLibFace> objToFaceList(Resource resource) {
        try {
            List<Vector3f> vertices = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<Vector2f> texCoords = new ArrayList<>();
            List<KittyLibFace> faces = new ArrayList<>();

            List<String> lines = resource.getReader().lines().toList();

            lines.forEach(line -> {
                String[] parts = line.split(" ");
                if(line.startsWith("v ")) {
                    vertices.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                    ));
                }

                if(line.startsWith("vn ")) {
                    normals.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                    ));
                }

                if(line.startsWith("vt ")) {
                    texCoords.add(new Vector2f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2])));
                }


                if(line.startsWith("f ")) {
                    try{
                        faces.add(new KittyLibFace(convertFace(new String[]{parts[1],parts[2],parts[3]}, vertices, normals, texCoords)));

                        if(parts.length >= 5) {
                            faces.add(new KittyLibFace(convertFace(new String[]{parts[1],parts[3],parts[4]}, vertices, normals, texCoords)));

                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

            return faces;
        }catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static KittyLibTriangleData[] convertFace(String[] parts, List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> texCoords) {
        //vertex/texCoord/normal

        KittyLibTriangleData[] triangleData = new KittyLibTriangleData[4];

        for(int i = 0;i<3;i++) {
            String[] faceDataPoints = parts[i].split("/");

            int vertexIndex = Integer.parseInt(faceDataPoints[0]) - 1;
            int textureIndex = Integer.parseInt(faceDataPoints[1]) - 1;
            int normalIndex = Integer.parseInt(faceDataPoints[2]) - 1;

            var vertex = vertices.get(vertexIndex);
            var normal = normals.get(normalIndex).normalize();
            var texture = texCoords.get(textureIndex);

            triangleData[i] = new KittyLibTriangleData(
                    new float[]{vertex.x(), vertex.y(), vertex.z()},
                    new float[]{normal.x(), normal.y(), normal.z()},
                    new float[]{texture.x(), texture.y()}
            );
        }

        triangleData[3] = triangleData[0];

        return triangleData;
    }
}