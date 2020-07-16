//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.example.face.MainActivity;
import com.example.face.R;
import com.example.face.Tool.LanguageUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class SettingDialog extends Dialog {

    private EditText pc_ed;
    private EditText ambient_ed;
    private EditText danger_ed;
    private EditText minTemp_ed;
    private JSONObject jsonObject;
    Context context;

    //创建回调接口
    public static interface Request{
        public void click(float a,float b,float c,float d,int mode);
        public void out();

    }

    private Request request;
    public void setCallBackListener(Request req){
        this.request = req;
    }


    private int language;
    private int showMode;

    public SettingDialog(@NonNull Context context,JSONObject jsonObject,int language) {
        super(context);
        this.context = context;
        this.jsonObject = jsonObject;
        this.language = language;
    }

    public SettingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected SettingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_dialog);
        initTitle();
        initUI();
    }

    private void initTitle(){
        AppCompatTextView pc = findViewById(R.id.setting_title_pc);
        AppCompatTextView hc = findViewById(R.id.setting_title_hc);
        AppCompatTextView low = findViewById(R.id.setting_title_low);
        AppCompatTextView hig = findViewById(R.id.setting_title_hig);
        AppCompatTextView show = findViewById(R.id.setting_title_show_check);
        pc.setText(LanguageUtil.getTempPc(language));
        hc.setText(LanguageUtil.getTempFactor(language));
        low.setText(LanguageUtil.getTempLow(language));
        hig.setText(LanguageUtil.getTempHig(language));
        show.setText(LanguageUtil.getTempShowMode(language));
    }

    private void initUI(){
        pc_ed = findViewById(R.id.pc_ed);
        ambient_ed = findViewById(R.id.ambient_ed);
        danger_ed = findViewById(R.id.danger_ed);
        minTemp_ed = findViewById(R.id.low_temp_ed);






        float pc = jsonObject.getFloatValue("pc");
        float ambient = jsonObject.getFloatValue("ambient");
        float danger = jsonObject.getFloatValue("danger");
        float low = jsonObject.getFloatValue("low");

        showMode = jsonObject.getIntValue("showMode");
        RadioGroup radioGroup = findViewById(R.id.temp_mode_group);
        RadioButton one = findViewById(R.id.temp_mode_one);
        RadioButton two = findViewById(R.id.temp_mode_two);
        if (showMode == 0){
            one.setChecked(true);
        }else {
            two.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.temp_mode_one){
                    showMode = 0;
                }else if (checkedId == R.id.temp_mode_two){
                    showMode = 1;
                }
            }
        });




        pc_ed.setText(String.valueOf(pc));
        ambient_ed.setText(String.valueOf(ambient));
        danger_ed.setText(String.valueOf(danger));
        minTemp_ed.setText(String.valueOf(low));
        //write();
        Button btn = findViewById(R.id.save_setting);
        btn.setText(LanguageUtil.getCheck(language));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request != null){
                    float pc,ambient,danger,low;
                    try {
                        pc = Float.parseFloat(pc_ed.getText().toString());
                    } catch (NumberFormatException e) {
                        pc = jsonObject.getFloatValue("pc");
                        e.printStackTrace();
                    }
                    try {
                        ambient = Float.parseFloat(ambient_ed.getText().toString());
                    } catch (NumberFormatException e) {
                        ambient = jsonObject.getFloatValue("ambient");
                        e.printStackTrace();
                    }
                    try {
                        danger = Float.parseFloat(danger_ed.getText().toString());
                    } catch (NumberFormatException e) {
                        danger = jsonObject.getFloatValue("danger");
                        e.printStackTrace();
                    }
                    try {
                        low = Float.parseFloat(minTemp_ed.getText().toString());
                    } catch (NumberFormatException e) {
                        low = jsonObject.getFloatValue("low");
                        e.printStackTrace();
                    }
                    request.click(pc,ambient,danger,low,showMode);
                }
                dismiss();
            }
        });

        Button cancel = findViewById(R.id.out_setting);
        cancel.setText(LanguageUtil.getCancel(language));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.out();
                dismiss();
            }
        });

    }



}
