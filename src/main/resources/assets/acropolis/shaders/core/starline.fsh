
// INITIAL HLSL BEGIN

#pragma warning( disable: 3081 3556 3557 3571 )
struct ss3080_sbfc
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3080_sbfc
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3080_sbfc
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3080_sbfc
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3080_sbfc
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_0;
    float pad_1;
    float pad_2;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3080_sbfc
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_3;
    float pad_4;
    float pad_5;
};
#pragma pack_matrix(column_major)
struct ss3083_sbff
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3083_sbff
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3083_sbff
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3083_sbff
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3083_sbff
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_6;
    float pad_7;
    float pad_8;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3083_sbff
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_9;
    float pad_10;
    float pad_11;
};
#pragma pack_matrix(column_major)
struct ss3086_sc02
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3086_sc02
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3086_sc02
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3086_sc02
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3086_sc02
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_12;
    float pad_13;
    float pad_14;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3086_sc02
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_15;
    float pad_16;
    float pad_17;
};
#pragma pack_matrix(column_major)
struct ss3089_sc05
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3089_sc05
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3089_sc05
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3089_sc05
{
    float3 _size;
    float _time;
    int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3089_sc05
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_18;
    float pad_19;
    float pad_20;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3089_sc05
{
    float3 _size;
    float _time;
    int _loaded;
    float pad_21;
    float pad_22;
    float pad_23;
};
#pragma pack_matrix(column_major)
float float_ctor_int(int x0)
{
    return float(x0);
}
float2 vec2_ctor(float x0, float x1)
{
    return float2(x0, x1);
}
float3 vec3_ctor(float x0)
{
    return float3(x0, x0, x0);
}
float4 vec4_ctor(float3 x0, float x1)
{
    return float4(x0, x1);
}
// Uniforms

uniform float3 _iResolution : register(c0);
uniform int _iFrame : register(c1);
#ifdef ANGLE_ENABLE_LOOP_FLATTEN
#define LOOP [loop]
#define FLATTEN [flatten]
#else
#define LOOP
#define FLATTEN
#endif
// Varyings

static float4 out_shadertoy_out_color = {0, 0, 0, 0};
static float4 gl_FragCoord = float4(0, 0, 0, 0);

cbuffer DriverConstants : register(b1)
{
float4 dx_ViewCoords : packoffset(c1);
float2 dx_FragCoordOffset : packoffset(c3);
float3 dx_DepthFront : packoffset(c2);
float2 dx_ViewScale : packoffset(c3.z);
uint dx_Misc : packoffset(c2.w);
};

#define GL_USES_FRAG_COORD
;
;
;
;
void f_mainImage_float4(inout float4 _fragColor3059, in float2 _fragCoord3060);
;
@@ PIXEL OUTPUT @@

PS_OUTPUT main(@@ PIXEL MAIN PARAMETERS @@){
@@ MAIN PROLOGUE @@
(out_shadertoy_out_color = float4(1.0, 1.0, 1.0, 1.0));
float4 _color3038 = {100000002004087734272.0, 100000002004087734272.0, 100000002004087734272.0, 100000002004087734272.0};
f_mainImage_float4(_color3038, gl_FragCoord.xy);
if ((out_shadertoy_out_color.x < 0.0))
{
(_color3038 = float4(1.0, 0.0, 0.0, 1.0));
}
if ((out_shadertoy_out_color.y < 0.0))
{
(_color3038 = float4(0.0, 1.0, 0.0, 1.0));
}
if ((out_shadertoy_out_color.z < 0.0))
{
(_color3038 = float4(0.0, 0.0, 1.0, 1.0));
}
if ((out_shadertoy_out_color.w < 0.0))
{
(_color3038 = float4(1.0, 1.0, 0.0, 1.0));
}
(out_shadertoy_out_color = vec4_ctor(_color3038.xyz, 1.0));
return generateOutput();
}
float f_Noise2d(in float2 _x3040)
{
float _xhash3041 = cos((_x3040.x * 37.0));
float _yhash3042 = cos((_x3040.y * 57.0));
return frac((415.926544 * (_xhash3041 + _yhash3042)));
}
float f_NoisyStarField(in float2 _vSamplePos3044, in float _fThreshhold3045)
{
float _StarVal3046 = f_Noise2d(_vSamplePos3044);
if ((_StarVal3046 >= _fThreshhold3045))
{
float sbfb = ((_StarVal3046 - _fThreshhold3045) / (1.0 - _fThreshhold3045));
(_StarVal3046 = (((((sbfb * sbfb) * sbfb) * sbfb) * sbfb) * sbfb));
}
else
{
(_StarVal3046 = 0.0);
}
return _StarVal3046;
}
float f_StableStarField(in float2 _vSamplePos3048, in float _fThreshhold3049)
{
float _fractX3050 = frac(_vSamplePos3048.x);
float _fractY3051 = frac(_vSamplePos3048.y);
float2 _floorSample3052 = floor(_vSamplePos3048);
float _v13053 = f_NoisyStarField(_floorSample3052, _fThreshhold3049);
float _v23054 = f_NoisyStarField((_floorSample3052 + float2(0.0, 1.0)), _fThreshhold3049);
float _v33055 = f_NoisyStarField((_floorSample3052 + float2(1.0, 0.0)), _fThreshhold3049);
float _v43056 = f_NoisyStarField((_floorSample3052 + float2(1.0, 1.0)), _fThreshhold3049);
float _StarVal3057 = (((((_v13053 * (1.0 - _fractX3050)) * (1.0 - _fractY3051)) + ((_v23054 * (1.0 - _fractX3050)) * _fractY3051)) + ((_v33055 * _fractX3050) * (1.0 - _fractY3051))) + ((_v43056 * _fractX3050) * _fractY3051));
return _StarVal3057;
}
void f_mainImage_float4(inout float4 _fragColor3059, in float2 _fragCoord3060)
{
float3 _vColor3061 = ((float3(0.100000001, 0.200000003, 0.400000006) * _fragCoord3060.y) / _iResolution.y);
float _StarFieldThreshhold3062 = {0.970000029};
float _xRate3063 = {0.200000003};
float _yRate3064 = {-0.0599999987};
float2 _vSamplePos3065 = (_fragCoord3060.xy + vec2_ctor((_xRate3063 * float_ctor_int(_iFrame)), (_yRate3064 * float_ctor_int(_iFrame))));
float _StarVal3066 = f_StableStarField(_vSamplePos3065, _StarFieldThreshhold3062);
(_vColor3061 += vec3_ctor(_StarVal3066));
(_fragColor3059 = vec4_ctor(_vColor3061, 1.0));
}

// INITIAL HLSL END


// COMPILER INPUT HLSL BEGIN

struct PS_INPUT
{
float4 dx_Position : SV_Position;
float4 gl_Position : TEXCOORD0;
float4 gl_FragCoord : TEXCOORD1;
};

#pragma warning( disable: 3081 3556 3557 3571 )
struct ss3080_sbfc
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3080_sbfc
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3080_sbfc
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3080_sbfc
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3080_sbfc
{
float3 _size;
float _time;
int _loaded;
float pad_0;
float pad_1;
float pad_2;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3080_sbfc
{
float3 _size;
float _time;
int _loaded;
float pad_3;
float pad_4;
float pad_5;
};
#pragma pack_matrix(column_major)
struct ss3083_sbff
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3083_sbff
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3083_sbff
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3083_sbff
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3083_sbff
{
float3 _size;
float _time;
int _loaded;
float pad_6;
float pad_7;
float pad_8;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3083_sbff
{
float3 _size;
float _time;
int _loaded;
float pad_9;
float pad_10;
float pad_11;
};
#pragma pack_matrix(column_major)
struct ss3086_sc02
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3086_sc02
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3086_sc02
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3086_sc02
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3086_sc02
{
float3 _size;
float _time;
int _loaded;
float pad_12;
float pad_13;
float pad_14;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3086_sc02
{
float3 _size;
float _time;
int _loaded;
float pad_15;
float pad_16;
float pad_17;
};
#pragma pack_matrix(column_major)
struct ss3089_sc05
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct rm_ss3089_sc05
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_ss3089_sc05
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(row_major)
struct std_rm_ss3089_sc05
{
float3 _size;
float _time;
int _loaded;
};
#pragma pack_matrix(column_major)
struct std_fp_ss3089_sc05
{
float3 _size;
float _time;
int _loaded;
float pad_18;
float pad_19;
float pad_20;
};
#pragma pack_matrix(row_major)
struct std_rm_fp_ss3089_sc05
{
float3 _size;
float _time;
int _loaded;
float pad_21;
float pad_22;
float pad_23;
};
#pragma pack_matrix(column_major)
float float_ctor_int(int x0)
{
return float(x0);
}
float2 vec2_ctor(float x0, float x1)
{
return float2(x0, x1);
}
float3 vec3_ctor(float x0)
{
return float3(x0, x0, x0);
}
float4 vec4_ctor(float3 x0, float x1)
{
return float4(x0, x1);
}
// Uniforms

uniform float3 _iResolution : register(c0);
uniform int _iFrame : register(c1);
#ifdef ANGLE_ENABLE_LOOP_FLATTEN
#define LOOP [loop]
#define FLATTEN [flatten]
#else
#define LOOP
#define FLATTEN
#endif
// Varyings

static float4 out_shadertoy_out_color = {0, 0, 0, 0};
static float4 gl_FragCoord = float4(0, 0, 0, 0);

cbuffer DriverConstants : register(b1)
{
float4 dx_ViewCoords : packoffset(c1);
float2 dx_FragCoordOffset : packoffset(c3);
float3 dx_DepthFront : packoffset(c2);
float2 dx_ViewScale : packoffset(c3.z);
uint dx_Misc : packoffset(c2.w);
};

#define GL_USES_FRAG_COORD
;
;
;
;
void f_mainImage_float4(inout float4 _fragColor3059, in float2 _fragCoord3060);
;
struct PS_OUTPUT
{
float4 out_shadertoy_out_color : SV_TARGET0;
};

PS_OUTPUT generateOutput()
{
PS_OUTPUT output;
output.out_shadertoy_out_color = out_shadertoy_out_color;
return output;
}


PS_OUTPUT main(PS_INPUT input){
float rhw = 1.0 / input.gl_FragCoord.w;
gl_FragCoord.x = input.dx_Position.x - dx_FragCoordOffset.x;
gl_FragCoord.y = input.dx_Position.y - dx_FragCoordOffset.y;
gl_FragCoord.z = input.dx_Position.z;
gl_FragCoord.w = rhw;

(out_shadertoy_out_color = float4(1.0, 1.0, 1.0, 1.0));
float4 _color3038 = {100000002004087734272.0, 100000002004087734272.0, 100000002004087734272.0, 100000002004087734272.0};
f_mainImage_float4(_color3038, gl_FragCoord.xy);
if ((out_shadertoy_out_color.x < 0.0))
{
(_color3038 = float4(1.0, 0.0, 0.0, 1.0));
}
if ((out_shadertoy_out_color.y < 0.0))
{
(_color3038 = float4(0.0, 1.0, 0.0, 1.0));
}
if ((out_shadertoy_out_color.z < 0.0))
{
(_color3038 = float4(0.0, 0.0, 1.0, 1.0));
}
if ((out_shadertoy_out_color.w < 0.0))
{
(_color3038 = float4(1.0, 1.0, 0.0, 1.0));
}
(out_shadertoy_out_color = vec4_ctor(_color3038.xyz, 1.0));
return generateOutput();
}
float f_Noise2d(in float2 _x3040)
{
float _xhash3041 = cos((_x3040.x * 37.0));
float _yhash3042 = cos((_x3040.y * 57.0));
return frac((415.926544 * (_xhash3041 + _yhash3042)));
}
float f_NoisyStarField(in float2 _vSamplePos3044, in float _fThreshhold3045)
{
float _StarVal3046 = f_Noise2d(_vSamplePos3044);
if ((_StarVal3046 >= _fThreshhold3045))
{
float sbfb = ((_StarVal3046 - _fThreshhold3045) / (1.0 - _fThreshhold3045));
(_StarVal3046 = (((((sbfb * sbfb) * sbfb) * sbfb) * sbfb) * sbfb));
}
else
{
(_StarVal3046 = 0.0);
}
return _StarVal3046;
}
float f_StableStarField(in float2 _vSamplePos3048, in float _fThreshhold3049)
{
float _fractX3050 = frac(_vSamplePos3048.x);
float _fractY3051 = frac(_vSamplePos3048.y);
float2 _floorSample3052 = floor(_vSamplePos3048);
float _v13053 = f_NoisyStarField(_floorSample3052, _fThreshhold3049);
float _v23054 = f_NoisyStarField((_floorSample3052 + float2(0.0, 1.0)), _fThreshhold3049);
float _v33055 = f_NoisyStarField((_floorSample3052 + float2(1.0, 0.0)), _fThreshhold3049);
float _v43056 = f_NoisyStarField((_floorSample3052 + float2(1.0, 1.0)), _fThreshhold3049);
float _StarVal3057 = (((((_v13053 * (1.0 - _fractX3050)) * (1.0 - _fractY3051)) + ((_v23054 * (1.0 - _fractX3050)) * _fractY3051)) + ((_v33055 * _fractX3050) * (1.0 - _fractY3051))) + ((_v43056 * _fractX3050) * _fractY3051));
return _StarVal3057;
}
void f_mainImage_float4(inout float4 _fragColor3059, in float2 _fragCoord3060)
{
float3 _vColor3061 = ((float3(0.100000001, 0.200000003, 0.400000006) * _fragCoord3060.y) / _iResolution.y);
float _StarFieldThreshhold3062 = {0.970000029};
float _xRate3063 = {0.200000003};
float _yRate3064 = {-0.0599999987};
float2 _vSamplePos3065 = (_fragCoord3060.xy + vec2_ctor((_xRate3063 * float_ctor_int(_iFrame)), (_yRate3064 * float_ctor_int(_iFrame))));
float _StarVal3066 = f_StableStarField(_vSamplePos3065, _StarFieldThreshhold3062);
(_vColor3061 += vec3_ctor(_StarVal3066));
(_fragColor3059 = vec4_ctor(_vColor3061, 1.0));
}

// COMPILER INPUT HLSL END

// FRAGMENT SHADER END