#!/bin/bash
for host in hadoop102 hadoop103 hadoop104 hadoop105 hadoop106
do
 echo =============== $host ===============
 ssh $host jps 
done
