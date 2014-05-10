package bits.triangulation;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;


/**
 * @author Philip DeCamp
 */
public class TriangulationRenderer {

    public static BufferedImage render( TriangulationData data, int w, int h ) {
        BufferedImage im = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = (Graphics2D)im.getGraphics();
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setBackground( Color.BLACK );
        g.clearRect( 0, 0, w, h );
        render( data, g, w, h );
        return im;
    }


    public static void render( TriangulationData data, Graphics2D g, int w, int h ) {
        // Compute bounds.
        int[][] verts;
        int[] vertMarks = null;

        Mapper m;

        {
            double x0 = Double.POSITIVE_INFINITY;
            double y0 = Double.POSITIVE_INFINITY;
            double x1 = Double.NEGATIVE_INFINITY;
            double y1 = Double.NEGATIVE_INFINITY;

            ByteBuffer pointBuf = data.copyPoints();
            pointBuf.mark();

            while( pointBuf.remaining() >= 16 ) {
                double x = pointBuf.getDouble();
                double y = pointBuf.getDouble();
                x0 = Math.min( x, x0 );
                x1 = Math.max( x, x1 );
                y0 = Math.min( y, y0 );
                y1 = Math.max( y, y1 );
            }

            m = new Mapper( x0, y0, x1, y1, w, h );

            pointBuf.rewind();
            verts = new int[pointBuf.remaining() / 16][2];

            for( int i = 0; i < verts.length; i++ ) {
                verts[i][0] = m.x( pointBuf.getDouble( 16*i ) );
                verts[i][1] = m.y( pointBuf.getDouble( 16 * i + 8 ) );
            }
        }

        if( data.hasPointMarkers() ) {
            vertMarks = new int[ verts.length ];
            data.copyPointMarkers().asIntBuffer().get( vertMarks );
        }


        // Render triangles.
        if( data.hasTriangles() ) {
            Color fillColor   = new Color( 0f, 0f, 1f, 0.5f );
            Color strokeColor = new Color( 0.5f, 0.5f, 1f, 1f );
            g.setStroke( new BasicStroke( 1f ) );
            ByteBuffer bb = data.copyTriangles();

            while( bb.remaining() >= 12 ) {
                Path2D path = new Path2D.Double();

                for( int i = 0; i < 3; i++ ) {
                    int[] v = verts[bb.getInt()];
                    if( i == 0 ) {
                        path.moveTo( v[0], v[1] );
                    } else {
                        path.lineTo( v[0], v[1] );
                    }
                }

                path.closePath();
                g.setColor( fillColor );
                g.fill( path );
                g.setColor( strokeColor );
                g.draw( path );
            }
        }


        // Render edges.
        if( data.hasEdges() ) {
            ByteBuffer marks = null;
            if( data.hasEdgeMarkers() ) {
                marks = data.copyEdgeMarkers();
            }

            g.setStroke( new BasicStroke( 3f ) );
            ByteBuffer bb = data.copyEdges();

            while( bb.remaining() >= 8 ) {
                int[] a = verts[ bb.getInt() ];
                int[] b = verts[ bb.getInt() ];

                if( marks == null ) {
                    g.setColor( Color.GREEN );
                } else {
                    g.setColor( markerColor( marks.getInt() ) );
                }

                g.drawLine( a[0], a[1], b[0], b[1] );
            }
        }

        // Render segments.
        if( data.hasSegments() ) {
            ByteBuffer marks = null;
            if( data.hasSegmentMarkers() ) {
                marks = data.copySegmentMarkers();
            }

            g.setStroke( new BasicStroke( 3f ) );
            g.setColor( new Color( 1f, 1f, 0f, 0.5f ) );
            ByteBuffer bb = data.copySegments();

            while( bb.remaining() >= 8 ) {
                int ia = bb.getInt();
                int ib = bb.getInt();
                int[] a = verts[ ia ];
                int[] b = verts[ ib ];

                if( marks == null ) {
                    g.setColor( Color.GREEN );
                } else {
                    g.setColor( markerColor( marks.getInt() ) );
                }

                g.drawLine( a[0], a[1], b[0], b[1] );
            }
        }


        // Render points.
        {
            Color normColor = new Color( 1f, 1f, 1f, 0.8f );
            Color edgeColor = new Color( 0f, 1f, 1f, 0.8f );

            for( int i = 0; i < verts.length; i++ ) {
                if( vertMarks != null && vertMarks[i] != 0 ) {
                    g.setColor( edgeColor );
                } else {
                    g.setColor( normColor );
                }

                int[] vert = verts[i];
                g.fillOval( vert[0] - 2, vert[1] - 2, 5, 5 );
            }

        }

        // Render Holes
        if( data.hasHoles() ) {
            g.setColor( new Color( 1f, 0f, 0f, 0.8f ) );
            ByteBuffer bb = data.copyHoles();
            while( bb.remaining() >= 16 ) {
                int x = m.x( bb.getDouble() );
                int y = m.y( bb.getDouble() );
                g.fillOval( x - 2, y - 2, 5, 5 );
            }
        }

    }


    private static Color markerColor( int m ) {
        return COLORS[ m % COLORS.length ];
    }


    private static final Color[] COLORS = {
        fade( Color.ORANGE ),
        fade( Color.MAGENTA ),
        fade( Color.CYAN ),
        fade( Color.YELLOW ),
        fade( Color.GREEN )
    };


    private static Color fade( Color c ) {
        return new Color( c.getRed(), c.getGreen(), c.getBlue(), 255 * 80 / 100 );
    }


    private static class Mapper {

        private final double mScale;
        private final double mTransX;
        private final double mTransY;

        Mapper( double x0, double y0, double x1, double y1, double ow, double oh ) {
            double sx = x0 < x1 ? ow / ( x1 - x0 ) : Double.NaN;
            double sy = y0 < y1 ? oh / ( y1 - y0 ) : Double.NaN;

            double scale;
            if( sx == sx ) {
                if( sy == sy ) {
                    scale = Math.min( sx, sy );
                } else {
                    scale = sx;
                }
            } else if( sy == sy ) {
                scale = sy;
            } else {
                scale = 1.0;
            }

            scale *= 0.9;

            mScale = scale;
            mTransX = 0.5 * ow - 0.5 * ( x0 + x1 ) * scale;
            mTransY = 0.5 * oh - 0.5 * ( y0 + y1 ) * scale;
        }


        public int x( double v ) {
            return (int)( v * mScale + mTransX + 0.5 );
        }

        public int y( double v ) {
            return (int)( v * mScale + mTransY + 0.5 );
        }

    }

}
