#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler3;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

in vec3 viewPosition;
in vec3 viewNormal;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);

    if (color.a < 0.1) {
        discard;
    }

    vec3 N = normalize(viewNormal);

    vec3 dp1 = dFdx(viewPosition);
    vec3 dp2 = dFdy(viewPosition);

    vec2 duv1 = dFdx(texCoord0);
    vec2 duv2 = dFdy(texCoord0);

    float r = 1.0 / (duv1.x * duv2.y - duv1.y * duv2.x);

    vec3 T = (dp1 * duv2.y - dp2 * duv1.y) * r;
    vec3 B = (dp2 * duv1.x - dp1 * duv2.x) * r;

    T = normalize(T - N * dot(N, T));
    B = normalize(cross(N, T));

    mat3 TBN = mat3(T, B, N);
    vec3 tangentNormal = texture(Sampler3, texCoord0).rgb * 2.0 - 1.0;

    vec3 mappedNormal = normalize(TBN * tangentNormal);

    color = minecraft_mix_light(Light0_Direction, Light1_Direction, mappedNormal, color);

    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
