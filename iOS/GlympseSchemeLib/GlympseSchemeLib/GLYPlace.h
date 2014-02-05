//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

@interface GLYPlace : NSObject

- (id)initWithName:(NSString*)name
          latitude:(double)latitude
         longitude:(double)longitude;

- (id)initWithString:(NSString*)jsonString;

- (NSString*)toString;

- (BOOL)isValid;

@property (readonly) NSString* name;
@property (readonly) double latitude;
@property (readonly) double longitude;

@end
