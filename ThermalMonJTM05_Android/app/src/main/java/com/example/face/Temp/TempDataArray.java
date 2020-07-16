//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Temp;

import android.util.Log;

import java.util.ArrayList;

public class TempDataArray {
    private int length = 20;
    public ArrayList<Double> faceTemp = new ArrayList<Double>();
    public int x=0;
    public int y=0;

    public TempDataArray(){
        for(int i =0;i<length;i++){
            faceTemp.add(0.0);
        }
    }

    //替换全部数据
    public void setArray(ArrayList<Double> t){
        Log.d("JYYQ_DISTANCE","SET ARRAY-------------------------");
        this.faceTemp = t;
    }

    public void setData(int xPoint,int yPoint){
        this.x = xPoint;
        this.y = yPoint;
    }

    public void clearData(){
        this.x = 0;
        this.y = 0;
    }

    //清除数据
    public void clear(){
        //Log.e("JYYQ_DISTANCE","CLEAR_______________________");
        this.x = 0;
        this.y = 0;
        faceTemp.clear();
        for(int i =0;i<length;i++){
            faceTemp.add(0.0);
        }
    }

    //替换数据
    public void replace(double d){
        Log.e("JYYQ_DISTANCE","REPLACE____"+d);
        faceTemp.clear();
        for(int i =0;i<length;i++){
            faceTemp.add(d);
        }
    }


    //添加新数据
    public void addNewData(double data){
        faceTemp.remove(0);
        faceTemp.add(data);
    }

    //计算均值
    public double getAverage(){

        float sum = 0;
        for (int i =0;i<10;i++){

            sum+=faceTemp.get(i);
        }
        return sum/10;
    }

    //计算两点距离
    public boolean checkDistance(int xPoint,int yPoint){
        int dx = xPoint-x;
        int dy = yPoint-y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance<25){
            Log.d("JYYQ_DISTANCE",faceTemp.get(0)+","+faceTemp.get(1));
            Log.e("JYYQ_DISTANCE",distance+" SUCCESS" +xPoint+","+yPoint+"||"+x+","+y);
            return true;
        }
        //Log.e("JYYQ_DISTANCE",":"+x+","+y+"ERROR"+xPoint+","+yPoint);
        return false;
    }



}
