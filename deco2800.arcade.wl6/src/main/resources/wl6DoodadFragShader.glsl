
uniform sampler2D texture;
uniform vec2 u_texoffset;
varying vec2 v_texpos;

void main (void)  
{
	vec4 tex = texture2D(texture, v_texpos + u_texoffset);
	if (tex.a != 1.0) discard;
	gl_FragColor = tex;
}
