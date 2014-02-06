//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

#import "GLYPlace.h"

extern const long long GLYFlagRecipientsEditable;
extern const long long GLYFlagRecipientsReadOnly;
extern const long long GLYFlagRecipientsDeleteOnly;
extern const long long GLYFlagRecipientsHidden;
extern const long long GLYFlagMessageEditable;
extern const long long GLYFlagMessageReadOnly;
extern const long long GLYFlagMessageDeleteOnly;
extern const long long GLYFlagMessageHidden;
extern const long long GLYFlagDestinationEditable;
extern const long long GLYFlagDestinationReadOnly;
extern const long long GLYFlagDestinationDeleteOnly;
extern const long long GLYFlagDestinationHidden;
extern const long long GLYFlagDurationEditable;
extern const long long GLYFlagDurationReadOnly;

@interface GLYCreateGlympseParams : NSObject

@property long long flags;
@property long long duration;
@property (readonly) NSMutableArray* recipients;
@property NSString* message;
@property GLYPlace* destination;
@property NSString* returnUrl;
@property NSString* returnCancelUrl;
@property NSString* initialNickname;

- (BOOL)isValid;

- (NSURL*)toGlympseURL;

@end
