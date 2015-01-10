package dnomyar.combo.utils;

import org.andengine.util.adt.color.Color;

/**
 * Created by Raymond on 2015-01-08.
 */
public class ColorUtils {
    public static float getColorFloat(int colorCode) {
        return (float) (colorCode/255.0);
    };

    /**
     * Converts a supplied HEX Color Code to OpenGL RGB Float Color string<br>
     * <b>Example :</b> ColorBackground(HexToOpenGL("#549d3a"));<br>
     * <b>Output :</b> ColorBackground(0.3294117647058824f, 0.6156862745098039f, 0.2274509803921569f);<br>
     * @author Trenton Shaffer
     * @param hexcolor - Hex Color Code to convert (i.e. #549d3a or 549d3a)
     * @return floatGLColor - Returns a string with RGB converted OpenGL float color values
     */
    public String HexToOpenGL(String hexcolor)
    {
        double[] GLVal = new double[3];

        if(hexcolor.length()>6)
            hexcolor = hexcolor.substring(1,7);

        GLVal[0] = Integer.parseInt(hexcolor.substring(0,2),16) / 255.0;
        GLVal[1] = Integer.parseInt(hexcolor.substring(2,4),16) / 255.0;
        GLVal[2] = Integer.parseInt(hexcolor.substring(4,6),16) / 255.0;
        String floatGLColor = GLVal[0] + "f, " + GLVal[1] + "f, " + GLVal[2] + "f";

        return floatGLColor;
    }


    public static Color getDefaultRed() {
        return new Color(ColorUtils.getColorFloat(244), ColorUtils.getColorFloat(67), ColorUtils.getColorFloat(54));
    }

    public static Color getPressedRed() {
        return new Color(ColorUtils.getColorFloat(239), ColorUtils.getColorFloat(83), ColorUtils.getColorFloat(80));
    }

    public static Color getDefaultGreen() {
        return new Color(ColorUtils.getColorFloat(76), ColorUtils.getColorFloat(175), ColorUtils.getColorFloat(80));
    }

    public static Color getPressedGreen() {
        return new Color(ColorUtils.getColorFloat(102), ColorUtils.getColorFloat(187), ColorUtils.getColorFloat(106));
    }

    public static Color getDefaultYellow() {
        return new Color(ColorUtils.getColorFloat(255), ColorUtils.getColorFloat(235), ColorUtils.getColorFloat(59));
    }

    public static Color getPressedYellow() {
        return new Color(ColorUtils.getColorFloat(255), ColorUtils.getColorFloat(238), ColorUtils.getColorFloat(88));
    }

    public static Color getDefaultBlue() {
        return new Color(ColorUtils.getColorFloat(33), ColorUtils.getColorFloat(150), ColorUtils.getColorFloat(243));
    }

    public static Color getPressedBlue() {
        return new Color(ColorUtils.getColorFloat(66), ColorUtils.getColorFloat(165), ColorUtils.getColorFloat(245));
    }

    public static Color getDefaultBg() {
        return new Color(ColorUtils.getColorFloat(44), ColorUtils.getColorFloat(62), ColorUtils.getColorFloat(80));
    }

    public static Color getDefaultGrey() {
        return new Color(ColorUtils.getColorFloat(97), ColorUtils.getColorFloat(97), ColorUtils.getColorFloat(97));
    }
}
