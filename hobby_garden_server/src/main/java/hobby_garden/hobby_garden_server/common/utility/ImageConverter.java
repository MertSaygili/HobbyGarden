package hobby_garden.hobby_garden_server.common.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageConverter {
    public BufferedImage base64StringToImage(String base64String) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        return ImageIO.read(bis);
    }
}
