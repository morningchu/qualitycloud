package midian.baselib.base;

import java.io.File;
import java.io.IOException;
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

public abstract class BaseImageChooseActivity extends BaseActivity implements OnPicChooserListener {
	private String imageChooseSavePath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/baselib/picture/";
	private int imageChooseCropWidth = 200;
	private int imageChooseCropHeight = 200;
	private int imageChooseAspectX = 1;
	private int imageChooseAspectY = 1;
	private int imageChooseThumbnailSize = 200;// 缩略图宽度
	private Uri imageChooseOrigUri;
	private Uri imageChooseCropUri;
	private File imageChooseFile;
	private String imageChooseImagePath;
	private String imageChooseThumbPath;
	private boolean isNeedCrop;// 是否需要裁剪

	protected void imageChoose(boolean isNeedCrop) {
		this.isNeedCrop = isNeedCrop;
		new PicChooserDialog(_activity, R.style.bottom_dialog).setListner(this).show();
	}

	@Override
	public void onClickFromGallery(View view) {
		imageChoosefromGallery();
	}

	@Override
	public void onClickFromCamera(View view) {
		imageChooseFromCamera();
	}

	/**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	protected void imageChoosefromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"), ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	/**
	 * 相机拍照
	 * 
	 * @param output
	 */
	protected void imageChooseFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (isNeedCrop) {
			// 需要裁剪
			Uri cameraTempFileUri = this.getCameraTempFile();
			if (cameraTempFileUri == null) {
				return;
			}
			intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraTempFileUri);
		} else {
			if (!sdCardReady()) {
				return;
			}
			imageChooseImagePath = imageChooseSavePath + "51mujiang_camera_"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imageChooseImagePath)));
		}
		startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	/**
	 * 拍照后裁剪
	 * 
	 * @param data
	 *            原始图片
	 * @param output
	 *            裁剪后图片
	 */
	protected void startActionCrop(Uri data) {
		Uri outputUri = this.getUploadTempFile(data);
		if (outputUri == null) {
			return;
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", outputUri);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", imageChooseAspectX);// 裁剪框比例
		intent.putExtra("aspectY", imageChooseAspectY);
		intent.putExtra("outputX", imageChooseCropWidth);// 输出图片大小
		intent.putExtra("outputY", imageChooseCropHeight);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	// 裁剪头像的绝对路径
	protected Uri getUploadTempFile(Uri uri) {
		if (!sdCardReady()) {
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (TextUtils.isEmpty(thePath)) {
			thePath = ImageUtils.getAbsoluteImagePath(this, uri);
		}
		String ext = FileUtils.getFileFormat(thePath);
		ext = TextUtils.isEmpty(ext) ? "jpg" : ext;
		// 照片命名
		String cropFileName = "51mujiang_crop_" + timeStamp + "." + ext;
		// 裁剪头像的绝对路径
		imageChooseImagePath = imageChooseSavePath + cropFileName;
		imageChooseFile = new File(imageChooseImagePath);

		imageChooseCropUri = Uri.fromFile(imageChooseFile);
		return this.imageChooseCropUri;
	}

	/**
	 * 拍照保存的绝对路径
	 * 
	 * @return
	 */
	protected Uri getCameraTempFile() {
		if (!sdCardReady()) {
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 照片命名
		String cropFileName = "51mujiang_camera_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		imageChooseImagePath = imageChooseSavePath + cropFileName;
		imageChooseFile = new File(imageChooseImagePath);
		imageChooseCropUri = Uri.fromFile(imageChooseFile);
		this.imageChooseOrigUri = this.imageChooseCropUri;
		return this.imageChooseCropUri;
	}

	/**
	 * 判断sd卡是否可用，已经创建图片保存文件夹
	 * 
	 * @return
	 */
	private boolean sdCardReady() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(imageChooseSavePath);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
			return true;
		} else {
			UIHelper.t(this, "无法保存上传的头像，请检查SD卡是否挂载");
			return false;
		}
	}

	/**
	 * 处理从相机获得原图
	 * 
	 * @param data
	 *            onActivityForResult方法中返回的data
	 */
	protected void hanldeCameraNoCrop(Intent data) {
		ImageUtils.scanPhoto(_activity, imageChooseImagePath);
		imageChooseFile = new File(imageChooseImagePath);
		if (!TextUtils.isEmpty(imageChooseImagePath) && imageChooseFile.exists()) {
			Bitmap imageChooseBitmap = ImageUtils.loadImgThumbnail(imageChooseImagePath, imageChooseThumbnailSize);
			saveThumb(imageChooseBitmap);
			renderThumb(imageChooseBitmap);
		}
	}

	/**
	 * 处理从相册或文件管理器选择原图
	 * 
	 * @param data
	 *            onActivityForResult方法中返回的data
	 */
	protected void hanldeGetImageNoCrop(Intent data) {
		Uri uri = data.getData();
		imageChooseImagePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);// 从相册
		// 如果是标准Uri
		if (TextUtils.isEmpty(imageChooseImagePath)) {
			imageChooseImagePath = ImageUtils.getAbsoluteImagePath(this, uri);// 从相册
		}

		if (TextUtils.isEmpty(imageChooseImagePath)) {
			imageChooseImagePath = uri.getPath();// 来自文件管理器
		}

		imageChooseFile = new File(imageChooseImagePath);
//		System.out.println(imageChooseImagePath);
		if (!TextUtils.isEmpty(imageChooseImagePath) && imageChooseFile.exists()) {
			Bitmap imageChooseBitmap = ImageUtils.loadImgThumbnail(imageChooseImagePath, imageChooseThumbnailSize);
			saveThumb(imageChooseBitmap);
			renderThumb(imageChooseBitmap);
		}
	}

	private void saveThumb(Bitmap thumb) {
		try {
			ImageUtils.saveImage(_activity, "avatar", thumb);
		} catch (IOException e) {
			UIHelper.t(_activity, "头像保存失败");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
				if (isNeedCrop) {
					startActionCrop(imageChooseOrigUri);// 拍照后要剪切
				} else {
					hanldeCameraNoCrop(data);// 拍照后原图
				}
				break;
			case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
				if (isNeedCrop) {
					startActionCrop(data.getData());
				} else {
					hanldeGetImageNoCrop(data);
				}
				break;
			case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
				// 使用裁剪后的图片(拍照或相册)
				if (!TextUtils.isEmpty(imageChooseImagePath) && imageChooseFile.exists()) {
					Bitmap imageChooseBitmap = ImageUtils.loadImgThumbnail(imageChooseImagePath,
							imageChooseThumbnailSize);
					saveThumb(imageChooseBitmap);
					renderThumb(imageChooseBitmap);
				}
				break;
			}
		}
	}

	public int getImageChooseCropWidth() {
		return imageChooseCropWidth;
	}

	public void setImageChooseCropWidth(int imageChooseCropWidth) {
		this.imageChooseCropWidth = imageChooseCropWidth;
	}

	public int getImageChooseCropHeight() {
		return imageChooseCropHeight;
	}

	public void setImageChooseCropHeight(int imageChooseCropHeight) {
		this.imageChooseCropHeight = imageChooseCropHeight;
	}

	public int getImageChooseAspectX() {
		return imageChooseAspectX;
	}

	public void setImageChooseAspectX(int imageChooseAspectX) {
		this.imageChooseAspectX = imageChooseAspectX;
	}

	public int getImageChooseAspectY() {
		return imageChooseAspectY;
	}

	public void setImageChooseAspectY(int imageChooseAspectY) {
		this.imageChooseAspectY = imageChooseAspectY;
	}

	public int getImageChooseThumbnailSize() {
		return imageChooseThumbnailSize;
	}

	public void setImageChooseThumbnailSize(int imageChooseThumbnailSize) {
		this.imageChooseThumbnailSize = imageChooseThumbnailSize;
	}

	public File getImageChooseFile() {
		return imageChooseFile;
	}

	public void setImageChooseFile(File imageChooseFile) {
		this.imageChooseFile = imageChooseFile;
	}

	public String getImageChooseImagePath() {
		return imageChooseImagePath;
	}

	public void setImageChooseImagePath(String imageChooseImagePath) {
		this.imageChooseImagePath = imageChooseImagePath;
	}

	public boolean isNeedCrop() {
		return isNeedCrop;
	}

	public void setNeedCrop(boolean isNeedCrop) {
		this.isNeedCrop = isNeedCrop;
	}

	public String getImageChooseSavepath() {
		return imageChooseSavePath;
	}

	/**
	 * 渲染缩略图
	 */
	protected abstract void renderThumb(Bitmap thumnail);

}
