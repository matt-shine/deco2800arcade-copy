
uniform sampler2D texture;
uniform vec2 u_texoffset;
varying vec2 v_texpos;

void main (void)  
{
	vec4 tex = texture2D(texture, v_texpos + u_texoffset);
	//if (tex.a == 0.0) discard;
	//if (tex.a != 1.0) gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	if (tex.a < 0.5) discard;
	else gl_FragColor = vec4(tex.xyz, 1.0);
}
