package ImageDemo;

import com.twelvemonkeys.image.ImageUtil;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.tiff.Rational;
import com.twelvemonkeys.imageio.metadata.tiff.TIFF;
import com.twelvemonkeys.imageio.metadata.tiff.TIFFEntry;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageMetadata;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriteParam;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriter;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageTest03 {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // 只是通过 ImageIO 读取文件，然后再写一份文件，那么两个文件大小以及图片效果应该是相同的。
        // 结果是：色彩暗淡了一些
        String path = "/Users/adminqian/shenqb/some/";
        String filepath = path + "gmarbles.tif";

        String dest = path + "gmarbles-12-monkey-2(3).tif";
        ImageOutputStream stream = new FileImageOutputStream(new File(dest));

        BufferedImage bufferedImage = ImageIO.read(new File(filepath));
        // ImageWriter 的真实类其实是 TIFFImageWriter
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("tiff");
        //TIFFImageWriter tiffImageWriter = new TIFFImageWriter();



        if (iter.hasNext()) {
            ImageWriter writer = iter.next();

            //ImageWriteParam 其实是 TIFFImageWriteParam
            ImageWriteParam param = writer.getDefaultWriteParam();
            TIFFImageWriteParam p = (TIFFImageWriteParam)param;
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionType("LZW");

            //param.setTilingMode();
            //param.setCompressionQuality(0.5f);
            writer.setOutput(stream);

            //ImageIO.getWriterFormatNames();

            // writer.write(bi);

            Iterator<ImageReader> tif = ImageIO.getImageReadersByFormatName("tif");
            ImageInputStream iis = ImageIO.createImageInputStream(new File(filepath));
            ImageReader imageReader = tif.next();
            imageReader.setInput(iis, true);
            IIOMetadata imageMetadata = imageReader.getImageMetadata(0);

            IIOMetadata streamMetadata = imageReader.getStreamMetadata();


            //sIIOMetadata data = writer.getDefaultImageMetadata(new ImageTypeSpecifier(bufferedImage), param);
            //Node asTree = data.getAsTree("javax_imageio_jpeg_image_1.0");
//            Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
//            Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
//            jfif.setAttribute("Xdensity", dpi + "");
//            jfif.setAttribute("Ydensity", dpi + "");
//            jfif.setAttribute("resUnits", "1");

            final List<Entry> entries = new ArrayList<Entry>();
            entries.add(new TIFFEntry(TIFF.TAG_X_RESOLUTION, new Rational(300L)));
            entries.add(new TIFFEntry(TIFF.TAG_Y_RESOLUTION, new Rational(300L)));

            final IIOMetadata tiffImageMetadata = new TIFFImageMetadata(entries);

            //writer.write(null, new IIOImage(bufferedImage, null, tiffImageMetadata), param);
            writer.write(streamMetadata, new IIOImage(bufferedImage, null, imageMetadata), null);
            stream.close();
            writer.dispose();
        }
    }
}
