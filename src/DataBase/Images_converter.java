/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Mohammad
 */
public class Images_converter {
    
    
    public static byte[] convertImageToByteArray(File file) {
        try {
            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos);
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            new RuntimeException(e.getMessage());
            return null;
        }
    }
    
    public static byte[] convertImageToByteArray(Image image) {
        try {
            BufferedImage bImage =  SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write( bImage, "jpg", bos);
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            new RuntimeException(e.getMessage());
            return null;
        }
    }
   
    
    public static Image convertByteArrayToImage(byte [] byteArray){
       Image image = new Image(new ByteArrayInputStream(byteArray));
        return image;
    }

}
