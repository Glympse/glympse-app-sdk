//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYCreateGlympseResult.h"
#import "GLYUriParser.h"
#import "GLYRecipient.h"

NSString* GLYReurnUriRecipients = @"gly_recipients";
NSString* GLYReurnUriDuration = @"gly_duration";

@implementation GLYCreateGlympseResult

- (id)initWithUriString:(NSString*)uriString
{
    if ( self = [super init] )
    {
        NSURL* url = [NSURL URLWithString:uriString];
        NSDictionary* arguments = [GLYUriParser parseQueryString:url.query];
        
        _recipients = [GLYRecipient fromString:[arguments objectForKey:GLYReurnUriRecipients]];
        _duration = [(NSString*)[arguments objectForKey:GLYReurnUriDuration] longLongValue];
    }
    return self;
}

- (NSURL*)toUrl:(NSString*)returnUrl recipients:(NSArray*)recipients duration:(long long)duration;
{
    NSMutableString* urlStr = [[NSMutableString alloc] initWithString:returnUrl];
    char separator = ( NSNotFound == [returnUrl rangeOfString:@"?"].location ) ? '?' : '&';
    [urlStr appendFormat:@"%c%@=%@", separator, GLYReurnUriRecipients, [GLYRecipient toString:recipients]];
    [urlStr appendFormat:@"&%@=%lld", GLYReurnUriDuration, duration];
    return [NSURL URLWithString:urlStr];
}

@end
