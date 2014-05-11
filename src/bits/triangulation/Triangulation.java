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


    public void computeEdges( boolean edges ) {
        mEdges = edges;
    }


    public void computeNeighbors( boolean neighbors ) {
        mNeighbors = neighbors;
    }


    /* Switches for the triangulator.
     * poly: -p switch.
     * refine: -r switch.
     * quality: -q switch.
     * minangle: minimum angle bound, specified after -q switch.
     * goodangle: cosine squared of minangle.
     * offconstant: constant used to place off-center Steiner points.
     * vararea: -a switch without number.
     * fixedarea: -a switch with number.
     * maxarea: maximum area bound, specified after -a switch.
     * usertest: -u switch.
     * regionattrib: -A switch.  convex: -c switch.
     * weighted: 1 for -w switch, 2 for -W switch.  jettison: -j switch
     * firstnumber: inverse of -z switch.  All items are numbered starting from `firstnumber'.
     * edgesout: -e switch.
     * voronoi: -v switch.
     * neighbors: -n switch.
     * geomview: -g switch.
     * nobound: -B switch.
     * nopolywritten: -P switch.
     * nonodewritten: -N switch.
     * noelewritten: -E switch.
     * noiterationnum: -I switch.
     * noholes: -O switch.
     * noexact: -X switch.
     * order: element order, specified after -o switch.
     * nobisect: count of how often -Y switch is selected.
     * steiner: maximum number of Steiner points, specified after -S switch.
     * incremental: -i switch.
     * sweepline: -F switch.
     * dwyer: inverse of -l switch.
     * splitseg: -s switch.
     * conformdel: -D switch.
     * docheck: -C switch.
     * quiet: -Q switch.
     * verbose: count of how often -V switch is selected.
     * usesegments: -p, -r, -q, or -c switch; determines whether segments are used at all.
     * Read the instructions to find out the meaning of these switches.
     */

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
