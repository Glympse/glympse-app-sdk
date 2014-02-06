//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

extern NSString* GLYReurnUriRecipients;
extern NSString* GLYReurnUriDuration;

@interface GLYCreateGlympseResult : NSObject

@property (readonly) NSArray* recipients;
@property (readonly) long long duration;

- (id)initWithUriString:(NSString*)uriString;

- (NSURL*)toUrl:(NSString*)returnUrl recipients:(NSArray*)recipients duration:(long long)duration;

@end
