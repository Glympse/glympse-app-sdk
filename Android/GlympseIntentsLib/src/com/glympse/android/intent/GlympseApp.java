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
     * Interface to track Glympse sending status.
     */
    public interface StatusListener
    {
        void glympseDoneSending(GlympseCallbackParams params);
        void glympseFailedToCreate(GlympseCallbackParams params);
    }

    /**
     * Interface to listen to Glympse events.
     */
    public interface EventsListener
    {
        void glympseCreating(GlympseCallbackParams params);
        void glympseCreated(GlympseCallbackParams params);
        void glympseDurationChanged(GlympseCallbackParams params);
    }

    /**
     * Returns true if we can either create a Glympse or install Glympse on this system.
     */
    public static boolean canCreateGlympse(Context context)
    {
        return Helpers.isIntentAvailable(context, getRawCreateGlympseIntent()) ||
               Helpers.isIntentAvailable(context, getRawInstallGlympseIntent());
    }

    /**
     * Return true if we can either view a Glympse or install Glympse on this system.
     * The includeWeb flag will use the Glympse app if it is installed and then
     * check to see if we can view this in the web.
     */
    public static boolean canViewGlympse(Context context, boolean includeWeb)
    {
        return Helpers.isIntentAvailable(context, getRawViewGlympseInAppIntent()) ||
               (includeWeb && Helpers.isIntentAvailable(context, getRawViewGlympseInWebIntent())) ||
               Helpers.isIntentAvailable(context, getRawInstallGlympseIntent());
    }

    /**
     * Launches the "create a glympse" activity.
     */
    public static boolean createGlympse(Context context, CreateGlympseParams params)
    {
        // Prepare intent.
        Intent intent = getCreateGlympseIntent(context, params);
        if (null != intent)
        {
            // Launch the activity.
            try
            {
                context.startActivity(intent);
                return true;
            }
            catch (Throwable e)
            {
            }
        }
        return false;
    }

    /**
     * Returns an Intent that can launch the "create a glympse" activity.
     */
    public static Intent getCreateGlympseIntent(Context context, CreateGlympseParams params)
    {
        // Make sure we were passed a create glympse params and that it looks valid.
        if ((null != params) && params.isValid())
        {
            // Build the create glympse Intent and make sure it is available.
            Intent intent = getRawCreateGlympseIntent();
            if (Helpers.isIntentAvailable(context, intent))
            {
                // Transfer the information from the params to the Intent.
                params.populateIntent(context, intent);
                return intent;
            }

            // If we failed to find the view glympse Intent on this system,
            // then use the install Glympse Intent instead.
            intent = getRawInstallGlympseIntent();
            return Helpers.isIntentAvailable(context, intent) ? intent : null;
        }

        return null;
    }

    /**
     * Launches the "view a glympse" activity.
     */
    public static boolean viewGlympse(Context context, boolean includeWeb, ViewGlympseParams params)
    {
        Intent intent = getViewGlympseIntent(context, includeWeb, params);
        if (null != intent)
        {
            // Launch the activity.
            try
            {
                context.startActivity(intent);
                return true;
            }
            catch (Throwable e)
            {
            }
        }
        return false;
    }

    /**
     * Returns an Intent that can launch the "view a glympse" activity.
     */
    public static Intent getViewGlympseIntent(Context context, boolean includeWeb, ViewGlympseParams params)
    {
        // Make sure we were passed a view glympse params and that it looks valid.
        if ((null != params) && params.isValid())
        {
            // Build the view glympse in app Intent and make sure it is available.
            Intent intent = getRawViewGlympseInAppIntent();
            if (Helpers.isIntentAvailable(context, intent))
            {
                // Transfer the information from the params to the Intent.
                params.populateIntentForApp(intent);
                return intent;
            }

            if (includeWeb)
            {
                // Build the view glympse in web Intent and make sure it is available.
                intent = getRawViewGlympseInWebIntent();
                if (Helpers.isIntentAvailable(context, intent))
                {
                    // Transfer the information from the params to the Intent.
                    params.populateIntentForWeb(intent);
                    return intent;
                }
            }

            // If we failed to find the create glympse Intent on this system,
            // then use the install Glympse Intent instead.
            intent = getRawInstallGlympseIntent();
            return Helpers.isIntentAvailable(context, intent) ? intent : null;
        }

        return null;
    }

    /**
     * Processes the return Intent from the "create a glympse" activity.
     */
    public static GlympseCallbackParams getCreateResult(Intent intent)
    {
        return new GlympseCallbackParams(intent);
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
    private static Intent getRawInstallGlympseIntent()
    {
        //return new Intent(Intent.ACTION_VIEW, Uri.parse("samsungapps://ProductDetail/com.glympse.android.glympse")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.glympse.android.glympse"));
    }

    /**
     * Helper function to get the raw "create a glympse" Intent.
     */
    private static Intent getRawCreateGlympseIntent()
    {
        return new Intent(Common.ACTION_GLYMPSE_CREATE);
    }

    /**
     * Helper function to get the raw "view a glympse in app" Intent.
     */
    private static Intent getRawViewGlympseInAppIntent()
    {
        return new Intent(Common.ACTION_GLYMPSE_VIEW);
    }

    /**
     * Helper function to get the raw "view a glympse in app" Intent.
     */
    private static Intent getRawViewGlympseInWebIntent()
    {
        return new Intent(Intent.ACTION_VIEW, UriParser.URI_SAMPLE);
    }
}
