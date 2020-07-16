//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;

import com.example.face.Face;

import java.io.IOException;

public class MusicPlay {
    private MediaPlayer adoptPlayer;
    private MediaPlayer dangerPlayer;
    private Context context;

    private int adoptLength;
    private int dangerLength;

    private boolean changeMode = false;
    private int value;

    private boolean isPlay;
    private int playIndex;
    private int mode;

    //创建回调接口
    public static interface Request{
        public void notDanger();
        public void notNormal();
    }

    private Request request;
    public void setCallBackListener(Request req){
        this.request = req;
    }

    public MusicPlay(Context context,int mode){
        this.context = context;
        this.mode = mode;
        String adoptName = LanguageUtil.getPassMusic(mode);
        String dangerName = LanguageUtil.getDangerMusic(mode);
        try {
            AssetFileDescriptor fd = context.getAssets().openFd(adoptName);
            adoptPlayer = new MediaPlayer();
            adoptPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            adoptPlayer.prepare();
            //adoptPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
            fd.close();



            //获取播放的最长时间
            adoptLength = adoptPlayer.getDuration();

            AssetFileDescriptor danger = context.getAssets().openFd(dangerName);
            dangerPlayer = new MediaPlayer();
            dangerPlayer.setDataSource(danger.getFileDescriptor(), danger.getStartOffset(), danger.getLength());
            danger.close();
            dangerPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);

            //dangerPlayer.prepareAsync();
            dangerPlayer.prepare();
            //获取警告的时间
            dangerLength = dangerPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //修改音量
    public void changeValue(int value){
        this.value = value;
        float va = (float)value/100;
        Log.e("JYYQ_VALUE",va+"");
        adoptPlayer.setVolume(va,va);
        dangerPlayer.setVolume(va,va);
    }

    //
    private void normalValueChange(){
        float va = (float)value/100;
        adoptPlayer.setVolume(va,va);
    }

    private void dangerValueChange(){
        float va = (float)value/100;
        dangerPlayer.setVolume(va,va);

    }


    public void playNormal(){
        changeMode = true;
        String adoptName = LanguageUtil.getPassMusic(mode);
        adoptPlayer.stop();
        adoptPlayer.release();
        adoptPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor fd = context.getAssets().openFd(adoptName);
            adoptPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            adoptPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    adoptPlayer.start();
                }
            });
            fd.close();
            normalValueChange();
            adoptPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeMode = false;
    }


    public void playDanger(){
        changeMode = true;
        String adoptName = LanguageUtil.getDangerMusic(mode);
        dangerPlayer.stop();
        dangerPlayer.release();
        dangerPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor fd = context.getAssets().openFd(adoptName);
            dangerPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            dangerPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    dangerPlayer.start();
                }
            });
            fd.close();
            dangerValueChange();
            dangerPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeMode = false;
    }




    //修改播放文件
    public void setMode(int mode){
        this.mode = mode;
        changeMode = true;
        adoptPlayer.stop();
        dangerPlayer.stop();
        //adoptPlayer.release();
        //dangerPlayer.release();
        String adoptName = LanguageUtil.getPassMusic(mode);
        String dangerName = LanguageUtil.getDangerMusic(mode);
        try {
            AssetFileDescriptor fd = context.getAssets().openFd(adoptName);
            MediaPlayer adoptPlayer = new MediaPlayer();
            adoptPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());

            this.adoptPlayer = adoptPlayer;

            this.adoptPlayer.prepare();
            //this.adoptPlayer.prepareAsync();

            AssetFileDescriptor danger = context.getAssets().openFd(dangerName);
            MediaPlayer dangerPlayer = new MediaPlayer();
            dangerPlayer.setDataSource(danger.getFileDescriptor(), danger.getStartOffset(), danger.getLength());
            this.dangerPlayer = dangerPlayer;
            //this.dangerPlayer.prepareAsync();
            this.dangerPlayer.prepare();
            fd.close();
            danger.close();
            this.dangerPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
            this.adoptPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);

            adoptLength = adoptPlayer.getDuration();
            dangerLength = dangerPlayer.getDuration();
            Log.e("JYYQ_MUSIC_CHANGE","CHANG MUSIC");
            changeValue(this.value);

        } catch (IOException e) {
            e.printStackTrace();
        }

        changeMode = false;
    }


    //播放音乐
    public void playMusic(int i ){
        if (!changeMode){
            int adoptSize =  adoptPlayer.getCurrentPosition();
            int dangerSize = dangerPlayer.getCurrentPosition();
            if (i == 0){
                if (adoptSize==0 || adoptSize>=adoptLength){
                    if (dangerSize == 0 || dangerSize >= dangerLength){
                        try{
                            playNormal();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    if (request != null){
                        request.notNormal();
                    }

                }
            }else if (i==1){
                try{
                    if (adoptSize > 0){
                        adoptPlayer.pause();
                        adoptPlayer.seekTo(0);
                    }
                    if (dangerSize == 0 || dangerSize >= dangerLength){
                        playDanger();
                    }else {
                        if (request!=null){
                            request.notDanger();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //




}
