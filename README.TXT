THE TRIANGLE LIBRARY:
This is a Java Wrapper for Jonathan Richard Shewchuk's (Triangle library)[https://www.cs.cmu.edu/~quake/triangle.html].
See src_c/README for more information on the library itself. In short, Triangle performs fast triangulations
of a set of points with different constraints.


BUILDING:
1. ant


RUNTIME:
1. All files in "lib" and "dist" directories (after build) are required in classpath. 
2. You'll need the libtriangle.jnilib file.  
3. Remember to add "-Djava.library.path=<lib dir>" to your JVM flags.


WRAPPER BY:
Philip DeCamp