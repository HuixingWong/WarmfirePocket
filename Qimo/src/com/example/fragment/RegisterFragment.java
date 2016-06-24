package com.example.fragment;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.example.activity.AddActivity;
import com.example.activity.LoginActivity;
import com.example.activity.R;
import com.example.activity.RegisterActivity;
import com.example.bmob.Notes;
import com.example.bmob.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {
	
	View mview;
	Button rs_login,rs_register;
	EditText rs_username,rs_password,rs_password2;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mview = inflater.inflate(R.layout.activity_register, container, false);
		rs_login = (Button) mview.findViewById(R.id.rs_login);
		rs_register = (Button) mview.findViewById(R.id.rs_register);
		rs_username = (EditText) mview.findViewById(R.id.rs_username);
		rs_password = (EditText) mview.findViewById(R.id.rs_password);
		rs_password2 = (EditText) mview.findViewById(R.id.rs_password2);
		return mview;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		rs_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
		rs_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(rs_password.getText().toString().equals(rs_password2.getText().toString())){
					register();
				}
				else{
			        Toast.makeText(getActivity(), "Á½´ÎÃÜÂë²»Í¬", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public void register(){
			BmobUser bu = new BmobUser();
			bu.setUsername(rs_username.getText().toString());
			bu.setPassword(rs_password.getText().toString());
			bu.signUp(getActivity(), new SaveListener() {
			    @Override
			    public void onSuccess() {
			    	Toast.makeText(getActivity(), "¹§Ï²Äú£¡×¢²á³É¹¦£¡", Toast.LENGTH_SHORT).show();
			    	Intent intent = new Intent();
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
			    }
			    @Override
			    public void onFailure(int code, String msg) {
			    	Toast.makeText(getActivity(), "±§Ç¸£¡×¢²áÊ§°Ü£¡", Toast.LENGTH_SHORT).show();
			    }
			});
	}

}
