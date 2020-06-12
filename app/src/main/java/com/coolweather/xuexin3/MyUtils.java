package com.coolweather.xuexin3;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.coolweather.xuexin3.pingLunAdapter.PingLun;
import com.coolweather.xuexin3.resultData.BasicData;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.coolweather.xuexin3.results.PosGuanZhu;
import com.coolweather.xuexin3.results.PosHuiFu;
import com.coolweather.xuexin3.results.PosPassword;
import com.coolweather.xuexin3.results.PosPingLun;
import com.coolweather.xuexin3.results.PosShouCang;
import com.coolweather.xuexin3.results.PosUser;
import com.coolweather.xuexin3.results.PostZan;
import com.coolweather.xuexin3.results.ResFaBu;
import com.coolweather.xuexin3.results.ResFenSiGuanZhu;
import com.coolweather.xuexin3.results.ResGuan;
import com.coolweather.xuexin3.results.ResLogin;
import com.coolweather.xuexin3.results.ResPingLun;
import com.coolweather.xuexin3.results.ResPossword;
import com.coolweather.xuexin3.results.ResSelect;
import com.coolweather.xuexin3.results.ResYanZhengMa;
import com.coolweather.xuexin3.results.ResZan;
import com.coolweather.xuexin3.results.ResZhuCe;
import com.coolweather.xuexin3.socket.FriendData;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyUtils {


    private static String TAG = "MyData";

    public static String name = "";
    public static String password = "";
    public static String zhucema = "";
    private static Handler mHandler = null;
    public static List<String> mImgUrl = new ArrayList<>();



    public static String phone = "18674877242";
    public static String pass0 = "18674877242";
    public static String pass1 = "18674877242";

    //注册码
    private static final String URL_1 = "http://121.36.5.207:8081/send/";
    //注册
    private static final String URL_2 = "http://121.36.5.207:8081/registered";
    //登录
    private static final String URL_3 = "http://121.36.5.207:8081/login";
    //更新信息
    private static final String URL_5 = "http://121.36.5.207:8081/update";
    //获取文章
    private static final String URL_6 = "http://121.36.5.207:8081/article/select/category/";
    //修改密码
    private static final String URL_10 = "http://121.36.5.207:8081/update/password";
    //上传照片
    private static final String URL_11 = "http://121.36.5.207:8081/update/photo";
    //上传动态
    private static final String URL_12 = "http://121.36.5.207:8081/article/add2";
    //获取生活
    private static final String URL_13 = "http://121.36.5.207:8081/article/select/dt";
    //获取动态
    private static final String URL_14 = "http://121.36.5.207:8081/article/select/mdt";
    //点赞
    private static final String URL_15 = "http://121.36.5.207:8081/like/add";
    //取消点赞
    private static final String URL_16 = "http://121.36.5.207:8081/like/delete";
    //收藏文章
    private static final String URL_17 = "http://121.36.5.207:8081/favor/add";
    //取消收藏
    private static final String URL_18 = "http://121.36.5.207:8081/favor/delete";
    //关注
    private static final String URL_19 = "http://121.36.5.207:8081/focus/add";
    //取消关注
    private static final String URL_20 = "http://121.36.5.207:8081/focus/delete";
    //获取评论
    private static final String URL_21 = "http://121.36.5.207:8081/leave/get?wid=";
    //添加评论
    private static final String URL_22 = "http://121.36.5.207:8081/leave/add";
    //添加回复
    private static final String URL_23 = "http://121.36.5.207:8081/reply/add";
    //关注数量
    private static final String URL_24_1 = "http://121.36.5.207:8081/focus/selectFocus?yid=";
    //粉丝数量
    private static final String URL_24_2 = "http://121.36.5.207:8081/focus/selectFans?yid=";

    public static final String getChatMsgUrl = "http://121.36.5.207:8081/getCacheMessage?yid=";


    /**
     * header
     */
    public static void setHandler(Handler handler){
        mHandler = handler;
    }

    /**
     * 发送massag
     * @param what
     * @param obj
     */
    public static void sentHanderMassage(int what, int obj){
        Message m = new Message();
        m.what = what;
        m.obj = obj;
        mHandler.sendMessage(m);
    }


    /**
     * 验证码
     * @param name
     */
    public static void get_1(String name) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL_1+name)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message m = new Message();
                m.what = 1;
                m.obj = 2;
                mHandler.sendMessage(m);
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int t = response.code();
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, "======================="+s2);
                    ResYanZhengMa resYanZhengMa = new Gson().fromJson(s2, ResYanZhengMa.class);
                    if(resYanZhengMa.isSuccess()){
                        Log.d(TAG, resYanZhengMa.getData().getCode());
                        Log.d(TAG, "-------------------------------"+MyUtils.zhucema);

                        MyUtils.zhucema = resYanZhengMa.getData().getCode();
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 1;
                        mHandler.sendMessage(m);
                    }else{
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 0;
                        mHandler.sendMessage(m);
                    }
                }
            }
        });

    }

    /**
     * 注册
     */
    public static void post_1(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        PosUser user = new PosUser(name, password);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.d(TAG,"JSON ============= "+json );
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_2)
                .post(requestBody)
                .build();



        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message m = new Message();
                m.what = 2;
                m.obj = 0;
                mHandler.sendMessage(m);
                Log.d(TAG, "请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, "----------"+s2);
                    ResZhuCe resZhuCe = new Gson().fromJson(s2, ResZhuCe.class);
                    boolean c = resZhuCe.isSuccess();
                    Log.d(TAG, "----------"+c);
                   if(c){
                       Message m = new Message();
                       m.what = 2;
                       m.obj = 1;
                       mHandler.sendMessage(m);
                   }else {
                       Message m = new Message();
                       m.what = 2;
                       m.obj = 0;
                       mHandler.sendMessage(m);
                   }
                }
            }
        });


    }

    /**
     * 登录
     */
    public static void post_2(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        PosUser user = new PosUser(name, password);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_3)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String ss1 = response.headers().toString();
                    String ss2 = response.body().string();
                    Log.d(TAG, "respon=="+ss1+"=="+ss2);
                    ResLogin resLogin = new Gson().fromJson(ss2, ResLogin.class);
                    if(resLogin.isSuccess()){
                        int s1 = resLogin.getData().getUser().getId();
                        String s2 = (String) resLogin.getData().getUser().getNickName();
                        String s3 = (String) resLogin.getData().getUser().getSignature();
                        String s4 = (String) resLogin.getData().getUser().getGender();
                        String s5 = resLogin.getData().getUser().getPhone();
                        String s6 = resLogin.getData().getUser().getPassword();
                        String s7 = (String) resLogin.getData().getUser().getSchool();
                        String s8 = (String) resLogin.getData().getUser().getMajor();
                        String s9 = (String) resLogin.getData().getUser().getPhoto();
                        MyData.sBasicData = new BasicData(s1,s2,s3,s4,s5,s6,s7,s8,s9);
                        MyData.isLogin = true;
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 1;
                        mHandler.sendMessage(m);
                    }else{
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 0;
                        mHandler.sendMessage(m);
                    }
                }
            }
        });
    }

    /**
     * 刷新个人数据
     */
    public static void post_upData(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        PosUser user = new PosUser(name, password);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_3)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String ss1 = response.headers().toString();
                    String ss2 = response.body().string();
                    Log.d(TAG, "respon=="+ss1+"=="+ss2);
                    ResLogin resLogin = new Gson().fromJson(ss2, ResLogin.class);
                    if(resLogin.isSuccess()){
                        int s1 = resLogin.getData().getUser().getId();
                        String s2 = (String) resLogin.getData().getUser().getNickName();
                        String s3 = (String) resLogin.getData().getUser().getSignature();
                        String s4 = (String) resLogin.getData().getUser().getGender();
                        String s5 = resLogin.getData().getUser().getPhone();
                        String s6 = resLogin.getData().getUser().getPassword();
                        String s7 = (String) resLogin.getData().getUser().getSchool();
                        String s8 = (String) resLogin.getData().getUser().getMajor();
                        String s9 = (String) resLogin.getData().getUser().getPhoto();
                        MyData.sBasicData = new BasicData(s1,s2,s3,s4,s5,s6,s7,s8,s9);
                        MyData.isLogin = true;
                        Message m = new Message();
                        m.what = 3;
                        m.obj = 1;
                        mHandler.sendMessage(m);
                    }
                }
            }
        });

    }


    /**
     * 更新个人信息
     * @param basicData
     */
    public static void post_5(BasicData basicData) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Gson gson = new Gson();
        String json = gson.toJson(basicData);
        Log.d(TAG,"JSON ============= "+json );
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_5)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message m = new Message();
                m.what = 1;
                m.obj = 0;
                mHandler.sendMessage(m);
                Log.d(TAG, "请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, "----------"+s2);
                    ResZhuCe resZhuCe = new Gson().fromJson(s2, ResZhuCe.class);
                    boolean c = resZhuCe.isSuccess();
                    Log.d(TAG, "----------"+c);
                    if(c){
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 1;
                        mHandler.sendMessage(m);
                    }else {
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 0;
                        mHandler.sendMessage(m);
                    }
                }
            }
        });
    }

    /**
     * 更新头像
     * @param imagePath
     */
    public static void post_5_1(String imagePath){

        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //内容
        requestBody.addFormDataPart("id", MyData.sBasicData.id+"");
            File file = new File(imagePath);
            RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            requestBody.addFormDataPart("file", imagePath, image);
        //请求
        Request request = new Request.Builder()
                .url(MyUtils.URL_11)
                .post(requestBody.build())
                .build();
        //回调
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                int t = call.hashCode();
                Log.d(TAG, "Code="+t);
                String message = e.getMessage();
                Log.d(TAG, "请求失败！"+message);
                Message m = new Message();
                m.what = 2;
                m.obj = 0;
                mHandler.sendMessage(m);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
//                    String s1 = response.headers().toString();
//                    String s2 = response.body().string();
//                    Log.d(TAG, "respon=="+s1+"=="+s2);
                    Message m = new Message();
                    m.what = 2;
                    m.obj = 1;
                    mHandler.sendMessage(m);
                }else {
                    Message m = new Message();
                    m.what = 2;
                    m.obj = 0;
                    mHandler.sendMessage(m);
                }
            }
        });

    }


    /**
     * F1里面的4种
     * <option value="postGraduate">考研</option>
     * 			<option value="competition">竞赛</option>
     * 			<option value="research">考证</option>
     * 			<option value="studyAbroad ">出国留学</option>
     * 			<option value="dailyLearning">日常学习</option>
     */
    public static void get_6(String s) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL_6+"?category="+s+"&yid="+MyData.sBasicData.id)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s1 = response.headers().toString();
                    String s2 = response.body().string();
                    Log.d(TAG, "respon=="+s1+"=="+s2);
                    ResSelect resSelect = new Gson().fromJson(s2, ResSelect.class);
                    if(resSelect.getData().getArticles()!=null){
                        if(resSelect.isSuccess()) {
                            MyData.listSlect.clear();
                            for (ResSelect.DataBean.ArticlesBean e : resSelect.getData().getArticles()) {
                                SeletDatas seletDatas = new SeletDatas(e.getId(), e.getYid(), e.getTitle(), e.getCategory(), e.getContent(), e.getDate(), e.getPhotos(),e.getLx(), e.getNickName(), e.getSignature(), e.getGender(), e.getPhoto(),e.getLikeAmount(),e.getLikeStatus(),e.getFocusStatus(),e.getLeaveAmount());
                                MyData.listSlect.add(seletDatas);
                                Log.d(TAG, "--------" + e.getId());
                            }
                            sentHanderMassage(1, 1);
                        }
                    }else {
                        sentHanderMassage(1, 0);
                    }
                }else {
                    sentHanderMassage(1, 0);
                }
            }
        });
    }

    /**
     * 修改密码
     */
    public static void post_10(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        PosPassword posPassword =  new PosPassword(MyUtils.phone, MyUtils.pass1, MyUtils.pass0);
        Gson gson = new Gson();
        String json = gson.toJson(posPassword);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_10)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResPossword resPossword = new Gson().fromJson(s2, ResPossword.class);
                    if(resPossword.isSuccess()){
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 1;
                        mHandler.sendMessage(m);
                    }else{
                        Message m = new Message();
                        m.what = 1;
                        m.obj = 0;
                        mHandler.sendMessage(m);
                    }
                }
            }
        });

    }


    /**
     * 发布说说
     */
    public static void post_12(){

        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //内容
        requestBody.addFormDataPart("yid", MyData.sBasicData.id+"");
        requestBody.addFormDataPart("title", mImgUrl.get(0));
        requestBody.addFormDataPart("content", mImgUrl.get(1));
        requestBody.addFormDataPart("date", mImgUrl.get(2));
        for (int i = 3; i < 6; i++) {
            String imagePath = mImgUrl.get(i);
            if(!"".equals(imagePath)){
                File file = new File(imagePath);
                RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                requestBody.addFormDataPart("files", imagePath, image);
            }
        }
        //请求
        Request request = new Request.Builder()
                .url(MyUtils.URL_12)
                .post(requestBody.build())
                .build();
        //回调
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                int t = call.hashCode();
                Log.d(TAG, "Code="+t);
                String message = e.getMessage();
                Log.d(TAG, "请求失败！"+message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s1 = response.headers().toString();
                    String s2 = response.body().string();
                    Log.d(TAG, "respon=="+s1+"=="+s2);
                    ResFaBu resFaBu = new Gson().fromJson(s2, ResFaBu.class);
                    if (resFaBu.isSuccess()){
                        sentHanderMassage(1,1);
                    }else {
                        sentHanderMassage(1,0);
                    }

                }
            }
        });
    }


    /**
     * F2里面
     */
    public static void get_13() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL_13+"?yid="+MyData.sBasicData.id)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s1 = response.headers().toString();
                    String s2 = response.body().string();
                    Log.d(TAG, "respon=="+s1+"=="+s2);
                    ResSelect resSelect = new Gson().fromJson(s2, ResSelect.class);
                    if(resSelect.getData().getArticles()!=null){
                        if(resSelect.isSuccess()) {
                            MyData.listSlect.clear();
                            for (ResSelect.DataBean.ArticlesBean e : resSelect.getData().getArticles()) {
                                SeletDatas seletDatas = new SeletDatas(e.getId(), e.getYid(), e.getTitle(), e.getCategory(), e.getContent(), e.getDate(), e.getPhotos(),e.getLx(), e.getNickName(), e.getSignature(), e.getGender(), e.getPhoto(),e.getLikeAmount(),e.getLikeStatus(),e.getFocusStatus(),e.getLeaveAmount());
                                MyData.listSlect.add(seletDatas);
                                Log.d(TAG, "--------" + e.getId());
                            }
                            sentHanderMassage(1, 1);
                        }
                    }else {
                        sentHanderMassage(1, 0);
                    }
                }else {
                    sentHanderMassage(1, 0);
                }
            }
        });
    }
    /**
     * 自己的动态
     */
    public static void get_14() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL_14+"?yid="+MyData.sBasicData.id)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s1 = response.headers().toString();
                    String s2 = response.body().string();
                    Log.d(TAG, "respon=="+s1+"=="+s2);
                    ResSelect resSelect = new Gson().fromJson(s2, ResSelect.class);
                    if(resSelect.getData().getArticles()!=null){
                        if(resSelect.isSuccess()) {
                            MyData.listSlect.clear();
                            for (ResSelect.DataBean.ArticlesBean e : resSelect.getData().getArticles()) {
                                SeletDatas seletDatas = new SeletDatas(e.getId(), e.getYid(), e.getTitle(), e.getCategory(), e.getContent(), e.getDate(), e.getPhotos(),e.getLx(), e.getNickName(), e.getSignature(), e.getGender(), e.getPhoto(),e.getLikeAmount(),e.getLikeStatus(),e.getFocusStatus(),e.getLeaveAmount());
                                MyData.listSlect.add(seletDatas);
                                Log.d(TAG, "--------" + e.getId());
                            }
                            sentHanderMassage(1, 1);
                        }
                    }else {
                        sentHanderMassage(1, 0);
                    }
                }else {
                    sentHanderMassage(1, 0);
                }
            }
        });
    }

    /**
     * 点赞
     */
    public static void post_15(Handler handler,int wid,int yid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        PostZan postZan =  new PostZan(1,wid,yid);

        String json = new Gson().toJson(postZan);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_15)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResZan resZan = new Gson().fromJson(s2, ResZan.class);
                    if(resZan.isSuccess()){
                        sentHanderMassage(1, 1);
                    }else{
                        sentHanderMassage(1, 0);
                    }
                }
            }
        });
    }

    /**
     * 取消点赞
     * @param handler
     * @param wid
     * @param yid
     */
    public static void post_16(Handler handler,int wid,int yid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        PostZan postZan =  new PostZan(1,wid,yid);

        String json = new Gson().toJson(postZan);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_16)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResZan resZan = new Gson().fromJson(s2, ResZan.class);
                    if(resZan.isSuccess()){
                        sentHanderMassage(2, 1);
                    }else{
                        sentHanderMassage(2, 0);
                    }
                }
            }
        });
    }


    /**
     * 收藏
     */
    public static void post_17(Handler handler,int wid,int yid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        Log.d(TAG, "time======"+time);
        PosShouCang posShouCang =  new PosShouCang(time,wid,yid);

        String json = new Gson().toJson(posShouCang);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_17)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResZan resZan = new Gson().fromJson(s2, ResZan.class);
                    if(resZan.isSuccess()){
                        sentHanderMassage(3, 1);
                    }else{
                        sentHanderMassage(3, 0);
                    }
                }
            }
        });
    }

    /**
     * 取消收藏
     */
    public static void post_18(Handler handler,int wid,int yid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        Log.d(TAG, "time======"+time);
        PosShouCang posShouCang =  new PosShouCang(time,wid,yid);

        String json = new Gson().toJson(posShouCang);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_18)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResZan resZan = new Gson().fromJson(s2, ResZan.class);
                    if(resZan.isSuccess()){
                        sentHanderMassage(4, 1);
                    }else{
                        sentHanderMassage(4, 0);
                    }
                }
            }
        });
    }


    /**
     * 关注
     */
    public static void post_19(Handler handler,int yid,int fid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        Log.d(TAG, "time======"+time);
        PosGuanZhu posGuanZhu =  new PosGuanZhu(yid,fid,time);

        String json = new Gson().toJson(posGuanZhu);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_19)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResGuan resGuan = new Gson().fromJson(s2, ResGuan.class);
                    if(resGuan.isSuccess()){
                        sentHanderMassage(5, 1);
                    }else{
                        sentHanderMassage(5, 0);
                    }
                }
            }
        });
    }

    /**
     * 取消收藏
     */
    public static void post_20(Handler handler,int yid,int fid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        Log.d(TAG, "time======"+time);
        PosGuanZhu posGuanZhu =  new PosGuanZhu(yid,fid,time);

        String json = new Gson().toJson(posGuanZhu);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_20)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResGuan resGuan = new Gson().fromJson(s2, ResGuan.class);
                    if(resGuan.isSuccess()){
                        sentHanderMassage(6, 1);
                    }else{
                        sentHanderMassage(6, 0);
                    }
                }
            }
        });
    }

    /**
     * 获取评论
     */
    public static void post_21(Handler handler,int wid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        final Request request = new Request.Builder()
                .url(URL_21+wid)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "pinglun请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResPingLun resPingLun= new Gson().fromJson(s2, ResPingLun.class);
                    MyData.listPing.clear();
                    if(resPingLun.isSuccess()){
                        for(ResPingLun.DataBean.ListBean e : resPingLun.getData().getList()){
                            PingLun.LeaveBean leaveBean = new PingLun.LeaveBean();
                            leaveBean.setId(e.getLeave().getId());
                            leaveBean.setPhoto(e.getLeave().getPhoto());
                            leaveBean.setLwNickName(e.getLeave().getLwNickName());
                            leaveBean.setDate(e.getLeave().getDate());
                            leaveBean.setContent(e.getLeave().getContent());
                            List<PingLun.RepliesBean> replies = new ArrayList<>();
                            for(ResPingLun.DataBean.ListBean.RepliesBean ee : e.getReplies()){
                                PingLun.RepliesBean repliesBean = new PingLun.RepliesBean();
                                repliesBean.setPhoto(ee.getPhoto());
                                repliesBean.setLrNickName(ee.getLrNickName());
                                repliesBean.setLfNickName(ee.getLfNickName());
                                repliesBean.setDate(ee.getDate());
                                repliesBean.setContent(ee.getContent());
                                replies.add(repliesBean);
                            }
                            MyData.listPing.add(new PingLun(leaveBean, replies));
                        }

                        sentHanderMassage(7, 1);
                    }else{
                        sentHanderMassage(7, 0);
                    }
                }
            }
        });
    }

    /**
     * 评论
     */
    public static void post_22(Handler handler,String content, int id, String lwNickName, String photo, int wid, int yid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        Log.d(TAG, "time======"+time);

        PosPingLun posPingLun = new PosPingLun(content,time,id, lwNickName, photo, wid, yid);

        String json = new Gson().toJson(posPingLun);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_22)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "评论请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResGuan resGuan = new Gson().fromJson(s2, ResGuan.class);
                    if(resGuan.isSuccess()){
                        sentHanderMassage(8, 1);
                    }else{
                        sentHanderMassage(8, 0);
                    }
                }
            }
        });
    }


    /**
     * 回复
     */
    public static void post_23(Handler handler,String content, int id, String lfNickName, int lid, String lrNickName, String photo, int wid, int yfid, int yid){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        Log.d(TAG, "time======"+time);

        PosHuiFu posHuiFu = new PosHuiFu(content,time,id,lfNickName, lid, lrNickName, photo, wid, yfid, yid);
        Log.d(TAG, "=============="+lid);
        String json = new Gson().toJson(posHuiFu);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder()
                .url(URL_23)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "评论请求成功！");
                int t = response.code();
                Log.d(TAG, "code---"+t);
                if(t == 200){
                    String s2 = response.body().string();
                    Log.d(TAG, s2);
                    ResGuan resGuan = new Gson().fromJson(s2, ResGuan.class);
                    if(resGuan.isSuccess()){
                        sentHanderMassage(9, 1);
                    }else{
                        sentHanderMassage(9, 0);
                    }
                }
            }
        });
    }


    /**
     * 获取关注
     */
    public static void post_24(Handler handler){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        final Request request = new Request.Builder()
                .url(URL_24_1+MyData.sBasicData.id)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "pinglun请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    String ss = response.body().string();
                    Log.d(TAG, ss);
                    MyData.sBasicData.guanZhuList.clear();
                    ResFenSiGuanZhu resFenSiGuanZhu = new Gson().fromJson(ss, ResFenSiGuanZhu.class);
                    for(ResFenSiGuanZhu.DataBean.ListBean e : resFenSiGuanZhu.getData().getList()){
                        MyData.sBasicData.guanZhuList.add(new FriendData(e.getId(),e.getNickName(),e.getSignature(),e.getGender(),e.getPhone(),e.getPassword(),e.getSchool(),e.getMajor(),e.getPhoto()));
                    }
                    post_24_2(handler);
                }
            }
        });
    }

    /**
     * 获取粉丝
     */
    public static void post_24_2(Handler handler){
        mHandler = handler;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        final Request request = new Request.Builder()
                .url(URL_24_2+MyData.sBasicData.id)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "pinglun请求成功！");
                int t = response.code();
                Log.d(TAG, "code-------------------"+t);
                if(t == 200){
                    MyData.sBasicData.fenSiList.clear();
                    ResFenSiGuanZhu resFenSiGuanZhu = new Gson().fromJson(response.body().string(), ResFenSiGuanZhu.class);
                    for(ResFenSiGuanZhu.DataBean.ListBean e : resFenSiGuanZhu.getData().getList()){
                        MyData.sBasicData.fenSiList.add(new FriendData(e.getId(),e.getNickName(),e.getSignature(),e.getGender(),e.getPhone(),e.getPassword(),e.getSchool(),e.getMajor(),e.getPhoto()));

                    }
                    sentHanderMassage(2, 1);
                }
            }
        });
    }
}





















