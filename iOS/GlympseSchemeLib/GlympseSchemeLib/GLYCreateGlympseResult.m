//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYCreateGlympseResult.h"
#import "GLYUriParser.h"
#import "GLYRecipient.h"

NSString* GLYReturnUriRecipients = @"gly_recipients";
NSString* GLYReturnUriDuration = @"gly_duration";

@implementation GLYCreateGlympseResult

- (id)initWithUriString:(NSString*)uriString
{
    if ( self = [super init] )
    {
        NSURL* url = [NSURL URLWithString:uriString];
        NSDictionary* arguments = [GLYUriParser parseQueryString:url.query];
        
        _recipients = [GLYRecipient fromString:[arguments objectForKey:GLYReturnUriRecipients]];
        _duration = [(NSString*)[arguments objectForKey:GLYReturnUriDuration] longLongValue];
    }
    return self;
}

@end
