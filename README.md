### Triangulation Library
This is a Java Wrapper for Jonathan Richard Shewchuk's [Triangle library](https://www.cs.cmu.edu/~quake/triangle.html).

Triangle performs fast triangulations of point sets and planar straight line graphs with support for 
different constraint types. See [src/main/c/README](src/main/c/README) for more information about Triangle.


### Build

**Java**  
1. $ ant

**C**  
The C code is precompiled for OS X and located in lib/libtriangulation.jnilib.  
To recompile:   
1. $ cd src/main/c  
2. $ make  

To compile for other platforms, you'll need to update the makefile.


### Runtime
1. All files in "lib" and "dist" directories (after build) are required in classpath.   
2. You'll need the libtriangle.jnilib file.  
3. Remember to add "-Djava.library.path=<lib dir>" to your JVM flags.  


---  
Wrapper by: Philip DeCamp