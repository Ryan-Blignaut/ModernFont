#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
	//	vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;

	float a = 1 - texture(Sampler0, texCoord0).r;
	float w = 0.2;
	float e = 0.22;
	float d =  1 - smoothstep(w, w+e, a);

	if (d < 0.1) {
		discard;
	}

	vec4 col = vec4(vec3(1.0), a);
	//	vec4 col = vec4(vec3(1.0), texture(Sampler0, texCoord0).r);
	vec4 colour =  col * vertexColor * ColorModulator;
	fragColor = linear_fog(colour, vertexDistance, FogStart, FogEnd, FogColor); /*linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor) * vec4(0.0, 0.0, 1.0, 1.0)*/;

	fragColor = vec4(1.0, 0.0, 0.0, 1.0);

}
