attribute vec3 a_position;
uniform mat4 uMVPMatrix;

void main(void)
{
   gl_Position = uMVPMatrix * vec4(a_position, 1.0);
}
