package midian.baselib.common;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import midian.baselib.base.BasePicActivity;
import midian.baselib.mining.app.zxing.camera.CameraManager;
import midian.baselib.mining.app.zxing.decoding.CaptureActivityHandler;
import midian.baselib.mining.app.zxing.decoding.InactivityTimer;
import midian.baselib.mining.app.zxing.decoding.RGBLuminanceSource;
import midian.baselib.mining.app.zxing.view.ViewfinderView;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.midian.baselib.R;
import com.midian.fastdevelop.afinal.core.AsyncTask;

/**
 * 二维码扫描
 * 
 * @author MIDIAN
 * 
 */
public class MipcaActivityCapture extends BasePicActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private BaseLibTopbarView topbar;// 标题
	public static final String TYPE = "type";
	public static final String TYPE_DEVICE = "type_device";
	public static final int REQUEST_CODE = 10117;
	String type = "";
	boolean isOpen = false;

	static public void gotoActivity(Context mContext, String type) {
		Intent intent = new Intent(mContext, MipcaActivityCapture.class);
		intent.putExtra(TYPE, type);
		((Activity) mContext).startActivityForResult(intent, REQUEST_CODE);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);

		CameraManager.init(getApplication());
		CameraManager.get().setTop(56);
		CameraManager.get().setMaxHeight(261);
		CameraManager.get().setMaxWidth(261);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		type = getIntent().getStringExtra(TYPE);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setMode(BaseLibTopbarView.MODE_2);
		topbar.setTitle(getString(R.string.scan_qr_code))
				.setLeftImageButton(R.drawable.icon_back, null)
				.setLeftText(getString(R.string.back), UIHelper.finish(this));
		topbar.setBackgroundColor(Color.parseColor("#000000"));
		findViewById(R.id.album).setOnClickListener(this);
		findViewById(R.id.light).setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (arg0.getId() == R.id.album) {
			startImagePick();
		} else if (arg0.getId() == R.id.light) {
			if (isOpen) {
				isOpen = false;
				CameraManager.get().closeLight();
			} else {
				isOpen = true;
				CameraManager.get().openLight();
			}

		}
	}

	private void reStart() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
		init();

	}

	@SuppressWarnings("deprecation")
	private void init() {
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		resultString = "";
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		if (mScanningImageTask != null) {
			mScanningImageTask.cancel(true);
			mScanningImageTask = null;
		}
		super.onDestroy();
	}

	String resultString;

	/**
	 * 处理扫描结果
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();

	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	ScanningImageTask mScanningImageTask;

	public void outputBitmap(Bitmap bitmap, String path) {
		if (mScanningImageTask == null)
			mScanningImageTask = new ScanningImageTask();
		mScanningImageTask.execute(path);
	};

	class ScanningImageTask extends AsyncTask<String, Void, Result> {

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			String pathString = params[0];
			return scanningImage(pathString);
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				UIHelper.t(_activity, "请选择有效的二维码");
			} else {
				handleDecode(result, null);
			}

		}

	}

	private Result scanningImage(String path) {
		if (TextUtils.isEmpty(path)) {

			return null;

		}
		// DecodeHintType 和EncodeHintType
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 先获取原大小
		Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false; // 获取新的大小

		int sampleSize = (int) (options.outHeight / (float) 200);

		if (sampleSize <= 0)
			sampleSize = 1;
		options.inSampleSize = sampleSize;
		scanBitmap = BitmapFactory.decodeFile(path, options);

		RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		try {

			return reader.decode(bitmap1, hints);

		} catch (NotFoundException e) {

			e.printStackTrace();

		} catch (ChecksumException e) {

			e.printStackTrace();

		} catch (FormatException e) {

			e.printStackTrace();

		}

		return null;

	}
}
