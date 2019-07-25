package com.wuc.performance.net;


import android.content.Context;
import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import okhttp3.Dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class OkHttpDNS implements Dns {

    private HttpDnsService dnsService;
    private static OkHttpDNS instance = null;

    private OkHttpDNS(Context context) {
        dnsService = HttpDns.getService(context, "");
    }

    public static OkHttpDNS getIns(Context context) {
        if (instance == null) {
            synchronized (OkHttpDNS.class) {
                if (instance == null) {
                    instance = new OkHttpDNS(context);
                }
            }
        }
        return instance;
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        String ip = dnsService.getIpByHostAsync(hostname);
        if(ip != null){
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
            return inetAddresses;
        }
        return Dns.SYSTEM.lookup(hostname);
    }
}
