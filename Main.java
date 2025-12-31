package main;

import account_summary.LineMatcher;
import account_summary.AccountSummary;
import account_summary.AccountSummaryException;
import account_summary.DateMatcher;
import account_summary.AccountSummaryLine;
import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pdf.PdfFile;
import pdf.PdfFileException;

/**
 *
 * @author j4nei
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            logger.info("{}", Version.getString());

            String filesPath = ".";

            for (File f : findPdfFiles(filesPath)) {

                PdfFile pf = new PdfFile(f.getAbsolutePath());

                logger.info("Processing file {} ...", pf.getFileName());

                AccountSummary as = new AccountSummary();

                for (String line : pf.getLines()) {

                    // Search for the TRADE DATE line to extract date
                    if (!as.foundDate() && DateMatcher.matches(line)) {
                        LocalDate tradeDate = DateMatcher.getTradeDate(line);
                        as.setDate(tradeDate);
                    }

                    // Process line only if date was found
                    if (as.foundDate() && LineMatcher.matches(line)) {
                        AccountSummaryLine asl = new AccountSummaryLine(line);
                        as.addLine(asl);
                    }
                }

                if (as.foundDate()) {
                    logger.info("Creating Excel file ...");
                    try {
                        as.writeWorkBook();
                    } catch (AccountSummaryException e) {
                        logger.error("Error creating Excel file {}", e.getMessage());
                    }
                } else {
                    logger.error("No Trade Date was found!");
                }
            }
        } catch (PdfFileException e) {
            logger.info("{}", e.getMessage());
        }
        
        logger.info("Finished.");
    }

    private static File[] findPdfFiles(String path) {
        File directoryPath = new File(path);

        logger.info("Searching PDF files in folder {} ...", directoryPath.getAbsolutePath());

        FilenameFilter pdfFileFilter = (File dir, String name) -> {
            String lowercaseName = name.toLowerCase();
            return lowercaseName.endsWith(".pdf");
        };

        File[] pdfFilesList = directoryPath.listFiles(pdfFileFilter);

        if (pdfFilesList.length > 0) {
            logger.info("Found {} PDF file(s) ...", pdfFilesList.length);
        } else {
            logger.error("No PDF files found!");
        }

        return pdfFilesList;
    }
}
