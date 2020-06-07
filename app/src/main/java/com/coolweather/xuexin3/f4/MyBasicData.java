package com.coolweather.xuexin3.f4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.PhotoUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.BasicData;

import java.io.File;

public class MyBasicData extends AppCompatActivity {

    private String TAG="MyBasicData";

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    private File cameraSavePath;
    private String mPhotoPath="";

    private EditText mNickName;
    private EditText mSignature;
    private EditText mGender;
    private EditText mPhone;
    private EditText mSchool;
    private EditText mMajor;
    private BasicData mBasicData;

    private ImageView mImageView;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    int t = (int) msg.obj;
                    if(t == 1){
//                        String photo = MyData.sBasicData.photo;
//                        MyData.sBasicData = mBasicData;
//                        MyData.sBasicData.photo = photo;
                        loging();
                        Toast.makeText(MyBasicData.this, "信息更新成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(MyBasicData.this, "信息更新失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    int t2 = (int) msg.obj;
                    Log.d(TAG, "照片更新");
                    if(t2 == 1){
//                        MyData.sBasicData.photo = mPhotoPath;
                        loging();
                        Toast.makeText(MyBasicData.this, "头像更新成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MyBasicData.this, "头像更新失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    int t3 = (int) msg.obj;
                    if(t3 == 1){
                        Glide.with(MyBasicData.this).load(MyData.sBasicData.photo).into(mImageView);
                    }else {

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
        initData();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("-----------+----------", mPhotoPath);
//                goPhotoAlbum();
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
                        mBasicData = new BasicData(id, nickName, signature, gender, phone, password, school, major, photo);
                        MyUtils.setHandler(mHandler);
                        MyUtils.post_5(mBasicData);
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
        mNickName.setText(MyData.sBasicData.nickName);
        mSignature.setText(MyData.sBasicData.signature);
        mGender.setText(MyData.sBasicData.gender);
        mPhone.setText(MyData.sBasicData.phone);
        mSchool.setText(MyData.sBasicData.school);
        mMajor.setText(MyData.sBasicData.major);
        Log.d("-----------------", MyData.sBasicData.photo);
        if(!"".equals(MyData.sBasicData.photo)){
            Glide.with(MyBasicData.this).load(MyData.sBasicData.photo).into(mImageView);
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
     * @param activity    当前activity
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param requestCode 剪裁图片的请求码
     */
    public static void startPhotoZoom(Activity activity, Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }


//    public void startPhotoZoom(Uri urill) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        mUri = Uri.fromFile(new File(Settings.TEMP_PATH, urill));
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//
//            mUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
//        }
//
//        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
//
//
//
//
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//        intent.setDataAndType(mUri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 500);
//        intent.putExtra("outputY", 500);
//        intent.putExtra("scale", true);
//
//        intent.putExtra("output", mUri); //保存路径
//        intent.putExtra("outputFormat", "JPEG");// 返回格式
//        intent.putExtra("return-data", true);
//        intent.putExtra("noFaceDetection", true);
//        startActivityForResult(intent, 2);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //打开图库后
            photoPath = GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            Log.d("=====================", photoPath);

            cropImageUri = Uri.fromFile(fileCropUri);
            Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
            startPhotoZoom(this, newUri, cropImageUri, 1, 1, 500, 500, 2);


        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            //打开剪切后
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mPhotoPath = GetPhotoFromPhotoAlbum.getRealPathFromUri(MyBasicData.this, Uri.fromFile(new File("/mnt/sdcard/temp")));
                    MyUtils.setHandler(mHandler);
                    MyUtils.post_5_1(mPhotoPath);
                }
            }).start();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.setHandler(mHandler);
                MyUtils.post_upData();
            }
        }).start();
    }

}












