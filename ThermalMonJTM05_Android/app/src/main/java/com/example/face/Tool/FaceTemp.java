//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.graphics.Bitmap;

public class FaceTemp {
    public double[] group;
    public float temp;
    public Bitmap bitmap;
    public FaceTemp(double[] group,float temp){
        this.group = group;
        this.temp = temp;
    }
}
