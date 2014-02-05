//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

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
}
