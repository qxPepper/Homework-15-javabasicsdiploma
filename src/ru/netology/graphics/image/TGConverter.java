package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TGConverter implements TextGraphicsConverter {
    private int width, newWidth;
    private int height, newHeight;
    private double ratio;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        //BufferedImage img = ImageIO.read(new File(url));
        TCSchema schema = new TCSchema();
        System.out.println();

        // Проверка на максимально допустимое соотношение сторон изображения.
        ratioWidthHeight(img);

        // Получение чёрно-белой картинки нужных размеров.
        BufferedImage bwImg = grayImage(img);

        // Получение картинки в текстовом виде.
        StringBuilder outStringBuilder = scanGrayAndDrawTextImage(bwImg, schema);

        return outStringBuilder.toString();
    }

    private void ratioWidthHeight(BufferedImage img) throws BadImageSizeException {
        double ratio = (double) img.getWidth() / img.getHeight();
        if (ratio > this.ratio) {
            throw new BadImageSizeException(this.ratio, ratio);
        }
    }

    private BufferedImage grayImage(BufferedImage img) {
        double k = Math.min((double) width / img.getWidth(), (double) height / img.getHeight());
        newWidth = (int) (img.getWidth() * k);
        newHeight = (int) (img.getHeight() * k);

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        //ImageIO.write(bwImg, "png", new File("out.png"));

        return bwImg;
    }

    private StringBuilder scanGrayAndDrawTextImage(BufferedImage bwImg, TCSchema schema) {
        var bwRaster = bwImg.getRaster();

        int[][] outSimbols = new int[newHeight][newWidth];
        int color;
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                color = bwRaster.getPixel(j, i, new int[3])[0];
                char c = schema.convert(color);
                outSimbols[i][j] = c;
            }
        }

        StringBuilder outStringBuilder = new StringBuilder();
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                outStringBuilder.append((char) outSimbols[i][j]);
                outStringBuilder.append((char) outSimbols[i][j]);
            }
            outStringBuilder.append("\n");
        }
        return outStringBuilder;
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
