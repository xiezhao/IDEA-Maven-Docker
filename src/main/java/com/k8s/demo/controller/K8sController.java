package com.k8s.demo.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.*;
import java.util.List;

@RestController
public class K8sController {

    public static String mac = "";

    @GetMapping("/mac")
    public String getIps() throws Exception {
        return getMAC();
    }


    @GetMapping("/host")
    public String gerVersion() throws Exception {
        InetAddress ia = InetAddress.getLocalHost();
        String host = ia.getHostName();//获取计算机主机名
        return host;
    }

    @GetMapping("/balance")
    public String getBalance() throws Exception {
        System.out.println("-------------------------------");
        return "success";
    }


    public static void main(String[] args) throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
        String host = ia.getHostName();//获取计算机主机名
        System.out.println(host);
    }

    public static String getMAC() throws SocketException {
        if (!StringUtils.isEmpty(K8sController.mac)) return K8sController.mac;
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while(en.hasMoreElements()){
            NetworkInterface iface = en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            StringBuilder sb = new StringBuilder();
            for(InterfaceAddress addr : addrs) {
                InetAddress  ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if(network==null){
                    continue;
                } else {
                    byte[] mac = network.getHardwareAddress();
                    if(mac==null) {
                        continue;
                    } else{
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        K8sController.mac = sb.toString();
                        break;
                    }
                }
            }
        }
        return K8sController.mac;
    }
}
