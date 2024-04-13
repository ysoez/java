package performance.latency;

import java.awt.image.BufferedImage;

class Colors {

    static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    static int createRGB(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }

}
