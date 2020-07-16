//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 设置开机启动软件
 * **/
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent thisIntent = new Intent(context, MainActivity.class);
            thisIntent.setAction("android.intent.action.MAIN");
            thisIntent.addCategory("android.intent.category.LAUNCHER");
            thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(thisIntent);
        }
    }
}
