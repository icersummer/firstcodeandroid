package com.example.activitylifecycletest;

//import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	public static final String TAG = "MainActivity";
	
	private Button buttonCopyEditText;
	private EditText editText;
	
	/**
	 * this is to test to change pic in ImageView dynamically
	 */
	private ImageView imageView;

	/**
	 * to test ProgressBar show/hide
	 */
	private ProgressBar progressBar;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button startNormalActivity = (Button) this.findViewById(R.id.start_normal_activity);
        Button startDialogActivity = (Button) this.findViewById(R.id.start_dialog_activity);
        startNormalActivity.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, NormalActivity.class);
				startActivity(intent);
			}
        	
        });
        startDialogActivity.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, DialogActivity.class);
				startActivity(intent);
			}
        	
        });
        
        buttonCopyEditText = (Button) this.findViewById(R.id.copy_edit_text);
        buttonCopyEditText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.copy_edit_text:
					String inputText = editText.getText().toString();
					Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT);
					break;
				default:
					break;
				}
			}
        	
        });
        
        imageView = (ImageView) this.findViewById(R.id.image_view);
        Button change_image_view = (Button)this.findViewById(R.id.change_image_view);
        change_image_view.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.change_image_view:
					imageView.setImageResource(R.drawable.jelly_bean_to_be_changed);
					break;
				default:
					break;
				}
			}        	
        });
        
        // Progress Bar code
        progressBar = (ProgressBar)this.findViewById(R.id.progress_bar);
        Button change_progress_bar = (Button)this.findViewById(R.id.change_progress_bar);
        change_progress_bar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.change_progress_bar:
					if(progressBar.getVisibility() == View.GONE){
						progressBar.setVisibility(View.VISIBLE);
					} else {
						progressBar.setVisibility(View.GONE);
					}
					break;
				case -10:// should be a button here
					// the logic code of progressBarStyleHorizontal
					int progress = progressBar.getProgress();
					progress += 10;
					progressBar.setProgress(progress);
					break;
				default:
					break;
				}
			}
        	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
