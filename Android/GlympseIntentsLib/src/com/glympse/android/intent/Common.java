//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

public class Common 
{
    public static final long FLAG_RECIPIENTS_EDITABLE       = 0x0000000000000000L;
    public static final long FLAG_RECIPIENTS_READ_ONLY      = 0x0000000000000001L;
    public static final long FLAG_RECIPIENTS_DELETE_ONLY    = 0x0000000000000002L;    
    public static final long FLAG_MESSAGE_EDITABLE          = 0x0000000000000000L;
    public static final long FLAG_MESSAGE_READ_ONLY         = 0x0000000000000010L;
    public static final long FLAG_MESSAGE_DELETE_ONLY       = 0x0000000000000020L;
    public static final long FLAG_MESSAGE_HIDDEN            = 0x0000000000000040L;    
    public static final long FLAG_DESTINATION_EDITABLE      = 0x0000000000000000L;
    public static final long FLAG_DESTINATION_READ_ONLY     = 0x0000000000000100L;
    public static final long FLAG_DESTINATION_DELETE_ONLY   = 0x0000000000000200L;
    public static final long FLAG_DESTINATION_HIDDEN        = 0x0000000000000400L;    
    public static final long FLAG_DURATION_EDITABLE         = 0x0000000000000000L;
    public static final long FLAG_DURATION_READ_ONLY        = 0x0000000000001000L;
    
    public static final String ACTION_GLYMPSE_CREATE     = "com.glympse.android.intent.CREATE";
    public static final String ACTION_GLYMPSE_VIEW       = "com.glympse.android.intent.VIEW";
    public static final String ACTION_GLYMPSE_CALLBACK   = "com.glympse.android.intent.CALLBACK";
    
    public static final String EXTRA_GLYMPSE_FLAGS              = "flags";
    public static final String EXTRA_GLYMPSE_BRAND              = "brand";
    public static final String EXTRA_GLYMPSE_RECIPIENTS         = "recipients";
    public static final String EXTRA_GLYMPSE_DURATION           = "duration";
    public static final String EXTRA_GLYMPSE_MESSAGE            = "message";
    public static final String EXTRA_GLYMPSE_DESTINATION        = "destination";
    public static final String EXTRA_GLYMPSE_CONTEXT            = "context";
    public static final String EXTRA_GLYMPSE_CODES              = "codes";    
    public static final String EXTRA_GLYMPSE_CALLBACK_PACKAGE   = "callback_package";
    public static final String EXTRA_GLYMPSE_CALLBACK_ACTION    = "callback_action";
    public static final String EXTRA_GLYMPSE_EVENTS             = "events";
    public static final String EXTRA_GLYMPSE_EVENT              = "event";
    public static final String EXTRA_GLYMPSE_REMAINING          = "remaining";    
    
    public static final String GLYMPSE_EVENT_CREATING           = "creating";
    public static final String GLYMPSE_EVENT_CREATED            = "created";
    public static final String GLYMPSE_EVENT_FAILED_TO_CREATE   = "failed_to_create";
    public static final String GLYMPSE_EVENT_DONE_SENDING       = "done_sending";
    public static final String GLYMPSE_EVENT_DURATION_CHANGED   = "duration_changed";    
}
