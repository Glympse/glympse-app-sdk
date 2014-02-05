//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GlympseApp 
{
    /**
     * Returns true if we can either create a Glympse or install Glympse on this system.
     */
    public static boolean canCreateGlympse(Context context)
    {
        return Helpers.isIntentAvailable(context, getCreateGlympseIntent()) ||
               Helpers.isIntentAvailable(context, getInstallGlympseIntent());
    }

    /**
     * Return true if we can either view a Glympse or install Glympse on this system.
     * The includeWeb flag will use the Glympse app if it is installed and then
     * check to see if we can view this in the web.
     */
    public static boolean canViewGlympse(Context context, boolean includeWeb)
    {
        return Helpers.isIntentAvailable(context, getViewGlympseInAppIntent()) ||
               (includeWeb && Helpers.isIntentAvailable(context, getViewGlympseInWebIntent())) ||
               Helpers.isIntentAvailable(context, getInstallGlympseIntent());
    }
    
    /**
     * Returns an Intent that can launch the "create a glympse" activity.
     */
    public static Intent createGlympse(Context context, CreateGlympseParams createGlympseParams)
    {
        // Make sure we were passed a create glympse params and that it looks valid.
        if ((null != createGlympseParams) && createGlympseParams.isValid())
        {
            // Build the create glympse Intent and make sure it is available.
            Intent intent = getCreateGlympseIntent();
            if (Helpers.isIntentAvailable(context, intent))
            {
                // Transfer the information from the params to the Intent.
                createGlympseParams.populateIntent(intent);
                return intent;
            }
            
            // If we failed to find the view glympse Intent on this system,
            // then use the install Glympse Intent instead.
            intent = getInstallGlympseIntent();
            return Helpers.isIntentAvailable(context, intent) ? intent : null;
        }
        
        return null;
    }
    
    /**
     * Returns an Intent that can launch the "view a glympse" activity.
     */
    public static Intent viewGlympse(Context context, boolean includeWeb, ViewGlympseParams viewGlympseParams)
    {
        // Make sure we were passed a view glympse params and that it looks valid.
        if ((null != viewGlympseParams) && viewGlympseParams.isValid())
        {
            // Build the view glympse in app Intent and make sure it is available.
            Intent intent = getViewGlympseInAppIntent();
            if (Helpers.isIntentAvailable(context, intent))
            {
                // Transfer the information from the params to the Intent.
                viewGlympseParams.populateIntentForApp(intent);
                return intent;
            }
            
            if (includeWeb)
            {
                // Build the view glympse in web Intent and make sure it is available.
                intent = getViewGlympseInWebIntent();
                if (Helpers.isIntentAvailable(context, intent))
                {
                    // Transfer the information from the params to the Intent.
                    viewGlympseParams.populateIntentForWeb(intent);
                    return intent;
                }
            }
            
            // If we failed to find the create glympse Intent on this system,
            // then use the install Glympse Intent instead.
            intent = getInstallGlympseIntent();
            return Helpers.isIntentAvailable(context, intent) ? intent : null;
        }
        
        return null;
    }
    
    /**
     * Processes the return Intent from the "create a glympse" activity.
     */ 
    public static CreateGlympseResult getCreateResult(int resultCode, Intent intent)
    {
        return new CreateGlympseResult(resultCode, intent);
    }
    
    /** 
     * Process a text buffer for glympse codes and glympse group names.
     */
    public static UriParser parseBuffer(String buffer)
    {
        return new UriParser(buffer);
    }
    
    /**
     * Helper function to get the raw "Glympse install" Intent.
     */
    public static Intent getInstallGlympseIntent()
    {
        //return new Intent(Intent.ACTION_VIEW, Uri.parse("samsungapps://ProductDetail/com.glympse.android.glympse")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.glympse.android.glympse"));
    }

    /**
     * Helper function to get the raw "create a glympse" Intent.
     */
    public static Intent getCreateGlympseIntent()
    {
        return new Intent(Common.ACTION_GLYMPSE_CREATE);
    }
    
    /**
     * Helper function to get the raw "view a glympse in app" Intent.
     */
    public static Intent getViewGlympseInAppIntent()
    {
        return new Intent(Common.ACTION_GLYMPSE_VIEW);
    }

    /**
     * Helper function to get the raw "view a glympse in app" Intent.
     */
    public static Intent getViewGlympseInWebIntent()
    {
        return new Intent(Intent.ACTION_VIEW, UriParser.URI_SAMPLE);
    }    
}
