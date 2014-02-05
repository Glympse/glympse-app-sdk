//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYCreateGlympseResult.h"
#import "GLYUriParser.h"

NSString* GLYReurnUriUrl = @"gly_url";
NSString* GLYReurnUriMessage = @"gly_msg";
NSString* GLYReurnUriDuration = @"gly_dur";

@implementation GLYCreateGlympseResult

- (id)initWithUriString:(NSString*)uriString
{
    if ( self = [super init] )
    {
        NSURL* url = [NSURL URLWithString:uriString];
        NSDictionary* arguments = [GLYUriParser parseQueryString:url.query];
        
        _url = (NSString*)[arguments objectForKey:GLYReurnUriUrl];
        _message = (NSString*)[arguments objectForKey:GLYReurnUriMessage];
        _duration = [(NSString*)[arguments objectForKey:GLYReurnUriDuration] longLongValue];
    }
    return self;
}

@end
