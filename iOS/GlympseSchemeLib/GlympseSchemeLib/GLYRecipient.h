//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

extern NSString* GLYRecipientTypeApp;
extern NSString* GLYRecipientTypeLink;
extern NSString* GLYRecipientTypeSms;
extern NSString* GLYRecipientTypeEmail;

@interface GLYRecipient : NSObject

- (id)initWithType:(NSString*)type
           subtype:(NSString*)subtype
             brand:(NSString*)brand
              name:(NSString*)name
           address:(NSString*)address;

- (id)initWithType:(NSString*)type
           subtype:(NSString*)subtype
              name:(NSString*)name
           address:(NSString*)address
               url:(NSString*)url
           message:(NSString*)message;

- (id)initWithString:(NSString*)jsonString;

- (BOOL)isValid;

+ (NSString*)toString:(NSArray*)recipients;

+ (NSArray*)fromString:(NSString*)recipientsStr;

@property (readonly) NSString* type;
@property (readonly) NSString* subtype;
@property (readonly) NSString* brand;
@property (readonly) NSString* name;
@property (readonly) NSString* address;
@property (readonly) NSString* url;
@property (readonly) NSString* message;

@end
