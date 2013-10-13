
uniform mat4 uMVPMatrix;
attribute vec3 a_position;
attribute vec2 a_texpos;

varying vec2 v_texpos;

void main(void)
{
	v_texpos = a_texpos;
	gl_Position = uMVPMatrix * vec4(a_position, 1.0);
}
