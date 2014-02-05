//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import java.util.LinkedList;

import android.content.Intent;

public class CreateGlympseParams
{
    private long        _flags;
    private Recipient[] _recipients;
    private int         _duration = -1;
    private String      _message;
    private Place       _destination;
    private String      _intentContext;
    
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
        if ((null != _intentContext) && !Helpers.isEmpty(_intentContext))
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_CONTEXT, _intentContext);
        }
    }
}
