package bits.triangulation;

import java.nio.*;


/**
 * @author decamp
 */
public class Test {


    public static void main( String[] args ) throws Exception {
        TriangulationData d = TriangulationData.newInstance();

        DoubleBuffer pin = alloc( 8 * 2 * 8 ).asDoubleBuffer();
        pin.put( 0 ).put( 0 );
        pin.put( 1 ).put( 0 );
        pin.put( 0 ).put( 1 );
        pin.put( 1 ).put( 1 );

        pin.put( 0.25 ).put( 0.25 );
        pin.put( 0.75 ).put( 0.25 );
        pin.put( 0.75 ).put( 0.75 );
        pin.put( 0.25 ).put( 0.75 );
        pin.flip();

        IntBuffer ein = alloc( 8 * 2 * 4 ).asIntBuffer();
        ein.put( 0 ).put( 1 );
        ein.put( 1 ).put( 2 );
        ein.put( 2 ).put( 3 );
        ein.put( 3 ).put( 0 );
        ein.put( 4 ).put( 5 );
        ein.put( 5 ).put( 6 );
        ein.put( 6 ).put( 7 );
        ein.put( 7 ).put( 4 );
        ein.flip();

        DoubleBuffer hin = alloc( 8 * 2 * 1 ).asDoubleBuffer();
        hin.put( 0.5 ).put( 0.4445 );
        hin.flip();

        d.setPoints( pin );
        d.setSegments( ein );
        // d.setHoles(hin);


        Triangulation t = new Triangulation();
        t.verbose( true );
        t.planarStraightLineGraph( true );
        TriangulationData out = TriangulationData.newInstance();

        t.triangulate( d, out );

        System.out.println( out.getPointCount() + "\t" + out.getTriangleCount() );

        IntBuffer ind = alloc( out.getTriangleCount() * 3 * 4 ).asIntBuffer();
        out.getTriangles( ind );
        ind.flip();

        while( ind.remaining() >= 3 ) {
            System.out.format( "%d  %d  %d\n", ind.get(), ind.get(), ind.get() );
        }

        d.dispose();
        out.dispose();

    }


    private static ByteBuffer alloc( int len ) {
        return ByteBuffer.allocateDirect( len ).order( ByteOrder.nativeOrder() );
    }

}
