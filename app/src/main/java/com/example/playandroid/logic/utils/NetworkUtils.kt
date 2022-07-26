package com.example.playandroid.logic.utils

import android.content.Context
import android.net.*
import android.util.Log

/**
 * 网络工具类
 */
object NetworkUtils {
    private const val NETWORK_NO = -1 // no network
    private const val NETWORK_WIFI = 1 // wifi network
    private const val NETWORK_2G = 2 // "2G" networks
    private const val NETWORK_3G = 3 // "3G" networks
    private const val NETWORK_4G = 4 // "4G" networks
    private const val NETWORK_UNKNOWN = 5 // unknown network
    private const val NETWORK_TYPE_GSM = 16
    private const val NETWORK_TYPE_TD_SCDMA = 17
    private const val NETWORK_TYPE_IWLAN = 18

    /**
     * 获取活动网络属性
     *
     * @param context 上下文
     * @return  NetworkCapabilities
     */
    private fun getActiveNetworkCapabilities(context: Context): NetworkCapabilities? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = cm.activeNetwork
        return cm.getNetworkCapabilities(nw)
    }

    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * 判断网络是否连接
     *
     * 需添加权限 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *
     * @param context 上下文
     * @param 'true':是
     *        'false':否
     */
    /* 要先使用NetworkInfo判断，再使用NetworkCapabilities判断。
       如果反过来：可能会导致因模拟器网络属性字段不包含VALIDATED，而
       判断网络不可用（实际可用）而不能展示数据，实机无任何影响，因此此
       举是为了保证功能不变的情况下，模拟器和实机的一致性。
     */
    fun isConnected(context: Context):Boolean{
        val info = getActiveNetworkInfo(context)
        if (info != null) {
            return  info.isConnected
        } else {
            val anc = getActiveNetworkCapabilities(context)
            if (anc != null){
                return anc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        anc.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        }
        return false
    }
}