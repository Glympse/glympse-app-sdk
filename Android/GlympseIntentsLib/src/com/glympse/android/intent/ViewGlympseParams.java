//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ViewGlympseParams
{
    private long               _flags;
    private LinkedList<String> _codes = new LinkedList<String>();

    /**
     * Sets the flags. See the FLAG_* values.
     */
    public void setFlags(long flags)
    {
        _flags = flags;
    }

    /**
     * Add a glympse code ("ABCD-1234") or glympse group ("!ExampleGroup") to
     * watch in the "view a glympse" activity.
     */
    public void addGlympseOrGroup(String code)
    {
        if (null != code)
        {
            code = code.trim();
            if (!Helpers.isEmpty(code))
            {
                _codes.add(code);
            }
        }
    }

    /**
     * Add all glympse codes ("ABCD-1234") and glympse groups ("!ExampleGroup")
     * that were found in a parsed text buffer.
     */
    public void addAllGlympsesAndGroups(UriParser parseBufferResult)
    {
        if (null != parseBufferResult)
        {
            if (null != parseBufferResult.getGlympses())
            {
                for (String code : parseBufferResult.getGlympses())
                {
                    addGlympseOrGroup(code);
                }
            }
            if (null != parseBufferResult.getGroups())
            {
                for (String group : parseBufferResult.getGroups())
                {
                    addGlympseOrGroup(group);
                }
            }
        }
    }

    /**
     * Helper function to check if this object contains valid data.
     */
    protected boolean isValid()
    {
        return (_codes.size() > 0);
    }

    /**
     * Helper function to transfer the data from this class to an Intent.
     */
    protected void populateIntentForApp(Context context, Intent intent)
    {
        // Specify library version that was used to populate this intent.
        intent.putExtra(Common.EXTRA_GLYMPSE_APP_SDK_REV, Common.APP_SDK_REV);

        // Pass package name in "source" argument.
        intent.putExtra(Common.EXTRA_GLYMPSE_SOURCE, context.getPackageName());

        // Copy over the flags if any are set.
        if (0 != _flags)
        {
            intent.putExtra(Common.EXTRA_GLYMPSE_FLAGS, _flags);
        }

        String[] codesArray = new String[_codes.size()];
        intent.putExtra(Common.EXTRA_GLYMPSE_CODES, _codes.toArray(codesArray));
    }

    /**
     * Helper function to transfer the data from this class to an Intent.
     */
    protected void populateIntentForWeb(Intent intent)
    {
        StringBuffer strbuf = new StringBuffer();
        for (String code : _codes)
        {
            strbuf.append((strbuf.length() <= 0) ? UriParser.URL_BASE : "&");
            strbuf.append(code);
        }
        intent.setData(Uri.parse(strbuf.toString()));
    }
}
