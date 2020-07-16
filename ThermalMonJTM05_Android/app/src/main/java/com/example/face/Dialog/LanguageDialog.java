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
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.face.R;
import com.example.face.Tool.LanguageUtil;

public class LanguageDialog extends Dialog {

    //创建回调接口
    public static interface Request{
        public void click(int item);
    }

    private Request request;
    public void setCallBackListener(Request req){
        this.request = req;
    }

    Context context;
    int mode;
    RadioGroup radioGroup;
    public int language;
    public LanguageDialog(Context context,int mode,int language) {
        super(context);
        this.context = context;
        this.mode = mode;
        this.language = language;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_selector);
        initUI();
    }


    private void initUI(){
        AppCompatTextView title = findViewById(R.id.language_title);
        title.setText(LanguageUtil.getLanguageTitle(language));


        radioGroup = findViewById(R.id.language_group);

        RadioButton arabic = findViewById(R.id.language_arabic);
        RadioButton eng  = findViewById(R.id.language_english);
        RadioButton fre = findViewById(R.id.language_french);
        RadioButton ita = findViewById(R.id.language_Italian);
        RadioButton jap = findViewById(R.id.language_japanese);
        RadioButton span = findViewById(R.id.language_spanish);
        RadioButton sim = findViewById(R.id.language_simplified);
        RadioButton trad = findViewById(R.id.language_traditional);

        switch (mode){
            case 0:
                arabic.setChecked(true);
                break;
            case 1:
                eng.setChecked(true);
                break;
            case 2:
                fre.setChecked(true);
                break;
            case 3:
                ita.setChecked(true);
                break;
            case 4:
                jap.setChecked(true);
                break;
            case 5:
                span.setChecked(true);
                break;
            case 6:
                sim.setChecked(true);
                break;
            case 7:
                trad.setChecked(true);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.language_arabic:
                        mode = 0;
                        break;
                    case R.id.language_english:
                        mode = 1;
                        break;
                    case R.id.language_french:
                        mode = 2;
                        break;
                    case R.id.language_Italian:
                        mode = 3;
                        break;
                    case R.id.language_japanese:
                        mode = 4;
                        break;
                    case R.id.language_spanish:
                        mode = 5;
                        break;
                    case R.id.language_simplified:
                        mode = 6;
                        break;
                    case R.id.language_traditional:
                        mode = 7;
                        break;

                }
            }
        });


        Button btn = findViewById(R.id.language_check);
        Button cancel = findViewById(R.id.language_out);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request!=null){
                    request.click(mode);
                }
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request != null){
                    request.click(-1);
                }
                dismiss();
            }
        });

        btn.setText("YES");
        cancel.setText("NO");

    }

}
