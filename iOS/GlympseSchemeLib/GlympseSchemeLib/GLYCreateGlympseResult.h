//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

extern NSString* GLYReturnUriRecipients;
extern NSString* GLYReturnUriDuration;

@interface GLYCreateGlympseResult : NSObject

@property (readonly) NSArray* recipients;
@property (readonly) long long duration;

- (id)initWithUriString:(NSString*)uriString;

@end
