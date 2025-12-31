package account_summary;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author J.Ernesto
 */
public class LineMatcher {

    // Process lines that end like .32 or .00)
    private static final Pattern PATTERN = Pattern.compile("\\.\\d{2}\\)?\\s*$",
            Pattern.UNICODE_CHARACTER_CLASS);
    
    public static boolean matches(String line) {
        
        Matcher matcher = PATTERN.matcher(line);
    
        boolean matchFound = matcher.find();
        
        // Discard the last 2 lines.
        if (line.contains("USD") || line.contains("NETTING")) {
            matchFound = false;
        }
        
        return matchFound;
    }
}
