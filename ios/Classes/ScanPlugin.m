#import "ScanPlugin.h"
#import <scan/scan-Swift.h>

@implementation ScanPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftScanPlugin registerWithRegistrar:registrar];
}
@end
