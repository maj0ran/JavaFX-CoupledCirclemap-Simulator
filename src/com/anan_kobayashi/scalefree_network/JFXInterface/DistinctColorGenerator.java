package com.anan_kobayashi.scalefree_network.JFXInterface;

import javafx.scene.paint.Color;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */

class DistinctColorGenerator {
    private static double hue;
    private static double saturation;
    private static double brightness;

    private static int counter;


    DistinctColorGenerator() {
        hue = 20;
        saturation = 1.0;
        brightness = 1.0;

        counter = 0;


    }

    Color nextColor() {
        final double golden_angle = 137.50776405003785;

        counter++;
        if (counter % 10 == 0) {
            saturation -= 0.25;
            if (saturation < 0.251) {
                brightness -= 0.25;
                saturation = 1;
                if (brightness < 0.251) {
                    brightness = 1;
                }
            }
        }
        Color c = Color.hsb(hue, saturation, brightness);
        hue += golden_angle;

        return c;
    }


}
