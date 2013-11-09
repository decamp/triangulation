#!/bin/bash

cd `dirname "$0"`
cd ..

names[0]=bits.triangulation.TriangulationData

for i in {0..0}
do
  javah -classpath build ${names[i]}
  f0=$(echo ${names[i]}|sed 's/\./_/g').h
  f1=src_c/triangle/src/$(echo ${names[i]}|sed 's/.*\.//g').h
  mv ${f0} ${f1}
done

