package com.example.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.fragment.MainFragment;
import com.example.fragment.MonthFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {
	
	String username;
	private ViewPager viewPager;
	private ArrayList<Fragment> pageList;
	private TextView[] txtList;	
	Button btn_add;
	private MainFragment mainFragment;
	private MonthFragment monthFragment;
    private MyPageAdapter adapter;  
	int sumbuy,sumsail,sumall,month,year,day;
	TextView txt_zhuan,txt_in,txt_out,txt_month,txt_yye,txt_cb;
	List<Map<String, Object>> list;
    private Bitmap cursor;  
    private int offSet;  
    private int currentItem;  
    private Matrix matrix = new Matrix();  
    private int bmWidth;  
    private Animation animation;  
	
	private void initeCursor() {  
        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.cursore);  
        bmWidth = cursor.getWidth();    
        DisplayMetrics dm;  
        dm = getResources().getDisplayMetrics();    
        offSet = (dm.widthPixels - 3 * bmWidth) / 6;  
        matrix.setTranslate(offSet, 0);  
        currentItem = 0;  
    }
	
	public class MyPageAdapter extends FragmentPagerAdapter {  
        
		private List<Fragment> mViewList; 
		
        public MyPageAdapter(FragmentManager fm,List<Fragment> mViewList) {
			super(fm);
			this.mViewList = mViewList;
		}
  
        @Override  
        public int getCount() {  
            // TODO Auto-generated method stub               
            return mViewList.size();  
        }  
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return mViewList.get(arg0);
		}  
          
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("username", username);
				intent.setClass(MainActivity.this, AddActivity.class);
				startActivityForResult(intent,1);
			}
		});
		
		Intent intent = getIntent();
		username = intent.getStringExtra("username");

		mainFragment = new MainFragment();
		monthFragment = new MonthFragment();
		initeCursor(); 
	    viewPager = (ViewPager) findViewById(R.id.viewPager);  
	    pageList = new ArrayList<Fragment>();
	    
		txtList = new TextView[3];
		txtList[0] = (TextView) findViewById(R.id.textView1);
		txtList[1] = (TextView) findViewById(R.id.textView2);
		
		pageList.add(mainFragment);
		pageList.add(monthFragment);
		adapter = new MyPageAdapter(getSupportFragmentManager(), pageList);
	    viewPager.setAdapter(adapter);  
	    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {  

	        // 当滑动式，顶部的imageView是通过animation缓慢的滑动  
	        @SuppressLint("ResourceAsColor")
			@Override  
	        public void onPageSelected(int arg0) {  
	            switch (arg0) {  
	            case 0:  
	                if (currentItem == 1) {  
	                    animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);  
	                    
	                } else if (currentItem == 2) {
	                	animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);
	                }  
	                txtList[0].setBackgroundResource(R.drawable.unselect);
	                txtList[1].setBackgroundResource(R.drawable.cursore);
	                break;  
	            case 1:  
	                if (currentItem == 0) {
	                	animation = new TranslateAnimation(0, offSet * 2 + bmWidth, 0, 0);
	                } else if (currentItem == 2) {
	                	animation = new TranslateAnimation(0, offSet * 2 + bmWidth, 0, 0);
	                }                          
	                txtList[1].setBackgroundResource(R.drawable.unselect);
	                txtList[0].setBackgroundResource(R.drawable.cursore);
	                break;
	            }  
	            currentItem = arg0;  
	            animation.setDuration(150);
	            animation.setFillAfter(true);
	        }  

	        @Override  
	        public void onPageScrolled(int arg0, float arg1, int arg2) {  

	        }  
	        @Override  
	        public void onPageScrollStateChanged(int arg0) {  

	        }  
	    });  

	    txtList[0].setOnClickListener(new View.OnClickListener() {  
	        @Override  
	        public void onClick(View arg0) {  
	            viewPager.setCurrentItem(0);  
	        }  
	    });  

	    txtList[1].setOnClickListener(new View.OnClickListener() {  
	        @Override  
	        public void onClick(View arg0) {  
	            viewPager.setCurrentItem(1);  
	        }  
	    }); 

	    txtList[0].setBackgroundResource(R.drawable.unselect);
	    txtList[1].setBackgroundResource(R.drawable.cursore);
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu1) {
			return true;
		}
		else if(id == R.id.menu2){
			Intent intent = new Intent();
			intent.putExtra("username", username);
			intent.setClass(MainActivity.this, SelectActivity.class);
			startActivityForResult(intent,1);
			return true;
		}
		else if(id == R.id.menu3){
			Intent intent = new Intent();
			intent.putExtra("username", username);
			intent.setClass(MainActivity.this, ChangePwdActivity.class);
			startActivityForResult(intent,1);
			return true;
		}
		else if(id == R.id.menu4){
			Intent intent = new Intent();
			intent.putExtra("username", username);
			intent.setClass(MainActivity.this, WelcomeActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
