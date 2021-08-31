package ImageDemo;

import com.twelvemonkeys.image.ImageUtil;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.tiff.Rational;
import com.twelvemonkeys.imageio.metadata.tiff.TIFF;
import com.twelvemonkeys.imageio.metadata.tiff.TIFFEntry;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageMetadata;
import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageTest04 {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {

        // 查看图片的信息，比较两者的差异
        String path = "/Users/adminqian/shenqb/some/";
        String filepath = path + "gmarbles.tif";
        String dest = path + "gmarbles-12-monkey-2(3).tif";

        BufferedImage bufferedImage = ImageIO.read(new File(filepath));
        BufferedImage bufferedImageDest = ImageIO.read(new File(dest));

        Iterator<ImageReader> tif = ImageIO.getImageReadersByFormatName("tif");
        ImageInputStream iis = ImageIO.createImageInputStream(new File(dest));
        if (tif.hasNext()) {
            ImageReader imageReader = tif.next();
            imageReader.setInput(iis, true);

            IIOMetadata streamMetadata = imageReader.getStreamMetadata();
            IIOMetadata imageMetadata = imageReader.getImageMetadata(0);

            Node node = imageMetadata.getAsTree("com_sun_media_imageio_plugins_tiff_image_1.0");
            IIOMetadata imageMetadata2 = new TIFFImageMetadata();
            imageMetadata2.mergeTree("com_sun_media_imageio_plugins_tiff_image_1.0", node);

            System.out.println("ok");
        }

        System.out.printf(getInfo(bufferedImage));
        System.out.printf(getInfo(bufferedImageDest));

        System.out.printf("over");
    }

    public static String getInfo(BufferedImage bufferedImage) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("宽：%s", bufferedImage.getWidth())).append("\r\n");
        builder.append(String.format("高：%s", bufferedImage.getHeight())).append("\r\n");
        builder.append(String.format("图片类型：%s", typeToDesc(bufferedImage.getType()))).append("\r\n");
        builder.append(String.format("色彩模型：%s", colorModelInfo(bufferedImage.getColorModel()))).append("\r\n");

        builder.append("属性\r\n");

        String[] propertyNames = bufferedImage.getPropertyNames();
        if (propertyNames != null) {
            for (String property : propertyNames) {
                if (property == null) continue;
                Object val = bufferedImage.getProperty(property);
                builder.append(String.format("key：%s ，val: %s", property, val)).append("\r\n");
            }
        }

        return builder.toString();
    }

    public static String colorModelInfo(ColorModel colorModel) {
        return colorModel.toString();
    }

    public static String typeToDesc(int type) throws Exception {
        switch (type) {
            case BufferedImage.TYPE_3BYTE_BGR:
                return "TYPE_3BYTE_BGR";
            case BufferedImage.TYPE_4BYTE_ABGR:
                return "TYPE_4BYTE_ABGR";
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                return "TYPE_4BYTE_ABGR_PRE";
            case BufferedImage.TYPE_BYTE_BINARY:
                return "TYPE_BYTE_BINARY";
            case BufferedImage.TYPE_BYTE_GRAY:
                return "TYPE_BYTE_GRAY";
            case BufferedImage.TYPE_BYTE_INDEXED:
                return "TYPE_BYTE_INDEXED";
            case BufferedImage.TYPE_CUSTOM:
                return "TYPE_CUSTOM";
            case BufferedImage.TYPE_INT_ARGB:
                return "TYPE_INT_ARGB";
            case BufferedImage.TYPE_INT_ARGB_PRE:
                return "TYPE_INT_ARGB_PRE";
            case BufferedImage.TYPE_INT_BGR:
                return "TYPE_INT_BGR";
            case BufferedImage.TYPE_INT_RGB:
                return "TYPE_INT_RGB";
            case BufferedImage.TYPE_USHORT_555_RGB:
                return "TYPE_USHORT_555_RGB";
            case BufferedImage.TYPE_USHORT_565_RGB:
                return "TYPE_USHORT_565_RGB";
            case BufferedImage.TYPE_USHORT_GRAY:
                return "TYPE_USHORT_GRAY";
            default:
                throw new Exception("not know" + type);
        }
    }
}
