//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import com.example.face.Temp.TempDataIndex;

import java.util.ArrayList;

public class MyDrawData {
    public ArrayList<FacePosition> list;
    public double[] temp;
    public double temp_data;
    public double black_data;
    public ArrayList<TempDataIndex> indexArrayList;
    public MyDrawData(ArrayList<FacePosition> list, double[] temp,double tempD,double black,ArrayList<TempDataIndex> indexArrayList){
        this.list = list;
        this.temp = temp;
        this.temp_data = tempD;
        black_data = black;
        this.indexArrayList = indexArrayList;
    }

    public double getMax(){
        try{
            double max = this.temp[0];
            for(int i = 0;i<temp.length;i++){
                if (temp[i]>max){
                    max = temp[i];
                }
            }
            String tempStr = String.format("%.1f",max);
            double tempDouble = Double.valueOf(tempStr);
            return tempDouble;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
