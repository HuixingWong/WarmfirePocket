package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bmob.Notes;
import com.example.activity.MipcaActivityCapture;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends ActionBarActivity {

	private final static int SCANNIN_GREQUEST_CODE = 1;
	String username;
	Button btn_submit,btn_back;
	EditText txt_type,txt_addin,txt_addout;
	ImageView img_code;
	private static RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		requestQueue = Volley.newRequestQueue(AddActivity.this);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_back = (Button) findViewById(R.id.btn_back);
		txt_addin = (EditText) findViewById(R.id.txt_addin);
		txt_addout = (EditText) findViewById(R.id.txt_addout);
		txt_type = (EditText) findViewById(R.id.txt_type);
		img_code = (ImageView) findViewById(R.id.img_code);
		
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        
        img_code.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AddActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
			}
		});
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!"".equals(txt_type.getText().toString())){
					if(!"".equals(txt_addin.getText().toString())){
						if(!"".equals(txt_addout.getText().toString())){
							submit(1);
							Intent intent = new Intent();
			                setResult(RESULT_OK, intent);
			                finish();
						}
						else{
							AlertDialog alertDialog = new AlertDialog.Builder(AddActivity.this).
								    setTitle("提示").
								    setMessage("收入不能为空").
								    setIcon(R.drawable.ic_launcher).
								    setPositiveButton("确定", null).
								    create();
							alertDialog.show();
						}
					}
					else{
						AlertDialog alertDialog = new AlertDialog.Builder(AddActivity.this).
							    setTitle("提示").
							    setMessage("成本不能为空").
							    setIcon(R.drawable.ic_launcher).
							    setPositiveButton("确定", null).
							    create();
						alertDialog.show();
					}
				}
				else{
					AlertDialog alertDialog = new AlertDialog.Builder(AddActivity.this).
						    setTitle("提示").
						    setMessage("类型不能为空").
						    setIcon(R.drawable.ic_launcher).
						    setPositiveButton("确定", null).
						    create();
					alertDialog.show();
				}
			}
		});
		
	}
	
	public void submit(int i){
		if(i == 1){
			Notes notes = new Notes();
			
			notes.setType(txt_type.getText().toString());
			notes.setBuy(Integer.parseInt(txt_addout.getText().toString()));
			notes.setSail(Integer.parseInt(txt_addin.getText().toString()));
			notes.setUsername(username);
			
			notes.save(AddActivity.this, new SaveListener() {

			    @Override
			    public void onSuccess() {
			        Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
			    }

			    @Override
			    public void onFailure(int code, String arg0) {
			    	Log.d("warmfire",arg0);
			        Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
			    }
			});
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				String code = bundle.getString("result");
				String url = "http://api.juheapi.com/jhbar/bar?pkg=Qimo" +
						"&barcode=" + code +
						"&cityid=1" +
						"&appkey=ec1f63450fb7ffd18a756a2740a526a5" +
						"&key=ec1f63450fb7ffd18a756a2740a526a5";
				StringRequest stringRequest = new StringRequest(url, 
						new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
			        	Log.d("warmfire", "arg0:"+arg0);
						txt_type.setText(jsonParse(arg0));
					}
					}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
				requestQueue.add(stringRequest);
			}
			break;
		}
    }	
	
	private String jsonParse(String json) {
		String name = null;
        try {
        	Log.d("warmfire", "json:"+json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject summary = result.getJSONObject("summary");
            name = summary.getString("name");    
        	Log.d("warmfire", "name:"+name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
	
}
