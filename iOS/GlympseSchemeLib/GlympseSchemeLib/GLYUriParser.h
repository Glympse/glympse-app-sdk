//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

@interface GLYUriParser : NSObject

- (id)initWithBuffer:(NSString*)buffer;

@property (readonly) NSArray* glympses;
@property (readonly) NSArray* requests;
@property (readonly) NSArray* groups;

- (BOOL)hasGlympseOrGroup;

@end
