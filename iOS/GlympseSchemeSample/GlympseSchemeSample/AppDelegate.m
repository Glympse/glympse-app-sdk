//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "AppDelegate.h"
#import "MainViewController.h"
#import "GLYCreateGlympseResult.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    self.window.backgroundColor = [UIColor whiteColor];
    self.window.rootViewController = [[MainViewController alloc] initWithNibName:@"MainViewController" bundle:nil];
    [self.window makeKeyAndVisible];
    return YES;
}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    if ( [[url scheme] isEqualToString:@"glydemoschemeresult"] )
    {
        GLYCreateGlympseResult *reply = [[GLYCreateGlympseResult alloc] initWithUriString:url.absoluteString];

        NSString *replyFormatted = [NSString stringWithFormat:@"Recipients: %@\nDuration: %lld mins",
                                    [reply.recipients componentsJoinedByString:@", "], (reply.duration / (60 * 1000))];
        
        [[[UIAlertView alloc] initWithTitle:@"Glympse Created" message:replyFormatted
                                   delegate:nil cancelButtonTitle:nil otherButtonTitles:@"OK", nil] show];
        return YES;
    }
    else if ( [[url scheme] isEqualToString:@"glydemoschemecancel"] )
    {
        [[[UIAlertView alloc] initWithTitle:@"Glympse Cancelled" message:@"User cancelled."
                                   delegate:nil cancelButtonTitle:nil otherButtonTitles:@"OK", nil] show];
        return YES;
    }
    return NO;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
