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
	float a = 1- texture(Sampler0, texCoord0).r;
	//	vec4 t = texture(Sampler0, texCoord0);
	//	a = median(t.r, t.g, t.b, t.a);

	//	vec2 sz = textureSize(Sampler0, 0);
	//	float dx = dFdx(texCoord0.x) * sz.x;
	//	float dy = dFdy(texCoord0.y) * sz.y;
	//	float toPixels = 8.0 * inversesqrt(dx * dx + dy * dy);
	//	float w = fwidth(texture(Sampler0, texCoord0).r);
	//	float opacity = smoothstep(0.5 - w, 0.5 + w, a);

	float w = 0.2;
	float e = 0.25;
	float d =  1 - smoothstep(w, w + e, a);

	vec4 col = vec4(vec3(1.0), d);
	vec4 colour =  col * vertexColor * ColorModulator;

	/*float a = 1 - texture(Sampler0, texCoord0).r;
	float w = 0.43;
	float e = 0.5;
	float d =  1 - smoothstep(w, w + e, a);

	vec4 col = vec4(vec3(1.0), d);
	vec4 colour =  col * vertexColor * ColorModulator;*/



	fragColor = linear_fog(colour, vertexDistance, FogStart, FogEnd, FogColor);
	if (d < 0.00001) discard;
	//	fragColor = vec4(vec3(1, 0, 1), texture(Sampler0, texCoord0).r);
}

