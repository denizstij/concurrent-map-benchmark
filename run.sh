
testMessage=$1
export JAVA="/opt/jre/1.8.0_25l64/bin/java"

export ARGUMENTS="-DNUM_TASKS=10,100,1000,10000,100000,1000000,10000000  -DNUM_ITERATIONS=5 -DNUM_WARMUP_TASKS=100000 -DNUM_PRODUCER=10 -DKEY_LENGTH=8"
#export ARGUMENTS="-DNUM_TASKS=10,100,1000,10000  -DNUM_ITERATIONS=5 -DNUM_WARMUP_TASKS=10000 -DNUM_PRODUCER=10 -DKEY_LENGTH=8"
export MAIN_CLASS="com.blogspot.denizstij.benchmark.BenchmarkMain"

GC_LOG_FILE="/var/tmp/benchmark.gc.log"
GC_OPTIONS=" -XX:+UseConcMarkSweepGC -XX:MaxGCPauseMillis=1000 -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=85 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_FILE -XX:+PrintGCApplicationConcurrentTime  -XX:+PrintGCApplicationStoppedTime "
SIZE_OPT=" -Xmx12G  -Xms12G -Xmn7600m -XX:SurvivorRatio=4 -XX:PermSize=128m -XX:MaxPermSize=256m "

CLASSPATH="-cp .:lib/*"

echo $JAVA $CLASSPATH  $GC_OPTIONS  $SIZE_OPT  $ARGUMENTS $MAIN_CLASS
eval $JAVA  $CLASSPATH $GC_OPTIONS  $SIZE_OPT  $ARGUMENTS $MAIN_CLASS

echo "Full GC number"
grep Full /var/tmp/benchmark.gc.log | wc -l
echo "*******************"
echo $testMessage
echo "*******************"
