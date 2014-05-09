/* DO NOT EDIT THIS FILE - it is machine generated */
#include <JavaVM/jni.h>
/* Header for class bits_triangulation_TriangulationData */

#ifndef _Included_bits_triangulation_TriangulationData
#define _Included_bits_triangulation_TriangulationData
#ifdef __cplusplus
extern "C" {
#endif
#undef bits_triangulation_TriangulationData_SIZE_INT
#define bits_triangulation_TriangulationData_SIZE_INT 4L
#undef bits_triangulation_TriangulationData_SIZE_REAL
#define bits_triangulation_TriangulationData_SIZE_REAL 8L
#undef bits_triangulation_TriangulationData_FIELD_POINTS
#define bits_triangulation_TriangulationData_FIELD_POINTS 0L
#undef bits_triangulation_TriangulationData_FIELD_POINT_ATTS
#define bits_triangulation_TriangulationData_FIELD_POINT_ATTS 1L
#undef bits_triangulation_TriangulationData_FIELD_POINT_MARKERS
#define bits_triangulation_TriangulationData_FIELD_POINT_MARKERS 2L
#undef bits_triangulation_TriangulationData_FIELD_POINT_COUNT
#define bits_triangulation_TriangulationData_FIELD_POINT_COUNT 3L
#undef bits_triangulation_TriangulationData_FIELD_POINT_ATT_COUNT
#define bits_triangulation_TriangulationData_FIELD_POINT_ATT_COUNT 4L
#undef bits_triangulation_TriangulationData_FIELD_TRIS
#define bits_triangulation_TriangulationData_FIELD_TRIS 5L
#undef bits_triangulation_TriangulationData_FIELD_TRI_ATTS
#define bits_triangulation_TriangulationData_FIELD_TRI_ATTS 6L
#undef bits_triangulation_TriangulationData_FIELD_TRI_AREAS
#define bits_triangulation_TriangulationData_FIELD_TRI_AREAS 7L
#undef bits_triangulation_TriangulationData_FIELD_TRI_NEIGHBORS
#define bits_triangulation_TriangulationData_FIELD_TRI_NEIGHBORS 8L
#undef bits_triangulation_TriangulationData_FIELD_TRI_COUNT
#define bits_triangulation_TriangulationData_FIELD_TRI_COUNT 9L
#undef bits_triangulation_TriangulationData_FIELD_TRI_CORNER_COUNT
#define bits_triangulation_TriangulationData_FIELD_TRI_CORNER_COUNT 10L
#undef bits_triangulation_TriangulationData_FIELD_TRI_ATT_COUNT
#define bits_triangulation_TriangulationData_FIELD_TRI_ATT_COUNT 11L
#undef bits_triangulation_TriangulationData_FIELD_SEGMENTS
#define bits_triangulation_TriangulationData_FIELD_SEGMENTS 12L
#undef bits_triangulation_TriangulationData_FIELD_SEGMENT_MARKERS
#define bits_triangulation_TriangulationData_FIELD_SEGMENT_MARKERS 13L
#undef bits_triangulation_TriangulationData_FIELD_SEGMENT_COUNT
#define bits_triangulation_TriangulationData_FIELD_SEGMENT_COUNT 14L
#undef bits_triangulation_TriangulationData_FIELD_HOLES
#define bits_triangulation_TriangulationData_FIELD_HOLES 15L
#undef bits_triangulation_TriangulationData_FIELD_HOLE_COUNT
#define bits_triangulation_TriangulationData_FIELD_HOLE_COUNT 16L
#undef bits_triangulation_TriangulationData_FIELD_REGIONS
#define bits_triangulation_TriangulationData_FIELD_REGIONS 17L
#undef bits_triangulation_TriangulationData_FIELD_REGION_COUNT
#define bits_triangulation_TriangulationData_FIELD_REGION_COUNT 18L
#undef bits_triangulation_TriangulationData_FIELD_EDGES
#define bits_triangulation_TriangulationData_FIELD_EDGES 19L
#undef bits_triangulation_TriangulationData_FIELD_EDGE_MARKERS
#define bits_triangulation_TriangulationData_FIELD_EDGE_MARKERS 20L
#undef bits_triangulation_TriangulationData_FIELD_EDGE_NORMS
#define bits_triangulation_TriangulationData_FIELD_EDGE_NORMS 21L
#undef bits_triangulation_TriangulationData_FIELD_EDGE_COUNT
#define bits_triangulation_TriangulationData_FIELD_EDGE_COUNT 22L
#undef bits_triangulation_TriangulationData_NUM_FIELDS
#define bits_triangulation_TriangulationData_NUM_FIELDS 23L
/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nTriangulate
 * Signature: (JLjava/lang/String;J)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nTriangulate
  (JNIEnv *, jclass, jlong, jstring, jlong);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nAlloc
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_bits_triangulation_TriangulationData_nAlloc
  (JNIEnv *, jclass);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nFree
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nFree
  (JNIEnv *, jclass, jlong);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nGetInt
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_nGetInt
  (JNIEnv *, jclass, jlong);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nSetInt
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetInt
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nGetLong
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_bits_triangulation_TriangulationData_nGetLong
  (JNIEnv *, jclass, jlong);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nSetLong
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetLong
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nGetFloat
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_bits_triangulation_TriangulationData_nGetFloat
  (JNIEnv *, jclass, jlong);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nSetFloat
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetFloat
  (JNIEnv *, jclass, jlong, jfloat);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nGetDouble
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_bits_triangulation_TriangulationData_nGetDouble
  (JNIEnv *, jclass, jlong);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nSetDouble
 * Signature: (JD)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetDouble
  (JNIEnv *, jclass, jlong, jdouble);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nSetBuffer
 * Signature: (JLjava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nSetBuffer
  (JNIEnv *, jclass, jlong, jobject);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nNativeAddress
 * Signature: (Ljava/nio/Buffer;)J
 */
JNIEXPORT jlong JNICALL Java_bits_triangulation_TriangulationData_nNativeAddress
  (JNIEnv *, jclass, jobject);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nCopy
 * Signature: (JJI)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nCopy
  (JNIEnv *, jclass, jlong, jlong, jint);

/*
 * Class:     bits_triangulation_TriangulationData
 * Method:    nGetFieldOffsets
 * Signature: ([J)V
 */
JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_nGetFieldOffsets
  (JNIEnv *, jclass, jlongArray);

#ifdef __cplusplus
}
#endif
#endif
