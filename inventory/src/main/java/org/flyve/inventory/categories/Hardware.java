/**
 *  LICENSE
 * 
 *  This file is part of Flyve MDM Inventory Library for Android.
 *
 *  Inventory Library for Android is a subproject of Flyve MDM.
 *  Flyve MDM is a mobile device management software.
 * 
 *  Flyve MDM is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 3
 *  of the License, or (at your option) any later version.
 *
 *  Flyve MDM is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  ---------------------------------------------------------------------
 *  @author    Rafael Hernandez - <rhernandez@teclib.com>
 *  @author    Ivan del Pino    - <idelpino@teclib.com>
 *  @copyright Copyright Teclib. All rights reserved.
 *  @copyright Copyright FusionInventory.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.FlyveLog;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * This class get all the information of the Environment
 */
public class Hardware extends Categories {

    /*
     * The serialization runtime associates with each serializable class a version number,
     * called a serialVersionUID, which is used during deserialization to verify that the sender
     * and receiver of a serialized object have loaded classes for that object that are compatible
     * with respect to serialization. If the receiver has loaded a class for the object that has a
     * different serialVersionUID than that of the corresponding sender's class, then deserialization
     * will result in an  InvalidClassException
     *
     *  from: https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     */
    private static final long serialVersionUID = 3528873342443549732L;

//    <!ELEMENT HARDWARE (USERID, OSVERSION, PROCESSORN, OSCOMMENTS,
//                        NAME, PROCESSORS, SWAP, ETIME, TYPE, OSNAME, IPADDR,
//                        WORKGROUP, DESCRIPTION, MEMORY, UUID, DNS, LASTLOGGEDUSER,
//                        USERDOMAIN, DATELASTLOGGEDUSER, DEFAULTGATEWAY, VMSYSTEM, WINOWNER,
//                        WINPRODID, WINPRODKEY, WINCOMPANY, WINLANG, CHASSIS_TYPE, VMNAME,
//                        VMHOSTSERIAL, ARCHNAME)>

    private Properties props;
    private Context context;
    private static final String OSNAME = "Android";
    private ArrayList<String> userInfo = new ArrayList<>();

    /**
     * Indicates whether some other object is "equal to" this one
     * @param Object obj the reference object with which to compare
     * @return boolean true if the object is the same as the one given in argument
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && (!super.equals(obj));
    }

    /**
     * Returns a hash code value for the object
     * @return int a hash code value for the object
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + (this.context != null ? this.context.hashCode() : 0);
        hash = 89 * hash + (this.props != null ? this.props.hashCode() : 0);
        hash = 89 * hash + (this.userInfo != null ? this.userInfo.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the context and the Hardware information
     * @param xCtx Context where this class work
     */
    public Hardware(Context xCtx) {
        super(xCtx);

        this.context = xCtx;

        props = System.getProperties();

        try {
            getUserInfo();

            Category c = new Category("HARDWARE", "hardware");

            c.put("DATELASTLOGGEDUSER", new CategoryValue(getDateLastLoggedUser(), "DATELASTLOGGEDUSER", "dateLastLoggedUser"));
            c.put("LASTLOGGEDUSER", new CategoryValue(getLastLoggedUser(), "LASTLOGGEDUSER", "lastLoggedUser"));
            c.put("NAME", new CategoryValue(getName(), "NAME", "name"));
            c.put("OSNAME", new CategoryValue(OSNAME, "OSNAME", "osName"));
            c.put("OSVERSION", new CategoryValue(getOsVersion(), "OSVERSION", "osVersion"));
            c.put("ARCHNAME", new CategoryValue(getArchName(), "ARCHNAME", "archName"));
            c.put("UUID", new CategoryValue(getUUID(), "UUID", "uuid"));
            c.put("USERID", new CategoryValue(getUserId(), "USERID", "userid"));
            c.put("MEMORY", new CategoryValue(new Memory(xCtx).getCapacity(), "MEMORY", "memory"));

            this.add(c);
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE, ex.getMessage()));
        }
    }

    /**
     * Get the if of the user logged
     * @return string id user logged
     */
    public String getUserId() {
        return getUserTagValue("id");
    }

    /**
     * Get the date of the last time the user logged
     * @return string the date in simple format
     */
    public String getDateLastLoggedUser() {
        String value = "N/A";
        try {
            String lastLoggedIn = getUserTagValue("lastLoggedIn");
            if (!"N/A".equals(lastLoggedIn)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm", Locale.getDefault());
                Date resultDate = new Date(Long.parseLong(lastLoggedIn));
                value = sdf.format(resultDate);
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_DATE_LAST_LOGGED_USER, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the name of the last logged user
     * @return string the user name
     */
    public String getLastLoggedUser() {
        String value = "N/A";
        try {
            if (userInfo.size() > 0) {
                DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                String info = userInfo.get(2).trim();
                Document parse = newDocumentBuilder.parse(new ByteArrayInputStream(info.getBytes()));
                value = parse.getFirstChild().getTextContent();
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_LAST_LOGGED_USER, ex.getMessage()));
        }
        return value;
    }

    private String getUserTagValue(String tagName) {
        String evaluate = "";
        String value = "N/A";
        try {
            if (userInfo.size() > 0) {
                String removeChar = userInfo.get(1).replaceAll("[\"><]", "");
                if (removeChar.contains("user ")) {
                    evaluate = removeChar.replaceAll(" ", ",").trim();
                }
                String[] splitValues = evaluate.split(",");
                Map<String, String> results = new HashMap<>();
                for (String a : splitValues) {
                    if (a.contains("=")) {
                        String[] keyValues = a.split("=", 2);
                        String key = keyValues[0];
                        String valueResult = keyValues[1];
                        results.put(key, valueResult);
                    }
                }
                return results.get(tagName) == null ? "N/A" : results.get(tagName);
            } else {
                return value;
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_USER_TAG, ex.getMessage()));
        }
        return value;
    }

    private void getUserInfo() {
        userInfo = new ArrayList<>();
        try {
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("cat /data/system/users/0.xml\n");
            dos.writeBytes("exit\n");
            dos.flush();
            dos.close();
            p.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String temp;
            while ((temp = in.readLine()) != null) {
                userInfo.add(temp);
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_USER_INFO, ex.getMessage()));
        }
    }

    /**
     * Get the hardware name
     * @return string with the model
     */
    public String getName() {
        String value = "N/A";
        try {
            value = Build.MODEL;
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_NAME, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the OS version
     * @return string the version
     */
    public String getOsVersion() {
        String value = "N/A";
        try {
            value = Build.VERSION.RELEASE;
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_VERSION, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the name of the architecture
     * @return string the OS architecture
     */
    public String getArchName() {
        String value = "N/A";
        try {
            value = props.getProperty("os.arch");
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_ARCH_NAME, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the Universal Unique Identifier (UUID)
     * @return string the Android ID
     */
    public String getUUID() {
        String value = "N/A";
        try {
            value = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.HARDWARE_UUID, ex.getMessage()));
        }
        return value;
    }

}
