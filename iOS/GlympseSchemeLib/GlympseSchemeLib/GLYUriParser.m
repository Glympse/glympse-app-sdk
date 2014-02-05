//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYUriParser.h"

@implementation GLYUriParser

- (id)initWithBuffer:(NSString*)buffer
{
    if ( self = [super init] )
    {
        _glympses = [[NSMutableArray alloc] init];
        _requests = [[NSMutableArray alloc] init];
        _groups = [[NSMutableArray alloc] init];
        
        // TODO:
    }
    return self;
}

- (BOOL)hasGlympseOrGroup
{
    return ( _glympses.count > 0 ) || ( _groups.count > 0 );
}

@end
