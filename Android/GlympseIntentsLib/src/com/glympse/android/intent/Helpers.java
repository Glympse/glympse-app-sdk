//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class Helpers 
{
    /**
     * Helper function to check if an Intent is available on the system.
     */
    public static boolean isIntentAvailable(Context context, Intent intent)
    {
        try
        {
            final PackageManager packageManager = context.getPackageManager();
            return (packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0);
        }
        catch (Throwable e)
        {
        }
        return false;
    }    
    
    /**
     * Helper function to check if the string is empty.  
     */
    public static boolean isEmpty(String str)
    {
        return ((null == str) || (str.length() <= 0));
    }
    
    /**
     * Helper to extract string array from a bundle by a given key. 
     */
    public static String[] getStringArray(Bundle bundle, String key)
    {
        if ((null != bundle) && !isEmpty(key))
        {
            Object obj = bundle.get(key);
            if (obj instanceof String[])
            {
                return (String[])obj;
            }
            if (obj instanceof List<?>)
            {
                try
                {
                    List<?> list = (List<?>)obj;
                    return list.toArray(new String[list.size()]);
                }
                catch (Throwable e)
                {
                }
            }
            if (obj instanceof String)
            {
                return new String[] { (String)obj };
            }
        }
        return null;
    }    
}
