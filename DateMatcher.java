package account_summary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author J.Ernesto
 */
public class DateMatcher {

    //private static final Pattern PATTERN = Pattern.compile("^\\s*TRADE\\s+DATE\\s+((\\d?)\\d\\/(\\d?)\\d\\/\\d{1,2})\\s*$");
    private static final Pattern PATTERN = Pattern.compile("^\\s*TRADE\\s+DATE\\s+((\\d?)\\d\\/(\\d?)\\d\\/\\d{1,2})\\s*$",
            Pattern.UNICODE_CHARACTER_CLASS);
    
    public static boolean matches(String line) {
        
        // UNICODE in REGEX.
        // \p{Z} is any separator (including all space characters)
        // \p{C} is invisible/control characters
        
//        String lineb = line;
//
//        line = line.replaceAll("^[\\p{Z}\\p{C}]+|[\\p{Z}\\p{C}]+$", "");
//        line = line.replaceAll("[\\p{Z}\\p{C}]+", " ");
//        
//        if (line.contains("TRADE DATE 6")) {
//            System.out.println(">" + lineb + "<");
//            for (int i = 0; i < lineb.length(); i++) {
//                System.out.printf("char[%d] = '%c' (U+%04X)%n", i,
//                    lineb.charAt(i), (int)lineb.charAt(i));
//            }
//
//            System.out.println(">" + line.trim() + "<");
//            for (int i = 0; i < line.length(); i++) {
//                System.out.printf("char[%d] = '%c' (U+%04X)%n", i,
//                    line.charAt(i), (int)line.charAt(i));
//            }
//        }
        
        Matcher matcher = PATTERN.matcher(line);
    
        boolean matchFound = matcher.find();

        return matchFound;
    }
    
    public static LocalDate getTradeDate(String line) {
        
        LocalDate date = null;
        
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {

            String d = matcher.group(1);
            String m1 = matcher.group(2);
            String d1 = matcher.group(3);
            
            String parsingPattern;

            if (m1.isEmpty()) {
                parsingPattern = "M/";
            } else {
                parsingPattern = "MM/";
            }
            
            if (d1.isEmpty()) {
                parsingPattern += "d/";
            } else {
                parsingPattern += "dd/";
            }
            
            parsingPattern += "yy";

            date = LocalDate.parse(d, DateTimeFormatter.ofPattern(parsingPattern));
        }
        
        return date;
    }
}
