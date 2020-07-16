//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.
package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.example.face.R;
import com.example.face.Tool.LanguageUtil;

public class FaceSetDialog extends Dialog {

    Context context;
    private JSONObject jsonObject;
    //创建回调接口
    public static interface FaceRequest{
        public void click(int min,int max,float scale,int minScale);
        public void out();

    }

    private FaceRequest request;
    public void setCallBackListener(FaceRequest req){
        this.request = req;
    }

    private int language;
    public FaceSetDialog(@NonNull Context context,JSONObject jsonObject,int language) {
        super(context);
        this.context = context;
        this.jsonObject = jsonObject;
        this.language = language;
    }

    public FaceSetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    protected FaceSetDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_set_dialog);
        initLanguage();
        initUI();
    }

    private void initLanguage(){
        AppCompatTextView min = findViewById(R.id.face_title_min);
        AppCompatTextView max = findViewById(R.id.face_title_max);
        AppCompatTextView dis = findViewById(R.id.face_title_wc);
        AppCompatTextView face = findViewById(R.id.face_title_rate);
        min.setText(LanguageUtil.getFaceMin(language));
        max.setText(LanguageUtil.getFaceMax(language));
        dis.setText(LanguageUtil.getFaceDistance(language));
        face.setText(LanguageUtil.getFaceProbability(language));
    }


    private EditText minFace;
    private EditText maxFace;
    private EditText scaleFace;
    private EditText configFace;
    private Button  button;



    public void initUI(){
        minFace = findViewById(R.id.face_min);
        maxFace = findViewById(R.id.face_max);
        scaleFace = findViewById(R.id.face_scale);
        configFace = findViewById(R.id.face_config);

        minFace.setText(String.valueOf(jsonObject.getInteger("min")));
        maxFace.setText(String.valueOf(jsonObject.getInteger("max")));
        scaleFace.setText(String.valueOf(jsonObject.getFloatValue("scale")));
        configFace.setText(String.valueOf(jsonObject.getInteger("faceMin")));


        button = findViewById(R.id.face_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min,max,config;
                float scale;
                try{
                    min = Integer.valueOf(minFace.getText().toString());
                }catch (NumberFormatException e){
                    min = jsonObject.getInteger("min");
                }

                try{
                    max = Integer.valueOf(maxFace.getText().toString());
                }catch (NumberFormatException e){
                    max = jsonObject.getInteger("max");
                }

                try{
                    scale = Float.valueOf(scaleFace.getText().toString());
                }catch (NumberFormatException e){
                    scale = jsonObject.getFloatValue("scale");
                }

                try{
                    config = Integer.valueOf(configFace.getText().toString());
                    if (config > 100){
                        config = 100;
                    }
                }catch (NumberFormatException e){
                    config = jsonObject.getInteger("faceMin");
                }


                if (request != null){
                    request.click(min,max,scale,config);
                }
                dismiss();
            }
        });


        Button cancel = findViewById(R.id.out_face);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.out();
                dismiss();
            }
        });
        button.setText(LanguageUtil.getCheck(language));
        cancel.setText(LanguageUtil.getCancel(language));
    }


}
