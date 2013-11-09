package bits.triangulation;

/**
 * @author decamp
 */
public final class TriangulationLib {

    private static boolean sInit = false;


    public static synchronized void init() {
        if( sInit ) {
            return;
        }
        System.loadLibrary( "triangulation" );
        sInit = true;
    }


    private TriangulationLib() {}

}
