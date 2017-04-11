package de.dff.cordova.plugins;

import android.Manifest;
import com.dff.cordova.plugin.common.CommonPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * IP Address plugin
 *
 * @author dff
 */
public class IpAddress extends CommonPlugin {

    private static final String TAG = "IpAddress";
    private static final String[] PERMISSIONS =
        {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE
        };

    private void requestPermissions() {
        for (String permission : PERMISSIONS) {
            CommonPlugin.addPermission(permission);
        }
    }

    @Override
    public void pluginInitialize() {
        super.pluginInitialize();
        requestPermissions();
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        try {
            callbackContext.success(getIPAddress());

            return true;
        } catch (Exception ex) {
            callbackContext.error(ex.getMessage());

            return false;
        }

    }

    /**
     * Get All Networkinterfaces from device
     *
     * @return JSONArray
     */
    public static JSONArray getIPAddress() throws Exception {
        JSONArray jsonInterfaces = new JSONArray();
        JSONArray jsonAddresses;
        JSONObject jsonAddress;
        List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface intf : interfaces) {
            JSONObject jsonInterface = new JSONObject();

            jsonInterface.put("displayName", intf.getDisplayName());
            jsonInterface.put("hardwareAddress", intf.getHardwareAddress());
            jsonInterface.put("name", intf.getName());

            jsonAddresses = new JSONArray();
            List<InetAddress> addrs = Collections.list(intf.getInetAddresses());

            for (InetAddress addr : addrs) {
                if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                    jsonAddress = new JSONObject();
                    jsonAddress.put("hostName", addr.getHostAddress().toUpperCase());
                    jsonAddress.put("isIPv4", true);
                    jsonAddresses.put(jsonAddress);
                } else if (!addr.isLoopbackAddress() && addr instanceof Inet6Address) {
                    jsonAddress = new JSONObject();
                    String sAddr = addr.getHostAddress().toUpperCase();
                    int delim = sAddr.indexOf('%');
                    jsonAddress.put("hostName", delim < 0 ? sAddr : sAddr.substring(0, delim));
                    jsonAddress.put("isIPv4", false);
                    jsonAddresses.put(jsonAddress);
                }
            }

            jsonInterface.put("addresses", jsonAddresses);
            jsonInterfaces.put(jsonInterface);
        }
        return jsonInterfaces;
    }
}

