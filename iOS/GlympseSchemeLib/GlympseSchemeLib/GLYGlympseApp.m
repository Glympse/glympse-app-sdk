//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYGlympseApp.h"

#import <UIKit/UIKit.h>

@implementation GLYGlympseApp

+ (BOOL)canOpenURL:(NSURL*)url
{
    return [[UIApplication sharedApplication] canOpenURL:url];
}

+ (BOOL)canCreateGlympse
{
    return [GLYGlympseApp canOpenURL:[GLYUriParser glympseCreateUri]];
}

+ (BOOL)canViewGlympse:(BOOL)includeWeb
{
    return includeWeb || [GLYGlympseApp canOpenURL:[GLYUriParser glympseCreateUri]];
}

+ (BOOL)createGlympse:(GLYCreateGlympseParams*)params
{
    // Check if Glympse application is installed and exposes proper interface for creating Glympses externally.
    // Also make sure we were passed a create glympse params and that it looks valid.
    if ( ![GLYGlympseApp canCreateGlympse] || ![params isValid] )
    {
        return NO;
    }
    
    // Launch Glympse application.
    return [[UIApplication sharedApplication] openURL:[params toGlympseURL]];
}

+ (BOOL)viewGlympse:(BOOL)includeWeb params:(GLYViewGlympseParams*)params
{
    // Also make sure we were passed a view glympse params and that it looks valid.
    if ( ![params isValid] )
    {
        return NO;
    }
    
    // Check if Glympse application is installed and can viewing Glympses can be initiated externally.
    if ( [GLYGlympseApp canViewGlympse:NO] )
    {
        // Launch Glympse application.
        return [[UIApplication sharedApplication] openURL:[params toGlympseURL]];
    }
    
    // Check if Glympse can be viewed on the web.
    if ( [GLYGlympseApp canViewGlympse:includeWeb] )
    {
        // Launch browser application.
        return [[UIApplication sharedApplication] openURL:[params toWebURL]];
    }
    
    return NO;
}

@end
