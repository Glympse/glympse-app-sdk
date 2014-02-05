//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

extern NSString* GLYReurnUriUrl;
extern NSString* GLYReurnUriMessage;
extern NSString* GLYReurnUriDuration;

@interface GLYCreateGlympseResult : NSObject

@property (readonly) NSString* url;
@property (readonly) NSString* message;
@property (readonly) long long duration;

- (id)initWithUriString:(NSString*)uriString;

@end
