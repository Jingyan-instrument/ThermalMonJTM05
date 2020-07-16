//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParameterArray {
    private Context context;
    //参数设定
    private JSONObject jsonData; //温度设置json
    private JSONObject jsonFace; //人脸设置json
    private JSONObject jsonLight; //闪光灯设置json
    private JSONObject jsonLanguage; //语言设置json
    private JSONObject jsonMusic; //音量设置json
    private JSONObject jsonMode; //模式设置 json

    //环境补偿因数
    private float ambient;

    //偏差值
    private float deviation ;

    //报警值
    private float danger;

    //最小体温值
    private float lowTemp;

    //温度显示模式 0 为摄氏度 1为华摄氏度
    private int tempShowMode = 0;

    //最小人脸宽度
    private int faceMinSize;

    //最大人脸宽度
    private int faceMaxSize;

    //人脸比例值
    private float faceScaleData;

    //人脸的最小识别率
    private int faceMinPercentage;

    //闪光灯设置
    private int lightModeState = 0;

    //自动灯光识别阀值
    private int lightModeData = 0;

    //温度显示模式
    private int modeShow = 0;

    //表情显示模式
    private int expression = 0;

    private int checkLight = 0;

    //当前语言
    private int languageMode = 5;

    //音量数值
    private int musicValue;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    public JSONObject getJsonFace() {
        return jsonFace;
    }

    public void setJsonFace(JSONObject jsonFace) {
        this.jsonFace = jsonFace;
    }

    public JSONObject getJsonLight() {
        return jsonLight;
    }

    public void setJsonLight(JSONObject jsonLight) {
        this.jsonLight = jsonLight;
    }

    public JSONObject getJsonLanguage() {
        return jsonLanguage;
    }

    public void setJsonLanguage(JSONObject jsonLanguage) {
        this.jsonLanguage = jsonLanguage;
    }

    public JSONObject getJsonMusic() {
        return jsonMusic;
    }

    public void setJsonMusic(JSONObject jsonMusic) {
        this.jsonMusic = jsonMusic;
    }

    public JSONObject getJsonMode() {
        return jsonMode;
    }

    public void setJsonMode(JSONObject jsonMode) {
        this.jsonMode = jsonMode;
    }

    public float getAmbient() {
        return ambient;
    }

    public void setAmbient(float ambient) {
        this.ambient = ambient;
    }

    public float getDeviation() {
        return deviation;
    }

    public void setDeviation(float deviation) {
        this.deviation = deviation;
    }

    public float getDanger() {
        return danger;
    }

    public void setDanger(float danger) {
        this.danger = danger;
    }

    public float getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(float lowTemp) {
        this.lowTemp = lowTemp;
    }

    public int getTempShowMode() {
        return tempShowMode;
    }

    public void setTempShowMode(int tempShowMode) {
        this.tempShowMode = tempShowMode;
    }

    public int getFaceMinSize() {
        return faceMinSize;
    }

    public void setFaceMinSize(int faceMinSize) {
        this.faceMinSize = faceMinSize;
    }

    public int getFaceMaxSize() {
        return faceMaxSize;
    }

    public void setFaceMaxSize(int faceMaxSize) {
        this.faceMaxSize = faceMaxSize;
    }

    public float getFaceScaleData() {
        return faceScaleData;
    }

    public void setFaceScaleData(float faceScaleData) {
        this.faceScaleData = faceScaleData;
    }

    public int getFaceMinPercentage() {
        return faceMinPercentage;
    }

    public void setFaceMinPercentage(int faceMinPercentage) {
        this.faceMinPercentage = faceMinPercentage;
    }

    public int getLightModeState() {
        return lightModeState;
    }

    public void setLightModeState(int lightModeState) {
        this.lightModeState = lightModeState;
    }

    public int getLightModeData() {
        return lightModeData;
    }

    public void setLightModeData(int lightModeData) {
        this.lightModeData = lightModeData;
    }

    public int getModeShow() {
        return modeShow;
    }

    public void setModeShow(int modeShow) {
        this.modeShow = modeShow;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }

    public int getCheckLight() {
        return checkLight;
    }

    public void setCheckLight(int checkLight) {
        this.checkLight = checkLight;
    }

    public int getLanguageMode() {
        return languageMode;
    }

    public void setLanguageMode(int languageMode) {
        this.languageMode = languageMode;
    }

    public int getMusicValue() {
        return musicValue;
    }

    public void setMusicValue(int musicValue) {
        this.musicValue = musicValue;
    }

    public ParameterArray(Context context){
        this.context = context;
        initJsonData();
    }
    //初始化设定数据
    public void initJsonData(){
//获取内部数据组合
        String setting = getTxt("setting.txt");
        String faceJson = "";
        String lightJson = "";
        String languageJson = "";
        String musicJson = "";
        String modeJson = "";
        if (setting.equals("")){ //判断是否为空，空则为第一次启动软件
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pc",0.0);
            jsonObject.put("ambient",0.25);
            jsonObject.put("danger",37.3);
            jsonObject.put("low",34.0);
            jsonObject.put("showMode",0);

            String json = jsonObject.toString();
            setting = json;
            write(json,"setting.txt");

            JSONObject faceObject = new JSONObject();
            faceObject.put("max",100);
            faceObject.put("min",30);
            faceObject.put("scale",1.0);
            faceObject.put("faceMin",25);
            faceJson = faceObject.toString();
            write(faceJson,"face.txt");

            JSONObject lightObject = new JSONObject();
            lightObject.put("mode",0);
            lightObject.put("threshold",40000);
            lightJson = lightObject.toString();
            write(lightJson,"light.txt");

            JSONObject language = new JSONObject();
            language.put("language",1);
            write(language.toString(),"language.txt");
            languageJson = language.toString();

            JSONObject music = new JSONObject();
            music.put("value",100);
            write(music.toString(),"music.txt");
            musicJson = music.toString();

            JSONObject mode = new JSONObject();
            mode.put("mode",0);
            mode.put("expression",0);
            write(mode.toString(),"mode.txt");
            modeJson = mode.toString();


        }else {
            faceJson = getTxt("face.txt");
            lightJson = getTxt("light.txt");
            languageJson = getTxt("language.txt");
            musicJson = getTxt("music.txt");

            modeJson = getTxt("mode.txt");
            if (modeJson.equals("")){
                JSONObject mode = new JSONObject();
                mode.put("mode",0);
                mode.put("expression",0);
                write(mode.toString(),"mode.txt");
                modeJson = mode.toString();
            }

        }



        jsonData = JSONObject.parseObject(setting);
        jsonFace = JSONObject.parseObject(faceJson);
        jsonLight = JSONObject.parseObject(lightJson);
        jsonLanguage = JSONObject.parseObject(languageJson);
        jsonMusic = JSONObject.parseObject(musicJson);
        jsonMode = JSONObject.parseObject(modeJson);

        deviation = jsonData.getFloatValue("pc");
        ambient = jsonData.getFloatValue("ambient");
        danger = jsonData.getFloatValue("danger");
        lowTemp = jsonData.getFloatValue("low");
        tempShowMode = jsonData.getIntValue("showMode");

        faceMinSize = jsonFace.getIntValue("min");
        faceMaxSize = jsonFace.getIntValue("max");
        faceScaleData = jsonFace.getFloatValue("scale");
        faceMinPercentage = jsonFace.getIntValue("faceMin");

        lightModeState = jsonLight.getIntValue("mode");
        lightModeData = jsonLight.getIntValue("threshold");

        musicValue = jsonMusic.getIntValue("value");

        languageMode =  jsonLanguage.getIntValue("language");

        modeShow = jsonMode.getIntValue("mode");

        expression = jsonMode.getIntValue("expression");
    }





    //编写设置文件 用于保存默认设置
    private void write(String data,String name){
        try {
            FileOutputStream fos = context.openFileOutput(name,
                    Context.MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //获取设置文件
    public String getTxt(String name){
        String str = "";
        // 读写入文件的内容
        try {
            FileInputStream fis = context.openFileInput(name);
            // 缓存
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            str = new String(buffer);
            //Log.e("JYYQ",str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    //删除设置文件
    private void resultDir(String name,String data){
        File f=new File(context.getFilesDir(), name);
        if (f.exists()){
            f.delete();
        }
        write(data,name);
    }

    public void tempSetting(float p, float a, float d,float l,int modeIndex){
        deviation = p;
        ambient = a;
        danger = d;
        lowTemp = l;
        tempShowMode = modeIndex;

        //JSONObject jsonObject = new JSONObject();
        jsonData.put("pc",deviation);
        jsonData.put("ambient",ambient);
        jsonData.put("danger",danger);
        jsonData.put("low", lowTemp);
        jsonData.put("showMode",modeIndex);
        String json = jsonData.toString();
        resultDir("setting.txt",json);
    }


    public void faceSetting(int min, int max, float scale, int minScale){
        faceMinSize = min;
        faceMaxSize = max;
        faceScaleData = scale;
        faceMinPercentage = minScale;
        //JSONObject faceObject = new JSONObject();
        jsonFace.put("max",max);
        jsonFace.put("min",min);
        jsonFace.put("scale",scale);
        jsonFace.put("faceMin",minScale);
        String faceJson = jsonFace.toString();
        resultDir("face.txt",faceJson);
    }

    public void lightSetting(int item, int data){
        lightModeState = item;
        lightModeData = data;
        jsonLight.put("mode", lightModeState);
        jsonLight.put("threshold", lightModeData);
        String lightJson = jsonLight.toString();
        resultDir("light.txt",lightJson);
    }

    public void resetSetting(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pc",0.0);
        jsonObject.put("ambient",0.25);
        jsonObject.put("danger",37.3);
        jsonObject.put("low",34.0);
        String setting = jsonObject.toString();
        resultDir("setting.txt",setting);

        JSONObject faceObject = new JSONObject();
        faceObject.put("max",100);
        faceObject.put("min",30);
        faceObject.put("scale",1.0);
        faceObject.put("faceMin",25);
        String faceJson = faceObject.toString();
        resultDir("face.txt",faceJson);


        JSONObject lightObject = new JSONObject();
        lightObject.put("mode",0);
        lightObject.put("threshold",170);
        String lightJson = lightObject.toString();
        resultDir("light.txt",lightJson);

        JSONObject language = new JSONObject();
        language.put("language",1);
        //write(language.toString(),"language.txt");
        String languageJson = language.toString();
        resultDir("language.txt",languageJson);

        JSONObject music = new JSONObject();
        music.put("value",100);
        String musicJson = music.toString();
        resultDir("music.txt",musicJson);

        JSONObject mode = new JSONObject();
        jsonObject.put("mode",0);
        jsonObject.put("expression",0);
        String modeJson = mode.toString();
        resultDir("mode.txt",modeJson);



        jsonData = JSONObject.parseObject(setting);
        jsonFace = JSONObject.parseObject(faceJson);
        jsonLight = JSONObject.parseObject(lightJson);
        jsonLanguage = JSONObject.parseObject(languageJson);
        jsonMode = JSONObject.parseObject(modeJson);

        deviation = jsonData.getFloatValue("pc");
        ambient = jsonData.getFloatValue("ambient");
        danger = jsonData.getFloatValue("danger");
        lowTemp = jsonData.getFloatValue("low");

        faceMinSize = jsonFace.getIntValue("min");
        faceMaxSize = jsonFace.getIntValue("max");
        faceScaleData = jsonFace.getFloatValue("scale");
        faceMinPercentage = jsonFace.getIntValue("faceMin");

        lightModeState = jsonLight.getIntValue("mode");
        lightModeData = jsonLight.getIntValue("threshold");

        languageMode = jsonLanguage.getIntValue("language");

        modeShow = 0;
        expression = 0;
    }

    public void setValue(int value){
        JSONObject music = new JSONObject();
        music.put("value",value);
        String musicJson = music.toString();
        resultDir("music.txt",musicJson);
    }

    public void setLanguage(int item){
        JSONObject language = new JSONObject();
        language.put("language",item);
        String languageJson = language.toString();
        resultDir("language.txt",languageJson);
    }

    public void setShowView(int mode, int face){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mode",mode);
        jsonObject.put("expression",face);
        String json = jsonObject.toString();
        resultDir("mode.txt",json);
    }


}
