//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYCreateGlympseParams.h"

const long long GLYFlagDialog                       = 0x0000000000000001L;
const long long GLYFlagRecipientsReadOnly           = 0x0000000000010000L;
const long long GLYFlagRecipientsHidden             = 0x0000000000020000L;
const long long GLYFlagDurationReadOnly             = 0x0000000000040000L;
const long long GLYFlagDurationHidden               = 0x0000000000080000L;
const long long GLYFlagMessageReadOnly              = 0x0000000000100000L;
const long long GLYFlagMessageHidden                = 0x0000000000200000L;
const long long GLYFlagDestinationReadOnly          = 0x0000000000400000L;
const long long GLYFlagDestinationHidden            = 0x0000000000800000L;

const static long long GLYDefaultDuration = 15 * 60 * 1000;

@implementation GLYCreateGlympseParams

- (id)initWithBuffer:(NSString*)buffer
{
    if ( self = [super init] )
    {
        _flags = 0;
        _duration = GLYDefaultDuration;
    }
    return self;
}

@end
