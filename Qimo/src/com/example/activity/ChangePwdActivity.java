package com.example.activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.bmob.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePwdActivity extends ActionBarActivity {
	
	String username;
	Button btn_change,cp_btn_back;
	EditText txt_oldpwd,txt_newpwd,txt_againpwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepwd);
		btn_change = (Button) findViewById(R.id.btn_change);
		cp_btn_back = (Button) findViewById(R.id.cp_btn_back);
		txt_oldpwd = (EditText) findViewById(R.id.txt_oldpwd);
		txt_newpwd = (EditText) findViewById(R.id.txt_newpwd);
		txt_againpwd = (EditText) findViewById(R.id.txt_againpwd);
		
		Intent intent = getIntent();
        username = intent.getStringExtra("username");
		
		cp_btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
			}
		});		
		btn_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(txt_oldpwd.getText().toString().equals("")){
					Toast.makeText(ChangePwdActivity.this, "旧密码不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(txt_newpwd.getText().toString().equals("")){
					Toast.makeText(ChangePwdActivity.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(txt_againpwd.getText().toString().equals("")){
					Toast.makeText(ChangePwdActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
				}
				else {
					if(txt_newpwd.getText().toString().equals(txt_againpwd.getText().toString())){
						check();	        	
					}
					else{
						Toast.makeText(ChangePwdActivity.this, "两次密码不相同，请重新输入", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	public void check(){
		BmobUser.updateCurrentUserPassword(ChangePwdActivity.this, txt_oldpwd.getText().toString(), txt_newpwd.getText().toString(), new UpdateListener() {

            @Override
            public void onSuccess() {
            	Toast.makeText(ChangePwdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
			}

            @Override
            public void onFailure(int code, String msg) {
            	Toast.makeText(ChangePwdActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
			}
        });
	}

}
