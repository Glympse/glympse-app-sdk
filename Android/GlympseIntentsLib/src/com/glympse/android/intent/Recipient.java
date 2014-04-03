//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import org.json.JSONObject;

public class Recipient
{
    public static final String TYPE_APP   = "app";
    public static final String TYPE_LINK  = "link";
    public static final String TYPE_SMS   = "sms";
    public static final String TYPE_EMAIL = "email";

    private static final String RECIPIENT_TYPE        = "type";
    private static final String RECIPIENT_SUBTYPE     = "subtype";
    private static final String RECIPIENT_NAME        = "name";
    private static final String RECIPIENT_ADDRESS     = "address";
    private static final String RECIPIENT_CREATE_ONLY = "create_only";
    private static final String RECIPIENT_BRAND       = "brand";
    private static final String RECIPIENT_URL         = "url";

    private String _type;
    private String _subtype;
    private String _brand;
    private String _name;
    private String _address;
    private boolean _createOnly = false;
    private String _url;

    protected Recipient(String type, String subtype, String brand, String name, String address, boolean createOnly, String url)
    {
        _type       = type;
        _subtype    = subtype;
        _brand      = brand;
        _name       = name;
        _address    = address;
        _createOnly = createOnly;
        _url        = url;
    }

    public static Recipient createNew(String type, String subtype, String brand, String name, String address)
    {
        return new Recipient(type, subtype, brand, name, address, false, null);
    }

    public static Recipient createNew(String type, String subtype, String brand, String name, String address, boolean createOnly)
    {
        return new Recipient(type, subtype, brand, name, address, createOnly, null);
    }

    public static Recipient createFromInvite(String type, String subtype, String name, String address, String url)
    {
        return new Recipient(type, subtype, null, name, address, false, url);
    }

    protected Recipient(String json)
    {
        try
        {
            populate(new JSONObject(json));
        }
        catch (Throwable e)
        {
        }
    }

    protected Recipient(JSONObject jsonObject)
    {
        populate(jsonObject);
    }

    protected void populate(JSONObject jsonObject)
    {
        if (null != jsonObject)
        {
            _type = jsonObject.optString(RECIPIENT_TYPE, null);
            _subtype = jsonObject.optString(RECIPIENT_SUBTYPE, null);
            _brand = jsonObject.optString(RECIPIENT_BRAND, null);
            _name = jsonObject.optString(RECIPIENT_NAME, null);
            _address = jsonObject.optString(RECIPIENT_ADDRESS, null);
            _createOnly = jsonObject.optBoolean(RECIPIENT_CREATE_ONLY, false);
            _url = jsonObject.optString(RECIPIENT_URL, null);
        }
    }

    @Override public String toString()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            if (!Helpers.isEmpty(_type))
            {
                jsonObject.put(RECIPIENT_TYPE, _type);
            }

            if (!Helpers.isEmpty(_subtype))
            {
                jsonObject.put(RECIPIENT_SUBTYPE, _subtype);
            }

            if (!Helpers.isEmpty(_brand))
            {
                jsonObject.put(RECIPIENT_BRAND, _brand);
            }

            if (!Helpers.isEmpty(_name))
            {
                jsonObject.put(RECIPIENT_NAME, _name);
            }

            if (!Helpers.isEmpty(_address))
            {
                jsonObject.put(RECIPIENT_ADDRESS, _address);
            }

            if (!Helpers.isEmpty(_url))
            {
                jsonObject.put(RECIPIENT_URL, _url);
            }

            if ( _createOnly )
            {
                jsonObject.put(RECIPIENT_CREATE_ONLY, _createOnly);
            }
        }
        catch (Throwable e)
        {
        }
        return jsonObject.toString();
    }

    public boolean isValid()
    {
        return !Helpers.isEmpty(_type);
    }

    public String getType()
    {
        return _type;
    }

    public String getSubtype()
    {
        return _subtype;
    }

    public String getBrand()
    {
        return _brand;
    }

    public String getName()
    {
        return _name;
    }

    public String getAddress()
    {
        return _address;
    }

    public boolean isCreateOnly()
    {
        return _createOnly;
    }

    public String getUrl()
    {
        return _url;
    }
}
