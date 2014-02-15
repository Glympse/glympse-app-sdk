//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

public class GlympseApp
{
    /**
     * Interface to track Glympse sending status.
     */
    public interface StatusListener
    {
        public void glympseDoneSending(GlympseCallbackParams params);
        public void glympseFailedToCreate(GlympseCallbackParams params);
    }

    /**
     * Interface to listen to Glympse events.
     */
    public interface EventsListener
    {
        public void glympseCreating(GlympseCallbackParams params);
        public void glympseCreated(GlympseCallbackParams params);
        public void glympseDurationChanged(GlympseCallbackParams params);
    }

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
     * Launches the "create a glympse" activity.
     */
    public static void createGlympse(Context context, CreateGlympseParams params, StatusListener status)
    {
        createGlympse(context, params, status, null);
    }

    /**
     * Launches the "create a glympse" activity.
     */
    public static void createGlympse(Context context, CreateGlympseParams params, StatusListener status, EventsListener events)
    {
        // Prepare intent.
        Intent intent = getCreateGlympseIntent(context, params, status, events);
        if (null != intent)
        {
            // Start listening to callback intents.
            String callbackAction = params.getCallbackAction();
            if ( !Helpers.isEmpty(callbackAction) )
            {
                BroadcastReceiver receiver = new GlympseIntentsReceiver(status, events);
                IntentFilter filter = new IntentFilter(callbackAction);
                context.getApplicationContext().registerReceiver(receiver, filter);
            }

            // Launch the  activity.
            context.startActivity(intent);
        }
    }

    /**
     * Returns an Intent that can launch the "create a glympse" activity.
     */
    public static Intent getCreateGlympseIntent(Context context, CreateGlympseParams params, StatusListener statusListener, EventsListener events)
    {
        // Enable GLympse events if corresponding listener is specified.
        params.setEvents(events != null);

        // Make sure we were passed a create glympse params and that it looks valid.
        if ((null != params) && params.isValid())
        {
            // Build the create glympse Intent and make sure it is available.
            Intent intent = getCreateGlympseIntent();
            if (Helpers.isIntentAvailable(context, intent))
            {
                // Configure callback mechanism.
                params.setCallback(context);

                // Transfer the information from the params to the Intent.
                params.populateIntent(intent);
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
     * Launches the "view a glympse" activity.
     */
    public static void viewGlympse(Context context, boolean includeWeb, ViewGlympseParams params)
    {
        Intent intent = getViewGlympseIntent(context, includeWeb, params);
        if (null != intent)
        {
            context.startActivity(intent);
        }
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
            Intent intent = getViewGlympseInAppIntent();
            if (Helpers.isIntentAvailable(context, intent))
            {
                // Transfer the information from the params to the Intent.
                params.populateIntentForApp(intent);
                return intent;
            }

            if (includeWeb)
            {
                // Build the view glympse in web Intent and make sure it is available.
                intent = getViewGlympseInWebIntent();
                if (Helpers.isIntentAvailable(context, intent))
                {
                    // Transfer the information from the params to the Intent.
                    params.populateIntentForWeb(intent);
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

    /**
     * Class to listen to Glympse application callback intents.
     */
    private static class GlympseIntentsReceiver extends BroadcastReceiver
    {
        private StatusListener _status;
        private EventsListener _events;

        public GlympseIntentsReceiver(StatusListener status, EventsListener events)
        {
            _status = status;
            _events = events;
        }

        @Override public void onReceive(Context context, Intent intent)
        {
            GlympseCallbackParams params = new GlympseCallbackParams(intent);
            String event = params.getEvent();
            if ( Helpers.isEmpty(event) )
            {
                return;
            }

            if ( Common.GLYMPSE_EVENT_CREATING.equals(event) )
            {
                if ( null != _events )
                {
                    _events.glympseCreating(params);
                }
            }
            else if ( Common.GLYMPSE_EVENT_CREATED.equals(event) )
            {
                if ( null != _events )
                {
                    _events.glympseCreated(params);
                }
            }
            else if ( Common.GLYMPSE_EVENT_FAILED_TO_CREATE.equals(event) )
            {
                if ( null != _status )
                {
                    _status.glympseFailedToCreate(params);
                }
                context.unregisterReceiver(this);
            }
            else if ( Common.GLYMPSE_EVENT_DONE_SENDING.equals(event) )
            {
                if ( null != _status )
                {
                    _status.glympseDoneSending(params);
                }
                if ( null == _events )
                {
                    context.unregisterReceiver(this);
                }
            }
            else if ( Common.GLYMPSE_EVENT_DURATION_CHANGED.equals(event) )
            {
                if ( null != _events )
                {
                    _events.glympseDurationChanged(params);
                }
                if ( params.getRemaining() <= 0 )
                {
                    context.unregisterReceiver(this);
                }
            }
        }
    }
}
