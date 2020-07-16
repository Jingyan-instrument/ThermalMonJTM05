//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.graphics.Point;
import android.util.Log;

import org.opencv.core.Rect;

public class FacePosition {
    public Point leftTopOne;
    public Point leftTopTwo;
    public Point leftTopTrd;

    public Point leftBomOne;
    public Point leftBomTwo;
    public Point leftBomTrd;

    public Point rightTopOne;
    public Point rightTopTwo;
    public Point rightTopTrd;

    public Point rightBomOne;
    public Point rightBomTwo;
    public Point rightBomTrd;

    public Rect rect;

    public int beginX;
    public int beginY;
    public int faceSize;
    public int faceWidth;


    //绘制数字框
    public int textTop;
    public int textLeft;
    public int textRight;
    public int textBom;

    public int textCenterX;
    public int textCenterY;
    public int textCenterRadio = 100;




    private float scale_data = (float) 10/3;

    public android.graphics.Rect testRect;
    public int heightScale = 130; //比例计算值

    public FacePosition(Rect rect,int scale){
        this.rect = rect;
        this.heightScale = scale;


        beginX = rect.x;
        faceWidth = rect.width;
        beginY = rect.y;
        faceSize = rect.height*heightScale/100;

        if (beginX >=240){
            beginX = 235;
        }

        if ((beginX+faceWidth) > 240 ){
            faceWidth = 240-beginX;
        }
        if (beginY >= 260 ){
            beginY = 260;
        }

        if ((beginY+faceSize)>290){
            faceSize = 290-beginY;
        }

        if (faceSize > 120){
            faceSize = 120;
        }

        int x = (int) (rect.x * scale_data);
        int y = (int) (rect.y * scale_data);
        int width = (int) (rect.width *scale_data);
        int height = (int) (rect.height *scale_data);

        if (x < 0){
            x = 0;
        }
        if (y <0){
            y = 0;
        }
        if ((x+width)>800){
            width = 800-x;
        }

        if ((y+height)>1066){
            height = 1066-y;
        }

        int wLine = width/4;
        int hLine = height/4;

        textLeft = x+width/2-100;
        textRight = x+width/2+100;
        textTop = y-100;
        textBom = y-10;
        textCenterX = x+width/2;
        textCenterY = y-45;

        leftTopOne = new Point(x,y+hLine);
        leftTopTwo = new Point(x,y);
        leftTopTrd = new Point(x+wLine,y);

        leftBomOne = new Point(x,y+height-hLine);
        leftBomTwo = new Point(x,y+height);
        leftBomTrd = new Point(x+wLine,height+y);


        rightTopOne = new Point(x+width-wLine,y);
        rightTopTwo = new Point(x+width,y);
        rightTopTrd = new Point(x+width,y+hLine);

        rightBomOne = new Point(x+width-wLine,y+height);
        rightBomTwo = new Point(x+width,y+height);
        rightBomTrd = new Point(x+width,y+height-hLine);

       // android.graphics.Rect r = new android.graphics.Rect(0,0,1,1);
        testRect = new android.graphics.Rect((int)(beginX*scale_data),(int)(beginY*scale_data),(int)((faceWidth+beginX)*scale_data),(int)((faceSize+beginY)*scale_data));
    }

    public int getX(){
        return rect.x+rect.width/2;
    }

    public int getY(){
        return rect.y+rect.height/2;
    }

    public int getSize(){
        return rect.width*rect.height;
    }

    //判断是否在区域内 ，
    public boolean checkRect(Rect rect){
        if (rect.x > this.rect.x && rect.x < (this.rect.width+this.rect.x)  && rect.y > this.rect.y && rect.y <(this.rect.y+this.rect.height)){
            return true;
        }
        return false;
    }




}
