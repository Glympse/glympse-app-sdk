//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYViewGlympseParams.h"

@implementation GLYViewGlympseParams

- (id)init
{
    if ( self = [super init] )
    {
        _codes = [[NSMutableArray alloc] init];
    }
    return self;
}

- (void)addGlympseOrGroup:(NSString*)code
{
    if ( code.length > 0 )
    {
        [_codes addObject:code];
    }
}

- (void)addAllGlympsesAndGroups:(GLYUriParser*)parseBufferResult
{
    [_codes addObjectsFromArray:parseBufferResult.glympses];
    [_codes addObjectsFromArray:parseBufferResult.groups];
}

- (BOOL)isVaid
{
    // TODO:
    return NO;
}

- (NSURL*)toGlympseURL;
{
    // TODO:    
    return nil;
}

- (NSURL*)toWebURL
{
    // TODO:
    return nil;
}

@end
