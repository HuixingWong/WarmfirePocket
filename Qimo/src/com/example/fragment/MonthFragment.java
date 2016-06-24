package com.example.fragment;

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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.FindStatisticsListener;

import com.example.activity.AddActivity;
import com.example.activity.R;
import com.example.bmob.Notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MonthFragment extends ListFragment {
	
	View mview;
	String username;
	int sumbuy,sumsail,sumall,month,year,day;
	TextView txt_zhuan,txt_in,txt_out,txt_month,txt_yye,txt_cb;
	List<Map<String, Object>> list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mview = inflater.inflate(R.layout.activity_main, container, false);
        txt_zhuan = (TextView) mview.findViewById(R.id.txt_zhuan);
        txt_in = (TextView) mview.findViewById(R.id.txt_in);
        txt_out = (TextView) mview.findViewById(R.id.txt_out);
        txt_month = (TextView) mview.findViewById(R.id.txt_month);
        txt_yye = (TextView) mview.findViewById(R.id.txt_yye);
        txt_cb = (TextView) mview.findViewById(R.id.txt_cb);
		init();
		return mview;
	}
	
	public void init(){
        
        Time time = new Time("GMT+8");       
        time.setToNow();           
        month = time.month + 1; 
        year = time.year;
        day = time.monthDay;
        txt_month.setText(month+"月收入");
        txt_yye.setText(month+"月营业额");
        txt_cb.setText(month+"月成本额");
        
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        Log.i("warmfire",username+"11111");
        
	}
	
	public void refresh(){
        sum();
              
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), queryData(), 
				R.layout.listitem, new String[] {"listtype", "listall", "listinfo", "listdate"}, 
				new int[] {R.id.listtype, R.id.listall, R.id.listinfo, R.id.listdate});
		setListAdapter(adapter);

	}
	
	@Override
	public void onResume() {
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
		String start = year + "-" + month + "- 01" + " 00:00:00";  
		Log.d("warmfire",start);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date  = null;
		try {
			date = sdf.parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		query.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(date));
		query.addWhereEqualTo("username", username);
		query.findObjects(getActivity(), new FindListener<Notes>() {
		        @Override
		        public void onSuccess(List<Notes> object) {
		            for (Notes gameScore : object) {
		               	
		               	String str = gameScore.getSail() + " - " + gameScore.getBuy();
		               	sumbuy = Integer.parseInt(gameScore.getBuy().toString());
		               	sumsail = Integer.parseInt(gameScore.getSail().toString());
		               	int i = sumsail - sumbuy;
		               	Log.d("warmfire","i:"+i+"-"+gameScore.getCreatedAt().toString());

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
		            Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
	        		return;
		        }
		});
		Log.d("warmfire",list.size()+"2");
	}
	
	public void sum(){
		BmobQuery<Notes> query = new BmobQuery<Notes>();
		query.addWhereEqualTo("username", username);		
		String start = year + "-" + month + "-01" + " 00:00:00";  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date  = null;
		try {
			date = sdf.parse(start);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		query.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(date));
		
		query.sum(new String[] { "sail", "buy" });
		query.findStatistics(getActivity(), Notes.class,new FindStatisticsListener() {

		    @Override
		    public void onSuccess(Object object) {
		        JSONArray ary = (JSONArray) object;
		        if(ary!=null){
		            try {
		                JSONObject obj = ary.getJSONObject(0);
		                sumsail = obj.getInt("_sumSail");
		                sumbuy = obj.getInt("_sumBuy");
		                txt_out.setText(Integer.toString(sumbuy));
		                txt_in.setText(Integer.toString(sumsail));
		                txt_zhuan.setText(Integer.toString(sumsail-sumbuy));
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
