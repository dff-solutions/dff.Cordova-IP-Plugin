package de.dff.cordova.plugins;

import java.io.*;
import java.net.*;
import java.util.*;   
 
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.CordovaResourceApi.OpenForReadResult;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import org.apache.http.conn.util.InetAddressUtils;

import android.util.Log;

/**
 * IP Address plugin
 * @author dff
 */
public class IpAddress extends CordovaPlugin {
 
	@Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    	try {		
			String ipAddress = getIPAddress(true);
			callbackContext.success(ipAddress);
            
            return true;
		}
		catch (Exception ex) {
			callbackContext.error(ex.getMessage());
            
            return false;
		}
        
    }

    /**
     * Get IP-Address from first non-localhost interface
     * @param useIPv4  true = return IPv4, false = return IPv6
     * @return  Address or empty string
     */
    public static String getIPAddress(boolean useIPv4) throws Exception {
		List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
		for (NetworkInterface intf : interfaces) {
			List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
			for (InetAddress addr : addrs) {
				if (!addr.isLoopbackAddress()) {
					String sAddr = addr.getHostAddress().toUpperCase();
					boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
					if (useIPv4) {
						if (isIPv4) 
							return sAddr;
					} else {
						if (!isIPv4) {
							int delim = sAddr.indexOf('%'); 
							return delim<0 ? sAddr : sAddr.substring(0, delim);
						}
					}
				}
			}
		}
		throw new Exception("No Networkinterfaces or only LoopbackAddresses found.");		
    }
}

