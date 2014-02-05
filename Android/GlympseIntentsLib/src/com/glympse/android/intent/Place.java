//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

package com.glympse.android.intent;

import org.json.JSONObject;

public class Place
{
    private static final String PLACE_NAME      = "name";
    private static final String PLACE_LATITUDE  = "latitude";
    private static final String PLACE_LONGITUDE = "longitude";
    
    private String _name;
    private double _latitude;
    private double _longitude;
    
    public Place(String name, double latitude, double longitude)
    {
        _name      = name;
        _latitude  = latitude;
        _longitude = longitude;
    }
    
    protected Place(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            
            _name      = jsonObject.optString(PLACE_NAME,      null);
            _latitude  = jsonObject.optDouble(PLACE_LATITUDE,  Double.NaN);
            _longitude = jsonObject.optDouble(PLACE_LONGITUDE, Double.NaN);
        }
        catch (Throwable e)
        {
        }
    }
    
    @Override public String toString()
    {
        JSONObject jsonObject = new JSONObject();
        if (isValid())
        {
            try
            {
                if (!Helpers.isEmpty(_name))
                {
                    jsonObject.put(PLACE_NAME, _name);
                }
                jsonObject.put(PLACE_LATITUDE,  _latitude);
                jsonObject.put(PLACE_LONGITUDE, _longitude);
            }
            catch (Throwable e)
            {
            }
        }
        return jsonObject.toString();
    }
    
    public boolean isValid()
    {
        return (
            (!Double.isNaN(_latitude) && !Double.isNaN(_longitude)) &&  // Neither is NaN
            ((_latitude  !=    0.0)   || (_longitude !=   0.0))     &&  // Lat or Lng is not 0 (we allow one to be 0, but not both)
            ((_latitude  >=  -90.0)   && (_latitude  <=  90.0))     &&  // Latitude is within range
            ((_longitude >= -180.0)   && (_longitude <= 180.0)));       // Longitude is within range
    }
    
    public String getName()
    {
        return _name;
    }
    
    public double getLatitude()
    {
        return _latitude;
    }

    public double getLongitude()
    {
        return _longitude;
    }
}
