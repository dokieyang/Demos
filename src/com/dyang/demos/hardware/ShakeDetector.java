package com.dyang.demos.hardware;

import java.util.ArrayList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * <p>
 * 1.registerListener 传感器后，不再使用是记得unregisterListener
 * </p>
 * <p>
 * 2.可以通过updateConfiguration设置灵敏度
 * </p>
 * 
 * @author Dokie
 * 
 */
public class ShakeDetector implements SensorEventListener {

	private static final float DEFAULT_THRESHOLD_ACCELERATION = 2.0F;

	private static final int DEFAULT_THRESHOLD_SHAKE_NUMBER = 3;

	private static final int INTERVAL = 200; // millseconds

	private static final String TAG = "ShakeDetector";

	private static SensorManager mSensorManager;

	private static ShakeDetector mShakeDetector;

	private OnShakeListener mShakeListener;

	private ArrayList<SensorBundle> mSensorBundles;

	private Object mLock;

	private float mThresholdAcceleration;

	private int mThresholdShakeNumber;

	private ShakeDetector(OnShakeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Shake listener must not be null");
		}
		mShakeListener = listener;
		mSensorBundles = new ArrayList<SensorBundle>();
		mLock = new Object();
		mThresholdAcceleration = DEFAULT_THRESHOLD_ACCELERATION;
		mThresholdShakeNumber = DEFAULT_THRESHOLD_SHAKE_NUMBER;
	}

	public static void updateConfiguration(float sensibility, int shakeNumber) {
		mShakeDetector.setConfiguration(sensibility, shakeNumber);
	}

	private void setConfiguration(float sensibility, int shakeNumber) {
		mThresholdAcceleration = sensibility;
		mThresholdShakeNumber = shakeNumber;
		synchronized (mLock) {
			mSensorBundles.clear();
		}
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		SensorBundle sensorBundle = new SensorBundle(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2], sensorEvent.timestamp);
		Log.i(TAG, sensorBundle.toString());
		synchronized (mLock) {
			if (mSensorBundles.size() == 0) {
				mSensorBundles.add(sensorBundle);
			} else if (sensorBundle.getTimestamp() - mSensorBundles.get(mSensorBundles.size() - 1).getTimestamp() > INTERVAL) {
				mSensorBundles.add(sensorBundle);
			}
		}

		performCheck();
	}

	private void performCheck() {
		synchronized (mLock) {
			int[] vector = { 0, 0, 0 };
			int[][] matrix = { 
					{ 0, 0 }, // Represents X axis, positive and
											// negative direction.
					{ 0, 0 }, // Represents Y axis, positive and negative
								// direction.
					{ 0, 0 } // Represents Z axis, positive and negative
								// direction.
			};

			for (SensorBundle sensorBundle : mSensorBundles) {
				if (sensorBundle.getXAcc() > mThresholdAcceleration && vector[0] < 1) {
					vector[0] = 1;
					matrix[0][0]++;
				}
				if (sensorBundle.getXAcc() < -mThresholdAcceleration && vector[0] > -1) {
					vector[0] = -1;
					matrix[0][1]++;
				}
				if (sensorBundle.getYAcc() > mThresholdAcceleration && vector[1] < 1) {
					vector[1] = 1;
					matrix[1][0]++;
				}
				if (sensorBundle.getYAcc() < -mThresholdAcceleration && vector[1] > -1) {
					vector[1] = -1;
					matrix[1][1]++;
				}
				if (sensorBundle.getZAcc() > mThresholdAcceleration && vector[2] < 1) {
					vector[2] = 1;
					matrix[2][0]++;
				}
				if (sensorBundle.getZAcc() < -mThresholdAcceleration && vector[2] > -1) {
					vector[2] = -1;
					matrix[2][1]++;
				}
			}

			for (int[] axis : matrix) {
				for (int direction : axis) {
					if (direction < mThresholdShakeNumber) {
						return;
					}
				}
			}

			mShakeListener.onShake();
			mSensorBundles.clear();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public static boolean create(Context context, OnShakeListener listener) {
		if (context == null) {
			throw new IllegalArgumentException("Context must not be null");
		}

		if (mSensorManager == null) {
			mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		}

		mShakeDetector = new ShakeDetector(listener);

		return mSensorManager.registerListener(mShakeDetector, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
	}

	public static boolean start() {
		if (mSensorManager != null && mShakeDetector != null) {
			return mSensorManager.registerListener(mShakeDetector, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		}
		return false;
	}

	public static void stop() {
		if (mSensorManager != null) {
			mSensorManager.unregisterListener(mShakeDetector);
		}
	}

	public static void destroy() {
		mSensorManager = null;
		mShakeDetector = null;
	}

	/**
	 * callback when the device has benn shaken
	 * 
	 * @author Dokie
	 * 
	 */
	public interface OnShakeListener {

		public void onShake();
	}

	private class SensorBundle {
		private float mXAcc; // x axis
		private float mYAcc; // y axis
		private float mZAcc; // z axis
		private long mTimestamp;

		public SensorBundle(float XAcc, float YAcc, float ZAcc, long timestamp) {
			mXAcc = XAcc;
			mYAcc = YAcc;
			mZAcc = ZAcc;
			mTimestamp = timestamp;
		}

		public float getXAcc() {
			return mXAcc;
		}

		public void setXAcc(float mXAcc) {
			this.mXAcc = mXAcc;
		}

		public float getYAcc() {
			return mYAcc;
		}

		public void setYAcc(float mYAcc) {
			this.mYAcc = mYAcc;
		}

		public float getZAcc() {
			return mZAcc;
		}

		public void setZAcc(float mZAcc) {
			this.mZAcc = mZAcc;
		}

		public long getTimestamp() {
			return mTimestamp;
		}

		public void setTimestamp(long mTimestamp) {
			this.mTimestamp = mTimestamp;
		}

		public String toString() {
			return "SensorBundle{" + " mXAcc=" + mXAcc + " mYAcc=" + mYAcc + " mZAcc=" + mZAcc + " mTimestamp=" + mTimestamp + "}";
		}

	}
}
