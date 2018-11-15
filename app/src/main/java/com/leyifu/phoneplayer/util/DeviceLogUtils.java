package com.leyifu.phoneplayer.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by hahaha on 2018/11/13 0013.
 */

public class DeviceLogUtils {

    public static final String TAG = "DeviceLogUtils";

    public static void initDevice(Context ctx) {

        String androidID = DeviceUtils.getAndroidID(ctx);
        Log.d(TAG, "androidID: " + androidID);

        String imei = DeviceUtils.getIMEI(ctx);
        Log.d(TAG, "imei: " + imei);

        String wifiMacAddr = DeviceUtils.getWifiMacAddr(ctx);
        Log.d(TAG, "wifiMacAddr: " + wifiMacAddr);

        String ip = DeviceUtils.getIP(ctx);
        Log.d(TAG, "ip: " + ip);

        String serial = DeviceUtils.getSerial();
        Log.d(TAG, "serial: " + serial);

//        String simSerial = DeviceUtils.getSIMSerial(ctx);
//        Log.d(TAG, "simSerial: " + simSerial);

        String mnc = DeviceUtils.getMNC(ctx);
        Log.d(TAG, "mnc: " + mnc);

        String carrier = DeviceUtils.getCarrier(ctx);
        Log.d(TAG, "carrier: " + carrier);

        String manufacturer = DeviceUtils.getManufacturer();
        Log.d(TAG, "manufacturer: " + manufacturer);

        String bootloader = DeviceUtils.getBootloader();
        Log.d(TAG, "bootloader: " + bootloader);

        String screenDisplayID = DeviceUtils.getScreenDisplayID(ctx);
        Log.d(TAG, "screenDisplayID: " + screenDisplayID);

        String displayVersion = DeviceUtils.getDisplayVersion();
        Log.d(TAG, "displayVersion: " + displayVersion);

        String language = DeviceUtils.getLanguage();
        Log.d(TAG, "language: " + language);

        String country = DeviceUtils.getCountry(ctx);
        Log.d(TAG, "country: " + country);

        String osVersion = DeviceUtils.getOSVersion();
        Log.d(TAG, "osVersion: " + osVersion);

//        String gsfid = DeviceUtils.getGSFID(ctx);
//        Log.d(TAG, "gsfid: " + gsfid);

//        String bluetoothMAC = DeviceUtils.getBluetoothMAC(ctx);
//        Log.d(TAG, "bluetoothMAC: " + bluetoothMAC);

        String psuedoUniqueID = DeviceUtils.getPsuedoUniqueID();
        Log.d(TAG, "psuedoUniqueID: " + psuedoUniqueID);

    }
}
