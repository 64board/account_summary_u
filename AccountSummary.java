package account_summary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author j4nei
 */
public class AccountSummary {

    private LocalDate date = null;
    private final List<AccountSummaryLine> lines;
    private final String[] columnsHeader = {"Acct", "Cash Ledger Balance",
        "Long Value", "Short Value", "Equity", "Daily P&L", "MTD P&L", "YTD P&L"};

    public AccountSummary() {
        lines = new ArrayList<>();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean foundDate() {
        return this.date != null;
    }
    
    public void addLine(AccountSummaryLine line) {
        lines.add(line);
    }

        private String getFileName() {
        return String.format("account_summary_%s.xlsx", date);
    }

    public void writeWorkBook() throws AccountSummaryException {

        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("AccountSummary");

        CreationHelper createHelper = workBook.getCreationHelper();

        CellStyle accountingCellStyle = workBook.createCellStyle();
        accountingCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)"));

        int rowCount = 0;
        Row row = sheet.createRow(rowCount++);
        for(int j = 0; j <= 7; j++) {
            row.createCell(j).setCellValue(columnsHeader[j]);
        }
        
        for (AccountSummaryLine asl : lines) {

            row = sheet.createRow(rowCount++);

            row.createCell(0).setCellValue(asl.getAccount());
            row.createCell(1).setCellValue(asl.getCash());
            row.createCell(2).setCellValue(asl.getLongValue());
            row.createCell(3).setCellValue(asl.getShortValue());
            row.createCell(4).setCellValue(asl.getEquity());
            row.createCell(5).setCellValue(asl.getDaily());
            row.createCell(6).setCellValue(asl.getMtd());
            row.createCell(7).setCellValue(asl.getYtd());

            for (int i = 1; i <= 7; i++) {
                row.getCell(i).setCellStyle(accountingCellStyle);
            }
        }

        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        try {
            try (FileOutputStream out = new FileOutputStream(getFileName())) {
                workBook.write(out);
                workBook.close();
            }
        } catch (IOException e) {
            throw new AccountSummaryException(e.getMessage());
        }
    }
}
