//------------------------------------------------------------------------------
//
//  Copyright (c) 2014 Glympse. All rights reserved.
//
//------------------------------------------------------------------------------

#import "MainViewController.h"
#import "GlympseApp.h"
#import "GLYRecipient.h"

@implementation MainViewController

#pragma mark - Object Lifecycle

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self)
    {
    }
    return self;
}

#pragma mark - View Lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // If for some reason we cannot create and/or view a Glympse, then we disable the buttons for those actions.
    self.createButton.enabled = [GlympseApp canCreateGlympse];
    self.viewButton.enabled = [GlympseApp canViewGlympse:YES];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
}


- (IBAction)createButton_TouchUpInside:(id)sender
{
    // Allocate a CreateGlympseParams object.
    GLYCreateGlympseParams *glympseCreateParams = [[GLYCreateGlympseParams alloc] init];
    
    // Since we are just creating a "link" recipient, we hide the recipient chooser.
    glympseCreateParams.flags = GLYFlagRecipientsHidden;
    
    // Grab the default subtype from our UI that we would like to use.
    NSString *subtype = [self.subtypeField.text stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
    // Grab the default brand from our UI that we would like to use.
    NSString *brand = [self.brandField.text stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
    // Specify that we want a single "app" recipient.
    [glympseCreateParams.recipients addObject:
     [[GLYRecipient alloc] initWithType:GLYRecipientTypeApp subtype:subtype brand:brand name:NULL address:NULL]];
    
    NSInteger minutes = [self.minutesField.text integerValue];
    glympseCreateParams.duration = (minutes * 60 * 1000);
    
    // Invoke the Glympse-app to create this Glympse, if possible, with these parameters
    if ( ![GlympseApp createGlympse:glympseCreateParams] )
    {
        [[[UIAlertView alloc] initWithTitle:@"Error" message:@"Glympse failed to handle the passed Create parameters."
                                   delegate:nil cancelButtonTitle:nil otherButtonTitles:@"OK", nil] show];
    }
}

- (IBAction)viewButton_TouchUpInside:(id)sender
{
    // Grab the text buffer from our UI that we would like to parse for Glympse URLs.
    NSString *buffer = self.sampleTextField.text;
    
    // Parse the buffer and check to see if it has any Glympse URLs that contain glympse codes or group names.
    GLYUriParser *parseBufferResult = [[GLYUriParser alloc] initWithBuffer:buffer];
    if ([parseBufferResult hasGlympseOrGroup])
    {
        // Allocate a ViewGlympseParams object and tell it what we want to view.
        GLYViewGlympseParams *glympseViewParams = [[GLYViewGlympseParams alloc] init];
        [glympseViewParams addAllGlympsesAndGroups:parseBufferResult];

        
        // Generate a "view a glympse" Intent and start the activity for it.
        if ( ![GlympseApp viewGlympse:YES params:glympseViewParams] )
        {
            [[[UIAlertView alloc] initWithTitle:@"Error" message:@"Glympse failed to handle the passed View parameters."
                                       delegate:nil cancelButtonTitle:nil otherButtonTitles:@"OK", nil] show];
        }
    }
}

@end
