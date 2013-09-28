
uniform sampler2D texture;
varying vec2 v_texpos;

void main (void)  
{
    gl_FragColor = texture2D(texture, v_texpos);
}
