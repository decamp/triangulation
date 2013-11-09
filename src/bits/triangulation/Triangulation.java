package bits.triangulation;


/**
 * @author decamp
 */
public class Triangulation {

    private boolean mVerbose = false;
    private boolean mPslg    = false;
    private boolean mEdges   = false;
    

    public void verbose( boolean verbose ) {
        mVerbose = verbose;
    }


    public void planarStraightLineGraph( boolean pslg ) {
        mPslg = pslg;
    }


    public void computeEdges( boolean edges ) {
        mEdges = edges;
    }


    public void triangulate( TriangulationData in, TriangulationData out ) {
        StringBuilder s = new StringBuilder( "z" );

        if( !mVerbose ) {
            s.append( " Q" );
        }

        if( mPslg ) {
            s.append( " p" );
        }

        if( mEdges ) {
            s.append( " e" );
        }

        TriangulationData.triangulate( in.pointer(), s.toString(), out.pointer() );
    }


}
