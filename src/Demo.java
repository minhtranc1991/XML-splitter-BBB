import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Demo {
    public static void main(String[] s) throws Exception {
        String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "\n";
//        String suffix = "\n</Products>\n";
        String outputPath = "xml/out.xml";
        int count = 0;
        try {
            int i = 0;
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader xsr = xif.createXMLStreamReader(new FileReader("seProducts.xml"));
            xsr.nextTag(); // Advance to statements element

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            while (xsr.nextTag() == XMLStreamConstants.START_ELEMENT) {
                File file = new File(outputPath);
                FileOutputStream fos = new FileOutputStream(file, true);
                t.transform(new StAXSource(xsr), new StreamResult(fos));
                count++;
                if (count == 1000) {
                    i++;
                    outputPath = "xml/out" + i + ".xml";
                    count = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}