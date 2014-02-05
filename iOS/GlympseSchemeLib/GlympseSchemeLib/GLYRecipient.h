//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

@interface GLYRecipient : NSObject

- (id)initWithType:(NSString*)type
           subtype:(NSString*)subtype
             brand:(NSString*)brand
              name:(NSString*)name
           address:(NSString*)address;

- (id)initWithType:(NSString*)type
              name:(NSString*)name
           address:(NSString*)address
               url:(NSString*)url;

- (id)initWithString:(NSString*)jsonString;

- (NSString*)toString;

@property (readonly) NSString* type;
@property (readonly) NSString* subtype;
@property (readonly) NSString* brand;
@property (readonly) NSString* name;
@property (readonly) NSString* address;
@property (readonly) NSString* url;

@end
