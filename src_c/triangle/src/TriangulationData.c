#include "TriangulationData.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define REAL double
#include "triangle.h"

JNIEXPORT jlong JNICALL Java_bits_triangulation_TriangulationData_alloc
(JNIEnv* env, jclass clazz)
{
	struct triangulateio* s = (struct triangulateio*)calloc(1,sizeof(struct triangulateio));
	return *(jlong*)&s;
}



JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_free
(JNIEnv* env, jclass clazz, jlong ptr, jboolean freePoints, jboolean freeSegs, jboolean freeTris)
{
	struct triangulateio* s = *(struct triangulateio**)&ptr;
	
	if(freePoints && s->pointlist) {
		trifree(s->pointlist);
		s->pointlist = 0;
	}
	
	if(freeSegs && s->segmentlist) {
		trifree(s->segmentlist);
		s->segmentlist = 0;
	}
	
	if(freeTris && s->trianglelist) {
		trifree(s->trianglelist);
		s->trianglelist = 0;
	}
	
	if(s->edgelist) {
		trifree(s->edgelist);
		s->edgelist = 0;
	}
	
	free(s);
}


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_setPoints
(JNIEnv* env, jclass clazz, jlong ptr, jobject buf, jint pos, jint len)
{
	struct triangulateio* s = *(struct triangulateio**)&ptr;
	REAL* vals = (REAL*)(*env)->GetDirectBufferAddress(env, buf);
	
	s->pointlist      = vals + pos;
	s->numberofpoints = len;
}


JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_setSegments
(JNIEnv* env, jclass clazz, jlong ptr, jobject buf, jint pos, jint len)
{
	struct triangulateio* s = *(struct triangulateio**)&ptr;
	int* vals = (int*)(*env)->GetDirectBufferAddress(env, buf);
	
	s->segmentlist      = vals + pos;
	s->numberofsegments = len;
}



JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_setHoles
(JNIEnv* env, jclass clazz, jlong ptr, jobject buf, jint pos, jint len)
{
	struct triangulateio* s = *(struct triangulateio**)&ptr;
	REAL* vals = (REAL*)(*env)->GetDirectBufferAddress(env, buf);
	
	s->holelist      = vals + pos;
	s->numberofholes = len;
}


JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_getPointCount
(JNIEnv* env, jclass clazz, jlong ptr)
{
	return (**(struct triangulateio**)&ptr).numberofpoints;
}

JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_getPoints
(JNIEnv* env, jclass clazz, jlong ptr, jobject buf, jint pos)
{
	struct triangulateio* s = *(struct triangulateio**)&ptr;
	REAL* vals = (REAL*)(*env)->GetDirectBufferAddress(env, buf);
	
	memcpy(vals + pos, s->pointlist + pos, s->numberofpoints * 2 * sizeof(REAL));
	return s->numberofpoints;
}


JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_getTriangleCount
(JNIEnv* env, jclass clazz, jlong ptr)
{
	return (**(struct triangulateio**)&ptr).numberoftriangles;	
}


JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_getTriangles
(JNIEnv* env, jclass clazz, jlong ptr, jobject buf, jint pos)
{
	struct triangulateio* s = *(struct triangulateio**)&ptr;
	int* vals = (int*)(*env)->GetDirectBufferAddress(env, buf);
	
	memcpy(vals + pos, s->trianglelist, s->numberoftriangles * 3 * sizeof(int));
	return s->numberoftriangles;
}


JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_getEdgeCount
(JNIEnv* env, jclass clazz, jlong ptr)
{
	return (**(struct triangulateio**)&ptr).numberofedges;
}


JNIEXPORT jint JNICALL Java_bits_triangulation_TriangulationData_getEdges
(JNIEnv* env, jclass clazz, jlong ptr, jobject buf, jint pos)
{
	struct triangulateio* s = *(struct triangulateio**)&ptr;
	int* vals = (int*)(*env)->GetDirectBufferAddress(env, buf);
	
	memcpy(vals + pos, s->edgelist, s->numberofedges * 2 * sizeof(int));
	return s->numberofedges;
}




JNIEXPORT void JNICALL Java_bits_triangulation_TriangulationData_triangulate
(JNIEnv* env, jclass clazz, jlong inPtr, jstring flags, jlong outPtr)
{
	struct triangulateio* sin  = *(struct triangulateio**)&inPtr;	
	struct triangulateio* sout = *(struct triangulateio**)&outPtr;	
	char* fl = (char*)(*env)->GetStringUTFChars(env, flags, 0);
	
	triangulate(fl, sin, sout, 0);
	
	(*env)->ReleaseStringUTFChars(env, flags, fl);
}

