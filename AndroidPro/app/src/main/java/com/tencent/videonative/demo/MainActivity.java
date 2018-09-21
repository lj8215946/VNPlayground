package com.tencent.videonative.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.tencent.videonative.IVNLoadAppCallback;
import com.tencent.videonative.VNApp;
import com.tencent.videonative.VideoNative;
import com.tencent.videonative.adapter.PathAdapter;
import com.tencent.videonative.app.input.IVNAppInfo;
import com.tencent.videonative.component.VNCompRichNodeFactory;

public class MainActivity extends Activity {

	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(new PathAdapter(LayoutInflater.from(this)));

	}

	@Override
	protected void onResume() {
		super.onResume();
		VNCompRichNodeFactory.clearPageInfoCache();	//清空一下组件的内存cache
	}
}
