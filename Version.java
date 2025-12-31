package main;

/******************************************************************************
 * Returns a string version
 * @author janeiros@mbfcc.com
 ******************************************************************************/
public class Version {

    // First working version.
    // private static final String version = "*** Account Summary v2021.06.11-1 ***";
    
    // Completely functional version.
    // private static final String version = "*** Account Summary v2021.06.12-1 ***";
    
    // Version that handles UNICODE space chars in the lines.
    private static final String version = "*** Account Summary UNICODE v2025.06.27-1 ***";
    
    public static String getString() {
        return version;
    }
}
