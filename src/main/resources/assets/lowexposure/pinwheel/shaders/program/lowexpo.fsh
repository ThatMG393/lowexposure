#version 330 compatibility

const float uBrightness = -0.05;
const float uContrast = 1.15;
const float uSaturation = 0.50;
const vec3 LUMA = vec3(0.299, 0.587, 0.114);

in vec2 texCoord;

uniform sampler2D DiffuseSampler0;

out vec4 fragColor;

void main() {
    fragColor = texture(DiffuseSampler0, texCoord);
    
    // saturate, then contrast around 0.5, then add brightness
    fragColor.rgb = ((mix(vec3(dot(fragColor.rgb, LUMA)), fragColor.rgb, uSaturation) - 0.5) * uContrast + 0.5) + uBrightness;
}
