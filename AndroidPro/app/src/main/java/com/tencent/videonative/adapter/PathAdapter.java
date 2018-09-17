package com.tencent.videonative.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.videonative.IVNLoadAppCallback;
import com.tencent.videonative.VNApp;
import com.tencent.videonative.VideoNative;
import com.tencent.videonative.demo.R;
import com.tencent.videonative.utils.ThreadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by connorlu on 2018/9/17.
 * connorlu@tencent.com
 * Copyright (c) 2018 Tencent. All rights reserved.
 */

public class PathAdapter extends Adapter<PathAdapter.PathViewHolder> implements IVNLoadAppCallback, View.OnClickListener {



    static class PathViewHolder extends RecyclerView.ViewHolder{

        private final TextView mPathTextView;

        public PathViewHolder(View itemView) {
            super(itemView);
            mPathTextView = (TextView)itemView.findViewById(R.id.path_text_view);
        }

        /**
         * 获取属性 <tt>mPathTextView</tt>.
         *
         * @return 获取属性 mPathTextView
         */
        public TextView getPathTextView() {
            return mPathTextView;
        }
    }

    private final LayoutInflater mLayoutInflater;
    private final List<String> mPaths;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private VNApp mApp;
    private String mAppDir;

    public PathAdapter(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
        mPaths = new ArrayList<>();

        VideoNative.getInstance().loadApp("59", this);

    }

    @Override
    public PathViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.activity_item, parent, false);
        PathViewHolder pvh = new PathViewHolder(itemView);
        pvh.getPathTextView().setOnClickListener(this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PathViewHolder holder, int position) {
        holder.getPathTextView().setText(getData(position));
        holder.getPathTextView().setTag(getData(position));
        holder.itemView.setBackgroundColor( position%2 == 0 ? Color.argb(255,200,200,200) : Color.argb(255,244,244,244) );
    }

    @Override
    public int getItemCount() {
        return mPaths.size();
    }

    public String getData(int position){
        return mPaths.get(position);
    }

    private void startMonitor(){
        ThreadManager.getInstance().execIo(new Runnable() {
            @Override
            public void run() {
                final File file = new File(mAppDir);
                final List<String> paths = new ArrayList<>();
                getVNMLFiles(file, paths);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mPaths.clear();
                        mPaths.addAll(paths);
                        notifyDataSetChanged();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startMonitor();
                            }
                        }, 3000);
                    }
                });
            }
        });
    }

    private void getVNMLFiles(File parent,List<String> paths){
        if(parent != null && parent.exists() && parent.isDirectory()){
            File[] children = parent.listFiles();
            if(children != null){
                for (File child : children) {
                    if(child.isDirectory()){
                        getVNMLFiles(child, paths);
                    }else if(child.getPath().endsWith(".page")){
                        String absPath = child.getAbsolutePath();
                        String vnpath = "vn://"+absPath.substring(mAppDir.length(),absPath.length()-5);
                        paths.add(vnpath);
                    }
                }
            }
        }
    }


    @Override
    public void onLoadAppStateChange(String s, int i) {

    }

    @Override
    public void onLoadAppProgressChange(String s, int i) {

    }

    @Override
    public void onLoadAppFinish(String s, int i, VNApp vnApp) {
        mApp = vnApp;
        mAppDir = vnApp.getVNAppDir();
        startMonitor();
    }


    @Override
    public void onClick(View v) {
        String path = (String)v.getTag();
        mApp.openPage(mLayoutInflater.getContext(), path);
    }
}
