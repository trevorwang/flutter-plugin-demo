import 'dart:async';

import 'package:flutter/services.dart';

class Scan {
  static const MethodChannel _channel = const MethodChannel('scan');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> codeScan() async {
    return await _channel.invokeMethod('codeScan');
  }
}
