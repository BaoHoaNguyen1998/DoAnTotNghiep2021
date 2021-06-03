package com.iuh.ABCStore.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static String getBaseURL(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);
		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		if (url.toString().endsWith("/")) {
			url.append("/");
		}
		return url.toString();
	}

	
	
	public static String getIP4() {
		
		try {
	        Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();  // gets All networkInterfaces of your device
	        while (networkInterfaces.hasMoreElements()) {
	            NetworkInterface inet = (NetworkInterface) networkInterfaces.nextElement();
	            Enumeration address = inet.getInetAddresses();
	            while (address.hasMoreElements()) {
	                InetAddress inetAddress = (InetAddress) address.nextElement();
	                if (inetAddress.isSiteLocalAddress()) {
	                    return inetAddress.getHostAddress();  /// gives ip address of your device
	                }
	            }
	        }
	    } catch (Exception e) {
	        // Handle Exception
	    	e.getStackTrace();
	    }
		return null;
		
	}
	public static String urlID() {
		InetAddress inetAddress;
		String id="";
		try {
			inetAddress = InetAddress.getLocalHost();
			id=inetAddress.getHostAddress();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	
	}
}
