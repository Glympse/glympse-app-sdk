//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

public class Common
{
    // Flags for ACTION_GLYMPSE_CREATE
    public static final long FLAG_RECIPIENTS_EDITABLE         = 0x0000000000000000L;
    public static final long FLAG_RECIPIENTS_READ_ONLY        = 0x0000000000000001L;
    public static final long FLAG_RECIPIENTS_DELETE_ONLY      = 0x0000000000000002L;
    public static final long FLAG_MESSAGE_EDITABLE            = 0x0000000000000000L;
    public static final long FLAG_MESSAGE_READ_ONLY           = 0x0000000000000010L;
    public static final long FLAG_MESSAGE_DELETE_ONLY         = 0x0000000000000020L;
    public static final long FLAG_MESSAGE_HIDDEN              = 0x0000000000000040L;
    public static final long FLAG_DESTINATION_EDITABLE        = 0x0000000000000000L;
    public static final long FLAG_DESTINATION_READ_ONLY       = 0x0000000000000100L;
    public static final long FLAG_DESTINATION_DELETE_ONLY     = 0x0000000000000200L;
    public static final long FLAG_DESTINATION_HIDDEN          = 0x0000000000000400L;
    public static final long FLAG_DURATION_EDITABLE           = 0x0000000000000000L;
    public static final long FLAG_DURATION_READ_ONLY          = 0x0000000000001000L;
    public static final long FLAG_USE_ACTIVITY_RESULT         = 0x0000000010000000L;
    public static final long FLAG_ENABLE_EVENTS               = 0x0000000020000000L;
    public static final long FLAG_APPEND_URI_QUERY            = 0x0000000040000000L;
    public static final long FLAG_APPEND_URI_FRAGMENT         = 0x0000000080000000L;

    // Flags for ACTION_GLYMPSE_VIEW
    public static final long FLAG_SHOW_SELF                   = 0x0000000000000001L;

    // Action to create (and optionally send) a Glympse.
    public static final String ACTION_GLYMPSE_CREATE          = "com.glympse.android.intent.CREATE";

    // Action to view one ore more glympses or Glympse groups..
    public static final String ACTION_GLYMPSE_VIEW            = "com.glympse.android.intent.VIEW";

    // Base action name used for Glympse to call back to the calling app with status and event updates.
    public static final String ACTION_GLYMPSE_CALLBACK        = "com.glympse.android.intent.CALLBACK";

    // Extras used with both ACTION_GLYMPSE_CREATE and ACTION_GLYMPSE_VIEW actions.
    public static final String EXTRA_GLYMPSE_FLAGS            = "flags";             // long (bit flags)
    public static final String EXTRA_GLYMPSE_SOURCE           = "source";            // string
    public static final String EXTRA_GLYMPSE_APP_SDK_REV      = "app_sdk_rev";       // int

    // Extras used with ACTION_GLYMPSE_CREATE action.
    public static final String EXTRA_GLYMPSE_RECIPIENTS       = "recipients";        // array of (json) strings
    public static final String EXTRA_GLYMPSE_DURATION         = "duration";          // long (milliseconds)
    public static final String EXTRA_GLYMPSE_MESSAGE          = "message";           // string
    public static final String EXTRA_GLYMPSE_DESTINATION      = "destination";       // string (json)
    public static final String EXTRA_GLYMPSE_INITIAL_NICKNAME = "initial_nickname";  // string
    public static final String EXTRA_GLYMPSE_INITIAL_AVATAR   = "initial_avatar";    // string uri
    public static final String EXTRA_GLYMPSE_CONTEXT          = "context";           // string
    public static final String EXTRA_GLYMPSE_CALLBACK_PACKAGE = "callback_package";  // string (package name)
    public static final String EXTRA_GLYMPSE_CALLBACK_ACTION  = "callback_action";   // string
    public static final String EXTRA_GLYMPSE_RETURN_URL       = "ret_url";           // string (url)
    public static final String EXTRA_GLYMPSE_CANCEL_URL       = "ret_cancel_url";    // string (url)

    // Extras used with ACTION_GLYMPSE_VIEW action.
    public static final String EXTRA_GLYMPSE_CODES            = "codes";             // array of strings

    // Extras used with ACTION_GLYMPSE_CALLBACK action.
    public static final String EXTRA_GLYMPSE_ERROR            = "error";             // string
    public static final String EXTRA_GLYMPSE_EVENT            = "event";             // string
    public static final String EXTRA_GLYMPSE_REMAINING        = "remaining";         // long (milliseconds)

    // One of these will be sent to your status listener or activity result.
    public static final String GLYMPSE_EVENT_DONE_SENDING     = "done_sending";
    public static final String GLYMPSE_EVENT_FAILED_TO_CREATE = "failed_to_create";

    // These are optional events you'll receive if you specify an event listener.
    public static final String GLYMPSE_EVENT_CREATING         = "creating";
    public static final String GLYMPSE_EVENT_CREATED          = "created";
    public static final String GLYMPSE_EVENT_DURATION_CHANGED = "duration_changed";

    public static final int APP_SDK_REV                       = 13;

    public static final String GLYMPSE_PACKAGE_NAME           = "com.glympse.android.glympse";
}
