package android_adapter_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.myerwema.R;

public class CartAdapter extends BaseAdapter {
	private List<CartBean> list;
	private Context context;
	private ViewHolder mHolder;
	private List<Type> typeList = new ArrayList<Type>();
	String strNum = "1";
	private String etValue = "value";
	// 用来存储editext中数据的list
	private List<Map<String, String>> mData = new ArrayList<Map<String, String>>();
//	@SuppressLint("UseSparseArrays")
	public Map<Integer, EditText> editorValue = new HashMap<Integer, EditText>();//

	@Override
	public int getCount() {
		return list.size();
	}

	public CartAdapter(Context context, List<CartBean> list) {
		this.list = list;
		this.context = context;
		initData();
	}

	private void initData() {
		for (CartBean cartBean : list) {
			Type type = Type.UnCheck;//勾选框
			typeList.add(type);
		}
		for (CartBean cartBean : list) {
			mData.add(new HashMap<String, String>());//Edittext存储数据
		}
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		mHolder = null;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.cart_item, parent, false);
			mHolder.pic = (ImageView) convertView.findViewById(R.id.goods_pic);
			mHolder.cb = (CheckBox) convertView.findViewById(R.id.good_check);
			mHolder.num = (EditText) convertView.findViewById(R.id.goods_num);
			mHolder.dec = (Button) convertView
					.findViewById(R.id.decrement_count);
			mHolder.add = (Button) convertView.findViewById(R.id.add_count);

			class MyTextWatcher implements TextWatcher {
				public MyTextWatcher(ViewHolder holder) {
					mHolder = holder;
				}

				/**
				 * 这里其实是缓存了一屏数目的viewholder， 也就是说一屏能显示10条数据，那么内存中就会有10个viewholder
				 * 在这的作用是通过edittext的tag拿到对应的position，用于储存edittext的值
				 */
				private ViewHolder mHolder;

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (!TextUtils.isEmpty(s.toString())) {
						// 当文本发生变化时，就保存值到list对应的position中
						int position = (Integer) mHolder.num.getTag();
						mData.get(position).put(etValue, s.toString()); //
						Log.i("afterTextChanged", "position" + position);
					}
				}
			}

			mHolder.num.addTextChangedListener(new MyTextWatcher(mHolder));

			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.num.setTag(position);
		mHolder.add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// //让edittext获得焦点
				editorValue.get(position).requestFocus();
				strNum = mData.get(position).get(etValue);
				// strNum = editorValue.get(position).getText().toString();
				Log.i("edittext", position + "");
				if (Util.isPositiveNum(strNum)) {
					int intnum = Integer.parseInt(strNum);
					intnum++;
					editorValue.get(position).setText("" + intnum);
				}
			}
		});
		String value = mData.get(position).get(etValue);
		if (!TextUtils.isEmpty(value)) {
			mHolder.num.setText(value);
		} else {
			mHolder.num.setText("");
		}
		// 每次都存一下，用于在edittext中取到对应位置的edittext
		editorValue.put(position, mHolder.num);
		mHolder.dec.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// mHolder.num.requestFocus();
				// strNum = mData.get(position).get(etValue);
				strNum = editorValue.get(position).getText().toString();
				if (Util.isPositiveNum(strNum)) {
					int intnum = Integer.parseInt(strNum);
					intnum--;
					if (Util.isPositiveNum(intnum + "")) {
						editorValue.get(position).setText("" + intnum);
					}
				}
			}
		});

		mHolder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					typeList.set(position, Type.Checked);
				} else {
					typeList.set(position, Type.UnCheck);
				}
			}
		});

		if (typeList.get(position) == Type.Checked) {
			mHolder.cb.setChecked(true);
		} else if (typeList.get(position) == Type.UnCheck) {
			mHolder.cb.setChecked(false);
		} else {
			mHolder.cb.setChecked(false);
		}
		return convertView;
	}

	static class ViewHolder {
		private ImageView pic;
		private CheckBox cb;
		private EditText num;
		private Button dec, add;
	}
	public enum Type {
		Checked, UnCheck;
	}
}
