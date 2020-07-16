//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.face.R;
import com.example.face.Tool.LanguageUtil;

public class MenuDialog extends Dialog  implements View.OnClickListener {
    Context context;

    //创建回调接口
    public static interface SettingRequest{
        public void click(String index);
        public void out();
    }

    private SettingRequest request;
    public void setCallBackListener(SettingRequest req){
        this.request = req;
    }
    private int language;
    public MenuDialog(@NonNull Context context,int language) {
        super(context);
        this.context = context;
        this.language = language;
    }



    protected MenuDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        initUI();
    }

    private void initUI(){
        Button temp = findViewById(R.id.data_setting);
        Button face = findViewById(R.id.face_setting);
        Button exit = findViewById(R.id.dialog_exit);
        Button light = findViewById(R.id.light_setting);
        Button reset = findViewById(R.id.reset);
        Button languages = findViewById(R.id.language);
        Button music = findViewById(R.id.music);
        Button mode = findViewById(R.id.mode);

        temp.setText(LanguageUtil.getMenuEnv(this.language));
        face.setText(LanguageUtil.getMenuFace(this.language));
        light.setText(LanguageUtil.getMenuLight(language));
        reset.setText(LanguageUtil.getMenuRest(language));
        exit.setText(LanguageUtil.getMenuOut(language));
        languages.setText(LanguageUtil.getMenuLanguage(language));
        music.setText(LanguageUtil.getMusicName(language));
        mode.setText(LanguageUtil.getSetTitle(language));




        temp.setOnClickListener(this);
        face.setOnClickListener(this);
        exit.setOnClickListener(this);
        light.setOnClickListener(this);
        reset.setOnClickListener(this);
        languages.setOnClickListener(this);
        music.setOnClickListener(this);
        mode.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        String tag= (String) v.getTag();
        if (request != null){
            request.click(tag);
            if (tag.equals("-1")){
                request.out();
            }
        }
        dismiss();
    }
}
