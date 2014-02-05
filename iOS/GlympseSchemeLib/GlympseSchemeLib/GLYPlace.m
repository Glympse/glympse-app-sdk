//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYPlace.h"

static NSString* GLYPlaceName = @"name";
static NSString* GLYPlaceLatitude = @"latitude";
static NSString* GLYPlaceLongitude = @"longitude";

@implementation GLYPlace

- (id)initWithName:(NSString*)name
          latitude:(double)latitude
         longitude:(double)longitude
{
    if ( self = [super init] )
    {
        _name = name;
        _latitude = latitude;
        _longitude = longitude;
    }
    return self;
}

- (id)initWithString:(NSString*)jsonString
{
    if ( self = [super init] )
    {
        NSData* jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
        NSDictionary* jsonObject = [NSJSONSerialization JSONObjectWithData:jsonData options:0 error:NULL];
        
        _name = [jsonObject objectForKey:GLYPlaceName];
        _latitude = [(NSNumber*)[jsonObject objectForKey:GLYPlaceLatitude] doubleValue];
        _longitude = [(NSNumber*)[jsonObject objectForKey:GLYPlaceLongitude] doubleValue];
    }
    return self;
}

- (NSString*)toString
{
    NSMutableDictionary* jsonObject = [[NSMutableDictionary alloc] init];
    
    if ( _name.length > 0 )
    {
        [jsonObject setObject:_name forKey:GLYPlaceName];
    }
    [jsonObject setObject:[NSNumber numberWithDouble:_latitude] forKey:GLYPlaceLatitude];
    [jsonObject setObject:[NSNumber numberWithDouble:_longitude] forKey:GLYPlaceLongitude];
    
    NSData* jsonData = [NSJSONSerialization dataWithJSONObject:jsonObject options:0 error:NULL];
    NSString* jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    return jsonString;
}

@end
