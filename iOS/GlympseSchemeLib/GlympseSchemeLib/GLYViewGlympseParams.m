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

- (BOOL)isValid
{
    return YES;
}

- (NSURL*)toGlympseURL;
{
    NSMutableString* uriString = [NSMutableString string];
    [uriString appendString:GLYLaunchUriScheme];
    [self appendCodes:uriString];
    return [NSURL URLWithString:uriString];

}

- (NSURL*)toWebURL
{
    NSMutableString* uriString = [NSMutableString string];
    [uriString appendString:GLYWebUri];
    [self appendCodes:uriString];
    return [NSURL URLWithString:uriString];
}

- (void)appendCodes:(NSMutableString*)uriString
{
    char arg = '?';
    for ( NSString* code in _codes )
    {
        [uriString appendFormat:@"%c%@", arg, code];
        arg = '&';
    }
}

@end
