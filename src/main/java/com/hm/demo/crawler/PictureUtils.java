package com.hm.demo.crawler;

import com.luciad.imageio.webp.WebPReadParam;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureUtils {
    public static void main(String[] args) throws Exception {
        webpToPng("D:\\private\\pictures\\comic\\韩漫\\秘密教学\\第77话-高傲的舒亚跑去子豪房间？\\001.webp",
                "D:\\private\\pictures\\comic\\韩漫\\秘密教学\\第77话-高傲的舒亚跑去子豪房间？\\001.png");

    }

    public static void webpToPng(String webpPath, String pngPath) throws IOException {
        // Obtain a WebP ImageReader instance
        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();

        // Configure decoding parameters
        WebPReadParam readParam = new WebPReadParam();
        readParam.setBypassFiltering(true);

        // Configure the input on the ImageReader
        reader.setInput(new FileImageInputStream(new File(webpPath)));

        // Decode the image
        BufferedImage image = reader.read(0, readParam);

        // the `png` can use `jpg`
        ImageIO.write(image, "png", new File(pngPath));
    }

}
