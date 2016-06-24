package com.example.fragment;


import java.util.List;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.activity.LoginActivity;
import com.example.activity.MainActivity;
import com.example.activity.R;
import com.example.activity.RegisterActivity;
import com.example.bmob.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	
	View mview;	
	Button login_login,login_register;
	EditText login_username,login_password;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mview = inflater.inflate(R.layout.activity_login, container, false);
		login_login = (Button) mview.findViewById(R.id.login_login);
		login_register = (Button) mview.findViewById(R.id.login_register);
		login_username = (EditText) mview.findViewById(R.id.login_username);
		login_password = (EditText) mview.findViewById(R.id.login_password);
		return mview;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		login_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), RegisterActivity.class);
				startActivity(intent);
			}
		});
		login_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				check();
			}
		});
		
	}
	
	public void check(){
		BmobUser bu2 = new BmobUser();
		bu2.setUsername(login_username.getText().toString());
		bu2.setPassword(login_password.getText().toString());
		bu2.login(getActivity(), new SaveListener() {
		    @Override
		    public void onSuccess() {
		    	Toast.makeText(getActivity(), "¹§Ï²Äú£¡µÇÂ¼³É¹¦£¡", Toast.LENGTH_SHORT).show();
		    	Intent intent = new Intent();
				intent.putExtra("username", login_username.getText().toString());
				intent.setClass(getActivity(), MainActivity.class);
				startActivity(intent);
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		    	Toast.makeText(getActivity(), "±§Ç¸£¡µÇÂ¼Ê§°Ü£¡", Toast.LENGTH_SHORT).show();
		    }
		});
	}

}
