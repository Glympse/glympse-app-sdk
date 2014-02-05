//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GlympseApp.h"

@implementation GlympseApp

+ (BOOL)canCreateGlympse
{
    return NO;
}

+ (BOOL)canViewGlympse:(BOOL)includeWeb
{
    return NO;
}

+ (BOOL)createGlympse:(GLYCreateGlympseParams*)createGlympseParams
{
    return NO;
}

+ (BOOL)viewGlympse:(BOOL)includeWeb params:(GLYViewGlympseParams*)viewGlympseParams
{
    return NO;
}

@end
