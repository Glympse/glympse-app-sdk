//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

extern NSString* GLYCreateUriScheme;
extern NSString* GLYLaunchUriScheme;
extern NSString* GLYWebUri;

@interface GLYUriParser : NSObject

- (id)initWithBuffer:(NSString*)buffer;

@property (readonly) NSMutableArray* glympses;
@property (readonly) NSMutableArray* requests;
@property (readonly) NSMutableArray* groups;

- (BOOL)hasGlympseOrGroup;

+ (NSURL*)glympseCreateUri;

+ (NSURL*)glympseLaunchUri;

+ (NSString*)toJsonString:(id)jsonObject;

+ (NSDictionary*)parseQueryString:(NSString*)query;

+ (NSString*)urlEncode:(NSString*)str;

+ (NSString*)urlDecode:(NSString*)str;

@end
