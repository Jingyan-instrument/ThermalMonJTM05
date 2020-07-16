//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.face.R;
import com.example.face.Tool.LanguageUtil;

public class AskResetDialog extends Dialog {

    //创建回调接口
    public static interface FaceRequest{
        public void click(int select);
    }

    private FaceRequest request;
    public void setCallBackListener(FaceRequest req){
        this.request = req;
    }

    private int language;

    public AskResetDialog(Context context,int language) {
        super(context);
        this.language = language;
    }

    protected AskResetDialog(Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_dialog);
        initUI();
    }

    public void initUI(){
        AppCompatTextView title = findViewById(R.id.reset_title);
        title.setText(LanguageUtil.getAskRest(language));

        Button btn = findViewById(R.id.reset_save);
        btn.setText(LanguageUtil.getRestCheck(language));

        Button out = findViewById(R.id.reset_out);
        out.setText(LanguageUtil.getRestCancel(language));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.click(1);
                dismiss();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.click(0);
                dismiss();
            }
        });
        //

    }

}
