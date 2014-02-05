//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYRecipient.h"

NSString* GLYRecipientTypeApp = @"app";
NSString* GLYRecipientTypeLink = @"link";
NSString* GLYRecipientTypeSms = @"sms";
NSString* GLYRecipientTypeEmail = @"email";

static NSString* GLYRecipientType = @"type";
static NSString* GLYRecipientSubtype = @"subtype";
static NSString* GLYRecipientBrand = @"brand";
static NSString* GLYRecipientName = @"name";
static NSString* GLYRecipientAddress = @"address";
static NSString* GLYRecipientUrl = @"url";

@implementation GLYRecipient

- (id)initWithType:(NSString*)type
           subtype:(NSString*)subtype
             brand:(NSString*)brand
              name:(NSString*)name
           address:(NSString*)address
{
    if ( self = [super init] )
    {
        _type = type;
        _subtype = subtype;
        _brand = brand;
        _name = name;
        _address = address;
    }
    return self;
}

- (id)initWithType:(NSString*)type
              name:(NSString*)name
           address:(NSString*)address
               url:(NSString*)url
{
    if ( self = [super init] )
    {
        _type = type;
        _name = name;
        _address = address;
        _url = url;
    }
    return self;
}

- (id)initWithString:(NSString*)jsonString
{
    if ( self = [super init] )
    {
        NSData* jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
        NSDictionary* jsonObject = [NSJSONSerialization JSONObjectWithData:jsonData options:0 error:NULL];
        
        _type = [jsonObject objectForKey:GLYRecipientType];
        _subtype = [jsonObject objectForKey:GLYRecipientSubtype];
        _brand = [jsonObject objectForKey:GLYRecipientBrand];
        _name = [jsonObject objectForKey:GLYRecipientName];
        _address = [jsonObject objectForKey:GLYRecipientAddress];
        _url = [jsonObject objectForKey:GLYRecipientUrl];
    }
    return self;
}

- (NSString*)toString
{
    NSMutableDictionary* jsonObject = [[NSMutableDictionary alloc] init];
    
    if ( _type.length > 0 )
    {
        [jsonObject setObject:_type forKey:GLYRecipientType];
    }
    if ( _subtype.length > 0 )
    {
        [jsonObject setObject:_subtype forKey:GLYRecipientSubtype];
    }
    if ( _brand.length > 0 )
    {
        [jsonObject setObject:_brand forKey:GLYRecipientBrand];
    }
    if ( _name.length > 0 )
    {
        [jsonObject setObject:_name forKey:GLYRecipientName];
    }
    if ( _address.length > 0 )
    {
        [jsonObject setObject:_address forKey:GLYRecipientAddress];
    }
    if ( _url.length > 0 )
    {
        [jsonObject setObject:_url forKey:GLYRecipientUrl];
    }
    
    NSData* jsonData = [NSJSONSerialization dataWithJSONObject:jsonObject options:0 error:NULL];
    NSString* jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    return jsonString;
}

@end
