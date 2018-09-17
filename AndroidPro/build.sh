export ANDROID_HOME=$ANDROID_SDK
export JAVA_HOME=$JDK8
export GRADLE_HOME=/data/rdm/apps/gradle/gradle-2.14.1
export PATH=$JDK8/bin:$GRADLE_HOME/bin:$PATH

gradle assemble --stacktrace

cp app/build/outputs/mapping/release/*.txt bin/
cp app/build/intermediates/multi-dex/release/*.txt bin/
cp app/build/outputs/apk/*.apk bin/