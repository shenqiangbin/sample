package ImageDemo;

import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageWriteParam;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class ImageTest03_1 {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // 只是通过 ImageIO 读取文件，然后再写一份文件，那么两个文件大小以及图片效果应该是相同的。
        // 结果是：色彩暗淡了一些
        String path = getDir();
        String filepath = Paths.get(path, "gmarbles.tif").toString();

        Iterator<ImageReader> tif = ImageIO.getImageReadersByFormatName("tif");
        ImageReader imageReader = tif.next();
        ImageInputStream iis = ImageIO.createImageInputStream(new File(filepath));
        imageReader.setInput(iis, true);
        IIOMetadata imageMetadata = imageReader.getImageMetadata(0);
        IIOMetadata streamMetadata = imageReader.getStreamMetadata();
        BufferedImage bufferedImage = imageReader.read(0);

        //BufferedImage bufferedImage = ImageIO.read(new File(filepath));


        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("tif");
        ImageWriter writer = iter.next();

        String[] types = new String[]{"CCITT RLE", "CCITT T.4", "CCITT T.6", "LZW", "JPEG", "ZLib", "PackBits", "Deflate"};


        String dest = Paths.get(path, "gmarbles-12-" + types[7] + "-0.5f-monkey.tif").toString();
        ImageOutputStream stream = new FileImageOutputStream(new File(dest));

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionType(types[7]);
        param.setCompressionQuality(0.5f);

        writer.setOutput(stream);
        writer.write(streamMetadata, new IIOImage(bufferedImage, null, imageMetadata), param);
        stream.flush();
        stream.close();
        writer.dispose();

    }

    public static String getDir() {
        String userDir = System.getProperty("user.dir");
        Path path = Paths.get(userDir, "img");
        System.out.println(path.toString());
        return path.toString();
    }


}
