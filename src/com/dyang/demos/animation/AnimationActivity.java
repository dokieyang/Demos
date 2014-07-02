package com.dyang.demos.animation;

import com.dyang.demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationActivity extends Activity {

	
	protected static final String TAG = "AnimationActivity";
	
	private Button mAlphaAniBtn;
	private Button mScaleAniBtn;
	private Button mTranslateAniBtn;
	private Button mRotateBtn;
	private Button mAniSetBtn;
	private ImageView mImageView;
	
    private Animation mAlphaAnimation;
	private Animation mScaleAnimatoin;
	private Animation mTranslateAnimatoin;
	private Animation mRotateAnimatoin;
	private AnimationSet mAniSet;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_animation);
		
		mImageView = (ImageView) findViewById(R.id.imageView1);
		mAlphaAniBtn = (Button)findViewById(R.id.bt_alphaAnimation);
		mScaleAniBtn = (Button)findViewById(R.id.bt_scaleAnimation);
		mTranslateAniBtn = (Button)findViewById(R.id.bt_translate_Animation);
		mRotateBtn = (Button)findViewById(R.id.bt_rotate_animation);
		mAniSetBtn = (Button)findViewById(R.id.bt_aniset_animation);
		
		mAlphaAniBtn.setOnClickListener(listener);
		mScaleAniBtn.setOnClickListener(listener);
		mTranslateAniBtn.setOnClickListener(listener);
		mRotateBtn.setOnClickListener(listener);
		mAniSetBtn.setOnClickListener(listener);
		
		///////
		mAlphaAnimation = new AlphaAnimation(0.1F, 1.0F);
		mAlphaAnimation.setRepeatCount(2);
		mAlphaAnimation.setDuration(5000);
		mAlphaAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				Log.d(TAG, "onAnimationStart");
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				Log.d(TAG, "onAnimationRepeat");
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Log.d(TAG, "onAnimationEnd");
				
			}
		});
		
		
		mScaleAnimatoin = new ScaleAnimation(0.5F, 1.0F, 0.0F, 1.0F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 1.0F);
		mScaleAnimatoin.setDuration(500);
		
		mTranslateAnimatoin = new TranslateAnimation(10, 100, 10, 100);
		mTranslateAnimatoin.setDuration(1000);
		
		
		mRotateAnimatoin = new RotateAnimation(0.0F, 360.0F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
		mRotateAnimatoin.setDuration(1000);
		
		
		mAniSet = new AnimationSet(false);
		mAniSet.addAnimation(mAlphaAnimation);
		mAniSet.addAnimation(mScaleAnimatoin);
		mAniSet.addAnimation(mTranslateAnimatoin);
		mAniSet.addAnimation(mRotateAnimatoin);
	}
	

	View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.bt_alphaAnimation:
				mImageView.startAnimation(mAlphaAnimation);
				break;
			case R.id.bt_scaleAnimation:
				mImageView.startAnimation(mScaleAnimatoin);
				break;
			case R.id.bt_translate_Animation:
				mImageView.startAnimation(mTranslateAnimatoin);
				break;
			case R.id.bt_rotate_animation:
				mImageView.startAnimation(mRotateAnimatoin);
				break;
			case R.id.bt_aniset_animation:
				mImageView.startAnimation(mAniSet);
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
