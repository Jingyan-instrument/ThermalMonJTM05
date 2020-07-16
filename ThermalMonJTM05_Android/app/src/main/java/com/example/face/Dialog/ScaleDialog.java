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

public class ScaleDialog extends Dialog {
    //创建回调接口
    public static interface ScaleResult{
        public void click(int data,boolean type);
    }

    private ScaleResult request;
    public void setCallBackListener(ScaleResult req){
        this.request = req;
    }
    private int scale;
    public ScaleDialog(@NonNull Context context,int scale) {
        super(context);
        this.scale = scale;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scale_dialog);
        initUI();
    }

    private void initUI(){
        final AppCompatTextView showData = findViewById(R.id.face_height_scale);
        final SeekBar seekBar = findViewById(R.id.face_height_seek);
        Button save = findViewById(R.id.face_height_save);
        Button cancel = findViewById(R.id.face_height_cancel);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showData.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData.setText(seekBar.getProgress()+"");

                if (request!=null){
                    request.click(seekBar.getProgress(),true);
                }
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request!=null){
                    request.click(0,false);
                }
                dismiss();
            }
        });
        seekBar.setProgress(this.scale);
        showData.setText(seekBar.getProgress()+"");
    }

}
