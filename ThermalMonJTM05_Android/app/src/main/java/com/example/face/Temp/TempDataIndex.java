//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Temp;

public class TempDataIndex {
    public double temp;
    public boolean type;
    public TempDataIndex(){
        temp = 40;
        type = false;
    }

    public void setTemp(double d){
        if (!type){
            this.temp = d;
        }
    }

    public void setType(boolean b){
        this.type = b;
    }

    public double getTemp(){
        return temp;
    }

    public boolean getType(){
        return type;
    }

}
