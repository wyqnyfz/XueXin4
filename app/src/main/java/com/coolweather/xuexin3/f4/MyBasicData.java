package com.coolweather.xuexin3.f4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.PhotoUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.BasicData;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class MyBasicData extends AppCompatActivity {

    private String TAG = "MyBasicData";

//    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
//    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
//    private Uri imageUri;
//    private Uri cropImageUri;

    private File cameraSavePath;
    private String mPhotoPath = "";

    private EditText mNickName;
    private EditText mSignature;
    private EditText mGender;
    private EditText mPhone;
    private EditText mSchool;
    private EditText mMajor;
    private BasicData mBasicData;

    private ImageView mImageView;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    int t = (int) msg.obj;
                    if (t == 1) {
                        loging();
                        Toast.makeText(MyBasicData.this, "信息更新成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MyBasicData.this, "信息更新失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    int t2 = (int) msg.obj;
                    Log.d(TAG, "照片更新");
                    if (t2 == 1) {
//                        MyData.sBasicData.photo = mPhotoPath;
                        loging();
                        Toast.makeText(MyBasicData.this, "头像更新成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MyBasicData.this, "头像更新失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    int t3 = (int) msg.obj;
                    if (t3 == 1) {
                        Glide.with(MyBasicData.this).load(MyData.sBasicData.photo).into(mImageView);
                    } else {

                    }
                    break;

            }
        }
    };
    private Uri mUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basic_data);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //隐藏bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void init() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        mNickName = findViewById(R.id.et_bd_nickName);
        mSignature = findViewById(R.id.et_bd_signature);
        mGender = findViewById(R.id.et_bd_gender);
        mPhone = findViewById(R.id.et_bd_phone);
        mSchool = findViewById(R.id.et_bd_school);
        mMajor = findViewById(R.id.et_bd_major);
        mImageView = findViewById(R.id.riv_bd_img);
        mNickName.setText(MyData.sBasicData.nickName);
        mSignature.setText(MyData.sBasicData.signature);
        mGender.setText(MyData.sBasicData.gender);
        mPhone.setText(MyData.sBasicData.phone);
        mSchool.setText(MyData.sBasicData.school);
        mMajor.setText(MyData.sBasicData.major);
        mPhotoPath = MyData.sBasicData.photo;

        initData();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("------？-----", mPhotoPath);
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON) //开启选择器
                        .setActivityTitle("头像裁剪")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)  //选择矩形裁剪
                        .start(MyBasicData.this);

            }
        });

        //数据跟新
        findViewById(R.id.bt_bd_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int id = MyData.sBasicData.id;
                        String nickName = mNickName.getText().toString();
                        String signature = mSignature.getText().toString();
                        String gender = mGender.getText().toString();
                        String phone = mPhone.getText().toString();
                        String password = MyData.sBasicData.password;
                        String school = mSchool.getText().toString();
                        String major = mMajor.getText().toString();
                        String photo = mPhotoPath;
                        Log.d(TAG, "->"+mPhotoPath);
                        mBasicData = new BasicData(id, nickName, signature, gender, phone, password, school, major, photo);
                        MyUtils.setHandler(mHandler);
                        MyUtils.post_5(mBasicData);
                        MyUtils.post_5_1(mPhotoPath);
                    }
                }).start();
            }
        });
        findViewById(R.id.bt_bd_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initData() {

        if (!"".equals(mPhotoPath)) {
            Glide.with(MyBasicData.this).load(mPhotoPath).into(mImageView);
        }

    }

    //激活相册操作
    private void goPhotoAlbum() {
        //sdk权限
        autoObtainStoragePermission();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }




    /**
     * sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

    }


    private void loging() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.setHandler(mHandler);
                MyUtils.post_upData();
            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = result.getUri(); //获取裁减后的图片的Uri



                    Log.e("imageUri:",resultUri+"");
                    mPhotoPath = getRealPathFromUri(this,resultUri);
                    Log.d("============", "url"+mPhotoPath);
                    Glide.with(MyBasicData.this).load(mPhotoPath).into(mImageView);


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.d("PhotoActivity", "onActivityResult: Error");
                    Exception exception = result.getError();
                }
                break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);


    }






    /**
     * 根据图片的Uri获取图片的绝对路径。@uri 图片的uri
     * @return 如果Uri对应的图片存在,那么返回该图片的绝对路径,否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        if(context == null || uri == null) {
            return null;
        }
        if("file".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Byfile(context,uri);
        } else if("content".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Api11To18(context,uri);
        }
//        int sdkVersion = Build.VERSION.SDK_INT;
//        if (sdkVersion < 11) {
//            // SDK < Api11
//            return getRealPathFromUri_BelowApi11(context, uri);
//        }
////        if (sdkVersion < 19) {
////             SDK > 11 && SDK < 19
////            return getRealPathFromUri_Api11To18(context, uri);
//            return getRealPathFromUri_ByXiaomi(context, uri);
////        }
//        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);//没用到
    }

    //针对图片URI格式为Uri:: file:///storage/emulated/0/DCIM/Camera/IMG_20170613_132837.jpg
    private static String getRealPathFromUri_Byfile(Context context,Uri uri){
        String uri2Str = uri.toString();
        String filePath = uri2Str.substring(uri2Str.indexOf(":") + 3);
        return filePath;
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = null;

        wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = { MediaStore.Images.Media.DATA };
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = { id };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * //适配api11-api18,根据uri获取图片的绝对路径。
     * 针对图片URI格式为Uri:: content://media/external/images/media/1028
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }







}












