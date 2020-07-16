//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

public class LightState {
    private boolean White = false;
    private boolean Red = false;
    private boolean Green = false;
    private boolean Blue = false;

    public LightState(){

    }

    public void changeType(String data){
        int lightType = StringUtil.hexStr2OneInt(data.substring(20,22)); //白色灯状态
        if (lightType == 0){
            this.White = false;
        }else if (lightType == 1){
            this.White = true;
        }
        int redType = StringUtil.hexStr2OneInt(data.substring(14,16));
        if (redType == 0){
            this.Red = false;
        }else {
            this.Red = true;
        }
        int greenType = StringUtil.hexStr2OneInt(data.substring(16,18));
        if (greenType == 0){
            this.Green = false;
        }else {
            this.Green = true;
        }
        int blueType = StringUtil.hexStr2OneInt(data.substring(18,20));
        if (blueType == 0){
            Blue = false;
        }else {
            Blue = true;
        }
    }

    //获取是否能修改指示灯
    public boolean getCloseLight(){
        if (!White && (Red || Green || Blue)){ //判断是否修改指示灯
            return true;
        }
        return false;
    }


    public boolean getWhiteType(){
        return White;
    }

    //判断是否开启红灯
    public boolean getOpenRed(){
        if (!White && !Red) { //判断是否修改指示灯
            return true;
        }
        return false;
    }

    //判断是否开启绿灯
    public boolean getOpenGreen(){
        if (!White && !Green) { //判断是否修改指示灯
            return true;
        }
        return false;
    }

    //判断是否开启蓝灯
    public boolean getOpenBlue(){
        if (!White && !Blue){
            return true;
        }
        return false;
    }




}
