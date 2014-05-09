package bits.triangulation;


/**
 * Triangulatio manages basic options for triangulation operations, which consist of converting Planar Straight Line
 * Graphs (PSLGs) into sets of triangles with several types of constraints that may be defined on boundaries and on
 * the shape of the resulting triangles.
 *
 * @author decamp
 * @see bits.triangulation.TriangulationData
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
        // 'z' indicates use zero-based indexing.
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

        TriangulationData.triangulate( in, s.toString(), out );
    }

}
