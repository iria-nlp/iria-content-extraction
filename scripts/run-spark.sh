#!/bin/bash

DEBUG_CONFIG=''
if [ "$SPARK_APP_DEBUG" == true ]; then
  DEBUG_CONFIG='-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=6308'
fi

if [ -z "$PROGRAM_ARGS" ]; then
    PROGRAM_ARGS="--env default"
fi

if [ -z $SPARK_MASTER_HOST ]; then
    SPARK_MASTER="spark-master" # default value from base image...
fi

if [ -z $SPARK_LOCAL_IP ]; then
    SPARK_LOCAL_IP=$(hostname -f)
fi

if [ -z $SPARK_DRIVER_MEMORY ]; then
    SPARK_DRIVER_MEMORY=2g
fi

if [ -z $SPARK_NETWORK_TIMEOUT ]; then
    SPARK_NETWORK_TIMEOUT=120
fi

if [ -z $SPARK_EXECUTOR_CORES ]; then
    SPARK_EXECUTOR_CORES=8
fi

if [ -z $SPARK_CORES_MAX ]; then
    SPARK_CORES_MAX=32
fi

if [ -z $SPARK_EXECUTOR_MEMORY ]; then
    SPARK_EXECUTOR_MEMORY=2g
fi

if [ -z $SPARK_DRIVER_MAX_RESULTS_GB ]; then
    SPARK_DRIVER_MAX_RESULTS_GB=1g
fi

if [ -z $SPARK_SQL_SHUFFLE_PARTITIONS ]; then
    SPARK_SQL_SHUFFLE_PARTITIONS=100
fi

if [ -z $SPARK_SQL_PARTITION_BYTES ]; then
    SPARK_SQL_PARTITION_BYTES=134217728
fi

if [ -z $SPARK_DEFAULT_PARALLELISM ]; then
    SPARK_DEFAULT_PARALLELISM=48
fi

echo "program arguments : $PROGRAM_ARGS"
echo "spark master: spark://$SPARK_MASTER_HOST:7077"
echo "spark driver host : $SPARK_LOCAL_IP"
echo "spark driver memory: $SPARK_DRIVER_MEMORY"
echo "spark executor memory: $SPARK_EXECUTOR_MEMORY"
echo "spark executor cores: $SPARK_EXECUTOR_CORES"
echo "spark cores max: $SPARK_CORES_MAX"
echo "spark shuffle partitions: $SPARK_SQL_SHUFFLE_PARTITIONS"
echo "spark partition size bytes: $SPARK_SQL_PARTITION_BYTES"
echo "spark parallelism: $SPARK_DEFAULT_PARALLELISM"

# APP_JAR="/opt/app/pkg/$(ls /opt/app/pkg/ | grep .*assembly.*\.jar)"
APP_JAR=/opt/spark/jars/app.jar

echo "executing batch job from $APP_JAR"

/opt/spark/bin/spark-submit \
    --class Main \
    --master spark://$SPARK_MASTER_HOST:7077 \
    --driver-memory $SPARK_DRIVER_MEMORY \
    --executor-memory $SPARK_EXECUTOR_MEMORY \
    --num-executors 3 \
    --conf spark.driver.host=$SPARK_LOCAL_IP \
    --conf spark.network.timeout=$SPARK_NETWORK_TIMEOUT \
    --conf spark.sql.broadcastTimeout=600 \
    --conf spark.driver.maxResultSize=$SPARK_DRIVER_MAX_RESULTS_GB \
    --conf spark.shuffle.blockTransferService=nio \
    --conf spark.executor.extraJavaOptions=$DEBUG_CONFIG \
    --conf spark.executor.cores=$SPARK_EXECUTOR_CORES \
    --conf spark.cores.max=$SPARK_CORES_MAX \
    --conf spark.sql.shuffle.partitions=$SPARK_SQL_SHUFFLE_PARTITIONS \
    --conf spark.sql.files.maxPartitionBytes=$SPARK_SQL_PARTITION_BYTES \
    --conf spark.default.parallelism=$SPARK_DEFAULT_PARALLELISM \
    $APP_JAR  \
    $PROGRAM_ARGS
