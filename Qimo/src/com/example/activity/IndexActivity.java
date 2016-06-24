package com.example.activity;

import cn.bmob.v3.Bmob;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class IndexActivity extends FragmentActivity {

	private Fragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
        Bmob.initialize(this, "5cc82c0d28d3feea72ea4e0e934fabbf");
		fragment = getfragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.wm, fragment);
		ft.commit();
	}
	
	abstract protected Fragment getfragment();
	
}
