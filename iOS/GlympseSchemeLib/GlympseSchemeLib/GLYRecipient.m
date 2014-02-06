//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYRecipient.h"
#import "GLYUriParser.h"

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
static NSString* GLYRecipientMessage = @"message";

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
           subtype:(NSString*)subtype
              name:(NSString*)name
           address:(NSString*)address
               url:(NSString*)url
           message:(NSString*)message;
{
    if ( self = [super init] )
    {
        _type = type;
        _subtype = subtype;
        _name = name;
        _address = address;
        _url = url;
        _message = message;
    }
    return self;
}

- (id)initWithString:(NSString*)jsonString
{
    if ( self = [super init] )
    {
        NSDictionary* jsonObject = [GLYUriParser toJsonObject:jsonString];
        [self fromObject:jsonObject];
    }
    return self;
}

- (id)initWithObject:(NSDictionary*)jsonObject
{
    if ( self = [super init] )
    {
        [self fromObject:jsonObject];
    }
    return self;
}

- (void)fromObject:(NSDictionary*)jsonObject
{
    _type = [jsonObject objectForKey:GLYRecipientType];
    _subtype = [jsonObject objectForKey:GLYRecipientSubtype];
    _brand = [jsonObject objectForKey:GLYRecipientBrand];
    _name = [jsonObject objectForKey:GLYRecipientName];
    _address = [jsonObject objectForKey:GLYRecipientAddress];
    _url = [jsonObject objectForKey:GLYRecipientUrl];
    _message = [jsonObject objectForKey:GLYRecipientMessage];
}

- (NSDictionary*)toObject
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
    if ( _message.length > 0 )
    {
        [jsonObject setObject:_message forKey:GLYRecipientMessage];
    }

    return jsonObject;
}

+ (NSString*)toString:(NSArray*)recipients
{
    NSMutableArray* recipientObjects = [[NSMutableArray alloc] init];
    for ( GLYRecipient* recipient in recipients )
    {
        if ( ![recipient isValid] )
        {
            continue;
        }
        [recipientObjects addObject:[recipient toObject]];
    }
    if ( recipientObjects.count <= 0 )
    {
        return nil;
    }
    return [GLYUriParser toJsonString:recipientObjects];
}

+ (NSArray*)fromString:(NSString*)recipientsStr
{
    NSMutableArray* recipients = [[NSMutableArray alloc] init];
    NSArray* recipientObjects = [GLYUriParser toJsonObject:recipientsStr];
    for ( NSDictionary* recipientObject in recipientObjects )
    {
        GLYRecipient* recipient = [[GLYRecipient alloc] initWithObject:recipientObject];
        [recipients addObject:recipient];
    }
    return recipients;
}

- (BOOL)isValid
{
    return YES;
}

@end
