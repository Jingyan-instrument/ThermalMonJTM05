//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.graphics.Bitmap;

public class MyTestDrawData{
    public Bitmap bitmap;
    public double temp;
    public String tem;
    public String black;
    public MyTestDrawData(Bitmap b,float t,String temp,String black){
        this.bitmap = b;
        this.temp = t;
        this.tem = temp;
        this.black = black;
    }
}