//------------------------------------------------------------------------------
//
// Copyright (c) 2014 Glympse Inc.  All rights reserved.
//
//------------------------------------------------------------------------------

#import "GLYUriParser.h"

NSString* GLYCreateUriScheme = @"glympse3:";
NSString* GLYLaunchUriScheme = @"glympse3:";
NSString* GLYWebUri = @"http://glympse.com/";
NSString* GLYGlympseHostPattern = @"glympse.";

@implementation GLYUriParser

- (id)initWithBuffer:(NSString*)buffer
{
    if ( self = [super init] )
    {
        _glympses = [[NSMutableArray alloc] init];
        _requests = [[NSMutableArray alloc] init];
        _groups = [[NSMutableArray alloc] init];
        
        [self extractUris:buffer];
    }
    return self;
}

- (BOOL)hasGlympseOrGroup
{
    return ( _glympses.count > 0 ) || ( _groups.count > 0 );
}

+ (NSURL*)glympseCreateUri
{
    return [NSURL URLWithString:GLYCreateUriScheme];
}

+ (NSURL*)glympseLaunchUri
{
    return [NSURL URLWithString:GLYLaunchUriScheme];
}

- (void)extractUris:(NSString*)buffer
{
    NSDataDetector* detector = [NSDataDetector dataDetectorWithTypes:NSTextCheckingTypeLink error:nil];
    NSArray* matches = [detector matchesInString:buffer options:0 range:NSMakeRange(0, [buffer length])];
    for ( NSTextCheckingResult* result in matches )
    {
        [self processUrl:result.URL];
    }
}

- (void)processUrl:(NSURL*)url
{
    if ( nil == url )
    {
        return;
    }
    
    if ( NSNotFound == [url.host rangeOfString:GLYGlympseHostPattern].location )
    {
        return;
    }
    
    // Check query component of URL for possible invite codes
    NSString *query = url.query;
    if ( nil != query )
    {
        NSDictionary* arguments = [GLYUriParser parseQueryString:query];
        for ( NSString* argument in [arguments allKeys] )
        {
            [self processCode:argument];
        }
    }
    
    // Check last path component of URL for possible invite code
    NSString *lastComponent = [url.pathComponents lastObject];
    if ( nil != lastComponent && ![lastComponent isEqualToString:@"/"] )
    {
        [self processCode:lastComponent];
    }
}

- (void)processCode:(NSString*)code
{
    if ( nil == code )
    {
        return;
    }
    
    // Check for a group name.
    if ( [code hasPrefix:@"!"] )
    {
        [_groups addObject:code];
    }
    else
    {
        // Convert the glympse code string to a long value.
        long long codeValue = [GLYUriParser base32ToLong:code];
        
        // Check if this is a normal glympse code.
        if ( [GLYUriParser isGlympseCode:codeValue] )
        {
            [_glympses addObject:code];
        }
        
        // Check if this is a glympse request code.
        else if ( [GLYUriParser isRequestCode:codeValue] )
        {
            [_requests addObject:code];
        }
    }
}

#pragma mark - Glympse invite code manipulations

+ (BOOL)isGlympseCode:(long long)code
{
    return ((0 != code) && (0 == ((int)(code >> 35) & 0x3L)));
}

+ (BOOL)isRequestCode:(long long)code
{
    return (1 == ((int)(code >> 35) & 0x3L));
}

static const int GLYBase32Decode[] = {
     0,  1,  2,  3,  4,  5,  6,  7,  8,  9,  // 0 1 2 3 4 5 6 7 8 9
    -1, -1, -1, -1, -1, -1, -1,              // : ; < = > ? @
    10, 11, 12, 13, 14, 15, 16, 17,  1,      // A B C D E F G H I
    18, 19,  1, 20, 21,  0, 22, 23, 24,      // J K L M N O P Q R
    25, 26, 27, 27, 28, 29, 30, 31,          // S T U V W X Y Z
    -1, -1, -1, -1, -1, -1,                  // [ \ ] ^ _ `
    10, 11, 12, 13, 14, 15, 16, 17,  1,      // a b c d e f g h i
    18, 19,  1, 20, 21,  0, 22, 23, 24,      // j k l m n o p q r
    25, 26, 27, 27, 28, 29, 30, 31           // s t u v w x y z
};

static const int GLYMinimumCodeLength = 6;
static const int GLYMaximumCodeLength = 128;

+ (long long)base32ToLong:(NSString*)str
{
    // Validate input.
    NSUInteger length = str.length;
    if ( length <= 0 )
    {
        return 0;
    }
    if ( ( length < GLYMinimumCodeLength ) || ( length > GLYMaximumCodeLength ) )
    {
        return 0;
    }
    
    long long result = 0;
    for ( NSUInteger index = 0 ; index < length ; ++index )
    {
        unichar ch = [str characterAtIndex:index];
        
        // We skip dashes. They are allowed, but aren't parsed as digits.
        if ( '-' != ch )
        {
            // Convert the character into its value.
            long long cur = ((ch >= 48) && (ch <= 122)) ? GLYBase32Decode[(int)ch - 48] : -1;
            
            // If we got back -1, then the character is not valid.
            if ( cur < 0 )
            {
                return 0;
            }
            
            // Add this value into our tally.
            result = ( result << 5 ) + cur;
        }
    }
    return result;
}

#pragma mark - Data formats

+ (NSString*)toJsonString:(id)jsonObject
{
    NSData* jsonData = [NSJSONSerialization dataWithJSONObject:jsonObject options:0 error:NULL];
    return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}

+ (id)toJsonObject:(NSString*)jsonString
{
    NSData* jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
    return [NSJSONSerialization JSONObjectWithData:jsonData options:0 error:NULL];
}

#pragma mark - URI helpers

+ (NSDictionary*)parseQueryString:(NSString*)query
{
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] init];
    NSArray* pairs = [query componentsSeparatedByString:@"&"];
    
    for ( NSString* pair in pairs )
    {
        NSArray* elements = [pair componentsSeparatedByString:@"="];
        NSString* key = nil;
        NSString* val = nil;
        if ( 2 == elements.count )
        {
            key = [[elements objectAtIndex:0] stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            val = [[elements objectAtIndex:1] stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        }
        else if ( 1 == elements.count )
        {
            key = [[elements objectAtIndex:0] stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            val = key;
        }
        if ( key && val )
        {
            [dict setObject:val forKey:key];
        }
    }
    return dict;
}


static const char URLSAFE[256] =
{
    /*      0 1 2 3  4 5 6 7  8 9 A B  C D E F */
    /* 0 */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* 1 */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* 2 */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,1,0,0,
    /* 3 */ 1,1,1,1, 1,1,1,1, 1,1,0,0, 0,0,0,0,
    
    /* 4 */ 0,1,1,1, 1,1,1,1, 1,1,1,1, 1,1,1,1,
    /* 5 */ 1,1,1,1, 1,1,1,1, 1,1,1,0, 0,0,0,1,
    /* 6 */ 0,1,1,1, 1,1,1,1, 1,1,1,1, 1,1,1,1,
    /* 7 */ 1,1,1,1, 1,1,1,1, 1,1,1,0, 0,0,0,0,
    
    /* 8 */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* 9 */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* A */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* B */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    
    /* C */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* D */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* E */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
    /* F */ 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0
};

+ (NSString*)urlEncode:(NSString*)str
{
    //NSString and CFString % encoding helpers do not handle & or / correctly, so we do it ourselves:
    const char DEC2HEX[16 + 1] = "0123456789ABCDEF";
    const char * cstr = [str UTF8String];
    const unsigned char * pSrc = (const unsigned char *)cstr;
    const size_t SRC_LEN = strlen(cstr);
    unsigned char * const pStart = malloc((SRC_LEN * 3 + 1) * sizeof(unsigned char));
    unsigned char * pEnd = pStart;
    const unsigned char * const SRC_END = pSrc + SRC_LEN;
    for (; pSrc < SRC_END; ++pSrc)
	{
		if ( URLSAFE[*pSrc] )
        {
            *pEnd++ = *pSrc;
        }
        else
        {
            // escape this char
            *pEnd++ = '%';
            *pEnd++ = DEC2HEX[*pSrc >> 4];
            *pEnd++ = DEC2HEX[*pSrc & 0x0F];
        }
	}
    // Put string terminator.
    *pEnd = '\0';
    // Copy the temporary c-str to a new, autoreleasing NSString
    NSString *output = [NSString stringWithUTF8String:(char*)pStart];
    free(pStart);
    return output;
}

+ (NSString*)urlDecode:(NSString*)str
{
    return [str stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
}

@end
