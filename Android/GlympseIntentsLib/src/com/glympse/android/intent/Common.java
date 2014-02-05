//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

public class Common 
{
    public static final long FLAG_DIALOG                = 0x0000000000000001L;
    public static final long FLAG_RECIPIENTS_READ_ONLY  = 0x0000000000010000L;
    public static final long FLAG_RECIPIENTS_HIDDEN     = 0x0000000000020000L;
    public static final long FLAG_DURATION_READ_ONLY    = 0x0000000000040000L;
    public static final long FLAG_DURATION_HIDDEN       = 0x0000000000080000L;
    public static final long FLAG_MESSAGE_READ_ONLY     = 0x0000000000100000L;
    public static final long FLAG_MESSAGE_HIDDEN        = 0x0000000000200000L;
    public static final long FLAG_DESTINATION_READ_ONLY = 0x0000000000400000L;
    public static final long FLAG_DESTINATION_HIDDEN    = 0x0000000000800000L;
    
    public static final String ACTION_GLYMPSE_CREATE     = "com.glympse.android.intent.CREATE";
    public static final String ACTION_GLYMPSE_VIEW       = "com.glympse.android.intent.VIEW";
    
    public static final String EXTRA_GLYMPSE_FLAGS       = "flags";
    public static final String EXTRA_GLYMPSE_BRAND       = "brand";
    public static final String EXTRA_GLYMPSE_RECIPIENTS  = "recipients";
    public static final String EXTRA_GLYMPSE_DURATION    = "duration";
    public static final String EXTRA_GLYMPSE_MESSAGE     = "message";
    public static final String EXTRA_GLYMPSE_DESTINATION = "destination";
    public static final String EXTRA_GLYMPSE_CONTEXT     = "context";
    public static final String EXTRA_GLYMPSE_CODES       = "codes";    
}
