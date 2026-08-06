package nico.kittylib.internal.client.obj;

import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import nico.kittylib.KittyLibMain;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ObjDeserializer {
    protected static final float[] NULL_MODEL = new float[0];

    public static float[] toVertexData(Identifier fileId, Resource resource) {
        try (BufferedReader reader = resource.getReader()) {
            List<Vector3f> vertices = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<Vector2f> texCoords = new ArrayList<>();
            List<Vector3i> faces = new ArrayList<>();

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(" ");
                    if (line.startsWith("v ")) {
                        vertices.add(new Vector3f(
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2]),
                                Float.parseFloat(parts[3])
                        ));
                    }

                    if (line.startsWith("vn ")) {
                        normals.add(new Vector3f(
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2]),
                                Float.parseFloat(parts[3])
                        ));
                    }

                    if (line.startsWith("vt ")) {
                        texCoords.add(new Vector2f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2])));
                    }

                    // f vertex/texCoord/normal vertex/texCoord/normal vertex/texCoord/normal
                    if (line.startsWith("f ")) {
                        Vector3i[] array = new Vector3i[parts.length - 1];
                        for (int i = 0; i < parts.length - 1; i++) {
                            String[] str = parts[i + 1].split("/");
                            array[i] = new Vector3i(
                                    Integer.parseInt(str[0]) - 1,
                                    Integer.parseInt(str[1]) - 1,
                                    Integer.parseInt(str[2]) - 1
                            );
                        }

                        if (parts.length == 4) {
                            faces.add(array[0]);
                            faces.add(array[1]);
                            faces.add(array[2]);
                            faces.add(array[0]);
                        } else {
                            faces.add(array[0]);
                            faces.add(array[1]);
                            faces.add(array[2]);
                            faces.add(array[3]);
                        }
                    }
                } catch (Exception e) {
                    KittyLibMain.LOGGER.error("[KittyLib-Obj] Error thrown while deserialization {}:{}", fileId, lineNumber);
                    KittyLibMain.LOGGER.error("\"{}\" <<<", line);
                    e.printStackTrace();
                } finally {
                    lineNumber++;
                }
            }
            try {
                /**
                 0 = x;
                 1 = y;
                 2 = z;
                 3 = nx;
                 4 = ny;
                 5 = nz;
                 6 = u;
                 7 = v;
                 */
                float[] vertexData = new float[faces.size() * 8];

                for (int i = 0; i < faces.size(); i++) {
                    Vector3i face = faces.get(i);

                    // vertex/texCoord/normal
                    Vector3f vertex = vertices.get(face.x);
                    Vector2f texCoord = texCoords.get(face.y);
                    Vector3f normal = normals.get(face.z);

                    vertexData[i * 8 + 0] = vertex.x();
                    vertexData[i * 8 + 1] = vertex.y();
                    vertexData[i * 8 + 2] = vertex.z();
                    vertexData[i * 8 + 3] = normal.x();
                    vertexData[i * 8 + 4] = normal.y();
                    vertexData[i * 8 + 5] = normal.z();
                    vertexData[i * 8 + 6] = texCoord.x();
                    vertexData[i * 8 + 7] = 1 - texCoord.y();
                }

                return vertexData;
            } catch (Exception e) {
                KittyLibMain.LOGGER.error("[KittyLib-Obj] Error thrown while vertexData assembly, {}", fileId);
                e.printStackTrace();
                return NULL_MODEL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return NULL_MODEL;
        }
    }
}