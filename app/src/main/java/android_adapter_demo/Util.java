package android_adapter_demo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Util {
	public static Context context;

	public static void setContext(Context ctx) {
		if (context == null) {
			context = ctx;
		}
	}

	public static Context getContext() {
		return context;
	}

	/**
	 * 获取imapplication对象
	 * 
	 */
	// public static Application getApplication() {
	// return (Application) context.getApplicationContext();
	// }

	// MD5加密 32位小�?
	public static String getMd5Value(String sSecret) {
		try {
			MessageDigest bmd5 = MessageDigest.getInstance("MD5");
			bmd5.update(sSecret.getBytes());
			int i;
			StringBuffer buf = new StringBuffer();
			byte[] b = bmd5.digest();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isValidPhoneNumber(String phone) {
		if (TextUtils.isEmpty(phone)) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	/**
	 * 显示dialog
	 */
	public static void showDialog(ProgressDialog pd) {
		try {
			pd.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// pd.setContentView(R.layout.progressdialog);
		pd.setMessage("努力加载�?..");
		pd.setCancelable(true);
	}

	public static void doDialog(Context context, String phone) {
		try {
			if (!TextUtils.isEmpty(phone)) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ phone));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} else {
				Toast.makeText(context, "电话号码有误�?", 0).show();
			}

		} catch (Exception e) {
		}
	}

	public static void sendEmail(Context context, String email) {
		Intent it = new Intent(Intent.ACTION_SEND);

		it.setType("这里是使用的右键类型，比如plain/text纯文�?");

		String add[] = new String[] { email };
		String sub = "";
		it.putExtra(Intent.EXTRA_EMAIL, add);
		it.putExtra(Intent.EXTRA_SUBJECT, sub);
		it.putExtra(Intent.EXTRA_TEXT, "--发自巴特勒Android客户端\n");
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
	}

	@SuppressLint("NewApi")
	public static void showCallDialog(final Context context, final String phone) {
		AlertDialog ad = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT).setMessage("是否拨打" + phone + "?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//
						doDialog(context, phone);
					}

				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//
					}
				}).create();
		ad.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ad.show();
	}

	public static Double saveTwoEnd(double f) {
		BigDecimal bg = new BigDecimal(f);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	public static Double saveThreeEnd(double f) {
		BigDecimal bg = new BigDecimal(f);
		double f1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	public static String saveTwoS(double f) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(f);
	}

	public static String saveThreeS(double f) {
		DecimalFormat df = new DecimalFormat("#0.000");
		return df.format(f);
	}

	public static boolean isNum(String str) {
		if (str.endsWith("E") || str.endsWith("-")) {
			return false;
		}
		return str.matches("-?[0-9]+.*[0-9]*$");
	}

	/**
	 * 判断是不是正整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPositiveNum(String str) {
		return str.matches("[0-9]+");
	}

	public static int getStatusBarHeight(Context context) {

		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			return sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}

	public static String long2Time(String mills) {
		Long timeLong = Long.parseLong(mills);
		Date d = new Date(timeLong * 1000);
		// 格式里的时如果用hh表示�?2小时制，HH表示�?4小时制�?MM必须是大�?
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(d);

	}

	public static Date stringToDate(String dateString) {
		Long timeLong = Long.parseLong(dateString);
		Date d = new Date(timeLong * 1000);
		return d;
	}

	public static boolean isEnglishOnly(String str) {
		int length = str.length();
		byte[] byt = str.getBytes();
		if (length == byt.length) {
			return true;
		}
		return false;
	}

	public static boolean isVideoFileUri(String uri) {
		String extension = MimeTypeMap.getFileExtensionFromUrl(uri);
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				extension);
		return mimeType != null && mimeType.startsWith("video/");
	}

	public static boolean isVideoContentUri(Uri uri) {
		String mimeType = context.getContentResolver().getType(uri);
		return mimeType != null && mimeType.startsWith("video/");
	}

	public static int readPictureDegree(String path) {
		if (TextUtils.isEmpty(path)) {
			return 0;
		}
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (Exception e) {
		}
		return degree;
	}

	public static boolean isIntentAvailable(Context context, Intent intent) {
		List<ResolveInfo> activities = context.getPackageManager()
				.queryIntentActivities(intent,
						PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return activities.size() != 0;
	}
}
