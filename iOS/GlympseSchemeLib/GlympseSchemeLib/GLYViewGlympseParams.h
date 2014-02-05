//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

#import "GLYUriParser.h"

@interface GLYViewGlympseParams : NSObject

- (void)addGlympseOrGroup:(NSString*)code;

- (void)addAllGlympsesAndGroups:(GLYUriParser*)parseBufferResult;

@property (readonly) NSMutableArray* codes;

@end
