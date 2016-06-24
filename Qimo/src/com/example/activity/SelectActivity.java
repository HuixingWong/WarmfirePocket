package com.example.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SelectActivity extends Activity {

	String username;
	Button btn_select,btn_selectback;
	EditText txt_syear,txt_smonth,txt_sday,txt_eyear,txt_emonth,txt_eday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);   
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
		btn_select = (Button) findViewById(R.id.btn_select);
		btn_selectback = (Button) findViewById(R.id.btn_selectback);
		txt_syear = (EditText) findViewById(R.id.txt_syear);
		txt_smonth = (EditText) findViewById(R.id.txt_smonth);
		txt_sday = (EditText) findViewById(R.id.txt_sday);
		txt_eyear = (EditText) findViewById(R.id.txt_eyear);
		txt_emonth = (EditText) findViewById(R.id.txt_emonth);
		txt_eday = (EditText) findViewById(R.id.txt_eday);
		
		btn_select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!"".equals(txt_syear.getText().toString()) && !"".equals(txt_smonth.getText().toString())
						&& !"".equals(txt_sday.getText().toString()) && !"".equals(txt_eyear.getText().toString())
						&& !"".equals(txt_emonth.getText().toString()) && !"".equals(txt_eday.getText().toString())){
							Intent intent = new Intent();
							intent.setClass(SelectActivity.this, ResultActivity.class);
							Bundle bundle = new Bundle(); 

							bundle.putString("syear", txt_syear.getText().toString());
							bundle.putString("smonth", txt_smonth.getText().toString());
							bundle.putString("sday", txt_sday.getText().toString());
							bundle.putString("eyear", txt_eyear.getText().toString());
							bundle.putString("emonth", txt_emonth.getText().toString());
							bundle.putString("eday", txt_eday.getText().toString());

							intent.putExtra("username", username);
							intent.putExtra("tag", bundle);  
							startActivityForResult(intent,2);
				}
				else{
					AlertDialog alertDialog = new AlertDialog.Builder(SelectActivity.this).
						    setTitle("提示").
						    setMessage("您还未填完").
						    setIcon(R.drawable.ic_launcher).
						    setPositiveButton("确定", null).
						    create();
					alertDialog.show();
				}
			}
		});
		btn_selectback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("username", username);
				setResult(RESULT_OK, intent);
                finish();
			}
		});
	}
	
}
