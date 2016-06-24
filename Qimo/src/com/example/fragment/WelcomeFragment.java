package com.example.fragment;

import com.example.activity.LoginActivity;
import com.example.activity.R;
import com.example.activity.RegisterActivity;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WelcomeFragment extends Fragment {
	
	View mview;
	ImageView img;
	Button btn_login,btn_register; 
	AnimationSet animationSet1;
	Intent intent;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				img.setImageResource(R.drawable.welcome);
				img.startAnimation(animationSet1);
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mview = inflater.inflate(R.layout.activity_welcome, container, false);
		img = (ImageView) mview.findViewById(R.id.img);
		btn_login = (Button) mview.findViewById(R.id.btn_login);
		btn_register = (Button) mview.findViewById(R.id.btn_register);
		handler.sendEmptyMessage(0);
		return mview;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		prepareAnimationForPic1();
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	
	private void prepareAnimationForPic1(){
		animationSet1 = new AnimationSet(true);
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(3000);
		animationSet1.addAnimation(aa);
		animationSet1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub	
			}
			
		});
	}
}
