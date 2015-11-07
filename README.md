THE TRIANGLE LIBRARY:
This is a Java Wrapper for Jonathan Richard Shewchuk's (Triangle library)[https://www.cs.cmu.edu/~quake/triangle.html].
See src/main/c/README for more information on the library itself. In short, Triangle performs fast triangulations of
point sets and planar straight line graphs with support for different constraint types.


BUILDING:

*Java*
1. ant

*C*
The C code is precompiled for OS X and located in lib/libtriangulation.jnilib.
To recompile,
1. cd src/main/c
2. make

To compile for other platforms, you'll need to update the makefile.


RUNTIME:
1. All files in "lib" and "dist" directories (after build) are required in classpath. 
2. You'll need the libtriangle.jnilib file.  
3. Remember to add "-Djava.library.path=<lib dir>" to your JVM flags.


WRAPPER BY:
Philip DeCamp