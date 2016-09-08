package midian.baselib.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import midian.baselib.utils.FileUtils;
import midian.baselib.utils.ImageUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.dialog.PicChooserDialog;
import midian.baselib.widget.dialog.PicChooserDialog.OnPicChooserListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.midian.baselib.R;

/**
 * 图片Activity基类
 * 
 * @author moshouguan
 * 
 */
public class BasePicActivity extends BaseActivity implements
		OnPicChooserListener {

	private final static int CROP = 200;
	private final static String FILE_SAVEPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/midianbaselib/pic/";
	private Uri origUri;
	private Uri cropUri;
	private File protraitFile;
	private Bitmap protraitBitmap;
	private String protraitPath;

	public void takePhoto() {
		PicChooserDialog dialog = new PicChooserDialog(this,
				R.style.bottom_dialog);
		dialog.setListner(this);
		dialog.show();
	}

	@Override
	public void onClickFromGallery(View view) {
		startImagePick();
	}

	@Override
	public void onClickFromCamera(View view) {
		startActionCamera();
	}

	/**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	public void startImagePick() {
		Intent intent= new Intent(Intent.ACTION_PICK,
			     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.addCategory(Intent.CATEGORY_OPENABLE);
//		intent.setType("image/*");
		startActivityForResult(
				Intent.createChooser(intent, getString(R.string.select_pic)),
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	/**
	 * 相机拍照
	 * 
	 * @param output
	 */
	private void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	/**
	 * 拍照后裁剪
	 * 
	 * @param data
	 *            原始图片
	 * @param output
	 *            裁剪后图片
	 */
	private void startActionCrop(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", this.getUploadTempFile(data));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP);// 输出图片大小
		intent.putExtra("outputY", CROP);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	// 裁剪头像的绝对路径
	private Uri getUploadTempFile(Uri uri) {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.t(this, getString(R.string.submit_head_pic_error));
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (!TextUtils.isEmpty(thePath)) {
			thePath = ImageUtils.getAbsoluteImagePath(this, uri);
		}
		String ext = FileUtils.getFileFormat(thePath);
		ext = TextUtils.isEmpty(ext) ? "jpg" : ext;
		// 照片命名
		String cropFileName = "osc_crop_" + timeStamp + "." + ext;
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);

		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}

	// 拍照保存的绝对路径
	private Uri getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.t(this, getString(R.string.submit_head_pic_error));
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// 照片命名
		String cropFileName = "osc_camera_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		this.origUri = this.cropUri;
		return this.cropUri;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		System.out.println("onActivityResult");
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
				startActionCrop(origUri);// 拍照后裁剪
				break;
			case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
				startActionCrop(data.getData());// 选图后裁剪
				break;
			case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
				// 获取头像缩略图
				if (!TextUtils.isEmpty(protraitPath) && protraitFile.exists()) {
					protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath,
							200, 200);
					outputBitmap(protraitBitmap, protraitPath);
				}
				break;
			}
		}
	}

	public void outputBitmap(Bitmap bitmap, String path) {

	}
}
