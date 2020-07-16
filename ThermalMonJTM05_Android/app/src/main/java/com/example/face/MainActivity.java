//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.
/**
 * V1.22
 * 软件版权声明
 *
 * 《人脸热成像测温软件》（英文名：ThermalMonJTM05）简称JTM05，系东莞市晶研仪器科技有限公司独立开发软件，东莞市晶研仪器科技有限公司依法独立享有该软件之所有权利，此软件所有内容（包括但不限于文字、图片等）均有版权限制，采用请注意许可。
 *
 * 该软件使用者（含个人、法人或其他组织），将之用于盈利或非盈利性的任何用途，必须经东莞市晶研仪器科技有限公司授权许可。
 *
 * 为适应实际的开发平台应用环境，对其功能、性能、界面等可以进行必要的修改，但不得去除东莞市晶研仪器科技有限公司的版本标示；未经东莞市晶研仪器科技有限公司书面授权许可，不得向任何第三方提供修改后的软件。
 *
 * 使用该软件必须保留东莞市晶研仪器科技有限公司的版权声明，将该软件从原有自然语言转换成另一自然语言文字的，仍应注明出处，并不得向任何第三方提供修改后的软件。
 *
 * 不得有其他侵犯东莞市晶研仪器科技有限公司软件版权之行为。
 *
 * 凡有上述侵权行为的个人、法人或其他组织，必须立即停止侵权并对其侵权行为造成的一切不良后果承担全部责任。对此前，尤其是此后侵犯东莞市晶研仪器科技有限公司版权的行为，东莞市晶研仪器科技有限公司将依据《著作权法》、《计算机软件保护条例》等相关法律法规追究其经济责任和法律责任。
 *
 * Software copyright notice
 *
 * ThermalMonJTM05(Chinese Name: 人脸热成像测温软件) JTM05 for short, is an independent software developed by Dongguan Jingyan Instrument Technology Co., Ltd. Dongguan Jingyan Instrument Technology Co., Ltd. enjoys all rights of the software according to law. All contents (including but not limited to words and pictures) of the software are subject to copyright restrictions. Please pay attention to the license when using it.
 *
 * Users of the software (including individuals, legal persons or other organizations) must be authorized by Dongguan Jingyan Instrument Technology Co., Ltd. to use it for profit or non-profit purposes.
 *
 * In order to adapt to the actual application environment of the development platform, the function, performance and interface of the platform can be modified as necessary, but the version mark of Dongguan Jingyan Instrument Technology Co., Ltd. shall not be removed. Without the written authorization of Dongguan Jingyan Instrument Technology Co., Ltd., the modified software shall not be provided to any third party.
 *
 * The copyright notice of Dongguan Jingyan Instrument Technology Co., Ltd. must be retained when using the software. If the software is converted from the original natural language to another natural language, the source should be noted, and the modified software should not be provided to any third party.
 *
 * Do not infringe the software copyright of Dongguan Jingyan Instrument Technology Co., Ltd.
 *
 * Any individual, legal person or other organization that has committed the above-mentioned infringement must immediately stop the infringement and bear full responsibility for all the adverse consequences caused by the infringement. Before this, especially after that, if the copyright of Dongguan Jingyan Instrument Technology Co., Ltd. is infringed, Dongguan Jingyan Instrument Technology Co., Ltd. will investigate its economic and legal responsibilities according to <the copyright law>, <regulations on the protection of computer software> and other relevant laws and regulations.
 * **/
package com.example.face;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.face.Dialog.CameraSetDialog;
import com.example.face.Dialog.ModeSelectorDialog;
import com.example.face.Temp.TempDataArray;
import com.example.face.Temp.TempDataIndex;
import com.example.face.ThreadTool.TimingArrayList;
import com.example.face.Dialog.AskResetDialog;
import com.example.face.Dialog.ErrorDialog;
import com.example.face.Tool.FacePosition;
import com.example.face.Dialog.FaceSetDialog;
import com.example.face.Tool.FaceTemp;
import com.example.face.Dialog.LanguageDialog;
import com.example.face.Dialog.LightSetDialog;
import com.example.face.Dialog.MenuDialog;
import com.example.face.Dialog.MusicDialog;
import com.example.face.Tool.LightState;
import com.example.face.Tool.MusicPlay;
import com.example.face.Tool.MyDrawData;
import com.example.face.Tool.MyLineView;
import com.example.face.Dialog.SettingDialog;
import com.example.face.Tool.MyTestDrawData;
import com.example.face.Tool.ParameterArray;
import com.example.face.Tool.StringUtil;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private ImageView background;
    private Context context;

    //硬件设备编号
    private int targetDeviceVendorId = 1155;
    private int targetDeviceProductId = 22336;

    //USB传输
    private UsbManager usbManager = null;
    private UsbDevice targetDevice = null;
    private UsbDeviceConnection targetConnection = null;
    private UsbInterface usbInterface = null;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private UsbEndpoint mBulkInPoint = null;
    private UsbEndpoint mBulkOutPoint = null;
    private int mBulkPacketSize;

    private final int readNumBytes = 1024 * 10;
    private byte[] usbData;
    String TAG = "JYYQ";


    private UsbReadThread mReadThread;

    private int readSpeed = 0;

    protected final Object ReadQueueLock = new Object();
    protected final Object WriteQueueLock = new Object();

    private final int WriteTimeOutMillis = 300;
    private final int ReadTimeOutMillis = 300;


    private AppCompatTextView tempTv; //温度显示控件
    private AppCompatTextView blackTv; // 黑体温度显示
    private JavaCamera2View javaCameraView; //OpenCV摄像头控件
    private ImageView cameraImg; // 显示主界面
    private MyLineView myLineView; //人脸框线条绘制
    private Mat rgba = new Mat();  //摄像头Mat
    private Mat showImg = new Mat(); //显示Mat


    private ParameterArray parameterArray;
    //灯光状态 红绿蓝白色彩状态类
    private LightState lightState;

    //报警音乐及通过音乐的播放状态
    private boolean isPlayDanger = false;
    private boolean isPlayNormal = false;



    final Bitmap bitmap = Bitmap.createBitmap(480,640,Bitmap.Config.ARGB_8888);
    ArrayList<FacePosition> facePositionArrayList = new ArrayList<FacePosition>();
    ArrayList<Integer> peopleList = new ArrayList<Integer>();
    //机器反馈数据缓冲区
    StringBuffer resultData = new StringBuffer("");

    int upPeopleNum = 0;


    TempDataArray[] tempDataArrays = new TempDataArray[5];

    private MusicPlay musicPlay; //音乐播放控制类

    private ImageView imageView;
    private ImageView allImgView;
    private AppCompatTextView showReturnTxt;

    //刷新变量
    private int refreshNum = 0;
    //温度数组
    private double[] aveArrays = new double[5];

    private int lastFace = 0; //记录上一次总人数

    Handler askLight;
    Runnable askRunnable;

    //设置按钮
    private Button settingBtn;
    //图标按钮
    private Button logoBtn;

    //内部数据测试显示
    private boolean isRoot = false;

    //测试数据显示
    private AppCompatTextView testTvShow;

    private int heightScale = 50;


    //标志符 0为默认的人脸检测 1为面部人脸显示
    private int CAMERA_CHECK_STATE = 0;

    //显示背景
    private LinearLayout imageBackground;



    //定时接口
    private TimingArrayList timingThread;
    private ArrayList<TempDataIndex> indexArrayList = new ArrayList<TempDataIndex>();


    //当前的偏差
    private int hind_t;
    private int hind_b;
    private int hind_l;
    private int hind_r;
    private String hind_face;

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    //初始化OpenCV成功
                   // javaCameraView.enableView();
                }
                break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        hideNavigationBar(); //隐藏状态栏

        setContentView(R.layout.activity_main);

        context = this;
        parameterArray = new ParameterArray(this);

        initTempArray();//初始化数据组

        initUI();

        initJsonData();

        initOther();

        musicPlay = new MusicPlay(this,parameterArray.getLanguageMode());

        musicPlay.setCallBackListener(new MusicPlay.Request() {
            @Override
            public void notDanger() {
                isPlayDanger = false;
            }

            @Override
            public void notNormal() {
                isPlayNormal = false;
            }
        });
        musicPlay.changeValue(parameterArray.getMusicValue()); //修改音量大小

        getUsbDevice();

        for (int i = 0;i<10;i++){
            peopleList.add(0);
        }

    }

    //初始化界面
    public void initUI(){
        testTvShow = findViewById(R.id.test_tv);
        settingBtn = findViewById(R.id.setting_button);


        logoBtn = findViewById(R.id.icon_img);
        logoBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isRoot = !isRoot;
                if (isRoot){
                    Toast.makeText(MainActivity.this,"打开测试模式",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"关闭测试模式",Toast.LENGTH_SHORT).show();
                    testTvShow.setText("");
                }
                return false;
            }
        });
        //isRoot = true;
        imageView = findViewById(R.id.img_show_view);
        allImgView = findViewById(R.id.img_all_view);
        showReturnTxt = findViewById(R.id.result_text_show);
        imageBackground = findViewById(R.id.img_background);
        tempTv = findViewById(R.id.temp_tv);
        blackTv = findViewById(R.id.black_tv);
        
        javaCameraView = this.findViewById(R.id.camera);
        javaCameraView.setVisibility(View.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);


        background = findViewById(R.id.background);
        background.setImageResource(R.drawable.normal_bg);
        cameraImg = findViewById(R.id.camera_show);
        myLineView = findViewById(R.id.camera_data);

//
    }


    //初始化参数设置
    public void initJsonData(){

        myLineView.setShowMojo(parameterArray.getExpression());
        myLineView.setDangerAndMin(parameterArray.getDanger(), parameterArray.getLowTemp());
    }


    public void initOther(){
        timingThread = new TimingArrayList();
        timingThread.setResultClick(new TimingArrayList.TimingResult() {
            @Override
            public void didShow(int index) {
                //Log.e("JYYQ_TEMP","INDEX:"+index);
                indexArrayList.get(index).setType(true);
            }
        });
        for (int i = 0;i<5;i++){
            TempDataIndex index = new TempDataIndex();
            indexArrayList.add(index);
        }
    }

    //初始化温度数组
    public void initTempArray(){
        lightState = new LightState();
        for (int i = 0;i<5;i++){
            TempDataArray tempDataArray = new TempDataArray();
            tempDataArrays[i] = tempDataArray;
            aveArrays[i] = 0;
        }
    }


    //弹出设置框

    public void showSettingDialog(View view){

        MenuDialog menuDialog = new MenuDialog(this,parameterArray.getLanguageMode());
        menuDialog.setCallBackListener(new MenuDialog.SettingRequest() {
            @Override
            public void click(String index) {
                Message msg = new Message();
                msg.what = Integer.valueOf(index); //根据返回值进行弹窗
                resultHandle.sendMessage(msg);
            }
            @Override
            public void out() {
                hideNavigationBar();
            }
        });
        menuDialog.setCanceledOnTouchOutside(false);
        menuDialog.show();

    }

    @Override
    public void onPause(){
        super.onPause();
        if (javaCameraView != null)
            javaCameraView.disableView();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("JYYQ_DEBUGE","DEBUGE");
        if (!OpenCVLoader.initDebug()){
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,baseLoaderCallback);
        }else{
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.e("JYYQ",width+"{}"+height);
        rgba = new Mat(width,height, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        rgba.release();
    }


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        rgba = inputFrame.rgba();

        if (parameterArray.getLightModeState() ==2){ //判断闪光灯模式

            if (parameterArray.getCheckLight()==1){
                //检测亮度
                Mat bgrs = new Mat();
                Imgproc.cvtColor(rgba,bgrs,Imgproc.COLOR_RGBA2BGR);
                int are = Light_Result(bgrs);
                bgrs.release();

                if (are<= parameterArray.getLightModeData() && !lightState.getWhiteType()){
                    Log.e("JYYQ_LIGHT_ARE","SEND HIGH");
                    usbWrite(StringUtil.getLightOrder(true));
                }
                if (are> parameterArray.getLightModeData() && lightState.getWhiteType()){
                    Log.e("JYYQ_LIGHT_ARE","SEND LOW");
                    usbWrite(StringUtil.getLightOrder(false));
                }
                parameterArray.setCheckLight(0);
            }
        }

        /**
         * 人脸识别流程
         * 1、旋转图像并修改图像大小为240*320
         * 2、调用facedetect方法识别人脸
         * 3、根据设定值排除人脸
         * 4、将检测到的人脸坐标以y轴中心作为分界线排列
         * 5、将人脸数组用X轴排序
         * 6、将人脸数据发送至机器
         * **/

        showImg = rotateRight(rgba); //旋转图像

        Mat faceMat = new Mat();
        Imgproc.resize(showImg,faceMat,new Size(240,320));


        final int finalNum;
        //标志符 0为默认的人脸检测 1为面部人脸显示

        if (CAMERA_CHECK_STATE == 0){

            Face[] facesArray;
            int length = 0;
            try {
                facesArray = facedetect(faceMat.getNativeObjAddr());
                length = facesArray.length;
            } catch (Exception e) {
                facesArray = new Face[0];
                e.printStackTrace();
            }
            Log.e("JYYQ","HAVE FACE: "+length);

            ArrayList<FacePosition> facePositions = new ArrayList<FacePosition>();


            ArrayList<FacePosition> faceTop = new ArrayList<FacePosition>();
            ArrayList<FacePosition> faceBom = new ArrayList<FacePosition>();

            if (length > 0 ){
                //检测人脸是否符合标准
                for (int i =0;i<length;i++){
                    Face face = facesArray[i];
                    int faceSize = faceTop.size()+faceBom.size();

                    if (face.faceConfidence >= parameterArray.getFaceMinPercentage() && faceSize < 5){
                        Rect rect = face.faceRect;
                        int face_size = rect.width*rect.height;

                        if (face_size >= (parameterArray.getFaceMinSize() * parameterArray.getFaceMinSize()) && face_size <= (parameterArray.getFaceMaxSize() * parameterArray.getFaceMaxSize())){
                            FacePosition facePosition = new FacePosition(rect,this.heightScale);
                            if (rect.y<160){
                                faceTop.add(facePosition);
                            }else{
                                if (rect.y <=260){
                                    faceBom.add(facePosition);
                                }
                            }
                        }
                    }
                }
                //人脸排序
                Collections.sort(faceTop, new Comparator<FacePosition>() {
                    @Override
                    public int compare(FacePosition o1, FacePosition o2) {
                        if (o1.rect.x > o2.rect.x){
                            return 1;
                        }else {
                            return -1;
                        }
                    }
                });


                //人脸排序
                Collections.sort(faceBom, new Comparator<FacePosition>() {
                    @Override
                    public int compare(FacePosition o1, FacePosition o2) {
                        if (o1.rect.x > o2.rect.x){
                            return 1;
                        }else {
                            return -1;
                        }
                    }
                });

                facePositions.addAll(faceTop);
                facePositions.addAll(faceBom);
                int face_group_length = facePositions.size();

                ArrayList<FacePosition> faceLastPositions = new ArrayList<FacePosition>();

                if (face_group_length > 1){
                    for (int i = 0;i<face_group_length;i++){
                        boolean isIn = false; //标志符 判断人脸是否重复
                        FacePosition nowFace = facePositions.get(i);
                        for (int j =0;j<face_group_length;j++){ //历遍其他人脸
                            if (j!=i){
                                FacePosition upFace = facePositions.get(j);
                                if (!checkIsInFace(upFace,nowFace)){ //判断当前人脸是否在其他人脸范围内
                                    isIn = true;
                                }
                            }
                        }
                        if (!isIn){
                            faceLastPositions.add(nowFace);

                        }
                    }
                    facePositions.clear();
                    facePositions.addAll(faceLastPositions);
                }


                //发送查询命令
                facePositionArrayList = facePositions;
                if (facePositions.size() > 0) {
                    String rectOrders = StringUtil.getRectangleFaceOrder(facePositions, "01");
                    usbWrite(rectOrders);
                }

            }
            //判断是否需要清除数据
            if (upPeopleNum!=0 && facePositions.size() ==0){
                lastFace = 0;
                isPlayDanger = false;
                isPlayNormal = false;

                facePositionArrayList.clear();
                this.upPeopleNum = 0;
                //清除数据组
                for (int i =0;i<5;i++){
                    tempDataArrays[i].clear();
                }
                new Handler(this.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        myLineView.clearView();
                    }
                });
            }

            this.upPeopleNum = facePositions.size();
            length = facePositions.size();
            //计算均值
            int up = average(this.peopleList);
            int num = 0;
            if (up == 0 && length > 0){
                this.peopleList.clear();
                num = length;
                for (int i = 0;i<10;i++){
                    peopleList.add(length);
                }
            }else {
                this.peopleList.remove(0);
                this.peopleList.add(length);
                num = average(this.peopleList);
            }

            faceMat.release();
            finalNum = num;

        }
        else if (CAMERA_CHECK_STATE == 1 ){
            finalNum = 0;
            Face[] facesArray = facedetect(faceMat.getNativeObjAddr());
            int length = facesArray.length;
            if (length > 0 ){
                //检测人脸是否符合标准
                Face face = facesArray[0];
                Rect rect = face.faceRect;
                FacePosition facePosition = new FacePosition(rect,this.heightScale);
                ArrayList<FacePosition> facePositions = new ArrayList<FacePosition>();
                facePositions.add(facePosition);
                String ord = StringUtil.getAllFace(facePositions);
                usbWrite(ord);
            }
        }else {
            finalNum = 0;
        }
        Utils.matToBitmap(showImg,bitmap);
        //调用线程进行显示图像
        new Handler(this.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                cameraImg.setImageBitmap(bitmap);
                if (finalNum == 0){
                    background.setImageResource(R.drawable.normal_bg);
                    if (lightState.getCloseLight()){//判断是否修改指示灯
                        usbWrite(StringUtil.getCloseLightOrder());
                    }
                }
            }
        });

        return rgba;
    }

    public boolean checkIsInFace(FacePosition face,FacePosition other){
        int x_start = face.rect.x;
        int y_start = face.rect.y;
        int x_end = face.rect.x+face.rect.width;
        int y_end = face.rect.y+face.rect.height;
        int other_x = other.rect.x;
        int other_y = other.rect.y;
        if (other_x > x_start && other_x < x_end && other_y > y_start && other_y <y_end){
            return false;
        }
        return true;
    }



    //定义人脸检测方法
    public final native Face[] facedetect(long var1);

    //加载人脸检测方法
    static {
        System.loadLibrary("facedetection");
    }


    public Mat rotateRight(Mat mat){
        Mat temp = new Mat();
        Core.transpose(mat,temp);
        Mat result = new Mat();
        Core.flip(temp,result,0);
        Core.flip(result,result,1);
        temp.release();
        return result;
    }

    public Mat rotateLeft(Mat mat){
        Mat temp = new Mat();
        Core.transpose(mat,temp);
        Mat result = new Mat();
        Core.flip(temp,result,0);
        Core.flip(result,result,0);
        temp.release();
        return result;
    }

    /**
     * 温度计算公式
     * fore：温度值+温度偏差值
     * tRoom： 室温值
     * return：公式计算出的人脸温度值
     * **/
    public float getTcore(float fore,float tRoom){

        double tdiff = 34.51-(parameterArray.getAmbient()*tRoom+27.95);
        //double Tcore = (fore+tdiff)+4-(tdiff+fore-25)/5;
        double Tcore;
        if ((fore+tdiff)<37.5){
            Tcore = (fore+tdiff)+11.9-(tdiff+fore-12)/2.3;
        }else {
            Tcore = (fore+tdiff)+4-(tdiff+fore-25)/5;
        }
        if (Tcore < fore){
            Tcore = fore;
        }
        return (float) Tcore;
    }


    //计算均值
    public static int average(ArrayList<Integer> list) {
        // 'average' is undefined if there are no elements in the list.
        if (list == null || list.isEmpty())
            return 0;
        // Calculate the summation of the elements in the list
        long sum = 0;
        int n = list.size();
        // Iterating manually is faster than using an enhanced for loop.
        for (int i = 0; i < n; i++)
            sum += list.get(i);
        // We don't want to perform an integer division, so the cast is mandatory.
        return (int) (sum/n);
    }




    //热成像交互 
    //---------------------------------------------------------------------------------------------------------------------------------------
    private void getUsbDevice() {
        //获取目标usb设备
        usbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceMap = usbManager.getDeviceList();
        Iterator<UsbDevice> iterator = deviceMap.values().iterator();
        while(iterator.hasNext()) {
            UsbDevice device = iterator.next();
            if (device.getVendorId() == targetDeviceVendorId && device.getProductId() == targetDeviceProductId) {
                targetDevice = device;
                Log.e("JYYQ",device.getVendorId()+","+device.getProductId());
                break;
            }
        }

        if (targetDevice == null) {
            //弹窗提示错误
            ErrorDialog errorDialog = new ErrorDialog(context,parameterArray.getLanguageMode());
            errorDialog.setCallBackListener(new ErrorDialog.ErrorRequest() {
                @Override
                public void click(int select) {
                    if (select == 1){
                        getUsbDevice();
                    }
                    hideNavigationBar();
                }
            });
            errorDialog.show();
            return;
        }

        //获取使用权限 ,
        if (!usbManager.hasPermission(targetDevice)) {
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            context.registerReceiver(mUsbPermissionReceiver, filter);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
            usbManager.requestPermission(targetDevice, pi);
        } else {
            initUsbDevice();
        }
    }
    private BroadcastReceiver mUsbPermissionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                boolean granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                if (granted) {
                    //获得了usb使用权限
                    Log.i(TAG, "获得了usb使用权限");
                    initUsbDevice();
                } else {
                    Log.i(TAG, "用户不允许USB访问设备");
                }
            }
        }
    };

    //初始化驱动
    private void initUsbDevice() {
        int interfaceCount = targetDevice.getInterfaceCount();

        for (int i = 0; i < interfaceCount; i++) {
            usbInterface = targetDevice.getInterface(i);
            if (usbInterface.getInterfaceClass() == UsbConstants.USB_CLASS_PRINTER) {
                break;
            }
        }
        if (usbInterface != null) {
            //获取UsbDeviceConnection
            targetConnection = usbManager.openDevice(targetDevice);
            if (targetConnection != null) {
                if (targetConnection.claimInterface(usbInterface, true)) {
                    for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                        UsbEndpoint endpoint = usbInterface.getEndpoint(j);
                        //类型为大块传输
                        if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                            if (endpoint.getDirection() == UsbConstants.USB_DIR_OUT) {
                                mBulkOutPoint = endpoint;
                            } else {
                                mBulkInPoint = endpoint;
                            }
                            mBulkPacketSize = endpoint.getMaxPacketSize();
                        }
                    }
                    usbData = new byte[readNumBytes];
                    startReadThread();
                }
            }
        }

        //根据设置的灯光模式发送灯光命令
        if (parameterArray.getLightModeState() == 0 || parameterArray.getLightModeState() == 2){
            usbWrite(StringUtil.getLightOrder(false));
        }else if (parameterArray.getLightModeState() == 1){
            usbWrite(StringUtil.getLightOrder(true));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //开启线程 定时检测是否需要自动检测环境光
        askLight = new Handler();
        askRunnable = new Runnable() {
            @Override
            public void run() {
                if (parameterArray.getCheckLight() == 0 && parameterArray.getLightModeState() == 2){
                    parameterArray.setCheckLight(1);
                }
                askLight.postDelayed(this, 3000);
            }
        };
        askLight.post(askRunnable);
        //checkCameraPermission();
        //MARK:发送相机启动命令
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        usbWrite(StringUtil.getCameraOpen(true));

        if (parameterArray.getModeShow() == 1){
            timingThread.beginRun();
        }else {
            dissTiming();
        }

    }

    //开启读取线程
    private void startReadThread() {
        if (mReadThread != null && mReadThread.isAlive()) {

        } else {
            mReadThread = new UsbReadThread();
            mReadThread.start();
        }
    }



    private class UsbReadThread extends Thread {
        @Override
        public void run() {
            try {
                while (isConnected()) {
                    if (isInterrupted()) {
                        break;
                    }
                    usbRead();
                    Thread.sleep(readSpeed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //判断机器是否连接
    private boolean isConnected() {
        return targetConnection != null && mBulkInPoint != null && mBulkOutPoint != null;
    }

    private int usbRead() {
        synchronized (ReadQueueLock) {
            int numBytesRead = targetConnection.bulkTransfer(mBulkInPoint, usbData, readNumBytes, ReadTimeOutMillis);

            if (numBytesRead > 0) {
                String readStr = StringUtil.bytes2HexStr(usbData, numBytesRead);
                Log.d("JYYQ",readStr.length()+"LONG");
                resultData.append(readStr);
                //Log.e("JYYQ_DISTANCE_FACE_GET","GET:"+resultData.toString());

                if (resultData.length() > 16){
                    int index = resultData.indexOf("AAAA0002");
                    Log.d("JYYQ","index"+index);
                    if (index != -1){
                        String data = resultData.substring(index);
                        int dataLength = data.length();
                        if (dataLength > 16){
                            String size = data.substring(8,12);
                            int length = StringUtil.hexStr2Int(size)*2+12;
                            Log.d("JYYQ","LENGTH:"+length+","+size);
                            if (dataLength >= length){
                                String order = data.substring(8,length-4);
                                String xor = data.substring(length-2,length);
                                String checkXor = StringUtil.checkXor(order);
                                Log.d("JYYQ","TYPE:"+data.substring(12,14)+",xor:"+xor+"."+checkXor+"orderLength:"+order.length());
                                if (xor.equalsIgnoreCase(checkXor)){ //校验xor通过
                                    int orderType = StringUtil.hexStr2OneInt(data.substring(12,14));
                                    ArrayList<FacePosition> list = facePositionArrayList;
                                    if (orderType == 1){
                                        int faceNum = StringUtil.hexStr2OneInt(data.substring(14,16)); //人脸数量
                                        float tRoom  = StringUtil.hexStr2Float(data.substring(20,24))/100-100;
                                        float black = StringUtil.hexStr2Float(data.substring(16,20))/100-100;
                                        final double[] t = new double[faceNum];
                                        int maxFace = list.size();
                                        final StringBuffer testData = new StringBuffer("");
                                        for (int i = 0;i<faceNum;i++){
                                            if (i<maxFace) {
                                                String face = data.substring(24 + i * 4, 24 + (i + 1) * 4);
                                                float temp = StringUtil.hexStr2Float(face)/100-100;

                                                float tcore = getTcore((float) (temp + this.parameterArray.getDeviation()), tRoom);

                                                double itemTemp = getShowTemp(tcore,list.get(i).getSize());
                                                t[i] = itemTemp;
                                                if (isRoot){
                                                    testData.append("NO："+i+"\n");
                                                    testData.append("机器反馈数据："+face+"\n");

                                                    String tempStr = String.format("%.1f",temp);
                                                    testData.append("计算前数据："+tempStr+"\n");

                                                    String tcoreStr = String.format("%.1f",tcore);
                                                    testData.append("计算后数据："+tcoreStr+"\n");

                                                    String sizeStr = String.format("%.1f",itemTemp);
                                                    testData.append("比例修正后："+sizeStr+"\n");
                                                }
                                            }
                                        }

                                        //判断人数是否变化
                                        Log.d("JYYQ_DISTANCE_FACE_GET","-------------------------------------");
                                        if (lastFace != maxFace){

                                            isPlayNormal = false;
                                            isPlayDanger = false;
                                            /**
                                             * 人数发生改变
                                             * 1、根据人脸坐标将原有人脸数据移动至新的位置
                                             * 2、初始化新人脸数据
                                             * 3、根据当前模式判断是否需要进行精准计算
                                             * **/
                                            ArrayList<TempDataArray> tempDataArrayArrayList = new ArrayList<>();
                                            ArrayList<TempDataIndex> dataArrayList = new ArrayList<TempDataIndex>();
                                            TempDataArray[] dataArrayListUp = tempDataArrays;
                                            int[] timingGroup = new int[maxFace];
                                            timingThread.dissRun();
                                            Log.d("JYYQ_FACE","FACE NUM:" + maxFace);
                                            for (int j=0;j<5;j++){
                                                if (j<maxFace){
                                                    //历遍判断是否含有对应的数据

                                                    TempDataArray tempDataArray = new TempDataArray();
                                                    TempDataIndex indexTemp = new TempDataIndex();

                                                    FacePosition facePosition = list.get(j);
                                                    boolean change = false;
                                                    for (int k = 0;k<5;k++){
                                                        boolean isIn = dataArrayListUp[k].checkDistance(facePosition.getX(),facePosition.getY());
                                                        if (isIn){
                                                            change = true;
                                                            tempDataArray.setArray(dataArrayListUp[k].faceTemp);
                                                            if (parameterArray.getModeShow() == 1){
                                                                int ind = timingThread.getWaitItem(k);
                                                                Log.d("JYYQ_DISTANCE_TEMP","KIND:"+k+","+indexArrayList.get(k).temp+","+j+","+indexArrayList.get(j).temp);
                                                                timingGroup[j] = ind;

                                                                indexTemp.temp = indexArrayList.get(k).temp;
                                                                indexTemp.setType(indexArrayList.get(k).type);
                                                                Log.d("JYYQ_DISTANCE_TEMP",indexTemp.temp+"");
                                                            }
                                                        }

                                                    }
                                                    if (!change){
                                                        Log.d("JYYQ_DISTANCE_TEMP","NOTHING"+t[j]);
                                                        tempDataArray.replace(t[j]);
                                                        if (parameterArray.getModeShow() == 1){
                                                            timingGroup[j] = 0;
                                                            indexTemp.temp = t[j];
                                                            indexTemp.setType(false);
                                                        }
                                                    }
                                                    tempDataArray.setData(facePosition.getX(),facePosition.getY());
                                                    tempDataArrayArrayList.add(tempDataArray);

                                                    dataArrayList.add(indexTemp);

                                                }else {

                                                }
                                            }
                                            Log.d("JYYQ_DISTANCE_TEMP","-------------------------------------");
                                            for (int d =0;d<5;d++){
                                                if (d<maxFace){
                                                    tempDataArrays[d] = tempDataArrayArrayList.get(d);
                                                    indexArrayList.get(d).temp = dataArrayList.get(d).getTemp();
                                                    indexArrayList.get(d).type = dataArrayList.get(d).getType();
                                                    //indexArrayList.get(d).setType(dataArrayList.get(d).getType());
                                                    if (parameterArray.getModeShow() == 1){
                                                        timingThread.setWait(d,timingGroup[d]);
                                                        timingThread.startWait(d);
                                                    }
                                                }else {
                                                    tempDataArrays[d].clearData();
                                                }
                                            }
                                            timingThread.beginRun();
                                        }else {
                                            //历遍计算
                                            /**
                                             * 人数未发生改变
                                             * 1、依次添加新温度值到对应数组
                                             * 2、计算当前温度数组均值
                                             * 3、每四次检测添加新数据进行一次均值检测，如果均值与当前值相差+-0.5则将原数组替换为当前值
                                             * **/
                                            refreshNum+=1;
                                            for (int j =0;j<maxFace;j++){
                                                double temp = t[j];
                                                tempDataArrays[j].addNewData(temp);
                                                indexArrayList.get(j).setTemp(temp);

                                                double average = tempDataArrays[j].getAverage();
                                                if (refreshNum>=4){
                                                    if (aveArrays[j] == 0){
                                                        aveArrays[j]=average;
                                                    }else {
                                                        double hind = temp-aveArrays[j];
                                                        if (hind > 0.5 || hind <-0.5){
                                                            tempDataArrays[j].replace(temp);
                                                            average = temp;
                                                            Log.e("JYYQ_DISTANCE","清空缓存");
                                                            if (parameterArray.getModeShow() == 1){
                                                                timingThread.restartWait(j);
                                                            }
                                                        }
                                                        aveArrays[j]=average;
                                                    }
                                                }
                                                t[j] = average;
                                            }
                                            if (refreshNum>=4){
                                                refreshNum = 0;
                                            }
                                        }
                                        lastFace = maxFace;

                                        if (isRoot){
                                            new Handler(this.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    testTvShow.setText(testData.toString());
                                                }
                                            });
                                        }

                                        Log.d("JYYQ_STATE",t[0]+"");
                                        MyDrawData myDrawData = new MyDrawData(list,t,tRoom,black,indexArrayList);
                                        Message msg = new Message();
                                        msg.what = 1;
                                        msg.obj = myDrawData;
                                        handler.sendMessage(msg);
                                    }
                                    if (orderType == 2){
                                        String faceString = data.substring(22,dataLength-4);
                                        //double[] faceGroup = StringUtil.getFaceView(faceString);
                                        float tRoom  = StringUtil.hexStr2Float(data.substring(18,22))/100-100;
                                        //Log.e("JYYQ_ROOM",tRoom+","+data.substring(20,24));
                                        float black = StringUtil.hexStr2Float(data.substring(14,18))/100-100;
                                        final String tem = String.format("%.1f",tRoom);
                                        final String bla = String.format("%.1f",black);

                                        FaceTemp faceTemp = StringUtil.getDraw(faceString);
                                        Mat mat = new Mat(60,80,CvType.CV_8UC3);
                                        mat.put(0,0,faceTemp.group);
                                        Mat right = rotateRight(mat);
                                        //创建Bitmap
                                        Bitmap bitmap = Bitmap.createBitmap(60,80,Bitmap.Config.ARGB_8888);
                                        Utils.matToBitmap(right,bitmap);
                                        //使用Message发送数据
                                        right.release();
                                        mat.release();
                                        MyTestDrawData tt = new MyTestDrawData(bitmap,20,tem,bla);
                                        Message msg = new Message();
                                        msg.what = 3;
                                        msg.obj = tt;
                                        handler.sendMessage(msg);
                                    }
                                    if (orderType == 3){ //读取区域内的数据
                                        String faceWidth = data.substring(22,24);
                                        String faceHeight = data.substring(24,26);
                                        float tRoom  = StringUtil.hexStr2Float(data.substring(18,22))/100-100;
                                        //Log.e("JYYQ_ROOM",tRoom+","+data.substring(20,24));
                                        float black = StringUtil.hexStr2Float(data.substring(14,18))/100-100;
                                        final String tem = String.format("%.1f",tRoom);
                                        final String bla = String.format("%.1f",black);

                                        String faceData = data.substring(26,dataLength-4);
                                        int fw = StringUtil.hexStr2OneInt(faceWidth);
                                        int fh = StringUtil.hexStr2OneInt(faceHeight);
//
                                        FaceTemp faceTemp = StringUtil.getDraw(faceData);

                                        Mat mat = new Mat(fh,fw,CvType.CV_8UC3);
                                        mat.put(0,0,faceTemp.group);
                                        Mat right = rotateLeft(mat);

                                        Bitmap rot = Bitmap.createBitmap(fh,fw,Bitmap.Config.ARGB_8888);
                                        Utils.matToBitmap(right,rot);
                                        right.release();
                                        mat.release();
                                        MyTestDrawData tt = new MyTestDrawData(rot,faceTemp.temp,tem,bla);
                                        //Log.e("JYYQ_FACE",faceData);
                                        Message msg = new Message();
                                        msg.what = 2;
                                        msg.obj = tt;
                                        handler.sendMessage(msg);
                                    }
                                    if (orderType == 4){
                                        lightState.changeType(data);
                                    }
                                    if (orderType == 5){
                                        int cameraType = StringUtil.hexStr2OneInt(data.substring(14,16)); //摄像头状态
                                        Message msg = new Message();
                                        msg.what = 5;
                                        msg.obj = cameraType;
                                        handler.sendMessage(msg);
                                    }
                                    //人脸全部数据
                                    if (orderType == 100){
                                        int top = StringUtil.hexStr2Int(data.substring(14,18));
                                        int right = StringUtil.hexStr2Int(data.substring(18,22));
                                        int bom = StringUtil.hexStr2Int(data.substring(22,26));
                                        int left = StringUtil.hexStr2Int(data.substring(26,30));
                                        hind_t = top;
                                        hind_b = bom;
                                        hind_l = left;
                                        hind_r = right;
                                        float pc  = StringUtil.hexStr2Float(data.substring(30,34))/100-100;
                                        Log.d("JYYQ_REQ",data.substring(30,34));
                                        final String tem = String.format("%.1f",pc);
                                        hind_face = tem;
                                        //int pc = StringUtil.hexStr2Int(data.substring(30,34));
                                        StringBuffer buffer = new StringBuffer("");
                                        buffer.append(("顶部: "+String.valueOf(top)+"\n"));
                                        buffer.append(("底部: "+String.valueOf(bom)+"\n"));
                                        buffer.append(("左部: "+String.valueOf(left)+"\n"));
                                        buffer.append(("右部: "+String.valueOf(right)+"\n"+" 偏差:"+tem));
                                        Message msg = new Message();
                                        msg.what = 4;
                                        msg.obj = buffer.toString();
                                        handler.sendMessage(msg);
                                    }
                                    //全图显示
                                    if (orderType == 101){
                                        String faceString = data.substring(22,dataLength-4);
                                        float tRoom  = StringUtil.hexStr2Float(data.substring(18,22))/100-100;
                                        float black = StringUtil.hexStr2Float(data.substring(14,18))/100-100;
                                        final String tem = String.format("%.1f",tRoom);
                                        final String bla = String.format("%.1f",black);

                                        FaceTemp faceTemp = StringUtil.getDraw(faceString);
                                        Mat mat = new Mat(60,80,CvType.CV_8UC3);
                                        mat.put(0,0,faceTemp.group);
                                        Mat right = rotateRight(mat);
                                        //创建Bitmap
                                        Bitmap bitmap = Bitmap.createBitmap(60,80,Bitmap.Config.ARGB_8888);
                                        Utils.matToBitmap(right,bitmap);
                                        //使用Message发送数据
                                        right.release();
                                        mat.release();
                                        MyTestDrawData tt = new MyTestDrawData(bitmap,20,tem,bla);
                                        Message msg = new Message();
                                        msg.what = 3;
                                        msg.obj = tt;
                                        handler.sendMessage(msg);
                                    }
                                } else {
                                    Log.e("JYYQ","XOR ERROR");
                                    Message msg = new Message();
                                    msg.what = -1;
                                    handler.sendMessage(msg);
                                }
                                if (dataLength == length){
                                    resultData = new StringBuffer("");
                                }else {
                                    String last = data.substring(length);
                                    resultData = new StringBuffer(last);
                                }
                            }
                        }
                    }
                }
                //Log.i(TAG, "------- 读到数据 -------  " + arr.length + "  ------"+numBytesRead);
            }
        }
        return 0;
    }

    //检测环境亮度面积
    /**
     * 处理流程如下：
     *模糊处理->hsv提取->合并原图->灰度化->阀值处理->计算面积
     *
     * **/
    public int Light_Result(Mat src){
        Mat dst = new Mat();
        Imgproc.blur(src,dst,new Size(15,15));
        Mat hsv = new Mat();
        Imgproc.cvtColor(dst,hsv,Imgproc.COLOR_BGR2HSV);
        dst.release();
        Core.inRange(hsv,new Scalar(0,0,175),new Scalar(255,255,255),hsv);
        Mat result = new Mat();
        Core.bitwise_and(src,src,result,hsv);
        hsv.release();
        Mat gray = new Mat();
        Imgproc.cvtColor(result,gray,Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(gray,gray,127,255,Imgproc.THRESH_BINARY);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(gray,contours,hierarchy,Imgproc.RETR_CCOMP,Imgproc.CHAIN_APPROX_SIMPLE);
        int arr = 0;
        for (int j = 0;j<contours.size();j++){
            arr += Imgproc.contourArea(contours.get(j));
        }
        gray.release();
        return arr;
    }

    //根据面积计算温度值
    /**
     * temp 公式计算后的数值
     * size 人脸面积
     * face_scale 人脸补偿值
     * */
    private float getShowTemp(float temp,int size){

        float sc = ((float) (getSizeFace(parameterArray.getFaceMaxSize())-size))/(getSizeFace(parameterArray.getFaceMaxSize())-getSizeFace(parameterArray.getFaceMinSize()))*parameterArray.getFaceScaleData();
        return temp+sc;
    }

    private float getSizeFace(float f){
        return f*f;
    }









    private int usbWrite(String hexString) {
        return usbWrite(StringUtil.hexStr2Bytes(hexString));
    }

    //return 0:写入错误；0>:写入成功
    private int usbWrite(byte[] command) {
        if (command != null && command.length > 0) {
            if (!isConnected()) {
                return 0;
            }
            int offset = 0;//记录已发送的长度
            int HasWritten;
            int odd_len = command.length;

            while (offset < command.length) {
                synchronized (WriteQueueLock) {
                    int mLen = Math.min(odd_len, mBulkPacketSize);
                    byte[] arrayOfByte = new byte[mLen];
                    if (offset == 0) {
                        System.arraycopy(command, 0, arrayOfByte, 0, mLen);
                    } else {
                        System.arraycopy(command, offset, arrayOfByte, 0, mLen);
                    }
                    HasWritten = targetConnection.bulkTransfer(mBulkOutPoint, arrayOfByte, mLen, WriteTimeOutMillis);
                    if (HasWritten < 0) {
                        return 0;
                    } else {
                        offset += HasWritten;
                        odd_len -= HasWritten;
                    }
                }
            }
            return offset;
        } else {
            return 0;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    //testTvShow.setText("校验出错\n");
                    break;
                case 1:
                    MyDrawData myDrawData = (MyDrawData) msg.obj;
                    if (parameterArray.getModeShow()==0){
                        myLineView.setData(myDrawData.list,myDrawData.temp,parameterArray.getTempShowMode());
                    }else {
                        myLineView.setData(myDrawData.list,myDrawData.temp,parameterArray.getTempShowMode(),myDrawData.indexArrayList);
                    }
                    String tem = String.format("%.1f",myDrawData.temp_data);
                    String bla = String.format("%.1f",myDrawData.black_data);
                    tempTv.setText(tem+"°C");
                    blackTv.setText(bla+"°C");
                    ArrayList<TempDataIndex> indexArrayList = myDrawData.indexArrayList;
                    if (parameterArray.getModeShow() == 0) {
                        double max = myDrawData.getMax();
                        if (max > parameterArray.getDanger()) {
                            if (!isPlayDanger) {
                                isPlayDanger = true;
                                musicPlay.playMusic(1);

                            }
                            background.setImageResource(R.drawable.danger_bg);
                            if (lightState.getOpenRed()){
                                usbWrite(StringUtil.getRedLightOrder());
                            }
                        } else if (max >= parameterArray.getLowTemp()) {
                            if (!isPlayNormal) {
                                isPlayNormal = true;
                                musicPlay.playMusic(0);
                            }
                            background.setImageResource(R.drawable.runing_bg);
                            if (lightState.getOpenGreen()){
                                usbWrite(StringUtil.getGreenLightOrder());
                            }
                        }
                    }else {
                        boolean canShow = false;
                        boolean notPeople = false;
                        double max = 0;
                        for (int p=0;p<myDrawData.list.size();p++){
                            TempDataIndex dataIndex = indexArrayList.get(p);
                            if (dataIndex.getType()){
                                canShow = true;
                                if (max < dataIndex.getTemp()){
                                    max = dataIndex.getTemp();
                                }
                            }else {
                                notPeople = true;
                            }
                        }
                        if (max > parameterArray.getDanger()){ //判断是否发烧
                            if (!isPlayDanger) {
                                isPlayDanger = true;
                                musicPlay.playMusic(1); //播放高温音乐
                            }
                            background.setImageResource(R.drawable.danger_bg);
                            if (lightState.getOpenRed()){
                                usbWrite(StringUtil.getRedLightOrder());
                            }
                        }else {
                            if (canShow){
                                if (max >= parameterArray.getLowTemp()) { //温度大于最低温度
                                    if (!isPlayNormal) {
                                        isPlayNormal = true;
                                        musicPlay.playMusic(0); //播放通过音乐
                                    }
                                }
                            }
                            if (canShow && !notPeople){ //全检测完成时显示绿色
                                background.setImageResource(R.drawable.runing_bg);
                                if (lightState.getOpenGreen()){
                                    usbWrite(StringUtil.getGreenLightOrder());
                                }
                            }
                            if (notPeople){
                                if (lightState.getOpenBlue()){
                                    usbWrite(StringUtil.getBlueLightOrder());
                                }
                                background.setImageResource(R.drawable.waiting_bg);
                            }
                        }
                    }
                    break;
                case 5:
                    int state = (int) msg.obj;
                    if (state == 1){
                        //开启摄像头
                        if (javaCameraView!=null) {
                            checkCameraPermission();
                        }
                    }
                    break;
            }
        }
    };

    //根据选择显示设置框
    private Handler resultHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://温度参数
                    openTempSettingDialog();
                    break;
                case 1://人脸设置
                    openFaceSettingDialog();
                    break;
                case 2://灯光设置
                    openLightSettingDialog();
                    break;
                case 3://恢复出厂设置
                    openAskReturnDialog();
                    break;
                case 4://语言设置
                    openLanguageDialog();
                    break;
                case 5: //音量设置
                    openMusicValueDialog();
                    break;
                case 6://显示设置
                    openShowViewDialog();
                    break;
            }
        }
    };

    //获取摄像头权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==3){
            JavaCameraView javaView = findViewById(R.id.test_camera);
            javaView.setCameraIndex(99);
            javaView.setVisibility(View.VISIBLE);
            javaView.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener() {
                @Override
                public void onCameraViewStarted(int width, int height) {

                }

                @Override
                public void onCameraViewStopped() {

                }

                @Override
                public Mat onCameraFrame(Mat inputFrame) {
                    return null;
                }
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            javaView.enableView();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            javaView.disableView();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timingThread.start(); //启动定时线程
            javaCameraView.enableView();
            hideNavigationBar();
        }
    }
/**
 *
 * **/
    //检测权限
    private void checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},3);
        }else {
            //已经获取权限

            new Handler(this.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JavaCameraView javaView = findViewById(R.id.test_camera);
                    javaView.setCameraIndex(99);
                    javaView.setVisibility(View.VISIBLE);
                    javaView.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener() {
                        @Override
                        public void onCameraViewStarted(int width, int height) {

                        }

                        @Override
                        public void onCameraViewStopped() {

                        }

                        @Override
                        public Mat onCameraFrame(Mat inputFrame) {
                            return null;
                        }
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    javaView.enableView();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    javaView.disableView();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timingThread.start(); //启动定时线程
                    javaCameraView.enableView();
                }
            });
        }
    }


    //隐藏系统状态栏
    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE;//0x00001000; // SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }

        try {
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    //MARK:修改当前的模式
    public void changeShowMode(View view){
        Button b = (Button) view;
        if (CAMERA_CHECK_STATE == 0){
            CAMERA_CHECK_STATE = 1;
            b.setText("正常人脸检测");
            imageBackground.setVisibility(View.VISIBLE);
        }else {
            CAMERA_CHECK_STATE = 0;
            imageBackground.setVisibility(View.GONE);
            b.setText("人脸全图显示");
        }
    }

    //MARK:发送修改全屏界面命令
    public void sendBlackViewOrder(View view){
        usbWrite(StringUtil.getSetCameraOrder());
    }


    //显示距离修改界面
    public void showCameraSetDialog(View view){
        String test = StringUtil.getHidingOrder();
        usbWrite(test);
        CameraSetDialog cameraSetDialog = new CameraSetDialog(this,hind_t,hind_b,hind_l,hind_r,hind_face);
        cameraSetDialog.setCallBackListener(new CameraSetDialog.CameraSetResult() {
            @Override
            public void result(int t, int b, int l, int r,int hind) {
                String order = StringUtil.getSetHidingOrder(t,b,l,r,hind);
                usbWrite(order);//发送命令

            }

            @Override
            public void diss() {
                hideNavigationBar();
            }
        });
        cameraSetDialog.show();
    }



    private void dissTiming(){
        timingThread.dissRun();
        for (int j = 0;j<5;j++){
            timingThread.stopWait(j);
        }
    }

    private void openTempSettingDialog(){
        SettingDialog settingDialog = new SettingDialog(context,parameterArray.getJsonData(),parameterArray.getLanguageMode());
        settingDialog.setCallBackListener(new SettingDialog.Request() {
            @Override
            public void click(float p, float a, float d,float l,int modeIndex) {
                parameterArray.tempSetting(p,a,d,l,modeIndex);
                myLineView.setDangerAndMin(d, l);
                hideNavigationBar();
            }

            @Override
            public void out() {
                hideNavigationBar();
            }
        });
        settingDialog.setCanceledOnTouchOutside(false);
        settingDialog.show();
    }

    private void openFaceSettingDialog(){
        FaceSetDialog faceSetDialog = new FaceSetDialog(context,parameterArray.getJsonFace(),parameterArray.getLanguageMode());
        faceSetDialog.setCallBackListener(new FaceSetDialog.FaceRequest() {
            @Override
            public void click(int min, int max, float scale, int minScale) {
                parameterArray.faceSetting(min,max,scale,minScale);
                hideNavigationBar();
            }

            @Override
            public void out() {
                hideNavigationBar();
            }
        });
        faceSetDialog.setCanceledOnTouchOutside(false);
        faceSetDialog.show();
    }


    private void openLightSettingDialog(){
        LightSetDialog dialog = new LightSetDialog(context,parameterArray.getJsonLight(),parameterArray.getLanguageMode());
        dialog.setCallBackListener(new LightSetDialog.Request() {
            @Override
            public void click(int item, int data) {
                Log.e("MyLight",data+"data");
                parameterArray.lightSetting(item,data);
                if (parameterArray.getLightModeState() == 0){
                    usbWrite(StringUtil.getLightOrder(false));
                }else if (parameterArray.getLightModeState() == 1){
                    usbWrite(StringUtil.getLightOrder(true));
                }
                if (parameterArray.getLightModeState() == 2){
                    //openLight = false;
                    usbWrite(StringUtil.getLightOrder(false));
                }
                hideNavigationBar();
            }

            @Override
            public void out() {
                hideNavigationBar();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void openAskReturnDialog(){
        AskResetDialog ask = new AskResetDialog(context,parameterArray.getLanguageMode());
        ask.setCallBackListener(new AskResetDialog.FaceRequest() {
            @Override
            public void click(int select) {
                hideNavigationBar();
                if (select == 1){
                    parameterArray.resetSetting();

                    myLineView.setDangerAndMin(parameterArray.getDanger(), parameterArray.getLowTemp());

                    //用于重置显示设置
                    dissTiming();

                    myLineView.setShowMojo(parameterArray.getExpression());

                    usbWrite(StringUtil.getLightOrder(false));
                }
            }
        });
        ask.setCanceledOnTouchOutside(false);
        ask.show();
    }

    private void openLanguageDialog(){
        LanguageDialog languageDialog = new LanguageDialog(context,parameterArray.getLanguageMode(),parameterArray.getLanguageMode());
        languageDialog.setCallBackListener(new LanguageDialog.Request() {
            @Override
            public void click(int item) {
                if (item!=-1){
                    parameterArray.setLanguageMode(item);
                    parameterArray.setLanguage(item);
                    musicPlay.setMode(item);
                }
                hideNavigationBar();
            }
        });
        languageDialog.setCanceledOnTouchOutside(false);
        languageDialog.show();
    }

    private void openMusicValueDialog(){
        MusicDialog musicDialog = new MusicDialog(context,parameterArray.getLanguageMode(),parameterArray.getMusicValue());
        musicDialog.setCallBackListener(new MusicDialog.Request() {
            @Override
            public void result(int item, boolean d) {
                if (d){
                    parameterArray.setMusicValue(item);
                    musicPlay.changeValue(item);
                    parameterArray.setValue(item);
                }
                hideNavigationBar();
            }
        });
        musicDialog.setCanceledOnTouchOutside(false);
        musicDialog.show();
    }

    private void openShowViewDialog(){
        ModeSelectorDialog modeSelectorDialog = new ModeSelectorDialog(context,parameterArray.getLanguageMode(),parameterArray.getModeShow(),parameterArray.getExpression());
        modeSelectorDialog.setModeResult(new ModeSelectorDialog.ModeResult() {
            @Override
            public void didClick(int mode, int face, boolean type) {
                if (type){
                    parameterArray.setModeShow(mode);
                    if (mode == 1){
                        timingThread.beginRun();
                    }else {
                        dissTiming();
                    }
                    parameterArray.setExpression(face);

                    myLineView.setShowMojo(face);
                    parameterArray.setShowView(mode,face);

                }
                hideNavigationBar();
            }
        });
        modeSelectorDialog.setCanceledOnTouchOutside(false);
        modeSelectorDialog.show();
    }

}
