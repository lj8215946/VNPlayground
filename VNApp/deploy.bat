REM set packageName=com.tencent.vn.playground
set packageName=com.tencent.vn.playground
set appfolder=vnapp

java -jar VideoNativePC.jar webpack_output output zips

adb shel mkdir -p /sdcard/Android/data/%packageName%/files/%appfolder%
adb shel mkdir /sdcard/Android/data/%packageName%/files/%appfolder%
adb push output /sdcard/Android/data/%packageName%/files/%appfolder%.tmp
adb shell cp -fR /sdcard/Android/data/%packageName%/files/%appfolder%.tmp/* /sdcard/Android/data/com.tencent.vn.playground/files/%appfolder%
adb shell rm -rf /sdcard/Android/data/%packageName%/files/%appfolder%.tmp
