package bits.triangulation;

import java.nio.*;


/**
 * @author decamp
 */
public class TriangulationData {


    public static TriangulationData newInstance() {
        TriangulationLib.init();
        long p = alloc();
        if( p == 0 ) {
            throw new RuntimeException( "Failed to allocate " + TriangulationData.class.getName() + " object." );
        }
        return new TriangulationData( p );
    }


    private long mPointer;

    // Maintain references to avoid garbage collection.
    private DoubleBuffer mPointBuf;
    private IntBuffer mSegmentBuf;
    private DoubleBuffer mHoleBuf;


    private TriangulationData( long pointer ) {
        mPointer = pointer;
    }



    public void setPoints( DoubleBuffer buf ) {
        mPointBuf = buf;
        setPoints( mPointer, buf, buf.position(), buf.remaining() / 2 );
    }


    public void setSegments( IntBuffer buf ) {
        mSegmentBuf = buf;
        setSegments( mPointer, buf, buf.position(), buf.remaining() / 2 );
    }


    public void setHoles( DoubleBuffer buf ) {
        mHoleBuf = buf;
        setHoles( mPointer, buf, buf.position(), buf.remaining() / 2 );
    }


    public int getPointCount() {
        return getPointCount( mPointer );
    }


    public void getPoints( DoubleBuffer out ) {
        int pos = out.position();
        int n = getPoints( mPointer, out, pos );
        out.position( pos + n * 2 );
    }


    public int getTriangleCount() {
        return getTriangleCount( mPointer );
    }


    public void getTriangles( IntBuffer out ) {
        int pos = out.position();
        int n = getTriangles( mPointer, out, pos );
        out.position( pos + n * 3 );
    }


    public int getEdgeCount() {
        return getEdgeCount( mPointer );
    }


    public void getEdges( IntBuffer out ) {
        int pos = out.position();
        int n = getEdges( mPointer, out, pos );
        out.position( pos + n * 2 );
    }



    public synchronized void dispose() {
        if( mPointer == 0 )
            return;

        long p = mPointer;
        mPointer = 0;

        free( p, mPointBuf == null, mSegmentBuf == null, true );
    }



    @Override
    protected void finalize() throws Throwable {
        dispose();
    }


    long pointer() {
        return mPointer;
    }



    private static native long alloc();

    private static native void free( long ptr, boolean freePoints, boolean freeSegs, boolean freeTris );

    private static native void setPoints( long ptr, DoubleBuffer buf, int pos, int len );

    private static native void setSegments( long ptr, IntBuffer buf, int pos, int len );

    private static native void setHoles( long ptr, DoubleBuffer buf, int pos, int len );

    private static native int getPointCount( long ptr );

    private static native int getPoints( long ptr, DoubleBuffer buf, int pos );

    private static native int getTriangleCount( long ptr );

    private static native int getTriangles( long ptr, IntBuffer buf, int pos );

    private static native int getEdgeCount( long ptr );

    private static native int getEdges( long ptr, IntBuffer buf, int pos );

    static native void triangulate( long inPtr, String flags, long outPtr );

}
