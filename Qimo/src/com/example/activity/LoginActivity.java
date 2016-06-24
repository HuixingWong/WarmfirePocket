package com.example.activity;

import com.example.fragment.LoginFragment;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends IndexActivity {
	
	protected LoginFragment getfragment() {
		return new LoginFragment();
	}
}
