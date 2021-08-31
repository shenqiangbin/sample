//package ImageDemo;
//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import org.apache.commons.imaging.*;
//import org.apache.commons.imaging.formats.png.PngConstants;
//import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
//
//import java.awt.*;
//import java.awt.image.ComponentColorModel;
//import java.awt.color.ColorSpace;
//import java.awt.image.BufferedImage;
//import java.awt.image.ColorModel;
//import java.awt.image.DataBuffer;
//import java.awt.image.DirectColorModel;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 参考：https://github.com/apache/commons-imaging
// * https://github.com/apache/commons-imaging/tree/master/src/test/java/org/apache/commons/imaging/examples
// */
//public class ImageTest01 {
//
//    public static void main(String[] args) throws ImageWriteException, ImageReadException, IOException {
//        fileToByte();
//        getInfo();
//    }
//
//
//    public static void fileToByte() throws IOException, ImageReadException, ImageWriteException {
//
//        String path = "/Users/adminqian/shenqb/some/";
//
//        String filepath = path + "gmarbles.tif";
//        File file = new File(filepath);
//        BufferedImage bufferedImage = Imaging.getBufferedImage(file);
//
//        String dest = path + "gmarbles-apache.tif";
//        FileOutputStream fileOutputStream = new FileOutputStream(dest);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put(ImagingConstants.PARAM_KEY_COMPRESSION, TiffConstants.TIFF_COMPRESSION_LZW);
//
//
//
//        //设置 dpi
//        //PixelDensity pixelDensity = PixelDensity.createFromPixelsPerInch(300, 300);
//        //map.put(ImagingConstants.PARAM_KEY_PIXEL_DENSITY, pixelDensity);
//
//        // 设置色彩空间
//        //TiffConstants.
//        //map.put(ImagingConstants.PARAM_KEY_STRICT)
//
////        ColorModel.getRGBdefault()
////        new ColorTools().correctImage(bufferedImage, file);
//
////        DirectColorModel model = new DirectColorModel(8,
////                0,       // Red
////                0,       // Green
////                0,       // Blue
////                0        // Alpha
////        );
//
//        // 卡到了无法修改色彩空间
//        //new ColorTools().relabelColorSpace(bufferedImage, ColorSpace.getInstance(ColorSpace.CS_GRAY));
//        //new ColorTools().relabelColorSpace(bufferedImage, createColorModel(8));
//
//        //Imaging.writeImageToBytes(bufferedImage, ImageFormats.TIFF, map);
//        Imaging.writeImage(bufferedImage, fileOutputStream, ImageFormats.TIFF, map);
//    }
//
//    public static ColorModel createColorModel(int bpc) throws IOException {
//        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
//        int[] nBits = {bpc};
//        ColorModel colorModel = new ComponentColorModel(cs, nBits, false, false,
//                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
//        return colorModel;
//    }
//
//    public static void getInfo() throws IOException, ImageReadException {
//
//        String path = "/Users/adminqian/shenqb/some/";
//        String filepath = path + "gmarbles.tif";
//        ImageInfo imageInfo = Imaging.getImageInfo(new File(filepath));
//        System.out.println(imageInfo);
//
//    }
//}
