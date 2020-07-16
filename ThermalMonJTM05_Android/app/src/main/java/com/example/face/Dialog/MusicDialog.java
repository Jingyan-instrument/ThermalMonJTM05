//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.face.R;
import com.example.face.Tool.LanguageUtil;

public class MusicDialog extends Dialog {

    //创建回调接口
    public static interface Request{
        public void result(int item,boolean d);
    }

    private Request request;
    public void setCallBackListener(Request req){
        this.request = req;
    }

    int value;
    int mode;
    public MusicDialog(@NonNull Context context,int mode,int value) {
        super(context);
        this.value = value;
        this.mode = mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_dialog);
        initUI();
    }

    private void initUI(){
        AppCompatTextView tv = findViewById(R.id.music_title);
        tv.setText(LanguageUtil.getMusicName(mode));

        final AppCompatTextView music_data = findViewById(R.id.music_data);
        music_data.setText(String.valueOf(value));

        final SeekBar seekBar = findViewById(R.id.music_seek);
        seekBar.setMax(100);
        seekBar.setProgress(value);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                music_data.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Button btn = findViewById(R.id.music_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request !=null){
                    request.result(seekBar.getProgress(),true);
                }
                dismiss();
            }
        });

        Button cancel = findViewById(R.id.music_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request !=null){
                    request.result(seekBar.getProgress(),false);
                }
                dismiss();
            }
        });

        cancel.setText(LanguageUtil.getCancel(mode));
        btn.setText(LanguageUtil.getCheck(mode));

    }
}
