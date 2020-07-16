//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.face.R;
import com.example.face.Temp.TempDataIndex;

import java.util.ArrayList;
import java.util.Random;

public class MyLineView extends View {
    private Context context;


    ArrayList<FacePosition> facePositionArrayList = new ArrayList<FacePosition>();
    double[] temp;
    //double[] test_group; //固定显示数据组合
    private Paint paint = new Paint();
    private float danger = (float) 37.2;
    private double minTemp = 33.3;
    private boolean showC;
    private int heightScale = 30;
    private int STATE = 0;
    private int showMojo = 0;
    ArrayList<TempDataIndex> tempArray = new ArrayList<TempDataIndex>();


    public MyLineView(Context context) {
        super(context);
        this.context = context;
        paint.setStrokeWidth(3);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.CENTER);


    }

    public MyLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint.setStrokeWidth(3);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.CENTER);

    }

    public MyLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint.setStrokeWidth(3);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.CENTER);

    }

    public void setShowMojo(int mojo){
        this.showMojo = mojo;
    }

    /**
     * 测试模式人脸显示
     * 进行数据包装处理
     * **/
    public void setData(ArrayList<FacePosition> list, double[] data, int show, ArrayList<TempDataIndex> tempDataIndexArrayList){
        this.facePositionArrayList = list;
        this.temp = data;
        if (show == 0){
            showC = true;
        }else {
            showC = false;
        }
        tempArray.clear();
        tempArray.addAll(tempDataIndexArrayList);
        this.STATE = 1;
        this.invalidate();
    }


    public void setData(ArrayList<FacePosition> list,double[] data,int show){
        this.facePositionArrayList = list;
        this.temp = data;
        if (show == 0){
            showC = true;
        }else {
            showC = false;
        }
        this.STATE = 0;
        this.invalidate();
    }

    public void setDangerAndMin(float danger,double min){
        this.minTemp = min;
        //this.minTemp = -1;
        this.danger = danger;
    }

    public void setArrayData(ArrayList<FacePosition> list){
        this.facePositionArrayList = list;
        this.invalidate();
    }

    public void clearView(){
        this.facePositionArrayList.clear();
        this.invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawView(canvas);
    }

    private void drawView(Canvas canvas){
        ArrayList<FacePosition> listFace = facePositionArrayList;
        for (int i = 0;i<listFace.size();i++){
            if (temp.length>i){
                if (STATE == 0){
                    Log.e("JYYQ_TEMP",temp[i]+"");
                    String tempStr = String.format("%.1f",temp[i]);
                    double tempDouble = Double.valueOf(tempStr);
                    if (tempDouble >= minTemp){
                        paint.setShader(null);
                        paint.setColor(Color.GREEN);
                        FacePosition facePosition = listFace.get(i);
                        //paint.setColor(Color.YELLOW);
                        //canvas.drawRect(facePosition.testRect,paint);
                        paint.setColor(Color.GREEN);
                        canvas.drawLine(facePosition.leftTopOne.x,facePosition.leftTopOne.y,facePosition.leftTopTwo.x,facePosition.leftTopTwo.y,paint);
                        canvas.drawLine(facePosition.leftTopTwo.x,facePosition.leftTopTwo.y,facePosition.leftTopTrd.x,facePosition.leftTopTrd.y,paint);

                        canvas.drawLine(facePosition.leftBomOne.x,facePosition.leftBomOne.y,facePosition.leftBomTwo.x,facePosition.leftBomTwo.y,paint);
                        canvas.drawLine(facePosition.leftBomTwo.x,facePosition.leftBomTwo.y,facePosition.leftBomTrd.x,facePosition.leftBomTrd.y,paint);

                        canvas.drawLine(facePosition.rightTopOne.x,facePosition.rightTopOne.y,facePosition.rightTopTwo.x,facePosition.rightTopTwo.y,paint);
                        canvas.drawLine(facePosition.rightTopTwo.x,facePosition.rightTopTwo.y,facePosition.rightTopTrd.x,facePosition.rightTopTrd.y,paint);

                        canvas.drawLine(facePosition.rightBomOne.x,facePosition.rightBomOne.y,facePosition.rightBomTwo.x,facePosition.rightBomTwo.y,paint);
                        canvas.drawLine(facePosition.rightBomTwo.x,facePosition.rightBomTwo.y,facePosition.rightBomTrd.x,facePosition.rightBomTrd.y,paint);

                        int x = facePosition.leftTopTwo.x;
                        int y = facePosition.leftTopTwo.y;
                        int r = facePosition.rightTopTwo.x;
                        int b = facePosition.rightBomTwo.y;


                        //double temps = temp[i];
                        if (tempDouble > danger){
                            RadialGradient dangerRadio = new RadialGradient(facePosition.textCenterX,facePosition.textCenterY,facePosition.textCenterRadio,Color.parseColor("#FFef230e"),Color.parseColor("#00ef230e"), Shader.TileMode.CLAMP);
                            paint.setShader(dangerRadio);
                            if (showMojo == 1){
                                Bitmap dangerBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.danger_face);
                                canvas.drawBitmap(dangerBitmap,null,new RectF(x,y,r,b),paint);
                            }
                        }else {
                            RadialGradient normalRadio = new RadialGradient(facePosition.textCenterX,facePosition.textCenterY,facePosition.textCenterRadio,Color.parseColor("#FF33C336"),Color.parseColor("#0033C336"), Shader.TileMode.CLAMP);
                            paint.setShader(normalRadio);
                            if (showMojo == 1){
                                Bitmap  normalBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.runing_face);
                                canvas.drawBitmap(normalBitmap,null,new RectF(x,y,r,b),paint);
                            }
                        }
                        canvas.drawRect(facePosition.textLeft,facePosition.textTop,facePosition.textRight,facePosition.textBom,paint);

                        paint.setShader(null);
                        paint.setColor(Color.WHITE);
                        if (showC){
                            String tem = String.format("%.1f",temp[i]);
                            canvas.drawText(tem,facePosition.textCenterX,facePosition.textBom-15,paint);
                        }else {
                            String tem = String.format("%.1f",getTemp(temp[i]));
                            canvas.drawText(tem,facePosition.textCenterX,facePosition.textBom-15,paint);
                        }

                        //canvas.drawText(String.valueOf(i),facePosition.textCenterX,facePosition.textBom-15,paint);

                    }
                }else {
                    TempDataIndex tempIndex = tempArray.get(i);
                    if (tempIndex.getType()){
                        paint.setColor(Color.GREEN);
                    }else {
                        paint.setColor(Color.BLUE);
                    }
                    FacePosition facePosition = listFace.get(i);

                    canvas.drawLine(facePosition.leftTopOne.x,facePosition.leftTopOne.y,facePosition.leftTopTwo.x,facePosition.leftTopTwo.y,paint);
                    canvas.drawLine(facePosition.leftTopTwo.x,facePosition.leftTopTwo.y,facePosition.leftTopTrd.x,facePosition.leftTopTrd.y,paint);

                    canvas.drawLine(facePosition.leftBomOne.x,facePosition.leftBomOne.y,facePosition.leftBomTwo.x,facePosition.leftBomTwo.y,paint);
                    canvas.drawLine(facePosition.leftBomTwo.x,facePosition.leftBomTwo.y,facePosition.leftBomTrd.x,facePosition.leftBomTrd.y,paint);

                    canvas.drawLine(facePosition.rightTopOne.x,facePosition.rightTopOne.y,facePosition.rightTopTwo.x,facePosition.rightTopTwo.y,paint);
                    canvas.drawLine(facePosition.rightTopTwo.x,facePosition.rightTopTwo.y,facePosition.rightTopTrd.x,facePosition.rightTopTrd.y,paint);

                    canvas.drawLine(facePosition.rightBomOne.x,facePosition.rightBomOne.y,facePosition.rightBomTwo.x,facePosition.rightBomTwo.y,paint);
                    canvas.drawLine(facePosition.rightBomTwo.x,facePosition.rightBomTwo.y,facePosition.rightBomTrd.x,facePosition.rightBomTrd.y,paint);

                    int x = facePosition.leftTopTwo.x;
                    int y = facePosition.leftTopTwo.y;
                    int r = facePosition.rightTopTwo.x;
                    int b = facePosition.rightBomTwo.y;


                    if (tempIndex.getType()){
                        double temps = tempIndex.temp;
                        if (temps > danger){
                            RadialGradient dangerRadio = new RadialGradient(facePosition.textCenterX,facePosition.textCenterY,facePosition.textCenterRadio,Color.parseColor("#FFef230e"),Color.parseColor("#00ef230e"), Shader.TileMode.CLAMP);
                            paint.setShader(dangerRadio);
                            if (showMojo == 1){
                                Bitmap dangerBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.danger_face);
                                canvas.drawBitmap(dangerBitmap,null,new RectF(x,y,r,b),paint);
                            }
                        }else {
                            RadialGradient normalRadio = new RadialGradient(facePosition.textCenterX,facePosition.textCenterY,facePosition.textCenterRadio,Color.parseColor("#FF33C336"),Color.parseColor("#0033C336"), Shader.TileMode.CLAMP);
                            paint.setShader(normalRadio);
                            if (showMojo == 1){
                                Bitmap  normalBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.runing_face);
                                canvas.drawBitmap(normalBitmap,null,new RectF(x,y,r,b),paint);
                            }
                        }
                        canvas.drawRect(facePosition.textLeft,facePosition.textTop,facePosition.textRight,facePosition.textBom,paint);

                        paint.setShader(null);
                        paint.setColor(Color.WHITE);
                        if (showC){
                            String tem = String.format("%.1f",temps);
                            canvas.drawText(tem,facePosition.textCenterX,facePosition.textBom-15,paint);
                        }else {
                            String tem = String.format("%.1f",getTemp(temps));
                            canvas.drawText(tem,facePosition.textCenterX,facePosition.textBom-15,paint);
                        }
                    }else {
                        if (showMojo == 1){
                            Bitmap nothingBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.nothing_face);
                            canvas.drawBitmap(nothingBitmap,null,new RectF(x,y,r,b),paint);
                        }
                    }
                }
            }
        }
    }
    //Double num=32+bundle.getDouble("num")*1.8;
    //温度转华摄氏度
    public double getTemp(double t){
        double num=32+t*1.8;
        return num;
    }




}
