#! /bin/bash
packageName="com.tencent.vn.playground"
appfolder="vnapp"

rm -rf webpack_output/*
rm -rf output/*
webpack
java -jar VideoNativePC.jar webpack_output output zips

./adb shell rm -rf /sdcard/Android/data/${packageName}/files/${appfolder}/*
./adb shell mkdir -p /sdcard/Android/data/${packageName}/files/${appfolder}
./adb shell mkdir /sdcard/Android/data/${packageName}/files/${appfolder}
./adb push output /sdcard/Android/data/${packageName}/files/${appfolder}.tmp
./adb shell cp -fR /sdcard/Android/data/${packageName}/files/${appfolder}.tmp/* /sdcard/Android/data/${packageName}/files/${appfolder}
./adb shell rm -rf /sdcard/Android/data/${packageName}/files/${appfolder}.tmp