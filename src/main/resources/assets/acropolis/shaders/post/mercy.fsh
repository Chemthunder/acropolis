#version 110

uniform sampler2D MainSampler;
varying vec2 texCoord;

void main()
{
    vec3 color = texture2D(MainSampler, texCoord).rgb;

        // for magenta
    vec3 target1 = vec3(0.6, 0.15, 0.45);

    vec3 target2 = vec3(0.35, 0.05, 0.4);

    vec3 target3 = vec3(0.75, 0.2, 0.7);

        // for the green i got
        vec3 target4 = vec3(0.05, 0.35, 0.3);

        vec3 normColor = normalize(color + 0.0001);

        float match1 = dot(normColor, normalize(target1));
        float match2 = dot(normColor, normalize(target2));
        float match3 = dot(normColor, normalize(target3));
        float match4 = dot(normColor, normalize(target4));

        float threshold = 0.968;

        if (match1 > threshold
        || match2 > threshold
        || match3 > threshold
        //|| match4 > threshold
        )
        {
            float gray = dot(color, vec3(0.2126, 0.7152, 0.0722));
            vec3 grayColor = vec3(gray);

            float desaturateAmount = -0.1;

            vec3 finalColor = mix(color*1.5, grayColor, desaturateAmount);

            gl_FragColor = vec4(finalColor, 1.0);
        }
        else
        {
            float gray = dot(color, vec3(0.2126, 0.7152, 0.0722));
            vec3 grayColor = vec3(gray);

            float desaturateAmount = 0.6;

            vec3 finalColor = mix(color, grayColor, desaturateAmount);

            gl_FragColor = vec4(finalColor, 1.0);
        }
}