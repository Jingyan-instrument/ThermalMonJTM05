//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.ThreadTool;

import android.util.Log;

import java.util.ArrayList;

public class TimingArrayList extends Thread implements TimingData.Result{
    private boolean STATE = true;
    private boolean RUN = false;
    private ArrayList<TimingData> timingDataArrayList = new ArrayList<TimingData>();

    @Override
    public void didShow(int index) {
        if (timingResult!=null){
            timingResult.didShow(index);
        }
    }

    //自定义接口
    public static interface TimingResult{
        public void didShow(int index);
    }

    private TimingResult timingResult;

    public TimingArrayList(){
        for (int i = 0;i<5;i++){
            TimingData t = new TimingData(i);
            t.setResult(this);
            timingDataArrayList.add(t);
        }
    }

    @Override
    public void run() {
        super.run();
        while (STATE){
            if (RUN){
                try {
                    Thread.sleep(1);
                    for (int j=0;j<5;j++){
                        if (RUN){
                            timingDataArrayList.get(j).checkCanAsk();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //重新开始
    public void restartWait(int index){
        timingDataArrayList.get(index).resetWait();
    }

    //停止定时
    public void stopWait(int index){
        //Log.d("JYYQ_TEMP","STOP:"+index);
        timingDataArrayList.get(index).stopWait();
    }

    //开启线程
    public void startWait(int index){
       // Log.d("JYYQ_TEMP","START:"+index);
        timingDataArrayList.get(index).startWait();
    }


    public void setWait(int index,int data){
        Log.d("JYYQ_DISTANCE","WAIT:"+data);
        timingDataArrayList.get(index).setWAIT_ITEM(data);
    }

    public void beginRun(){
        RUN = true;
    }

    public void dissRun(){
        RUN = false;
    }

    public int getWaitItem(int index){
        return timingDataArrayList.get(index).getWAIT_ITEM();
    }

    public void setResultClick(TimingResult t){
        this.timingResult = t;
    }

}
