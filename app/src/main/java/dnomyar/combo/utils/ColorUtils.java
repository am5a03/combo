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
        return new Color(ColorUtils.getColorFloat(229), ColorUtils.getColorFloat(115), ColorUtils.getColorFloat(115));
    }

    public static Color getPressedRed() {
        return new Color(ColorUtils.getColorFloat(255), ColorUtils.getColorFloat(205), ColorUtils.getColorFloat(210));
    }

    public static Color getDefaultOrange() {
        return new Color(ColorUtils.getColorFloat(255), ColorUtils.getColorFloat(183), ColorUtils.getColorFloat(77));
    }

    public static Color getPressedOrange() {
        return new Color(ColorUtils.getColorFloat(255), ColorUtils.getColorFloat(224), ColorUtils.getColorFloat(178));
    }

    public static Color getDefaultYellow() {
        return new Color(ColorUtils.getColorFloat(255), ColorUtils.getColorFloat(241), ColorUtils.getColorFloat(118));
    }

    public static Color getPressedYellow() {
        return new Color(ColorUtils.getColorFloat(255), ColorUtils.getColorFloat(249), ColorUtils.getColorFloat(196));
    }

    public static Color getDefaultGreen() {
        return new Color(ColorUtils.getColorFloat(129), ColorUtils.getColorFloat(199), ColorUtils.getColorFloat(132));
    }

    public static Color getPressedGreen() {
        return new Color(ColorUtils.getColorFloat(200), ColorUtils.getColorFloat(230), ColorUtils.getColorFloat(201));
    }

    public static Color getDefaultBlue() {
        return new Color(ColorUtils.getColorFloat(100), ColorUtils.getColorFloat(181), ColorUtils.getColorFloat(246));
    }

    public static Color getPressedBlue() {
        return new Color(ColorUtils.getColorFloat(187), ColorUtils.getColorFloat(222), ColorUtils.getColorFloat(251));
    }

    public static Color getDefaultIndigo() {
        return new Color(ColorUtils.getColorFloat(121), ColorUtils.getColorFloat(134), ColorUtils.getColorFloat(203));
    }

    public static Color getPressedIndigo() {
        return new Color(ColorUtils.getColorFloat(197), ColorUtils.getColorFloat(202), ColorUtils.getColorFloat(233));
    }

    public static Color getDefaultPurple() {
        return new Color(ColorUtils.getColorFloat(186), ColorUtils.getColorFloat(104), ColorUtils.getColorFloat(200));
    }

    public static Color getPressedPurple() {
        return new Color(ColorUtils.getColorFloat(225), ColorUtils.getColorFloat(190), ColorUtils.getColorFloat(231));
    }

    public static Color getDefaultBg() {
        return new Color(ColorUtils.getColorFloat(44), ColorUtils.getColorFloat(62), ColorUtils.getColorFloat(80));
    }

    public static Color getDefaultGrey() {
        return new Color(ColorUtils.getColorFloat(97), ColorUtils.getColorFloat(97), ColorUtils.getColorFloat(97));
    }
}
