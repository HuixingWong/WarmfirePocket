package com.example.activity;

import com.example.fragment.RegisterFragment;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

public class RegisterActivity extends IndexActivity {
	
	protected RegisterFragment getfragment() {
		return new RegisterFragment();
	}
}
