package com.vjia.bookcollector;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * this is used to test how to build a beautiful UI
 *
 */
@Deprecated
public class UITestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_ui_beauty_test);
		
		Button button4 = (Button)this.findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				alterDialog();
			}
		});
	}

	private void alterDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setTitle("简单标题")
				.setMessage("简单消息框")
				.setPositiveButton("确定", null)
				.show();
		// Android中常用的弹出提示框，
		// 如 普通消息框、确认框、输入框、单选框、复选框、列表框、图片框
		// 参考
		// http://blog.csdn.net/centralperk/article/details/7493731
	}
}
