//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.
package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.face.R;
import com.example.face.Tool.LanguageUtil;

public class ModeSelectorDialog extends Dialog {


    //自定义接口
    public static interface ModeResult{
        public void didClick(int mode,int face,boolean type);
    }
    private ModeResult modeResult;
    public void setModeResult(ModeResult modeResult){
        this.modeResult = modeResult;
    }

    private int language = 0;
    private Context context;
    private RadioGroup modeGroup;
    private RadioGroup faceGroup;


    private int mode;
    private int face;
    public ModeSelectorDialog(@NonNull Context context,int lan,int mode,int face) {
        super(context);
        this.context = context;
        this.language = lan;
        this.mode  = mode;
        this.face = face;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_dialog);
        initUI();
    }

    private void initUI(){
        AppCompatTextView title = findViewById(R.id.mode_selector_title);
        AppCompatTextView modeTitle = findViewById(R.id.show_mode_title);
        AppCompatTextView faceTitle = findViewById(R.id.face_show_title);

        title.setText(LanguageUtil.getSetTitle(language));
        modeTitle.setText(LanguageUtil.getSetModeTitle(language));
        faceTitle.setText(LanguageUtil.getFaceModeTitle(language));

        modeGroup = findViewById(R.id.show_mode_group);
        faceGroup = findViewById(R.id.face_show_group);



        RadioButton modeNormal = findViewById(R.id.show_mode_normal);
        RadioButton modeFlash = findViewById(R.id.show_mode_did);

        modeNormal.setText(LanguageUtil.getQuick(language));
        modeFlash.setText(LanguageUtil.getPrecise(language));

        RadioButton faceClose = findViewById(R.id.face_show_close);
        RadioButton faceOpen = findViewById(R.id.face_show_open);

        faceClose.setText(LanguageUtil.getLightClose(language));
        faceOpen.setText(LanguageUtil.getLightOpen(language));

        if (mode == 0){
            modeNormal.setChecked(true);
        }else {
            modeFlash.setChecked(true);
        }

        if (face == 0){
            faceClose.setChecked(true);
        }else {
            faceOpen.setChecked(true);
        }

        modeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.show_mode_normal){
                    mode = 0;
                }else {
                    mode = 1;
                }
            }
        });


        faceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.face_show_close){
                    face = 0;
                }else {
                    face = 1;
                }
            }
        });


        Button click = findViewById(R.id.mode_save);
        Button close = findViewById(R.id.mode_out);

        close.setText(LanguageUtil.getCancel(language));
        click.setText(LanguageUtil.getCheck(language));

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modeResult != null){
                    modeResult.didClick(mode,face,true);
                }
                dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modeResult != null){
                    modeResult.didClick(mode,face,false);
                }
                dismiss();
            }
        });



    }

}
