//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.face.R;

public class ErrorDialog extends Dialog {

    //创建回调接口
    public static interface ErrorRequest{
        public void click(int select);
    }

    private ErrorRequest request;
    public void setCallBackListener(ErrorRequest req){
        this.request = req;
    }

    private int language;

    public ErrorDialog(@NonNull Context context,int lan) {
        super(context);
        language = lan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_dialog);
        initUI();
    }

    private void initUI(){
        AppCompatTextView title = findViewById(R.id.error_title);

        final Button restart = findViewById(R.id.error_restart);
        Button cancel = findViewById(R.id.error_out);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request != null){
                    request.click(1);
                }
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request != null){
                    request.click(0);
                }
                dismiss();
            }
        });


    }

}
