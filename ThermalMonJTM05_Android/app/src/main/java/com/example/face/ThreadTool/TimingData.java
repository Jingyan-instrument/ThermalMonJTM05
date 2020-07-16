//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.ThreadTool;

public class TimingData {
    int index;
    private boolean STATE = true;
    private int WAIT_ITEM = 0;
    private boolean BEGIN_STATE = false;
    private int WAIT_TIME = 1000;

    //自定义接口
    public static interface Result{
        public void didShow(int index);
    }

    private Result timingResult;

    public TimingData(int index){
        this.index = index;
    }

    //判断是否能检测
    public void checkCanAsk(){
        if (BEGIN_STATE){
            if (WAIT_ITEM == WAIT_TIME){
                WAIT_ITEM = WAIT_TIME+1;
                //间隔设定时间秒数后反馈进行显示
                if (timingResult!=null){
                    timingResult.didShow(index);
                }
            }else if (WAIT_ITEM < WAIT_TIME){
                WAIT_ITEM+=1;
            }
        }
    }

    public void setResult(Result r){
        this.timingResult = r;
    }

    public void setWAIT_ITEM(int item){
        WAIT_ITEM = item;
    }

    //重新进行识别
    public void resetWait(){
        WAIT_ITEM = 0;
    }


    //获取等待item
    public int getWAIT_ITEM(){
        return WAIT_ITEM;
    }

    //停止定时
    public void stopWait(){
        BEGIN_STATE = false;
        WAIT_ITEM = 0;
    }

    //修改状态开始定时
    public void startWait(){
        BEGIN_STATE = true;
    }

}
