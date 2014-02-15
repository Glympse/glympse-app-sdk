//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import android.content.Intent;

public class GlympseCallbackParams
{
    protected long        _duration = -1;
    protected long        _remaining = -1;
    protected Recipient[] _recipients;
    protected String      _message;
    protected String      _event;
    protected Place       _destination;
    protected String      _intentContext;

    public GlympseCallbackParams()
    {
    }

    /**
     * Parses the Glympse callback intent.
     */
    public GlympseCallbackParams(Intent intent)
    {
        if (null != intent)
        {
            // Attempt to parse the recipients.
            String[] recipientJsons = intent.getStringArrayExtra(Common.EXTRA_GLYMPSE_RECIPIENTS);
            if ((null != recipientJsons) && (recipientJsons.length > 0))
            {
                _recipients = new Recipient[recipientJsons.length];
                for (int i = 0; i < recipientJsons.length; i++)
                {
                    _recipients[i] = new Recipient(recipientJsons[i]);
                }
            }

            // Parse the duration.
            _duration = intent.getLongExtra(Common.EXTRA_GLYMPSE_DURATION, _duration);

            // Parse the remaining time.
            _remaining = intent.getLongExtra(Common.EXTRA_GLYMPSE_REMAINING, _remaining);

            // Parse event type.
            _event = intent.getStringExtra(Common.EXTRA_GLYMPSE_EVENT);

            // Attempt to parse the message.
            String message = intent.getStringExtra(Common.EXTRA_GLYMPSE_MESSAGE);
            if ((null != message) && !Helpers.isEmpty(message))
            {
                _message = message;
            }

            // Attempt to parse the destination.
            String destinationJson = intent.getStringExtra(Common.EXTRA_GLYMPSE_DESTINATION);
            if ((null != destinationJson) && !Helpers.isEmpty(destinationJson))
            {
                Place destination = new Place(destinationJson);
                if (destination.isValid())
                {
                    _destination = destination;
                }
            }

            // Attempt to parse the context.
            String intentContext = intent.getStringExtra(Common.EXTRA_GLYMPSE_CONTEXT);
            if ((null != intentContext) && !Helpers.isEmpty(intentContext))
            {
                _intentContext = intentContext;
            }
        }
    }

    /**
     * Returns event that caused this intent.
     */
    public String getEvent()
    {
        return _event;
    }

    /**
     * Returns the array of recipients of the created glympse, or null if unknown/error.
     */
    public Recipient[] getRecipients()
    {
        return _recipients;
    }

    /**
     * Returns the duration of the created glympse, or -1 if unknown/error.
     */
    public long getDuration()
    {
        return _duration;
    }

    /**
     * Returns the remaining time of the created glympse, or -1 if unknown/error.
     */
    public long getRemaining()
    {
        return _remaining;
    }

    /**
     * Returns the message of the created glympse, or null if unknown/error.
     */
    public String getMessage()
    {
        return _message;
    }

    /**
     * Returns the destination of the created glympse, or null if unknown/error.
     */
    public Place getDestination()
    {
        return _destination;
    }

    /**
     * Returns the same context that was used to create the activity, or null if unknown/error.
     */
    public String getContext()
    {
        return _intentContext;
    }
}
