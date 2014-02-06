//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;

public class CreateGlympseParams
{
    private long        _flags = 0;
    private Recipient[] _recipients;
    private int         _duration = -1;
    private String      _message;
    private Place       _destination;
    private String      _intentContext;
    private String      _callbackPackage;
    private String      _callbackAction;
    private boolean     _events = false;
    private String      _initialNickname;
    private String      _initialAvatar;
    
    /**
     * Sets the flags. See the FLAG_* values.
     */
    public void setFlags(long flags)
    {
        _flags = flags;
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
     * Sets nickname for the user if it isn’t already set.
     */
    public void setInitialNickname(String nickname)
    {
        _initialNickname = nickname;        
    }  
    
    /**
     * Sets avatar for the user if it isn’t already set.
     */
    public void setInitialAvatar(String avatarUri)
    {
        _initialAvatar = avatarUri;        
    }          

    protected void setEvents(boolean events)
    {
        _events = events;
    }    
    
    protected void setCallback(Context context)
    {
        _callbackPackage = context.getPackageName();
        _callbackAction = Common.ACTION_GLYMPSE_CALLBACK + "_" + this.hashCode();
    }    

    protected String getCallbackAction()
    {
        return _callbackAction;
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
    protected void populateIntent(Intent intent)
    {
        // Copy over the brand ID if it is set.
        if (0 != _flags)
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_FLAGS, _flags);
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
        
        // Copy over callback package if it is set.
        if (!Helpers.isEmpty(_callbackPackage))
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_CALLBACK_PACKAGE, _callbackPackage);
        }
        
        // Copy over callback action if it is set.
        if (!Helpers.isEmpty(_callbackAction))
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_CALLBACK_ACTION, _callbackAction);
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
        
        // Copy over events flag. 
        intent.putExtra(Common.EXTRA_GLYMPSE_EVENTS, _events);
    }
}
