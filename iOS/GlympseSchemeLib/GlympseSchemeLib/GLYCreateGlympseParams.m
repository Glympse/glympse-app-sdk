//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYCreateGlympseParams.h"
#import "GLYUriParser.h"
#import "GLYRecipient.h"

const long long GLYFlagDialog                       = 0x0000000000000001L;
const long long GLYFlagRecipientsReadOnly           = 0x0000000000010000L;
const long long GLYFlagRecipientsHidden             = 0x0000000000020000L;
const long long GLYFlagDurationReadOnly             = 0x0000000000040000L;
const long long GLYFlagDurationHidden               = 0x0000000000080000L;
const long long GLYFlagMessageReadOnly              = 0x0000000000100000L;
const long long GLYFlagMessageHidden                = 0x0000000000200000L;
const long long GLYFlagDestinationReadOnly          = 0x0000000000400000L;
const long long GLYFlagDestinationHidden            = 0x0000000000800000L;

const static long long GLYDefaultDuration = 15 * 60 * 1000;

NSString* GLYCreateUriFlags = @"flags";
NSString* GLYCreateUriDuration = @"duration";
NSString* GLYCreateUriRecipients = @"recipients";
NSString* GLYCreateUriMessage = @"message";
NSString* GLYCreateUriDestination = @"destination";
NSString* GLYCreateUriReturnUrl = @"ret_url";
NSString* GLYCreateUriReturnCancelUrl = @"ret_cancel_url";

@implementation GLYCreateGlympseParams

- (id)initWithBuffer:(NSString*)buffer
{
    if ( self = [super init] )
    {
        _flags = 0;
        _duration = GLYDefaultDuration;
        _recipients = [[NSMutableArray alloc] init];
    }
    return self;
}

- (BOOL)isValid
{
    return ( _recipients.count > 0 );
}

- (NSURL*)toGlympseURL;
{
    NSMutableString* uriString = [NSMutableString string];
    [uriString appendString:GLYCreateUriScheme];
    
    [uriString appendFormat:@"?%@=%lld", GLYCreateUriFlags, _flags];
    [uriString appendFormat:@"&%@=%lld", GLYCreateUriDuration, _duration];
    NSString* recipientsStr = [GLYRecipient toString:_recipients];
    if ( recipientsStr.length > 0 )
    {
        [uriString appendFormat:@"&%@=%@", GLYCreateUriRecipients, [GLYUriParser urlEncode:recipientsStr]];
    }
    if ( _message.length > 0 )
    {
        [uriString appendFormat:@"&%@=%@", GLYCreateUriMessage, [GLYUriParser urlEncode:_message]];
    }
    if ( _destination && [_destination isValid] )
    {
        [uriString appendFormat:@"&%@=%@", GLYCreateUriDestination, [GLYUriParser urlEncode:[_destination toString]]];
    }
    if ( _returnUrl.length > 0 )
    {
        [uriString appendFormat:@"&%@=%@", GLYCreateUriReturnUrl, [GLYUriParser urlEncode:_returnUrl]];
    }
    if ( _returnCancelUrl.length > 0 )
    {
        [uriString appendFormat:@"&%@=%@", GLYCreateUriReturnCancelUrl, [GLYUriParser urlEncode:_returnCancelUrl]];
    }
    
    return [NSURL URLWithString:uriString];
}

@end
