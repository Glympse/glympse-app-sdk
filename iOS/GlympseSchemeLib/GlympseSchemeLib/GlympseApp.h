//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import <Foundation/Foundation.h>

#import "GLYCreateGlympseParams.h"
#import "GLYViewGlympseParams.h"

@interface GlympseApp : NSObject

/**
 * Returns true if we can either create a Glympse or install Glympse on this system.
 */
+ (BOOL)canCreateGlympse;

/**
 * Return true if we can either view a Glympse or install Glympse on this system.
 * The includeWeb flag will use the Glympse app if it is installed and then
 * check to see if we can view this in the web.
 */
+ (BOOL)canViewGlympse:(BOOL)includeWeb;

/**
 * Launches Glympse application in "create a glympse" mode.
 */
+ (BOOL)createGlympse:(GLYCreateGlympseParams*)params;

/**
 * Launches Glympse application in "view a glympse" mode.
 */
+ (BOOL)viewGlympse:(BOOL)includeWeb params:(GLYViewGlympseParams*)params;

@end
