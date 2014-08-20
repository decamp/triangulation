package bits.triangulation;

import java.nio.*;


/**
 * Used to pass data into and out of the triangulate operations.
 * <p>
 * TriangulationData defines a PLSG, or Planar Straight Line Graph.
 * By definition, a PSLG is just a list of vertices and segments.
 * Additionally, a TriangulationData object may also contain information
 * about holes and concavities, as well as regional attributes and
 * constraints on the areas of triangles.
 * <p>
 * Segments: Segments are edges whose presence in the triangulation is
 * enforced (although each segment may be subdivided into smaller edges).
 * Each segment is specified by listing the indices of its two endpoints.
 * This means that you must include its endpoints in the vertex list.
 * Each segment, like each vertex, may have a boundary marker.
 * <p>
 * Arrays are used to store points, triangles, markers, and so forth.  In
 * all cases, the first item in any array is stored starting at index [0].
 * However, that item is item number `1' unless the `z' switch is used, in
 * which case it is item number `0'.  Hence, you may find it easier to
 * index points (and triangles in the neighbor list) if you use the `z'
 * switch.  Unless, of course, you're calling Triangle from a Fortran
 * program.
 * <p>
 * Description of fields (except the `numberof' fields, which are obvious):
 * <p>
 * `pointlist':  An array of point coordinates.  The first point's x
 *   coordinate is at index [0] and its y coordinate at index [1], followed
 *   by the coordinates of the remaining points.  Each point occupies two
 *   REALs.
 * `pointattributelist':  An array of point attributes.  Each point's
 *   attributes occupy `numberofpointattributes' REALs.
 * `pointmarkerlist':  An array of point markers; one int per point.
 * <p>
 * `trianglelist':  An array of triangle corners.  The first triangle's
 *   first corner is at index [0], followed by its other two corners in
 *   counterclockwise order, followed by any other nodes if the triangle
 *   represents a nonlinear element.  Each triangle occupies
 *   `numberofcorners' ints.
 * `triangleattributelist':  An array of triangle attributes.  Each
 *   triangle's attributes occupy `numberoftriangleattributes' REALs.
 * `trianglearealist':  An array of triangle area constraints; one REAL per
 *   triangle.  Input only.
 * `neighborlist':  An array of triangle neighbors; three ints per
 *   triangle.  Output only.
 * <p>
 * `segmentlist':  An array of segment endpoints.  The first segment's
 *   endpoints are at indices [0] and [1], followed by the remaining
 *   segments.  Two ints per segment.
 * `segmentmarkerlist':  An array of segment markers; one int per segment.
 * <p>
 * `holelist':  An array of holes.  The first hole's x and y coordinates
 *   are at indices [0] and [1], followed by the remaining holes.  Two
 *   REALs per hole.  Input only, although the pointer is copied to the
 *   output structure for your convenience.
 * <p>
 * `regionlist':  An array of regional attributes and area constraints.
 *   The first constraint's x and y coordinates are at indices [0] and [1],
 *   followed by the regional attribute at index [2], followed by the
 *   maximum area at index [3], followed by the remaining area constraints.
 *   Four REALs per area constraint.  Note that each regional attribute is
 *   used only if you select the `A' switch, and each area constraint is
 *   used only if you select the `a' switch (with no number following), but
 *   omitting one of these switches does not change the memory layout.
 *   Input only, although the pointer is copied to the output structure for
 *   your convenience.
 * <p>
 * `edgelist':  An array of edge endpoints.  The first edge's endpoints are
 *   at indices [0] and [1], followed by the remaining edges.  Two ints per
 *   edge.  Output only.
 * `edgemarkerlist':  An array of edge markers; one int per edge.  Output
 *   only.
 * `normlist':  An array of normal vectors, used for infinite rays in
 *   Voronoi diagrams.  The first normal vector's x and y magnitudes are
 *   at indices [0] and [1], followed by the remaining vectors.  For each
 *   finite edge in a Voronoi diagram, the normal vector written is the
 *   zero vector.  Two REALs per edge.  Output only.
 * <p>
 *
 * Any input fields that Triangle will examine must be initialized.
 * Furthermore, for each output array that Triangle will write to, you
 * must either provide space by setting the appropriate pointer to point
 * to the space you want the data written to, or you must initialize the
 * pointer to NULL, which tells Triangle to allocate space for the results.
 * The latter option is preferable, because Triangle always knows exactly
 * how much space to allocate.  The former option is provided mainly for
 * people who need to call Triangle from Fortran code, though it also makes
 * possible some nasty space-saving tricks, like writing the output to the
 * same arrays as the input.
 * <p>
 * Triangle will not free() any input or output arrays, including those it
 * allocates itself; that's up to you.  You should free arrays allocated by
 * Triangle by calling the trifree() procedure defined below.  (By default,
 * trifree() just calls the standard free() library procedure, but
 * applications that call triangulate() may replace trimalloc() and
 * trifree() in triangle.c to use specialized memory allocators.)
 * <p>
 * Here's a guide to help you decide which fields you must initialize
 * before you call triangulate().
 * <p>
 * `in':
 *
 *   - `pointlist' must always point to a list of points; `numberofpoints'
 *     and `numberofpointattributes' must be properly set.
 *     `pointmarkerlist' must either be set to NULL (in which case all
 *     markers default to zero), or must point to a list of markers.  If
 *     `numberofpointattributes' is not zero, `pointattributelist' must
 *     point to a list of point attributes.
 *   - If the `r' switch is used, `trianglelist' must point to a list of
 *     triangles, and `numberoftriangles', `numberofcorners', and
 *     `numberoftriangleattributes' must be properly set.  If
 *     `numberoftriangleattributes' is not zero, `triangleattributelist'
 *     must point to a list of triangle attributes.  If the `a' switch is
 *     used (with no number following), `trianglearealist' must point to a
 *     list of triangle area constraints.  `neighborlist' may be ignored.
 *   - If the `p' switch is used, `segmentlist' must point to a list of
 *     segments, `numberofsegments' must be properly set, and
 *     `segmentmarkerlist' must either be set to NULL (in which case all
 *     markers default to zero), or must point to a list of markers.
 *   - If the `p' switch is used without the `r' switch, then
 *     `numberofholes' and `numberofregions' must be properly set.  If
 *     `numberofholes' is not zero, `holelist' must point to a list of
 *     holes.  If `numberofregions' is not zero, `regionlist' must point to
 *     a list of region constraints.
 *   - If the `p' switch is used, `holelist', `numberofholes',
 *     `regionlist', and `numberofregions' is copied to `out'.  (You can
 *     nonetheless get away with not initializing them if the `r' switch is
 *     used.)
 *   - `edgelist', `edgemarkerlist', `normlist', and `numberofedges' may be
 *     ignored.
 * <p>
 * `out':
 *
 *   - `pointlist' must be initialized (NULL or pointing to memory) unless
 *     the `N' switch is used.  `pointmarkerlist' must be initialized
 *     unless the `N' or `B' switch is used.  If `N' is not used and
 *     `in->numberofpointattributes' is not zero, `pointattributelist' must
 *     be initialized.
 *   - `trianglelist' must be initialized unless the `E' switch is used.
 *     `neighborlist' must be initialized if the `n' switch is used.  If
 *     the `E' switch is not used and (`in->numberofelementattributes' is
 *     not zero or the `A' switch is used), `elementattributelist' must be
 *     initialized.  `trianglearealist' may be ignored.
 *   - `segmentlist' must be initialized if the `p' or `c' switch is used,
 *     and the `P' switch is not used.  `segmentmarkerlist' must also be
 *     initialized under these circumstances unless the `B' switch is used.
 *   - `edgelist' must be initialized if the `e' switch is used.
 *     `edgemarkerlist' must be initialized if the `e' switch is used and
 *     the `B' switch is not.
 *   - `holelist', `regionlist', `normlist', and all scalars may be ignored.
 * <p>
 * `vorout' (only needed if `v' switch is used):
 *
 *   - `pointlist' must be initialized.  If `in->numberofpointattributes'
 *     is not zero, `pointattributelist' must be initialized.
 *     `pointmarkerlist' may be ignored.
 *   - `edgelist' and `normlist' must both be initialized.
 *     `edgemarkerlist' may be ignored.
 *   - Everything else may be ignored.
 *
 * After a call to triangulate(), the valid fields of `out' and `vorout'
 * will depend, in an obvious way, on the choice of switches used.  Note
 * that when the `p' switch is used, the pointers `holelist' and
 * `regionlist' are copied from `in' to `out', but no new space is
 * allocated; be careful that you don't free() the same array twice.  On
 * the other hand, Triangle will never readPointerField the `pointlist'
 * pointer (or any  others); new space is allocated for `out->pointlist', or
 * if the `N' switch is used, `out->pointlist' remains uninitialized.
 * <p>
 * All of the meaningful `numberof' fields will be properly set; for
 * instance, `numberofedges' will represent the number of edges in the
 * triangulation whether or not the edges were written.  If segments are
 * not used, `numberofsegments' will indicate the number of boundary edges.
 */
public final class TriangulationData {

    public static TriangulationData create() {
        TriangulationLib.init();
        long p = nAlloc();
        if( p == 0 ) {
            throw new OutOfMemoryError();
        }
        return new TriangulationData( p );
    }


    private static final int SIZE_INT  = 4;
    private static final int SIZE_REAL = 8;

    private static final int FIELD_POINTS           = 0;
    private static final int FIELD_POINT_ATTS       = 1;
    private static final int FIELD_POINT_MARKERS    = 2;
    private static final int FIELD_POINT_COUNT      = 3;
    private static final int FIELD_POINT_ATT_COUNT  = 4;
    private static final int FIELD_TRIS             = 5;
    private static final int FIELD_TRI_ATTS         = 6;
    private static final int FIELD_TRI_AREAS        = 7;
    private static final int FIELD_TRI_NEIGHBORS    = 8;
    private static final int FIELD_TRI_COUNT        = 9;
    private static final int FIELD_TRI_CORNER_COUNT = 10;
    private static final int FIELD_TRI_ATT_COUNT    = 11;
    private static final int FIELD_SEGMENTS         = 12;
    private static final int FIELD_SEGMENT_MARKERS  = 13;
    private static final int FIELD_SEGMENT_COUNT    = 14;
    private static final int FIELD_HOLES            = 15;
    private static final int FIELD_HOLE_COUNT       = 16;
    private static final int FIELD_REGIONS          = 17;
    private static final int FIELD_REGION_COUNT     = 18;
    private static final int FIELD_EDGES            = 19;
    private static final int FIELD_EDGE_MARKERS     = 20;
    private static final int FIELD_EDGE_NORMS       = 21;
    private static final int FIELD_EDGE_COUNT       = 22;

    private static final int    NUM_FIELDS = 23;
    private static final long[] OFFS       = new long[NUM_FIELDS];


    private long mPointer;

    // Maintain references to avoid garbage collection and track what requires native de-allocation.
    private final Object[] mRefs = new Object[NUM_FIELDS];


    private TriangulationData( long pointer ) {
        mPointer = pointer;
    }


    public boolean hasPoints() {
        return isNonNull( FIELD_POINTS );
    }


    public boolean hasPointAttributes() {
        return isNonNull( FIELD_POINT_ATTS );
    }


    public boolean hasPointMarkers() {
        return isNonNull( FIELD_POINT_MARKERS );
    }


    public boolean hasTriangles() {
        return isNonNull( FIELD_TRIS );
    }


    public boolean hasTriangleAttributes() {
        return isNonNull( FIELD_TRI_ATTS );
    }


    public boolean hasTriangleAreas() {
        return isNonNull( FIELD_TRI_AREAS );
    }


    public boolean hasTriangleNeighbors() {
        return isNonNull( FIELD_TRI_NEIGHBORS );
    }


    public boolean hasSegments() {
        return isNonNull( FIELD_SEGMENTS );
    }


    public boolean hasSegmentMarkers() {
        return isNonNull( FIELD_SEGMENT_MARKERS );
    }


    public boolean hasHoles() {
        return isNonNull( FIELD_HOLES );
    }


    public boolean hasRegions() {
        return isNonNull( FIELD_REGIONS );
    }


    public boolean hasEdges() {
        return isNonNull( FIELD_EDGES );
    }


    public boolean hasEdgeMarkers() {
        return isNonNull( FIELD_EDGE_MARKERS );
    }


    public boolean hasNorms() {
        return isNonNull( FIELD_EDGE_NORMS );
    }


    public int pointCount() {
        return nGetInt( mPointer + OFFS[FIELD_POINT_COUNT] );
    }


    public int attributesPerPoint() {
        return nGetInt( mPointer + OFFS[FIELD_POINT_ATT_COUNT] );
    }


    public int triangleCount() {
        return nGetInt( mPointer + OFFS[FIELD_TRI_COUNT] );
    }


    public int cornersPerTriangle() {
        return nGetInt( mPointer + OFFS[FIELD_TRI_CORNER_COUNT] );
    }


    public int attributesPerTriangle() {
        return nGetInt( mPointer + OFFS[FIELD_TRI_ATT_COUNT] );
    }


    public int segmentCount() {
        return nGetInt( mPointer + OFFS[ FIELD_SEGMENT_COUNT ] );
    }


    public int holeCount() {
        return nGetInt( mPointer + OFFS[FIELD_HOLE_COUNT] );
    }


    public int regionCount() {
        return nGetInt( mPointer + OFFS[FIELD_REGION_COUNT] );
    }


    public int edgeCount() {
        return nGetInt( mPointer + OFFS[FIELD_EDGE_COUNT] );
    }


    public int pointBufferSize() {
        return hasPoints() ? pointCount() * 16 : 0;
    }


    public int pointAttributeBufferSize() {
        return hasPointAttributes() ? pointCount() * attributesPerPoint() * SIZE_REAL : 0;
    }


    public int pointMarkersBufferSize() {
        return hasPointMarkers() ? pointCount() * SIZE_INT : 0;
    }


    public int triangleBufferSize() {
        return hasTriangles() ? triangleCount() * (3 * SIZE_INT) : 0;
    }


    public int triangleAttributesBufferSize() {
        return hasTriangleAttributes() ? triangleCount() * (attributesPerTriangle() * SIZE_REAL) : 0;
    }


    public int triangleAreaBufferSize() {
        return hasTriangleAreas() ? triangleCount() * SIZE_REAL : 0;
    }


    public int triangleNeighborBufferSize() {
        return hasTriangleNeighbors() ? triangleCount() * (3 * SIZE_INT) : 0;
    }


    public int segmentBufferSize() {
        return hasSegments() ? segmentCount() * (2 * SIZE_INT) : 0;
    }


    public int segmentMarkerBufferSize() {
        return hasSegmentMarkers() ? segmentCount() * SIZE_INT : 0;
    }


    public int holeBufferSize() {
        return hasHoles() ? holeCount() * (2 * SIZE_REAL) : 0;
    }


    public int regionBufferSize() {
        return hasRegions() ? regionCount() * (4 * SIZE_REAL) : 0;
    }


    public int edgeBufferSize() {
        return hasEdges() ? edgeCount() * (2 * SIZE_INT) : 0;
    }


    public int edgeMarkerBufferSize() {
        return hasEdgeMarkers() ? edgeCount() * SIZE_INT : 0;
    }


    public int normBufferSize() {
        return hasNorms() ? edgeCount() * (2 * SIZE_REAL) : 0;
    }


    public void copyPoints( ByteBuffer out ) {
        readPointerField( FIELD_POINTS, out, pointBufferSize() );
    }


    public ByteBuffer copyPoints() {
        return readPointerField( FIELD_POINTS, pointBufferSize() );
    }


    public void copyPointAttributes( ByteBuffer out ) {
        readPointerField( FIELD_POINT_ATTS, out, pointAttributeBufferSize() );
    }


    public ByteBuffer copyPointAttributes() {
        return readPointerField( FIELD_POINT_ATTS, pointAttributeBufferSize() );
    }


    public void copyPointMarkers( ByteBuffer out ) {
        readPointerField( FIELD_POINT_MARKERS, out, pointMarkersBufferSize() );
    }


    public ByteBuffer copyPointMarkers() {
        return readPointerField( FIELD_POINT_MARKERS, pointMarkersBufferSize() );
    }


    public void copyTriangles( ByteBuffer out ) {
        readPointerField( FIELD_TRIS, out, triangleBufferSize() );
    }


    public ByteBuffer copyTriangles() {
        return readPointerField( FIELD_TRIS, triangleBufferSize() );
    }


    public void copyTriangleAttributes( ByteBuffer out ) {
        readPointerField( FIELD_TRI_ATTS, out, triangleAttributesBufferSize() );
    }


    public ByteBuffer copyTriangleAttributes() {
        return readPointerField( FIELD_TRI_ATTS, triangleAttributesBufferSize() );
    }


    public void copyTriangleAreas( ByteBuffer out ) {
        readPointerField( FIELD_TRI_AREAS, out, triangleAreaBufferSize() );
    }


    public ByteBuffer copyTriangleAreas() {
        return readPointerField( FIELD_TRI_AREAS, triangleAreaBufferSize() );
    }


    public void copyTriangleNeighbors( ByteBuffer out ) {
        readPointerField( FIELD_TRI_NEIGHBORS, out, triangleNeighborBufferSize() );
    }


    public ByteBuffer copyTriangleNeighbors() {
        return readPointerField( FIELD_TRI_NEIGHBORS, triangleNeighborBufferSize() );
    }


    public void copySegments( ByteBuffer out ) {
        readPointerField( FIELD_SEGMENTS, out, segmentBufferSize() );
    }


    public ByteBuffer copySegments() {
        return readPointerField( FIELD_SEGMENTS, segmentBufferSize() );
    }


    public void copySegmentMarkers( ByteBuffer out ) {
        readPointerField( FIELD_SEGMENT_MARKERS, out, segmentMarkerBufferSize() );
    }


    public ByteBuffer copySegmentMarkers() {
        return readPointerField( FIELD_SEGMENT_MARKERS, segmentMarkerBufferSize() );
    }


    public void copyHoles( ByteBuffer out ) {
        readPointerField( FIELD_HOLES, out, holeBufferSize() );
    }


    public ByteBuffer copyHoles() {
        return readPointerField( FIELD_HOLES, holeBufferSize() );
    }


    public void copyRegions( ByteBuffer out ) {
        readPointerField( FIELD_REGIONS, out, regionBufferSize() );
    }


    public ByteBuffer copyRegions() {
        return readPointerField( FIELD_REGIONS, regionBufferSize() );
    }


    public void copyEdges( ByteBuffer out ) {
        readPointerField( FIELD_EDGES, out, edgeBufferSize() );
    }


    public ByteBuffer copyEdges() {
        return readPointerField( FIELD_EDGES, edgeBufferSize() );
    }


    public void copyEdgeMarkers( ByteBuffer out ) {
        readPointerField( FIELD_EDGE_MARKERS, out, edgeMarkerBufferSize() );
    }


    public ByteBuffer copyEdgeMarkers() {
        return readPointerField( FIELD_EDGE_MARKERS, edgeMarkerBufferSize() );
    }


    public void copyEdgeNorms( ByteBuffer out ) {
        readPointerField( FIELD_EDGE_NORMS, out, normBufferSize() );
    }


    public ByteBuffer copyEdgeNorms() {
        return readPointerField( FIELD_EDGE_NORMS, normBufferSize() );
    }



    public void setPoints( ByteBuffer buf ) {
        setBufferField( FIELD_POINTS, buf );
        nSetInt( mPointer + OFFS[FIELD_POINT_COUNT], buf.remaining() / ( 2 * SIZE_REAL ) );
    }


    public void setPointAttributes( ByteBuffer buf, int attsPerPoint ) {
        setBufferField( FIELD_POINT_ATTS, buf );
        nSetInt( mPointer + OFFS[FIELD_POINT_ATT_COUNT], attsPerPoint );
    }


    public void setPointMarkers( ByteBuffer buf ) {
        setBufferField( FIELD_POINT_MARKERS, buf );
    }


    public void setTriangles( ByteBuffer buf ) {
        setBufferField( FIELD_TRIS, buf );
        nSetInt( mPointer + OFFS[FIELD_TRI_COUNT], buf == null ? 0 : buf.remaining() / ( 3 * SIZE_INT ) );
    }


    public void setTriangleAttributes( ByteBuffer buf ) {
        setBufferField( FIELD_TRI_ATTS, buf );
    }


    public void setTriangleAreas( ByteBuffer buf ) {
        setBufferField( FIELD_TRI_AREAS, buf );
    }


    public void setTriangleNeighbors( ByteBuffer buf ) {
        setBufferField( FIELD_TRI_NEIGHBORS, buf );
    }


    public void setSegments( ByteBuffer buf ) {
        setBufferField( FIELD_SEGMENTS, buf );
        nSetInt( mPointer + OFFS[FIELD_SEGMENT_COUNT], buf == null ? 0 : buf.remaining() / ( 2 * SIZE_INT ) );
    }


    public void setSegmentMarkers( ByteBuffer buf ) {
        setBufferField( FIELD_SEGMENT_MARKERS, buf );
    }


    public void setHoles( ByteBuffer buf ) {
        setBufferField( FIELD_HOLES, buf );
        nSetInt( mPointer + OFFS[FIELD_HOLE_COUNT], buf == null ? 0 : buf.remaining() / ( 2 * SIZE_REAL ) );
    }


    public void setRegions( ByteBuffer buf ) {
        setBufferField( FIELD_REGIONS, buf );
        nSetInt( mPointer + OFFS[FIELD_SEGMENTS], buf == null ? 0 : buf.remaining() / ( 4 * SIZE_REAL ) );
    }


    public void setEdges( ByteBuffer buf ) {
        setBufferField( FIELD_EDGES, buf );
        nSetInt( mPointer + OFFS[FIELD_EDGE_COUNT], buf == null ? 0 : buf.remaining() / ( 2 * SIZE_INT ) );
    }


    public void setEdgeMarkers( ByteBuffer buf ) {
        setBufferField( FIELD_EDGE_MARKERS, buf );
    }


    public void setEdgeNorms( ByteBuffer buf ) {
        setBufferField( FIELD_EDGE_NORMS, buf );
    }


    public synchronized void release() {
        if( mPointer == 0 ) {
            return;
        }

        long p = mPointer;
        mPointer = 0;

        for( int i = 0; i < NUM_FIELDS; i++ ) {
            if( mRefs[i] != null ) {
                mRefs[i] = null;
                nSetBuffer( p + OFFS[i], null );
            }
        }

        nFreeTriangleStruct( p );
    }

    @Override
    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }


    private boolean isNonNull( int field ) {
        return nGetLong( mPointer + OFFS[field] ) != 0L;
    }


    private void setBufferField( int field, Buffer buf ) {
        if( mRefs[field] == null ) {
            long prev = nGetLong( mPointer + OFFS[field] );
            if( prev != 0L ) {
                nFree( prev );
            }
        }

        mRefs[field] = buf;
        nSetBuffer( mPointer + OFFS[field], buf );
    }


    private ByteBuffer readPointerField( int field, int len ) {
        ByteBuffer ret = createBuf( len );
        readPointerField( field, ret, len );
        ret.flip();
        return ret;
    }


    private void readPointerField( int srcField, ByteBuffer dst, int len ) {
        int pos = dst.position();
        int n   = dst.remaining();
        if( len < n ) {
            n = len;
        }
        nCopy( nGetLong( mPointer + OFFS[srcField] ), nNativeAddress( dst ) + pos, n );
        dst.position( pos + n );
    }


    static void initClass() {
        nGetFieldOffsets( OFFS );
    }


    static void triangulate( TriangulationData src, String flags, TriangulationData dst ) {
        nTriangulate( src.mPointer, flags, dst.mPointer );
        // Triangle may copy two pointers from the input struct to the output struct,
        // so we need to copy those references.
        dst.mRefs[ FIELD_HOLES ]   = src.mRefs[ FIELD_HOLES ];
        dst.mRefs[ FIELD_REGIONS ] = src.mRefs[ FIELD_REGIONS ];
    }



    private static ByteBuffer createBuf( int size ) {
        ByteBuffer bb = ByteBuffer.allocateDirect( size );
        bb.order( ByteOrder.nativeOrder() );
        return bb;
    }


    private static native void nTriangulate( long inPtr, String flags, long outPtr );

    private static native long nAlloc();

    private static native void nFree( long ptr );

    private static native void nFreeTriangleStruct( long ptr );

    private static native int nGetInt( long ptr );

    private static native void nSetInt( long ptr, int val );

    private static native long nGetLong( long ptr );

    private static native void nSetLong( long ptr, long val );

    private static native float nGetFloat( long ptr );

    private static native void nSetFloat( long ptr, float val );

    private static native double nGetDouble( long ptr );

    private static native void nSetDouble( long ptr, double val );

    private static native void nSetBuffer( long ptr, Buffer buf );

    private static native long nNativeAddress( Buffer buf );

    private static native void nCopy( long srcPtr, long dstPtr, int bytes );

    private static native void nGetFieldOffsets( long[] out );

}
