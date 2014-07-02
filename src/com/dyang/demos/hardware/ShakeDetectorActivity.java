package com.dyang.demos.hardware;

import com.dyang.demos.R;
import com.dyang.demos.hardware.ShakeDetector.OnShakeListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShakeDetectorActivity extends Activity {

	private TextView mShakeDetectorTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_shake_detector);
		
		mShakeDetectorTV = (TextView) findViewById(R.id.shake_detector_tv);

		ShakeDetector.create(this, new OnShakeListener() {
			@Override
			public void onShake() {
				Toast.makeText(getApplicationContext(), "Device shaken!",Toast.LENGTH_SHORT).show();
				
				mShakeDetectorTV.setText(mShakeDetectorTV.getText().toString()  + " Device shaken!" + "\r\n");
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		ShakeDetector.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		ShakeDetector.stop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShakeDetector.destroy();
	}
}
