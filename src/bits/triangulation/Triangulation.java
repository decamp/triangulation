package bits.triangulation;


/**
 * Triangulation manages basic parameters for triangulation operations, which consist of converting
 * Planar Straight Line Graphs (PSLGs) into sets of triangles with several types of constraints
 * that may be defined on boundaries and on the shape of the resulting triangles.
 *
 * @see bits.triangulation.TriangulationData
 */
public class Triangulation {

    private boolean mVerbose   = false;
    private boolean mPslg      = false;
    private boolean mEdges     = false;
    private boolean mNeighbors = false;


    public void verbose( boolean verbose ) {
        mVerbose = verbose;
    }


    public void isPlanarStraightLineGraph( boolean pslg ) {
        mPslg = pslg;
    }

    /**
     * @param edges Specifies if edges should be copied to output.
     */
    public void computeEdges( boolean edges ) {
        mEdges = edges;
    }

    /**
     * @param neighbors Specifies whether to generate a list of neighbors for each triangle.
     */
    public void computeNeighbors( boolean neighbors ) {
        mNeighbors = neighbors;
    }


    public void triangulate( TriangulationData in, TriangulationData out ) {
        // 'z' indicates use zero-based indexing.
        StringBuilder s = new StringBuilder( "z" );

        if( !mVerbose )  s.append( " Q" );
        if( mPslg )      s.append( " p" );
        if( mEdges )     s.append( " e" );
        if( mNeighbors ) s.append( " n" );

        TriangulationData.triangulate( in, s.toString(), out );
    }

}
