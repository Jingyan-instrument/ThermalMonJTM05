//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.App;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;

import com.example.face.R;
import com.example.face.Tool.StringUtil;

import java.util.HashMap;
import java.util.Iterator;

public class UsbActivity extends AppCompatActivity {

    private final String LOGTAG = "UsbActivity";

    private Context context;

    private int targetDeviceVendorId = 1155;
    private int targetDeviceProductId = 22336;

    private UsbManager usbManager = null;
    private UsbDevice targetDevice = null;
    private UsbDeviceConnection targetConnection = null;
    private UsbInterface usbInterface = null;

    private UsbEndpoint mBulkInPoint = null;
    private UsbEndpoint mBulkOutPoint = null;
    private int mBulkPacketSize;

    protected final Object ReadQueueLock = new Object();
    protected final Object WriteQueueLock = new Object();

    private UsbReadThread mReadThread;


    private int readSpeed = 0;

    private final int readNumBytes = 1024 * 10;
    private byte[] usbData;

    private final int WriteTimeOutMillis = 5 * 1000;
    private final int ReadTimeOutMillis = 5 * 1000;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);
        context = this;

    }

    //获取机器列表并进行处理
    private void getUsbDevice() {
        //获取目标usb设备
        usbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceMap = usbManager.getDeviceList();
        Iterator<UsbDevice> iterator = deviceMap.values().iterator();
        while(iterator.hasNext()) {
            UsbDevice device = iterator.next();
            if (device.getVendorId() == targetDeviceVendorId && device.getProductId() == targetDeviceProductId) {
                targetDevice = device;
                break;
            }
        }

        if (targetDevice == null) {
            findDeviceError();
            return;
        }
        //获取使用权限
        if (!usbManager.hasPermission(targetDevice)) {
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            context.registerReceiver(mUsbPermissionReceiver, filter);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
            usbManager.requestPermission(targetDevice, pi);
        } else {
            initUsbDevice();
        }
    }

    private BroadcastReceiver mUsbPermissionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                boolean granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                if (granted) {
                    //获得了usb使用权限
                    Log.i(LOGTAG, "获得了usb使用权限");
                    initUsbDevice();
                } else {
                    Log.i(LOGTAG, "用户不允许USB访问设备");
                }
            }
        }
    };

    private void initUsbDevice() {
        int interfaceCount = targetDevice.getInterfaceCount();
        usbInterface = null;
        for (int i = 0; i < interfaceCount; i++) {
            usbInterface = targetDevice.getInterface(i);
            if (usbInterface.getInterfaceClass() == UsbConstants.USB_CLASS_PRINTER) {
                break;
            }
        }
        if (usbInterface != null) {
            //获取UsbDeviceConnection
            targetConnection = usbManager.openDevice(targetDevice);
            if (targetConnection != null) {
                if (targetConnection.claimInterface(usbInterface, true)) {
                    for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                        UsbEndpoint endpoint = usbInterface.getEndpoint(j);
                        //类型为大块传输
                        if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                            if (endpoint.getDirection() == UsbConstants.USB_DIR_OUT) {
                                mBulkOutPoint = endpoint;
                            } else {
                                mBulkInPoint = endpoint;
                            }
                            mBulkPacketSize = endpoint.getMaxPacketSize();
                        }
                    }
                    usbData = new byte[readNumBytes];
                    startReadThread();
                    connectDeviceSuccess();
                }
            }
        }
    }




    //启动读取线程
    private void startReadThread() {
        if (mReadThread != null && mReadThread.isAlive()) {

        } else {
            mReadThread = new UsbReadThread();
            mReadThread.start();
        }
    }

    private class UsbReadThread extends Thread {
        @Override
        public void run() {
            try {
                while (isConnected()) {
                    if (isInterrupted()) {
                        break;
                    }
                    usbRead();
                    Thread.sleep(readSpeed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isConnected() {
        return targetConnection != null && mBulkInPoint != null && mBulkOutPoint != null;
    }

    //读取线程 进行数据拼接解析
    private int usbRead() {
        synchronized (ReadQueueLock) {
            int numBytesRead = targetConnection.bulkTransfer(mBulkInPoint, usbData, readNumBytes, ReadTimeOutMillis);
            if (numBytesRead > 0) {
                //反馈的机器数据
                String readStr = StringUtil.bytes2HexStr(usbData, numBytesRead);

            }
        }
        return 0;
    }


    private int usbWrite(String hexString) {
        Log.i(LOGTAG, "write = " + hexString);
        return usbWrite(StringUtil.hexStr2Bytes(hexString));
    }

    //return 0:写入错误；0>:写入成功
    private int usbWrite(byte[] command) {
        if (command != null && command.length > 0) {
            if (!isConnected()) {
                return 0;
            }
            int offset = 0;//记录已发送的长度
            int HasWritten;
            int odd_len = command.length;

            while (offset < command.length) {
                synchronized (WriteQueueLock) {
                    int mLen = Math.min(odd_len, mBulkPacketSize);
                    byte[] arrayOfByte = new byte[mLen];
                    if (offset == 0) {
                        System.arraycopy(command, 0, arrayOfByte, 0, mLen);
                    } else {
                        System.arraycopy(command, offset, arrayOfByte, 0, mLen);
                    }
                    HasWritten = targetConnection.bulkTransfer(mBulkOutPoint, arrayOfByte, mLen, WriteTimeOutMillis);
                    if (HasWritten < 0) {
                        return 0;
                    } else {
                        offset += HasWritten;
                        odd_len -= HasWritten;
                    }
                }
            }
            return offset;
        } else {
            return 0;
        }
    }

    //关闭连接
    public synchronized void closeDevice() {
        if (targetConnection != null) {
            if (usbInterface != null) {
                targetConnection.releaseInterface(usbInterface);
                usbInterface = null;
            }
            targetConnection.close();
            targetConnection = null;
        }
        mBulkInPoint = null;
        mBulkOutPoint = null;
    }


    //搜索机器失败
    private void findDeviceError(){

    }

    //机器连接成功
    private void connectDeviceSuccess(){

    }

}
