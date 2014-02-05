//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

#import "GLYUriParser.h"

@interface GLYViewGlympseParams : NSObject

@property (readonly) NSMutableArray* codes;

- (void)addGlympseOrGroup:(NSString*)code;

- (void)addAllGlympsesAndGroups:(GLYUriParser*)parseBufferResult;

- (BOOL)isVaid;

- (NSURL*)toGlympseURL;

- (NSURL*)toWebURL;

@end
