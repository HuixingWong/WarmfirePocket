package com.example.activity;

import cn.bmob.v3.Bmob;

import com.example.fragment.WelcomeFragment;

import android.support.v4.app.Fragment;
import android.util.Log;

public class WelcomeActivity extends IndexActivity {
	
	protected Fragment getfragment() {
		// TODO Auto-generated method stub
        Log.d("warmfire","main");
		return new WelcomeFragment();
	}
}
