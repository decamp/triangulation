package bits.triangulation;

import java.nio.*;


/**
 * @author decamp
 */
public class TriangulationTest {


    public static void main( String[] args ) throws Exception {
        test2();
    }


    static void test1() throws Exception {
        TriangulationData d = TriangulationData.create();
    }


    static void test2() throws Exception {
        TriangulationData d = TriangulationData.create();

        ByteBuffer pin = alloc( 8 * 2 * 8 );
        pin.putDouble( 0 ).putDouble( 0 );
        pin.putDouble( 1 ).putDouble( 0 );
        pin.putDouble( 0 ).putDouble( 1 );
        pin.putDouble( 1 ).putDouble( 1 );

        pin.putDouble( 0.25 ).putDouble( 0.25 );
        pin.putDouble( 0.75 ).putDouble( 0.25 );
        pin.putDouble( 0.75 ).putDouble( 0.75 );
        pin.putDouble( 0.25 ).putDouble( 0.75 );
        pin.flip();

        ByteBuffer markin = alloc( 8 * 4 );
        for( int i = 0; i < 4; i++ ) {
            markin.putInt( 3 );
        }
        for( int i = 0; i < 4; i++ ) {
            markin.putInt( 1 );
        }
        markin.flip();


        ByteBuffer ein  = alloc( 8 * 2 * 4 );
        ByteBuffer emin = alloc( 8 * 4 );
        ein.putInt( 0 ).putInt( 1 ); emin.putInt( 1 );
        ein.putInt( 1 ).putInt( 2 ); emin.putInt( 1 );
        ein.putInt( 2 ).putInt( 3 ); emin.putInt( 1 );
        ein.putInt( 3 ).putInt( 0 ); emin.putInt( 1 );
        ein.putInt( 4 ).putInt( 5 ); emin.putInt( 1 );
        ein.putInt( 5 ).putInt( 6 ); emin.putInt( 1 );
        ein.putInt( 6 ).putInt( 7 ); emin.putInt( 1 );
        ein.putInt( 7 ).putInt( 4 ); emin.putInt( 1 );
        ein.flip();

        ByteBuffer hin = alloc( 8 * 2 * 1 );
        hin.putDouble( 0.5 ).putDouble( 0.4445 );
        hin.flip();

        d.setPoints( pin );
        d.setSegments( ein );
        d.setHoles( hin );

//        ImagePanel.showImage( TriangulationRenderer.render( d, 1500, 1500 ) );

        Triangulation t = new Triangulation();
        t.verbose( true );
        t.isPlanarStraightLineGraph( true );
        t.computeNeighbors( true );
        //t.computeEdges( true );

        TriangulationData out = TriangulationData.create();
        t.triangulate( d, out );

        System.out.println( out.pointCount() + "\t" + out.triangleCount() );
        ByteBuffer ind = out.copyTriangles();

        while( ind.remaining() >= 24 ) {
            System.out.format( "%d  %d  %d\n", ind.getInt(), ind.getInt(), ind.getInt() );
        }

        d.release();

        ImagePanel.showImage( TriangulationRenderer.render( out, 1500, 1500 ) );

        out.release();

        System.out.println( "OKAY" );

    }


    private static ByteBuffer alloc( int len ) {
        return ByteBuffer.allocateDirect( len ).order( ByteOrder.nativeOrder() );
    }

}
