REM set packageName=com.tencent.vn.playground
set packageName=com.tencent.vn.playground
set appfolder=vnapp

java -jar VideoNativePC.jar webpack_output output zips

.\platform-tools\adb shell rm -rf /sdcard/Android/data/%packageName%/files/%appfolder%/*
.\platform-tools\adb shell mkdir -p /sdcard/Android/data/%packageName%/files/%appfolder%
.\platform-tools\adb shell mkdir /sdcard/Android/data/%packageName%/files/%appfolder%
.\platform-tools\adb push output /sdcard/Android/data/%packageName%/files/%appfolder%.tmp
.\platform-tools\adb shell cp -fR /sdcard/Android/data/%packageName%/files/%appfolder%.tmp/* /sdcard/Android/data/com.tencent.vn.playground/files/%appfolder%
.\platform-tools\adb shell rm -rf /sdcard/Android/data/%packageName%/files/%appfolder%.tmp
