//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

@interface GLYUriParser : NSObject

- (id)initWithBuffer:(NSString*)buffer;

@property (readonly) NSMutableArray* glympses;
@property (readonly) NSMutableArray* requests;
@property (readonly) NSMutableArray* groups;

- (BOOL)hasGlympseOrGroup;

+ (NSDictionary*)parseQueryString:(NSString*)query;

@end
