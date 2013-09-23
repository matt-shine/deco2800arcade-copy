
uniform sampler2D texture;
uniform vec2 u_texoffset;
varying vec2 v_texpos;

void main (void)  
{
   gl_FragColor = texture2D(texture, v_texpos + u_texoffset);
}
