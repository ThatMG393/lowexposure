#version 330 compatibility

uniform float uBrightness;
uniform float uContrast;
uniform float uSaturation;
uniform vec3 uLuma;

in vec2 texCoord;

uniform sampler2D DiffuseSampler0;

out vec4 fragColor;

void main() {
    fragColor = texture(DiffuseSampler0, texCoord);
    
    // saturate, then contrast around 0.5, then add brightness
    fragColor.rgb = ((mix(vec3(dot(fragColor.rgb, uLuma)), fragColor.rgb, uSaturation) - 0.5) * uContrast + 0.5) + uBrightness;
}
