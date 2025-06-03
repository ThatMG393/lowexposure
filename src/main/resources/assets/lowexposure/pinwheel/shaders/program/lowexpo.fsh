#version 330 compatibility

const float exposure = -0.3;
const float colorMix = 0.3;

in vec2 texCoord;

uniform sampler2D DiffuseSampler0;

out vec4 fragColor;

void main() {
    fragColor = texture(DiffuseSampler0, texCoord);
    
    // Apply exposure
    vec3 exposed = fragColor.rgb * pow(2.0, exposure);
    
    // Heavy desaturation
    float lum = dot(exposed, vec3(0.299, 0.587, 0.114));
    vec3 desaturated = mix(vec3(lum), exposed, colorMix);
    
    // Darken and add brownish tint
    // vec3 deadTone = desaturated * vec3(0.8, 0.75, 0.6);
    
    fragColor.rgb = desaturated;
}