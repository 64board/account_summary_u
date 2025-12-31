package pdf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author j4nei
 */
public class PdfFile {

    private String text;
    private List<String> lines;
    private File pdfFile;

    public PdfFile(String filePath) throws PdfFileException {

        pdfFile = new File(filePath);

        try (PDDocument document = PDDocument.load(pdfFile)) {

            AccessPermission ap = document.getCurrentAccessPermission();

            if (!ap.canExtractContent()) {
                throw new PdfFileException("You do not have permission to extract text.");
            }

            PDFTextStripper stripper = new PDFTextStripper();

            // This example uses sorting, but in some cases it is more useful to switch it off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(false);

            text = stripper.getText(document);
            extractLines();

        } catch (IOException e) {
            throw new PdfFileException(e.getMessage());
        }
    }

    public String getFileName() {
        return this.pdfFile.getName();
    }

    public String getText() {
        return text;
    }

    public List<String> getLines() {
        return lines;
    }

    private void extractLines() {
        this.lines = Arrays.asList(text.split("\n"));
    }

}
