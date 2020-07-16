//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.face.R;

public class CameraSetDialog extends Dialog implements View.OnClickListener {
    private int top = 0;
    private int bom = 320;
    private int left = 0;
    private int right = 240;
    String pc;

    private EditText editText;
    //创建回调接口
    public static interface CameraSetResult{
        public void result(int t,int b,int l,int r,int pc);
        public void diss();
    }

    private CameraSetResult request;
    public void setCallBackListener(CameraSetResult req){
        this.request = req;
    }

    public CameraSetDialog(@NonNull Context context,int t,int b,int l,int r,String p) {
        super(context);
        top = t;
        bom = b;
        left = l;
        right = r;
        this.pc = p;
    }

    public CameraSetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_dialog);
        initUI();
    }

    private void initUI(){
        final AppCompatTextView topData = findViewById(R.id.top_data);
        SeekBar topSeek = findViewById(R.id.top_seek);

        final AppCompatTextView bomData = findViewById(R.id.bom_data);
        SeekBar bomSeek = findViewById(R.id.bom_seek);

        final AppCompatTextView leftData = findViewById(R.id.left_data);
        SeekBar leftSeek = findViewById(R.id.left_seek);

        final AppCompatTextView rightData = findViewById(R.id.right_data);
        SeekBar rightSeek = findViewById(R.id.right_seek);

        topSeek.setProgress(top);
        bomSeek.setProgress(bom);
        leftSeek.setProgress(left);
        rightSeek.setProgress(right);

        topData.setText(String.valueOf(top));
        bomData.setText(String.valueOf(bom));
        leftData.setText(String.valueOf(left));
        rightData.setText(String.valueOf(right));

        editText = findViewById(R.id.temp_pc);
        editText.setText(this.pc);

        topSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress>=bom){
                    seekBar.setProgress(bom-1);
                }else {
                    topData.setText(String.valueOf(progress));
                    top = progress;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bomSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress<=top){
                    seekBar.setProgress(top+1);
                }else {
                    bomData.setText(String.valueOf(progress));
                    bom = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        leftSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress>=right){
                    seekBar.setProgress(right-1);
                }else {
                    left = progress;
                    leftData.setText(String.valueOf(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rightSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress<=left){
                    seekBar.setProgress(left+1);
                }else {
                    rightData.setText(String.valueOf(progress));
                    right = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button ok = findViewById(R.id.set_camera_save);
        ok.setTag(0);
        ok.setOnClickListener(this);
        Button cancel = findViewById(R.id.set_camera_cancel);
        cancel.setTag(1);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        if (btn.getText().equals("发送")){
            if (request!=null){
                double pc;
                try{
                    pc = Double.valueOf(editText.getText().toString());
                } catch (Exception e) {
                    pc = 0;
                    e.printStackTrace();
                }
                int hind = (int)(pc*100)+10000;
                request.result(top,bom,left,right,hind);
            }
        }else {
            if (request!=null){
                request.diss();
            }
            dismiss();
        }
    }
}
