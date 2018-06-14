package com.example.mrzheng.lanlanapp.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mrzheng.lanlanapp.DataBaseService.HttpService;
import com.example.mrzheng.lanlanapp.Extra.Base64AndBitmap;
import com.example.mrzheng.lanlanapp.Model.UserInfo;
import com.example.mrzheng.lanlanapp.R;
import com.example.mrzheng.lanlanapp.Utils.PhotoUtils;
import com.example.mrzheng.lanlanapp.View.BottomDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mrzheng on 18-6-2.
 */

public class ModifyPersonalInfoActivity extends AppCompatActivity
        implements View.OnClickListener, HttpService{

    private CircleImageView avatar;
    private RelativeLayout changeAvator;
    private EditText nickname;
    private EditText sex;
    private EditText name;
    private EditText school;
    private Button save;

    //下面为只读
    private EditText stuId;
    private EditText tel;

    /**
     * 头像相关
     */
    private Uri imageUri;
    private Uri cropImageUri;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private Bitmap bitmap;
    private String avatarBase64;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    /**
                     * 改变全局变量中的个人用户信息
                     * 跳转到主界面
                     */
                    UserInfo.school = school.getText().toString();
                    UserInfo.nickname = nickname.getText().toString();
                    UserInfo.sex = sex.getText().toString();
                    UserInfo.name = name.getText().toString();
                    UserInfo.avatar = avatarBase64;

                    Toast.makeText(ModifyPersonalInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ModifyPersonalInfoActivity.this,HomeActivity.class));

                    break;
                case 2:
                    Toast.makeText(ModifyPersonalInfoActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal);

        initView();
        initInformation();
        setClickListener();

    }

    public void initView(){
        avatar = (CircleImageView)findViewById(R.id.avator_bitch);
        changeAvator = (RelativeLayout)findViewById(R.id.change_avator);
        nickname = (EditText)findViewById(R.id.nickname);
        sex = (EditText)findViewById(R.id.sex);
        name = (EditText)findViewById(R.id.name);
        school = (EditText)findViewById(R.id.school);
        stuId = (EditText)findViewById(R.id.stuid);
        tel = (EditText)findViewById(R.id.tel);
        save = (Button)findViewById(R.id.save);
    }

    public void initInformation(){

        nickname.setText(UserInfo.nickname);
        sex.setText(UserInfo.sex);
        name.setText(UserInfo.name);
        school.setText(UserInfo.school);
        stuId.setText(UserInfo.stu_id);
        tel.setText(UserInfo.tel);
        avatar.setImageBitmap(Base64AndBitmap.base64ToBitmap(UserInfo.avatar));

    }

    public void setClickListener(){
        changeAvator.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_avator:
                /**
                 * 保存头像
                 */
                showPhotoDialog();
                break;
            case R.id.save:
                /**
                 * 保存除了头像以外的其他个人信息
                 */
                //检测是否已经联网
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo == null || networkInfo.isAvailable()){
                    //当前有可用网络
                    httpPost(nickname.getText().toString(),
                            sex.getText().toString(),
                            name.getText().toString(),
                            school.getText().toString(),
                            stuId.getText().toString());//学号用来定位查询，并不能更改


                }else{
                    //当前没有可用网络
                    Toast toast = Toast.makeText(ModifyPersonalInfoActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
        }
    }

    private void showPhotoDialog() {
        // 判断6.0权限问题
        final BottomDialog dialog = new BottomDialog(this, "拍照", "从相册选择");
        dialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 拍照
                 */
                autoObtainCameraPermission();
                //Toast.makeText(ModifyPersonalInfoActivity.this,"confirmlistener",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setMiddleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 相册
                 */
                autoObtainStoragePermission();
                //Toast.makeText(ModifyPersonalInfoActivity.this,"middleLister",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(ModifyPersonalInfoActivity.this,"您已经拒绝过一次",Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(ModifyPersonalInfoActivity.this, "com.zz.fileprovider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                Toast.makeText(ModifyPersonalInfoActivity.this,"设备没有SD卡",Toast.LENGTH_SHORT).show();

            }
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(ModifyPersonalInfoActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        Toast.makeText(ModifyPersonalInfoActivity.this,"设备没有SD卡",Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(ModifyPersonalInfoActivity.this,"请允许打开相机",Toast.LENGTH_SHORT).show();
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    Toast.makeText(ModifyPersonalInfoActivity.this,"请允许操作SDcard",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        Toast.makeText(ModifyPersonalInfoActivity.this,"设备没有SD卡",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);
                    }
                    break;
                default:
            }
        }
    }

    private void showImages(Bitmap bitmap) {
        avatar.setImageBitmap(bitmap);
    }




    public void httpPost(String nickname,String sex,String name,String school,String stu_id){

        if(bitmap!=null){
            avatarBase64 = Base64AndBitmap.bitmapToBase64(bitmap);
        }else{
            avatarBase64 = UserInfo.avatar;
        }


        new Thread(()->{

            try{
                String url =IP+"/ModifyUserInfoServlet";
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("nickname",nickname)
                        .add("name",name)
                        .add("sex",sex)
                        .add("school",school)
                        .add("stu_id",stu_id)
                        .add("avatar",avatarBase64)//传递的是当前头像的base64编码
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Response response = okHttpClient.newCall(request).execute();

                if(response.isSuccessful()){

                    String str = response.body().string();
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    Type type = new TypeToken<Map<String,String>>(){}.getType();
                    Map<String,String> map = gson.fromJson(str,type);
                    Message message = new Message();

                    if(map.get("tag").equals("success")){
                        message.what = 1;
                    }else{
                        message.what = 2;
                        message.obj = map.get("info");
                    }
                    handler.sendMessage(message);
                }

            }catch (IOException e){
                e.printStackTrace();
            }

        }).start();

    }


}
