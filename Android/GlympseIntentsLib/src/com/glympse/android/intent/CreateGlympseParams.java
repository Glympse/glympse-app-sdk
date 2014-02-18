//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import java.util.LinkedList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.glympse.android.intent.GlympseApp.EventsListener;
import com.glympse.android.intent.GlympseApp.StatusListener;

public class CreateGlympseParams
{
    private long           _flags;
    private Recipient[]    _recipients;
    private int            _duration = -1;
    private String         _message;
    private Place          _destination;
    private String         _intentContext;
    private String         _initialNickname;
    private String         _initialAvatar;
    private StatusListener _statusListener;
    private EventsListener _eventsListener;

    /**
     * Sets the flags. See the FLAG_* values.
     */
    public void setFlags(long flags)
    {
        _flags = flags;
    }
    
    /**
     * Gets the flags. See the FLAG_* values.
     */
    public long getFlags()
    {
        return _flags;
    }    

    /**
     * Sets the status listener that is used to return the resulting Glympse
     * information once the Glympse is created and sent. If no StatusListener
     * is passed in, then Glympse will return the Glympse creation information
     * in the return Intent from the Glympse activity. It is highly recommended
     * to pass in a StatusListener since creating and sending a Glympse is an
     * asynchronous operation and might not complete by the time the activity
     * returns to your process. If no StatusListener is used and the user backs
     * out of the Glympse UI before the Glympse can be fully created sent, then
     * there is no way for your application to access the created Glympse.
     */
    public void setStatusListener(StatusListener statusListener)
    {
        _statusListener = statusListener;
    }

    /**
     * Sets the listener for extended events. This is only needed if
     * you are interested in receiving ongoing events about the state
     * of the Glympse.
     */
    public void setEventsListener(EventsListener eventsListener)
    {
        _eventsListener = eventsListener;
    }

    /**
     * Returns the currently set StatusListener.
     */
    public StatusListener getStatusListener()
    {
        return _statusListener;
    }

    /**
     * Returns the currently set EventsListener.
     */
    public EventsListener getEventsListener()
    {
        return _eventsListener;
    }

    /**
     * Sets the initial recipients to send the Glymspe to.
     */
    public void setRecipients(Recipient[] recipients)
    {
        _recipients = recipients;
    }

    /**
     * Sets an initial recipient to send the Glymspe to.
     */
    public void setRecipient(Recipient recipient)
    {
        setRecipients(new Recipient[] { recipient } );
    }

    /**
     * Sets the initial duration for this Glympse. If not set, the Glympse
     * application will use its default duration.
     */
    public void setDuration(int duration)
    {
        _duration = duration;
    }

    /**
     * Sets the initial message for this Glympse.
     */
    public void setMessage(String message)
    {
        _message = message;
    }

    /**
     * Sets the initial destination for this Glympse.
     */
    public void setDestination(Place destination)
    {
        _destination = destination;
    }

    /**
     * Sets the initial destination for this Glympse.
     */
    public void setContext(String intentContext)
    {
        _intentContext = intentContext;
    }

    /**
     * Sets nickname for the user if it isn't already set.
     */
    public void setInitialNickname(String nickname)
    {
        _initialNickname = nickname;
    }

    /**
     * Sets avatar for the user if it isn't already set.
     */
    public void setInitialAvatar(String avatarUri)
    {
        _initialAvatar = avatarUri;
    }

    /**
     * Helper function to check if this object contains valid data.
     */
    protected boolean isValid()
    {
        return true;
    }

    /**
     * Helper function to transfer the data from this class to an Intent.
     */
    protected void populateIntent(Context context, Intent intent)
    {
        // Pass package name in "source" argument. 
        intent.putExtra(Common.EXTRA_GLYMPSE_SOURCE, context.getPackageName());
        
        long flags = _flags |
            ((null != _eventsListener) ? Common.FLAG_ENABLE_EVENTS : 0);

        // Copy over the flags if any are set.
        if (0 != flags)
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_FLAGS, flags);
        }

        // Copy over any recipients.
        if (null != _recipients)
        {
            LinkedList<String> recipientJsons = new LinkedList<String>();
            for (Recipient recipient : _recipients)
            {
                if (recipient.isValid())
                {
                    recipientJsons.add(recipient.toString());
                }
            }
            if (!recipientJsons.isEmpty())
            {
                String[] recipientsArray = new String[recipientJsons.size()];
                intent.putExtra(Common.EXTRA_GLYMPSE_RECIPIENTS, recipientJsons.toArray(recipientsArray));
            }
        }

        // Copy over the duration if it is set.
        if (_duration >= 0)
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_DURATION, (long)_duration);
        }

        // Copy over the message if it is set.
        if ((null != _message) && !Helpers.isEmpty(_message))
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_MESSAGE, _message);
        }

        // Copy over the destination if it is set and valid.
        if ((null != _destination) && _destination.isValid())
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_DESTINATION, _destination.toString());
        }

        // Copy over the context if it is set.
        if (!Helpers.isEmpty(_intentContext))
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_CONTEXT, _intentContext);
        }

        // Copy over nickname if it is set.
        if (!Helpers.isEmpty(_initialNickname))
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_INITIAL_NICKNAME, _initialNickname);
        }

        // Copy over avatar if it is set.
        if (!Helpers.isEmpty(_initialAvatar))
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_INITIAL_AVATAR, _initialAvatar);
        }

        // Copy over callback package.
        if (null != context)
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_CALLBACK_PACKAGE, context.getPackageName());
        }

        // Copy over callback action if needed.
        if ((null != _statusListener) || (null != _eventsListener))
        {
            // Build the action string to use for our callback.
            String callbackAction = Common.ACTION_GLYMPSE_CALLBACK + "_" + this.hashCode();

            // Set this in the intent so Glympse knows how to call us back.
            intent.putExtra(Common.EXTRA_GLYMPSE_CALLBACK_ACTION, callbackAction);

            // Register a broadcast listener to capture the return intents.
            BroadcastReceiver receiver = new GlympseIntentsReceiver(_statusListener, _eventsListener);
            IntentFilter filter = new IntentFilter(callbackAction);
            context.getApplicationContext().registerReceiver(receiver, filter);
        }
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
