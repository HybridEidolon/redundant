varying vec4 position;
uniform float time;

attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

float wave(vec2 p, float angle) {
  vec2 direction = vec2(cos(angle), sin(angle));
  return cos(dot(p, direction));
}

float wrap(float x) {
  return abs(mod(x, 2.)-1.);
}

void main() {
  vec2 p = (vec2(v_texCoords.x * 2., v_texCoords.y) - 0.5) * 8.6;
  vec4 col = texture2D(u_texture, v_texCoords) * v_color;

  float brightness = 0.;
  vec3 supercolor = vec3(0);
  for (float i = 1.; i <= 4.; i++) {
    brightness += wave(p, time / i);
    supercolor.r = wave(p, (time + .5) / i) + .2;
    supercolor.g = wave(p, (time + 3) / i) + .2;
    supercolor.b = wave(p, (time + 1) / i) + .2;
  }

  brightness = wrap(brightness);
  gl_FragColor.rgba = col;
  gl_FragColor.rgb += vec3(brightness) + supercolor;
}