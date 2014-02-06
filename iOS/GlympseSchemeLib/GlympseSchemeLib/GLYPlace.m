//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYPlace.h"
#import "GLYUriParser.h"

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
        NSDictionary* jsonObject = [GLYUriParser toJsonObject:jsonString];
        
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
    
    return [GLYUriParser toJsonString:jsonObject];
}

- (BOOL)isValid
{
    return YES;
}

@end
