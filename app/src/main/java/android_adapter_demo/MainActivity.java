package android_adapter_demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.administrator.myerwema.R;

public class MainActivity extends Activity {
	private ListView lv;
	private CartAdapter adapter;
	private List<CartBean> list = new ArrayList<CartBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		lv = (ListView) findViewById(R.id.listview);
		adapter = new CartAdapter(this, list);
		lv.setAdapter(adapter);
	}
	private void initData() {
		list.clear();
		for(int i=0;i<20;i++){
			CartBean bean = new CartBean();
			list.add(bean);
		}
	}

}
