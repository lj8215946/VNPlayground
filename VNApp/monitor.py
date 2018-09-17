#!/usr/bin/env python
# -*- coding: utf-8 -*-
from watchdog.observers import Observer
from watchdog.events import *
from sys import platform
import shutil
import subprocess
import os.path
import time

class Path(object):

    @classmethod
    def listenPath(cls):
        # 这里填VN 源码的目录
        sourcecFile = '/source/'
        return os.path.abspath('.') + sourcecFile

    @classmethod
    def npmOutPutPath(cls):
        outPutFile = '/output/59/'
        return os.path.abspath('.') + outPutFile

    # @classmethod
    # def simulatorPath(cls):
    #     # 这里填iOS 沙箱的目录,安卓的直接改这个地址
    #     targetPath = '/sdcard/Android/data/com.tencent.vn.playground/files/.webapp/dirs'
    #     return targetPath
        

class VNTool(FileSystemEventHandler):

    def __init__(self):
        FileSystemEventHandler.__init__(self)
        self.isBuilding = False
        self.buildOutput = ''

    def on_moved(self, event):
        tool.doAction()

    def on_created(self, event):
        tool.doAction()

    def on_deleted(self, event):
        tool.doAction()

    def on_modified(self, event):
        tool.doAction()

    def buildVN(self):
        if platform.startswith("win") or platform == "cygwin":
            self.buildOutput = subprocess.Popen('npm run build_windows', shell=True, stdout=subprocess.PIPE ,stderr = subprocess.STDOUT).communicate()[0]
        else:
            self.buildOutput = subprocess.Popen('npm run build_mac', shell=True, stdout=subprocess.PIPE ,stderr = subprocess.STDOUT).communicate()[0]

    def watchFile(self,observerPath):
        print('开始监听路径:',observerPath)
        observer = Observer()
        observer.schedule(self, observerPath, True)
        observer.start()
        try:
            while True:
                time.sleep(0.1)
        except KeyboardInterrupt:
            observer.stop()
        observer.join()

    def checkSuccess(self,output):
        outputString = str(output,'utf-8')
        errorStartIndex =  outputString.find('[Fatal Error]')
        if(errorStartIndex == -1):
            return True

        errorEndIndex = outputString.find('npm ERR!')
        log = outputString[errorStartIndex:errorEndIndex]
        print('\n--------打包失败--------')
        print(log)
        return False


    def doAction(self):
        if(self.isBuilding):
            print('正在npm build,这次build暂不执行')
            return

        os.system('clear')
        print('正在打包...')
        self.isBuilding = True
        try:
            self.buildVN()
        except:
            pass

        self.isBuilding = False
        if(self.checkSuccess(self.buildOutput)):
            print('打包完成')


tool = VNTool()
#开始监听文件改变
sourceFile = Path.listenPath()
tool.watchFile(sourceFile)