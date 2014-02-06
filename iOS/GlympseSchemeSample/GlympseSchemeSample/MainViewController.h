//------------------------------------------------------------------------------
//
//  Copyright (c) 2014 Glympse. All rights reserved.
//
//------------------------------------------------------------------------------

#import <UIKit/UIKit.h>

@interface MainViewController : UIViewController

@property (weak, nonatomic) IBOutlet UIButton *createButton;
@property (weak, nonatomic) IBOutlet UIButton *viewButton;

@property (weak, nonatomic) IBOutlet UITextField *subtypeField;
@property (weak, nonatomic) IBOutlet UITextField *brandField;
@property (weak, nonatomic) IBOutlet UITextField *minutesField;

@property (weak, nonatomic) IBOutlet UITextView *sampleTextField;


- (IBAction)createButton_TouchUpInside:(id)sender;
- (IBAction)viewButton_TouchUpInside:(id)sender;

@end
