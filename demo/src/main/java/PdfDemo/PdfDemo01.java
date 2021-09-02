package PdfDemo;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStreamImpl;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

public class PdfDemo01 {

    public static void main(String[] args) throws IOException {
        String folderPath = PathUtil.getPath("file");
        String file = Paths.get(folderPath, "Hive编程指南.pdf").toString();
        test(file);
    }

    static void test(String file) throws IOException {

        PDDocument document = PDDocument.load(new File(file));

        PDPageTree pages = document.getDocumentCatalog().getPages();
        Iterator<PDPage> iter = pages.iterator();
        int count = 0;
        while (iter.hasNext()) {
            PDPage page = iter.next();
            PDResources resources = page.getResources();
            for (COSName c : resources.getXObjectNames()) {
                PDXObject o = resources.getXObject(c);
                // https://github.com/mkl-public/testarea-itext5/blob/master/src/test/java/mkl/testarea/itext5/extract/ImageExtraction.java
                if (o instanceof PDImageXObject) {
                    PDImageXObject image = (PDImageXObject) o;


                    //File file = new File(imageDir, pageIndex + "-" + System.nanoTime() + "." + img.getSuffix());
                    FileOutputStream stream = new FileOutputStream(new File("/Users/adminqian/Desktop/tmp/compress2/" + count++ + "-min2." + image.getSuffix()));
                    //ImageIO.write(((PDImageXObject) o).getImage(), image.getSuffix(), stream);
                    //ImageIO.write(((PDImageXObject) o).getImage(), "tif", stream);
                    //Thumbnails.of(image.getImage()).scale(1).outputQuality(1f).outputFormat(image.getSuffix()).toOutputStream(stream);

//                    FileImageOutputStream stream2 = new FileImageOutputStream(new File("/Users/adminqian/Desktop/tmp/compress/" + count++ + "-min2." + image.getSuffix()));
//                    toImg(image.getSuffix(), stream2, image.getImage());
                    //TiffOutput.TiffOutput(image.getImage(), stream, 300);

                    //stream.flush();
                    //stream.close();

                    ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                    MemoryCacheImageOutputStream outputStream = new MemoryCacheImageOutputStream(imgBytes);

                    toImg(image.getSuffix(), outputStream, image.getImage(), o.getStream());

                    PDImageXObject newObj = PDImageXObject.createFromByteArray(document, imgBytes.toByteArray(), "1");
                    resources.put(c, newObj);

                }
            }
            System.out.println("ok");
        }

        String newFile = PathUtil.getNewFileName(file, "压缩2");
        document.save(new FileOutputStream(newFile));

    }

    static void toImg(String format, ImageOutputStreamImpl stream, RenderedImage fileBufferd, PDStream pdStream) throws IOException {
        //这么写是为了防止使用ImageIO.write后失真


        Iterator<ImageReader> tif = ImageIO.getImageReadersByFormatName("tif");
        ImageReader imageReader = tif.next();
        ImageInputStream iis = ImageIO.createImageInputStream(pdStream);
        imageReader.setInput(iis, true);
        //IIOMetadata imageMetadata = imageReader.getImageMetadata(0);
        IIOMetadata imageMetadata = null;
        //IIOMetadata streamMetadata = imageReader.getStreamMetadata();
        IIOMetadata streamMetadata = null;
        //BufferedImage bufferedImage = imageReader.read(0);

        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(format);
        if (iter.hasNext()) {
            ImageWriter writer = iter.next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            //param.setTilingMode();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            //param.setCompressionType("CCITT T.6");
            //param.setCompressionType("JPEGJPEG");
            param.setCompressionType("LZW");
            param.setCompressionQuality(0.5f);


            //ImageTypeSpecifier imageType = ImageTypeSpecifier.createFromRenderedImage(fileBufferd);
            //TIFFImageMetadata imageMetadata = (TIFFImageMetadata) writer.getDefaultImageMetadata(imageType, param);
            //imageMetadata = createImageMetadata(imageMetadata, fileBufferd.getHeight(), fileBufferd.getWidth(), dpi, compression, fileBufferd.getType());

            //param.setCompressionQuality(0.2f);
//            param.setCompressionQuality(1f);
            writer.setOutput(stream);
            // writer.write(bi);
            writer.write(streamMetadata, new IIOImage(fileBufferd, null, imageMetadata), param);
            stream.flush();
            stream.close();
            writer.dispose();
        }
    }
}
