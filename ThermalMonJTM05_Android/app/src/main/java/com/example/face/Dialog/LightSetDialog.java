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
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.face.R;
import com.alibaba.fastjson.JSONObject;
import com.example.face.Tool.LanguageUtil;

import org.json.JSONException;


public class LightSetDialog extends Dialog {

    //创建回调接口
    public static interface Request{
        public void click(int item,int data);
        public void out();
    }

    private Request request;
    public void setCallBackListener(Request req){
        this.request = req;
    }
    private int language;
    Context context;
    RadioGroup radioGroup;
    //EditText  editText;
    SeekBar seekBar;
    JSONObject jsonObject;
    int mode = 0;

    public LightSetDialog(@NonNull Context context, JSONObject json,int language) {
        super(context);
        this.context = context;
        this.jsonObject = json;
        this.language = language;
    }

    public LightSetDialog(@NonNull Context context, int themeResId, JSONObject json) {
        super(context, themeResId);
        this.context = context;
    }
//
    protected LightSetDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, JSONObject json) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_set);
        initUI();
    }



    private void initUI(){
        radioGroup = findViewById(R.id.light_mode_group);
        int d = jsonObject.getIntValue("mode");
        int data = jsonObject.getIntValue("threshold");
        mode = d;

        RadioButton close = findViewById(R.id.close_light);
        RadioButton open = findViewById(R.id.open_light);
        RadioButton checkLight = findViewById(R.id.check_light);
        AppCompatTextView title = findViewById(R.id.light_title_data);
        AppCompatTextView modeTitle = findViewById(R.id.light_title_mode);

        title.setText(LanguageUtil.getLightFz(language));
        modeTitle.setText(LanguageUtil.getLightMode(language));
        close.setText(LanguageUtil.getLightClose(language));
        open.setText(LanguageUtil.getLightOpen(language));
        checkLight.setText(LanguageUtil.getLightCheck(language));


        switch (mode){
            case 0:
                close.setChecked(true);
                break;
            case 1:
                open.setChecked(true);
                break;
            case 2:
                checkLight.setChecked(true);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.close_light:
                        mode = 0;
                        break;
                    case R.id.open_light:
                        mode = 1;
                        break;
                    case R.id.check_light:
                        mode = 2;
                        break;
                }
            }
        });

        final AppCompatTextView tv = findViewById(R.id.light_data);
        tv.setText(String.valueOf(data));
        seekBar = findViewById(R.id.light_seek);
        seekBar.setProgress(data);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button btn = findViewById(R.id.light_check);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int data = seekBar.getProgress();
                if (request != null){
                    request.click(mode,data);
                }
                dismiss();
                Log.e("JYYQ_DIALOG",data+","+mode);
            }
        });
        Button cancel = findViewById(R.id.light_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.out();
                dismiss();
            }
        });

        btn.setText(LanguageUtil.getCheck(language));
        cancel.setText(LanguageUtil.getCancel(language));
    }

}
