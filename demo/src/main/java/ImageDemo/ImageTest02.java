package ImageDemo;

import com.twelvemonkeys.image.ImageUtil;
import com.twelvemonkeys.imageio.metadata.tiff.Rational;
import com.twelvemonkeys.imageio.metadata.tiff.TIFF;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriteParam;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriter;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.tiff.TIFFEntry;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageMetadata;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImageTest02 {
    /**
     * 可以设置 LZW
     * 设置 dpi ？
     * 设置 colorModel ？
     * 设置 宽高 ？
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String path = "/Users/adminqian/shenqb/some/";
        String filepath = path + "gmarbles.tif";

        String dest = path + "gmarbles-12-monkey-2(1).tif";
        //FileOutputStream stream = new FileOutputStream(dest);
        ImageOutputStream stream = new FileImageOutputStream(new File(dest));

        BufferedImage bufferedImage = ImageIO.read(new File(filepath));

        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("tiff");

        //TIFFImageWriter tiffImageWriter = new TIFFImageWriter();


        if (iter.hasNext()) {
            ImageWriter writer = iter.next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            //TIFFImageWriteParam

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionType("LZW");
            //param.setCompressionType("Deflate");
            //param.setCompressionQuality(0.92f);
            param.setCompressionQuality(0.5f);
            writer.setOutput(stream);

            //ImageIO.getWriterFormatNames();

            // writer.write(bi);

            IIOMetadata data = writer.getDefaultImageMetadata(new ImageTypeSpecifier(bufferedImage), param);
            //Node asTree = data.getAsTree("javax_imageio_jpeg_image_1.0");
//            Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
//            Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
//            jfif.setAttribute("Xdensity", dpi + "");
//            jfif.setAttribute("Ydensity", dpi + "");
//            jfif.setAttribute("resUnits", "1");

            final List<Entry> entries = new ArrayList<Entry>();
            entries.add(new TIFFEntry(TIFF.TAG_X_RESOLUTION, new Rational(300L)));
            entries.add(new TIFFEntry(TIFF.TAG_Y_RESOLUTION, new Rational(300L)));

            int newWidth = bufferedImage.getWidth() / 4;
            int newHeight = bufferedImage.getHeight() / 4;

            // 调整大小，调整之后，色彩更暗淡了
            //BufferedImage resize = resize(bufferedImage, newWidth, newHeight);
            Image tmp = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            // 使用这个，颜色没有变淡
            BufferedImage resize = ImageUtil.toBuffered(tmp);

            final IIOMetadata tiffImageMetadata = new TIFFImageMetadata(entries);



//            writer.write(null, new IIOImage(bufferedImage, null, tiffImageMetadata), param);
            writer.write(null, new IIOImage(resize, null, tiffImageMetadata), param);
            stream.close();
            writer.dispose();
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

//    private static void setDPIViaDOM(IIOMetadata imageMetadata) throws IIOInvalidTreeException {
//        String METADATA_NAME = "com_sun_media_imageio_plugins_tiff_image_1.0";
//        int DPI_X = 300;
//        int DPI_Y = 300;
//        // Get the DOM tree.
//        IIOMetadataNode root = (IIOMetadataNode) imageMetadata.getAsTree(METADATA_NAME);
//
//        // Get the IFD.
//        IIOMetadataNode ifd = (IIOMetadataNode) root.getElementsByTagName("TIFFIFD").item(0);
//
//        // Get {X,Y}Resolution tags.
//        BaselineTIFFTagSet base = BaselineTIFFTagSet.getInstance();
//        TIFFTag tagXRes = base.getTag(BaselineTIFFTagSet.TAG_X_RESOLUTION);
//        TIFFTag tagYRes = base.getTag(BaselineTIFFTagSet.TAG_Y_RESOLUTION);
//
//        // Create {X,Y}Resolution nodes.
//        IIOMetadataNode nodeXRes = createRationalNode(tagXRes.getName(), tagXRes.getNumber(), DPI_X, 1);
//        IIOMetadataNode nodeYRes = createRationalNode(tagYRes.getName(), tagYRes.getNumber(), DPI_Y, 1);
//
//        // Append {X,Y}Resolution nodes to IFD node.
//        ifd.appendChild(nodeXRes);
//        ifd.appendChild(nodeYRes);
//
//        // Set metadata from tree.
//        imageMetadata.setFromTree(METADATA_NAME, root);
//    }
//
//    /**
//     * Creates a node of TIFF data type RATIONAL.
//     */
//    private static IIOMetadataNode createRationalNode(String tagName, int tagNumber, int numerator, int denominator) {
//        // Create the field node with tag name and number.
//        IIOMetadataNode field = new IIOMetadataNode("TIFFField");
//        field.setAttribute("name", tagName);
//        field.setAttribute("number", "" + tagNumber);
//
//        // Create the RATIONAL node.
//        IIOMetadataNode rational = new IIOMetadataNode("TIFFRational");
//        rational.setAttribute("value", numerator + "/" + denominator);
//
//        // Create the RATIONAL node and append RATIONAL node.
//        IIOMetadataNode rationals = new IIOMetadataNode("TIFFRationals");
//        rationals.appendChild(rational);
//
//        // Append RATIONALS node to field node.
//        field.appendChild(rationals);
//
//        return field;
//    }
}
