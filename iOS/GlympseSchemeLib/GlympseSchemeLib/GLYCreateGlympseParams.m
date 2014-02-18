//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYCreateGlympseParams.h"
#import "GLYUriParser.h"
#import "GLYRecipient.h"

extern int GLYAppSdkRev;

const long long GLYFlagRecipientsEditable           = 0x0000000000000000L;
const long long GLYFlagRecipientsReadOnly           = 0x0000000000000001L;
const long long GLYFlagRecipientsDeleteOnly         = 0x0000000000000002L;
const long long GLYFlagRecipientsHidden             = 0x0000000000000004L;
const long long GLYFlagMessageEditable              = 0x0000000000000000L;
const long long GLYFlagMessageReadOnly              = 0x0000000000000010L;
const long long GLYFlagMessageDeleteOnly            = 0x0000000000000020L;
const long long GLYFlagMessageHidden                = 0x0000000000000040L;
const long long GLYFlagDestinationEditable          = 0x0000000000000000L;
const long long GLYFlagDestinationReadOnly          = 0x0000000000000100L;
const long long GLYFlagDestinationDeleteOnly        = 0x0000000000000200L;
const long long GLYFlagDestinationHidden            = 0x0000000000000400L;
const long long GLYFlagDurationEditable             = 0x0000000000000000L;
const long long GLYFlagDurationReadOnly             = 0x0000000000001000L;

const static long long GLYDefaultDuration = 15 * 60 * 1000;

NSString* GLYCreateUriFlags = @"flags";
NSString* GLYCreateUriDuration = @"duration";
NSString* GLYCreateUriRecipients = @"recipients";
NSString* GLYCreateUriMessage = @"message";
NSString* GLYCreateUriDestination = @"destination";
NSString* GLYCreateUriReturnUrl = @"ret_url";
NSString* GLYCreateUriReturnCancelUrl = @"ret_cancel_url";
NSString* GLYCreateUriInitialNickname = @"initial_nickname";
NSString* GLYCreateUriSource = @"source";
NSString* GLYCreateUriAppSdkRev = @"app_sdk_rev";

@implementation GLYCreateGlympseParams

- (id)init
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
    if ( _initialNickname.length > 0 )
    {
        [uriString appendFormat:@"&%@=%@", GLYCreateUriInitialNickname, [GLYUriParser urlEncode:_initialNickname]];
    }
    
    // Specify 'source' argument. It should alwys contain package name.
    [uriString appendFormat:@"&%@=%@", GLYCreateUriSource, [GLYUriParser urlEncode:[NSBundle mainBundle].bundleIdentifier]];
    
    // Also append SDK revision that generated this URI.
    [uriString appendFormat:@"&%@=%d", GLYCreateUriAppSdkRev, GLYAppSdkRev];
    
    return [NSURL URLWithString:uriString];
}

@end
