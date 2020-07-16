//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class StringUtil {

    //获取当前机器的偏移设置
    public static String getHidingOrder(){
        StringBuffer stringBuffer =new StringBuffer("AAAA000200046400");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }

    //获取发送上下左右偏移设置命令
    public static String getSetHidingOrder(int t,int b,int l,int r,int pc){
        StringBuffer stringBuffer =new StringBuffer("AAAA0002000E6401");
        String top = int2HexStr(t,false);
        String rig = int2HexStr(r,false);
        String lef = int2HexStr(l,false);
        String bom = int2HexStr(b,false);
        String p = int2HexStr(pc,false);
        stringBuffer.append(toFormat(top));
        stringBuffer.append(toFormat(rig));
        stringBuffer.append(toFormat(bom));
        stringBuffer.append(toFormat(lef));
        stringBuffer.append(p);
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        Log.d("JYYQ_REQ",pc+","+p);
        Log.d("JYYQ_REQ",stringBuffer.toString());
        return stringBuffer.toString();
    }

    public static String getSetCameraOrder(){
        StringBuffer stringBuffer =new StringBuffer("AAAA0002");
        stringBuffer.append("000365");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        Log.d("JYYQ",stringBuffer.toString());
        return stringBuffer.toString();
    }

    public static String getAllCameraOrder(){
        StringBuffer stringBuffer =new StringBuffer("AAAA0002");
        stringBuffer.append("000302");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        Log.d("JYYQ",stringBuffer.toString());
        return stringBuffer.toString();
    }


    public static double[] getFaceView(String src){
        int l = src.length() / 4;
        int[] intGroup = new int[l];
        int[] imgGroup = new int[l];
        double[] mat_data = new double[l*3];
        for (int y = 0;y<l;y++){
            int t = Integer.valueOf(src.substring(y * 4, y * 4 + 4), 16);
            intGroup[y] = t;
            imgGroup[y] = t;
        }
        Arrays.sort(intGroup);
        int min = intGroup[0];
        int max = intGroup[l-1];
        int space = max-min;
        Log.e("JYYQ","MIN:"+min+", MAX:"+max);
        for (int i =0;i<l;i++){
            int datas = 255 - (max - imgGroup[i]) / space * 255;
            mat_data[i*3] = datas;
            mat_data[i*3+1] = datas;
            mat_data[i*3+2] = datas;
            //ret[i] = (byte) ((imgGroup[i])/(max-min)*125);
        }

        return mat_data;
    }

    public static FaceTemp getDraw(String src){
        int hind = 2;
        int l = src.length() / 4;
        int[] intGroup = new int[l];
        int[] imgGroup = new int[l];
        double[] mat_data = new double[l*3];
        for (int y = 0;y<l;y++){
            int t = Integer.valueOf(src.substring(y * 4, y * 4 + 4), 16);
            intGroup[y] = t;
            imgGroup[y] = t;
        }
        Arrays.sort(intGroup);
        int min = intGroup[0];
        int max = intGroup[l-1];

        float maxTemp = ((float)max/100-100);

        float space = max-min;
        //int mean = sum/l;
        float allTemp = 0;
        int allSize = 0;
        float hindTemp = maxTemp-hind;
        for (int i =0;i<l;i++){
            int t = imgGroup[i];
            float temp = ((float)t)/100-100;
            float datas = 255 - ((max - t))*255/space;
            mat_data[i*3] = datas;
            mat_data[i*3+1] = datas;
            mat_data[i*3+2] = datas;

            if (temp>hindTemp){
                allTemp+=temp;
                allSize+=1;
            }
        }
        //Log.e("JYYQ","MIN:"+min+", MAX:"+max);
        //float temp = ((float)mean)/100-100;
        float temp = allTemp/allSize;
        Log.e("JYYQ_TEMP",temp+"???");
        FaceTemp faceTemp = new FaceTemp(mat_data,temp);
        return faceTemp;
    }


    //开启关闭摄像头 0为关闭 1为开启
    public static String getCameraOpen(boolean type){
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        stringBuffer.append("00");
        String length = int2HexStr(4,false);
        stringBuffer.append(length);
        stringBuffer.append("05");
        if (type){
            stringBuffer.append("01");
        }else {
            stringBuffer.append("00");
        }
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }


    //获取白色灯光开启或关闭命令
    public static String getLightOrder(boolean type){
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        stringBuffer.append("00");
        String length = int2HexStr(7,false);
        stringBuffer.append(length);
        stringBuffer.append("04000000");
        if (type){
            stringBuffer.append("01");
        }else {
            stringBuffer.append("00");
        }
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        Log.e("JYYQ_LIGHT","LIGHT: "+stringBuffer.toString());
        return stringBuffer.toString();
    }

    //MARK:获取蓝色灯光开启或关闭命令
    public static String getBlueLightOrder(){
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        stringBuffer.append("00");
        String length = int2HexStr(7,false);
        stringBuffer.append(length);
        stringBuffer.append("04");
        stringBuffer.append("00000100");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }

    //MARK:获取绿色灯光开启或关闭命令
    public static String getGreenLightOrder(){
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        stringBuffer.append("00");
        String length = int2HexStr(7,false);
        stringBuffer.append(length);
        stringBuffer.append("04");
        stringBuffer.append("00010000");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }

    //MARK:获取红色灯光开启或关闭命令
    public static String getRedLightOrder(){
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        stringBuffer.append("00");
        String length = int2HexStr(7,false);
        stringBuffer.append(length);
        stringBuffer.append("04");
        stringBuffer.append("01000000");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }

    //MARK:关闭全部灯光
    public static String getCloseLightOrder(){
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        stringBuffer.append("00");
        String length = int2HexStr(7,false);
        stringBuffer.append(length);
        stringBuffer.append("04");
        stringBuffer.append("00000000");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }



    public static String getAllFace(ArrayList<FacePosition> list){
        int num = list.size();
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        String length = int2HexStr(3+num*8,false);
        String faceNum = int2HexStr(num,false);
        stringBuffer.append("00");
        stringBuffer.append(length);
        stringBuffer.append("03");
        //stringBuffer.append(faceNum);
        for (int i = 0;i<1;i++){
            FacePosition f = list.get(i);
            String x = int2HexStr(f.rect.x,false);
            String y = int2HexStr(f.rect.y,false);
            String size = int2HexStr(f.rect.height,false);
            String width = int2HexStr(f.rect.width,false);

//            String x = int2HexStr(0,false);
//            String y = int2HexStr(0,false);
//            String size = int2HexStr(1,false);
//            String width = int2HexStr(1,false);

            x = toFormat(x);
            y = toFormat(y);
            width = toFormat(width);
            size = toFormat(size);

            stringBuffer.append(x);
            stringBuffer.append(y);
            stringBuffer.append(width);
            stringBuffer.append(size);
        }
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }

    public static String getRectangleFaceOrder(ArrayList<FacePosition> list,String type){
        int num = list.size();
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        String length = int2HexStr(4+num*8,false);
        String faceNum = int2HexStr(num,false);
        stringBuffer.append("00");
        stringBuffer.append(length);
        stringBuffer.append(type);
        stringBuffer.append(faceNum);
        for (int i = 0;i<list.size();i++){
            FacePosition f = list.get(i);
//            String x = int2HexStr(f.rect.x,false);
//            String y = int2HexStr(f.rect.y,false);
//            String size = int2HexStr(f.rect.height/2,false);
//            String width = int2HexStr(f.rect.width,false);
            String x = int2HexStr(f.beginX,false);
            String y = int2HexStr(f.beginY,false);
            String size = int2HexStr(f.faceSize,false);
            String width = int2HexStr(f.faceWidth,false);

            x = toFormat(x);
            y = toFormat(y);

            width = toFormat(width);
            size = toFormat(size);

            stringBuffer.append(x);
            stringBuffer.append(y);
            stringBuffer.append(width);
            stringBuffer.append(size);
        }
       // Log.e("JYYQ_ERROR","");
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }


    public static String getCheckOrder(ArrayList<FacePosition> list){
        int num = list.size();
        StringBuffer stringBuffer = new StringBuffer("AAAA0002");
        //String order ="AAAA0002";
        String length = int2HexStr(4+num*6,false);
        String faceNum = int2HexStr(num,false);
        stringBuffer.append("00");
        stringBuffer.append(length);
        stringBuffer.append("01");
        stringBuffer.append(faceNum);
        for (int i = 0;i<list.size();i++){
            FacePosition f = list.get(i);
            String x = int2HexStr(f.beginX,false);
            String y = int2HexStr(f.beginY,false);
            String size = int2HexStr(f.faceSize,false);

            x = toFormat(x);
            y = toFormat(y);
            size = toFormat(size);

            stringBuffer.append(x);
            stringBuffer.append(y);
            stringBuffer.append(size);
        }
        String xor = checkXor(stringBuffer.substring(8));
        stringBuffer.append("03");
        stringBuffer.append(xor);
        return stringBuffer.toString();
    }


    public static String toFormat(String str){
        int length = str.length();
        String result = "";
        if (length == 2){
            result = "00"+str;
        }else if (length == 3){
            result = "0"+str;
        }else if (length == 1){
            result = "000"+str;
        }else if (length == 4){
            return str;
        }
        return result;
    }

    public static String checkXor(String data) {
        int checkData = 0;
        try {
            checkData = 0;
            for (int i = 0; i < data.length(); i = i + 2) {
                //将十六进制字符串转成十进制
                int start = Integer.parseInt(data.substring(i, i + 2), 16);
                //进行异或运算
                checkData = start ^ checkData;
            }
        } catch (Exception e) {
            Log.e("JYYQERROR",data);
            e.printStackTrace();
        }
        return integerToHexString(checkData);
    }

    public static String integerToHexString(int s) {
        String ss = Integer.toHexString(s);
        if (ss.length() % 2 != 0) {
            ss = "0" + ss;//0F格式
        }
        return ss.toUpperCase();
    }


    //将16进制转int
    public static int hexStr2Int(String src){
        byte[] g = hexStr2Bytes(src);
        int data = getIntData(g[0])*256 + getIntData(g[1]);
        return data;
    }

    public static int hexStr2OneInt(String src){
        byte[] g = hexStr2Bytes(src);

        int data = getIntData(g[0]);
        return data;
    }

    //byte转int
    public static int getIntData(byte b){
        return b & 0xFF;

    }


    public static float hexStr2Float(String src){
        byte[] data = hexStr2Bytes(src);
        int a = data[0] & 0xFF;
        int b = data[1] & 0xFF;
        //Log.e("JYYQ",data[0]+"??"+data[1]);
        //int i = b & 0xff
        int result = a*256+b;
        return result;
    }

    public static int toInt(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;

        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * i);
        }
        return iOutcome;
    }



    public static byte[] hexStr2BytesTest(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }


    public static byte[] hexStr2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    public static String bytes2HexStr(byte[] b, int size) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < size; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }

    public static String intStr2HexStr(String intStr, boolean isKeepOne) {
        String hexStr;
        int tempInt = Integer.parseInt(intStr);
        hexStr = Integer.toHexString(tempInt);
        if (hexStr.length() == 1 && !isKeepOne) {
            hexStr = "0" + hexStr;
        }
        return hexStr.toUpperCase();
    }

    public static String int2HexStr(int num, boolean isKeepOne) {
        String hexStr;
        hexStr = Integer.toHexString(num);
        if (hexStr.length() == 1 && !isKeepOne) {
            hexStr = "0" + hexStr;
        }
        if (hexStr.length() == 0){
            Log.e("JYYQ_ERROR",num+" This is the error");
        }else {
            //Log.e("JYYQ_ERROR",num+" This is the normal");
        }
        return hexStr.toUpperCase();
    }

    private static String[] binaryArray =
            {"0000", "0001", "0010", "0011",
                    "0100", "0101", "0110", "0111",
                    "1000", "1001", "1010", "1011",
                    "1100", "1101", "1110", "1111"};

    //转换为二进制字符串
    public static String bytes2BinaryStr(byte[] bArray) {
        String outStr = "";
        int pos;
        for (byte b : bArray) {
            //高四位
            pos = (b & 0xF0) >> 4;
            outStr += binaryArray[pos];
            //低四位
            pos = b & 0x0F;
            outStr += binaryArray[pos];
        }
        return outStr;
    }

    //16进制字符串转为二进制字符串
    public static String hexStr2BinaryStr(String src) {
        return bytes2BinaryStr(hexStr2Bytes(src));
    }

    //每隔两位增加空格
    public static String getFileAddSpace(String replace) {
        String regex = "(.{2})";
        replace = replace.replaceAll(regex, "$1 ");
        return replace;
    }




}