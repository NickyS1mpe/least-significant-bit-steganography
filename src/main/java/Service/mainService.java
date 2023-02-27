package Service;

import Function.Decrypt;
import Function.Encrypt;
import Function.LSB_Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @ClassName: main
 * @Description: TODO
 * @Author: Nick Lee
 * @Date: Create in 15:46 2023/2/22
 **/
public class mainService {

    BufferedImage image;
    LSB_Color color;
    String mes;

    /**
     * @Author: Nick Lee
     * @Description: 读取图片
     * @Date: 2023/2/27 9:49
     * @Return:
     **/
    public boolean readPic(String road) {
        try {
            image = ImageIO.read(new File(road));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Author: Nick Lee
     * @Description: 写入图片
     * @Date: 2023/2/27 9:48
     * @Return:
     **/
    public boolean writePic(String road) {
        try {
            String format = road.substring(road.lastIndexOf(".") + 1);
            ImageIO.write(color.getImage(), format, new File(road));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Author: Nick Lee
     * @Description: 加载Color类
     * @Date: 2023/2/27 9:48
     * @Return:
     **/
    public void load() {
        color = new LSB_Color(image);
        color.getColor();
    }

    /**
     * @Author: Nick Lee
     * @Description: 解密消息
     * @Date: 2023/2/27 9:48
     * @Return:
     **/
    public boolean Decrypt() {
        try {
            Decrypt decrypt = new Decrypt(color.getContent());
            decrypt.Decode();
            mes = decrypt.getMes();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Author: Nick Lee
     * @Description: 加密消息
     * @Date: 2023/2/27 9:49
     * @Return:
     **/
    public boolean Encrypt(String message) {
        try {
            mes = message;
            Encrypt encrypt = new Encrypt(mes);
            encrypt.Encode();
            color.setContent(encrypt.getBinary());
            color.setImage();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @Author: Nick Lee
     * @Description: 主控函数
     * @Date: 2023/2/27 9:49
     * @Return:
     **/
    public void LSB_Control(String sel, String cur, String read, String write) {
        if (readPic(read)) {
            load();
            if (sel.equals("r")) {
                Decrypt();
                System.out.println(mes);
            } else if (sel.equals("w")) {
                Encrypt(cur);
                if (writePic(write))
                    System.out.println("Encrypt Success");
            } else
                System.out.println("Wrong Input");
        } else
            System.out.println("Loading error");
    }

}
