#include "TriangulationData.h"

#include <stdlib.h>
#include <string.h>
#include <stddef.h>
#include <stdio.h>

#define REAL double
#include "triangle.h"


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nTriangulate
(JNIEnv *env, jclass clazz, jlong inPtr, jstring flags, jlong outPtr)
{
	struct triangulateio* sin  = *(struct triangulateio**)&inPtr;
	struct triangulateio* sout = *(struct triangulateio**)&outPtr;
	char* fl = (char*)(*env)->GetStringUTFChars(env, flags, 0);
	triangulate(fl, sin, sout, 0);
	(*env)->ReleaseStringUTFChars( env, flags, fl );
    fflush( stdout );
}


JNIEXPORT jlong JNICALL Java_bits_triangulation_TriangulationData_nAlloc
(JNIEnv *env, jclass clazz)
{
	struct triangulateio* s = (struct triangulateio*)calloc( 1, sizeof(struct triangulateio) );
	return *(jlong*)&s;
}


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nFree
(JNIEnv *env, jclass clazz, jlong ptr)
{
    trifree( *(void**)&ptr );
}

JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nFreeTriangleStruct
(JNIEnv *env, jclass clazz, jlong ptr)
{
    if( ptr == 0 ) {
        return;
    }

	struct triangulateio* s = *(struct triangulateio**)&ptr;
        
    if( s->pointlist ) {
        trifree( s->pointlist );
    }
    if( s->pointattributelist ) {
        trifree( s->pointattributelist );
    }
    if( s->pointmarkerlist ) {
        trifree( s->pointmarkerlist );
    }
    if( s->trianglelist ) {
        trifree( s->trianglelist );
    }
    if( s->trianglearealist ) {
        trifree( s->trianglearealist );
    }
    if( s->neighborlist ) {
        trifree( s->neighborlist );
    }
    if( s->segmentlist ) {
        trifree( s->segmentlist );
    }
    if( s->segmentmarkerlist ) {
        trifree( s->segmentmarkerlist );
    }
    if( s->holelist ) {
        trifree( s->holelist );
    }
    if( s->regionlist ) {
        trifree( s->regionlist );
    }
    if( s->edgelist ) {
        trifree( s->edgelist );
    }
    if( s->edgemarkerlist ) {
        trifree( s->edgemarkerlist );
    }
    if( s->normlist ) {
        trifree( s->normlist );
    }

    free( s );
}



JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_nGetInt
(JNIEnv *env, jclass clazz, jlong ptr)
{
    return **(jint**)&ptr;
}

JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetInt
(JNIEnv *env, jclass clazz, jlong ptr, jint val)
{
    **(jint**)&ptr = val;
}

JNIEXPORT jlong JNICALL Java_bits_triangulation_TriangulationData_nGetLong
(JNIEnv *env, jclass clazz, jlong ptr)
{
    return **(jlong**)&ptr;
}

JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetLong
(JNIEnv *env, jclass clazz, jlong ptr, jlong val)
{
    **(jlong**)&ptr = val;
}


JNIEXPORT jfloat JNICALL Java_bits_triangulation_TriangulationData_nGetFloat
(JNIEnv *env, jclass clazz, jlong ptr)
{
    return **(jfloat**)&ptr;
}


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetFloat
(JNIEnv *env, jclass clazz, jlong ptr, jfloat val)
{
    **(jfloat**)&ptr = val;
}


JNIEXPORT jdouble JNICALL Java_bits_triangulation_TriangulationData_nGetDouble
(JNIEnv *env, jclass clazz, jlong ptr)
{
    return **(jdouble**)&ptr;
}


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetDouble
(JNIEnv *env, jclass clazz, jlong ptr, jdouble val)
{
    **(jdouble**)&ptr = val;
}



JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetBuffer
(JNIEnv *env, jclass clazz, jlong ptr, jobject buf)
{
	REAL** s = *(REAL***)&ptr;
    if( buf ) {
        *s = (REAL*)(*env)->GetDirectBufferAddress(env, buf);
    } else {
        *s = NULL;
    }
}


JNIEXPORT jlong JNICALL Java_bits_triangulation_TriangulationData_nNativeAddress
(JNIEnv *env, jclass clazz, jobject buf)
{
    char* data = (*env)->GetDirectBufferAddress(env, buf);
    return (jlong)data;
}


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nCopy
(JNIEnv *env, jclass clazz, jlong src, jlong dst, jint len)
{
    memcpy( *(void**)&dst, *(const void**)&src, len );
}


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nGetFieldOffsets
(JNIEnv *env, jclass clazz, jlongArray arrObj)
{
    jlong *arr = (*env)->GetLongArrayElements( env, arrObj, NULL );
    
    arr[ 0] = offsetof( struct triangulateio, pointlist );
    arr[ 1] = offsetof( struct triangulateio, pointattributelist );
    arr[ 2] = offsetof( struct triangulateio, pointmarkerlist );
    arr[ 3] = offsetof( struct triangulateio, numberofpoints );
    arr[ 4] = offsetof( struct triangulateio, numberofpointattributes );
    arr[ 5] = offsetof( struct triangulateio, trianglelist );
    arr[ 6] = offsetof( struct triangulateio, triangleattributelist );
    arr[ 7] = offsetof( struct triangulateio, trianglearealist );
    arr[ 8] = offsetof( struct triangulateio, neighborlist );
    arr[ 9] = offsetof( struct triangulateio, numberoftriangles );
    arr[10] = offsetof( struct triangulateio, numberofcorners );
    arr[11] = offsetof( struct triangulateio, numberoftriangleattributes );
    arr[12] = offsetof( struct triangulateio, segmentlist );
    arr[13] = offsetof( struct triangulateio, segmentmarkerlist );
    arr[14] = offsetof( struct triangulateio, numberofsegments );
    arr[15] = offsetof( struct triangulateio, holelist );
    arr[16] = offsetof( struct triangulateio, numberofholes );
    arr[17] = offsetof( struct triangulateio, regionlist );
    arr[18] = offsetof( struct triangulateio, numberofregions );
    arr[19] = offsetof( struct triangulateio, edgelist );
    arr[20] = offsetof( struct triangulateio, edgemarkerlist );
    arr[21] = offsetof( struct triangulateio, normlist );
    arr[22] = offsetof( struct triangulateio, numberofedges );
        
    (*env)->ReleaseLongArrayElements( env, arrObj, arr, 0 );
}




