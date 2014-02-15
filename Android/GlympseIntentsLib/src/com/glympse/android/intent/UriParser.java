//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;

public class UriParser
{
    public static final String URL_BASE   = "http://glympse.com/";
    public static final Uri    URI_SAMPLE = Uri.parse(URL_BASE + "ABCD-1234");

    private LinkedList<String> _glympses = new LinkedList<String>();
    private LinkedList<String> _requests = new LinkedList<String>();
    private LinkedList<String> _groups   = new LinkedList<String>();

    public List<String> getGlympses()
    {
        return _glympses;
    }

    public List<String> getRequests()
    {
        return _requests;
    }

    public List<String> getGroups()
    {
        return _groups;
    }

    public boolean hasGlympseOrGroup()
    {
        return (((null != _glympses) && (_glympses.size() > 0)) ||
                ((null != _groups)   && (_groups.size()   > 0)));
    }

    protected UriParser(String buffer)
    {
        // Build a regular expression that can locate all Glympse URLs.
        final Pattern pattern = Pattern.compile("(?:\\bglympse)(?:.[a-z]{2,}/|:|2:|://|2://)([a-z0-9]+-[a-z0-9]+(-[a-z0-9]+)*|![a-z0-9_{}]+)", Pattern.CASE_INSENSITIVE);

        // Loop through each Glympse code/group found in the buffer.
        Matcher matcher = pattern.matcher(buffer);
        while (matcher.find())
        {
            processCode(matcher.group(1));
        }
    }

    private void processCode(String code)
    {
        // Check for a group name.
        if (code.startsWith("!"))
        {
            _groups.add(code);
        }
        else
        {
            // Convert the glympse code string to a long value.
            long codeValue = base32ToLong(code);

            // Check if this is a normal glympse code.
            if (isGlympseCode(codeValue))
            {
                _glympses.add(code);
            }

            // Check if this is a glympse request code.
            else if (isRequestCode(codeValue))
            {
                _requests.add(code);
            }
        }
    }

    private static final int[] BASE32_DECODE =
    {
         0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  // 0 1 2 3 4 5 6 7 8 9
        -1, -1, -1, -1, -1, -1, -1,              // : ; < = > ? @
        10, 11, 12, 13, 14, 15, 16, 17,  1,      // A B C D E F G H I
        18, 19,  1, 20, 21,  0, 22, 23, 24,      // J K L M N O P Q R
        25, 26, 27, 27, 28, 29, 30, 31,          // S T U V W X Y Z
        -1, -1, -1, -1, -1, -1,                  // [ \ ] ^ _ `
        10, 11, 12, 13, 14, 15, 16, 17,  1,      // a b c d e f g h i
        18, 19,  1, 20, 21,  0, 22, 23, 24,      // j k l m n o p q r
        25, 26, 27, 27, 28, 29, 30, 31           // s t u v w x y z
    };

    private static long base32ToLong(String text)
    {
        // Convert this base32 encoded string to a long value.
        long result = 0;
        for (int i = 0, length = text.length(); i < length; i++)
        {
            char c = text.charAt(i);

            // We skip dashes. They are allowed, but aren't parsed as digits.
            if ('-' != c)
            {
                // Convert the character into its value.
                int value = ((c >= 48) && (c <= 122)) ? BASE32_DECODE[(int)c - 48] : -1;

                // If we got back -1, then the character is not valid.
                if (value < 0)
                {
                    return 0;
                }

                // Add this value into our tally.
                result = (result << 5) + (long)value;
            }
        }

        return result;
    }

    private static boolean isGlympseCode(long code)
    {
        return ((0 != code) && (0 == ((int)(code >> 35) & 0x3L)));
    }

    private static boolean isRequestCode(long code)
    {
        return (1 == ((int)(code >> 35) & 0x3L));
    }
}
