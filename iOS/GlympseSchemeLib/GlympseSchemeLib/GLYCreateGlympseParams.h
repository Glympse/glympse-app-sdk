//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

#import "GLYPlace.h"

extern const long long GLYFlagDialog;
extern const long long GLYFlagRecipientsReadOnly;
extern const long long GLYFlagRecipientsHidden;
extern const long long GLYFlagDurationReadOnly;
extern const long long GLYFlagDurationHidden;
extern const long long GLYFlagMessageReadOnly;
extern const long long GLYFlagMessageHidden;
extern const long long GLYFlagDestinationReadOnly;
extern const long long GLYFlagDestinationHidden;

@interface GLYCreateGlympseParams : NSObject

@property long long flags;
@property long long duration;
@property (readonly) NSMutableArray* recipients;
@property NSString* message;
@property GLYPlace* destination;
@property NSString* returnUrl;
@property NSString* returnCancelUrl;

- (BOOL)isValid;

- (NSURL*)toGlympseURL;

@end
