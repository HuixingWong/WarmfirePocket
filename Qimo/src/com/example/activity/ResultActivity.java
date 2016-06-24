package com.example.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bmob.Notes;

import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.FindStatisticsListener;

public class ResultActivity extends ListActivity {

	String username;
	Button btn_radd;
	int sumbuy,sumsail,sumall,month,year;
	TextView txt_rzhuan,txt_rin,txt_rout,txt_rmonth,txt_ryye,txt_rcb;
	String syear,smonth,sday,eyear,emonth,eday;
	List<Map<String, Object>> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
        Intent intent1 = getIntent();
        username = intent1.getStringExtra("username");

        btn_radd = (Button) findViewById(R.id.btn_radd);
        txt_rzhuan = (TextView) findViewById(R.id.txt_rzhuan);
        txt_rin = (TextView) findViewById(R.id.txt_rin);
        txt_rout = (TextView) findViewById(R.id.txt_rout);
        txt_rmonth = (TextView) findViewById(R.id.txt_rmonth);
        txt_ryye = (TextView) findViewById(R.id.txt_ryye);
        txt_rcb = (TextView) findViewById(R.id.txt_rcb);
        
        Intent intent = getIntent();  
        Bundle bundle = intent.getBundleExtra("tag");  
        syear = bundle.getString("syear");   
        smonth = bundle.getString("smonth");   
        sday = bundle.getString("sday");  
        eyear = bundle.getString("eyear");   
        emonth = bundle.getString("emonth");   
        eday = bundle.getString("eday");  
        
        month = Integer.parseInt(smonth); 
        year = Integer.parseInt(syear);
        txt_rmonth.setText(month+"月收入");
        txt_ryye.setText(month+"月营业额");
        txt_rcb.setText(month+"月成本额");
        
        btn_radd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("username", username);
                setResult(RESULT_OK, intent);
                finish();
			}
		});
        
        //refresh();
        
	}
	
	public void refresh(){
        sum();              
        SimpleAdapter adapter = new SimpleAdapter(ResultActivity.this, queryData(), 
				R.layout.listitem, new String[] {"listtype", "listall", "listinfo", "listdate"}, 
				new int[] {R.id.listtype, R.id.listall, R.id.listinfo, R.id.listdate});
		setListAdapter(adapter);

	}
//	
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		Log.d("warmfire", "onStart");
//		refresh();
//	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("warmfire", "onResume");
		BmobQuary();
		refresh();
	}

	/**
	 * 查询数据
	 * @return 
	 */
	public List<Map<String, Object>> queryData(){
		
		return list;
	}
	
	public void BmobQuary(){
		list = new ArrayList<Map<String, Object>>();
		BmobQuery<Notes> query = new BmobQuery<Notes>();

		query.addWhereEqualTo("username", username);
		List<BmobQuery<Notes>> and = new ArrayList<BmobQuery<Notes>>();
		
		//大于00：00：00
		BmobQuery<Notes> q1 = new BmobQuery<Notes>();
		String start = syear + "-" + smonth + "-" + sday + " 00:00:00";  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date  = null;
		try {
			date = sdf.parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		q1.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(date));
		and.add(q1);
		
		//小于23：59：59
		BmobQuery<Notes> q2 = new BmobQuery<Notes>();
		String end = eyear + "-" + emonth + "-" + eday + " 23:59:59"; 
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date1  = null;
		try {
			date1 = sdf1.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		q2.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date1));
		and.add(q2);
		
		//添加复合与查询
		query.and(and);
		
		query.findObjects(this, new FindListener<Notes>() {
		        @Override
		        public void onSuccess(List<Notes> object) {
		            for (Notes gameScore : object) {
		               	
		               	String str = gameScore.getSail() + " - " + gameScore.getBuy();
		               	sumbuy = Integer.parseInt(gameScore.getBuy().toString());
		               	sumsail = Integer.parseInt(gameScore.getSail().toString());
		               	int i = sumsail - sumbuy;
		               	Log.d("warmfire","i:"+i+"-"+gameScore.getCreatedAt().toString()+".");

		               	Map<String, Object> map = new HashMap<String, Object>();
		        		map.put("listtype", gameScore.getType());
		        		map.put("listall", i);
		        		map.put("listinfo", str);
		        		map.put("listdate", gameScore.getCreatedAt().toString());
		        		list.add(map);
		            }
		    		Log.d("warmfire",list.size()+"1");
		        }
		        @Override
		        public void onError(int code, String msg) {
		        	Log.d("warmfire",code+msg);
		            Toast.makeText(ResultActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
		        }
		});
		Log.d("warmfire",list.size()+"2");
	}
	
	public void sum(){
		BmobQuery<Notes> query = new BmobQuery<Notes>();

		List<BmobQuery<Notes>> and = new ArrayList<BmobQuery<Notes>>();
		
		//大于00：00：00
		BmobQuery<Notes> q1 = new BmobQuery<Notes>();
		String start = syear + "-" + smonth + "-" + sday + " 00:00:00";  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date  = null;
		try {
			date = sdf.parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		q1.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(date));
		and.add(q1);
		
		//小于23：59：59
		BmobQuery<Notes> q2 = new BmobQuery<Notes>();
		String end = eyear + "-" + emonth + "-" + eday + " 23:59:59"; 
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date1  = null;
		try {
			date1 = sdf1.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		q2.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date1));
		and.add(q2);
		
		//添加复合与查询
		query.and(and);
		query.sum(new String[] { "sail", "buy" });
		query.addWhereEqualTo("username", username);
		query.findStatistics(this, Notes.class,new FindStatisticsListener() {

		    @Override
		    public void onSuccess(Object object) {
		        JSONArray ary = (JSONArray) object;
		        if(ary!=null){
		            try {
		                JSONObject obj = ary.getJSONObject(0);
		                sumsail = obj.getInt("_sumSail");
		                sumbuy = obj.getInt("_sumBuy");
		                txt_rout.setText(Integer.toString(sumbuy));
		                txt_rin.setText(Integer.toString(sumsail));
		                txt_rzhuan.setText(Integer.toString(sumsail-sumbuy));
		            } catch (JSONException e) {
		            	
		            }
		        }else{
		        }
		    }

		    @Override
		    public void onFailure(int code, String msg) {
	        	Log.d("warmfire",code+msg);
		    }
		});
	}

}
