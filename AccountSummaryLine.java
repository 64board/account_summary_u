package account_summary;

/**
 *
 * @author j4nei
 */
public class AccountSummaryLine {
    
    private final String account;
    private String accountName;
    private final String cash;
    private final String longValue;
    private final String shortValue;
    private final String equity;
    private final String daily;
    private final String mtd;
    private final String ytd;
    
    public AccountSummaryLine(String line) {
        
        line = line.replaceAll("^[\\p{Z}\\p{C}]+|[\\p{Z}\\p{C}]+$", "");
        line = line.replaceAll("[\\p{Z}\\p{C}]+", " ");
        
        String[] fields = line.trim().split("\\s+");
        
        ytd = fields[fields.length - 1];
        mtd = fields[fields.length - 2];
        daily = fields[fields.length - 3];
        equity = fields[fields.length - 4];
        shortValue = fields[fields.length - 5];
        longValue = fields[fields.length - 6];
        cash = fields[fields.length - 7];
        
        accountName = "";
        for(int i = 1; i < fields.length - 7; i++) {
            accountName += fields[i] + " ";
        }
        
        account = fields[0];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccountSummaryLine{account=").append(account);
        sb.append(", accountName=").append(accountName);
        sb.append(", cash=").append(cash);
        sb.append(", longValue=").append(longValue);
        sb.append(", shortValue=").append(shortValue);
        sb.append(", equity=").append(equity);
        sb.append(", daily=").append(daily);
        sb.append(", mtd=").append(mtd);
        sb.append(", ytd=").append(ytd);
        sb.append('}');
        return sb.toString();
    }

    public String getAccount() {
        return account;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getCash() {
        return parseDouble(cash);
    }

    public double getLongValue() {
        return parseDouble(longValue);
    }

    public double getShortValue() {
        return parseDouble(shortValue);
    }

    public double getEquity() {
        return parseDouble(equity);
    }

    public double getDaily() {
        return parseDouble(daily);
    }

    public double getMtd() {
        return parseDouble(mtd);
    }

    public double getYtd() {
        return parseDouble(ytd);
    }
    
    private double parseDouble(String s) {
        return Double.valueOf(s.replace("(", "-").replace(")", "").replace(",", ""));
    }
}
