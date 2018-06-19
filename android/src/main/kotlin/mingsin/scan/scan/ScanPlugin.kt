package mingsin.scan.scan

import android.content.Intent
import com.google.zxing.integration.android.IntentIntegrator
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.PluginRegistry.Registrar

class ScanPlugin(private val registrar: Registrar) : MethodCallHandler {
    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "scan")
            channel.setMethodCallHandler(ScanPlugin(registrar))
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            "codeScan" -> {
                if (listener.result == null) {
                    registrar.addActivityResultListener(listener)
                }
                listener.result = result
                IntentIntegrator(registrar.activity())
                        .setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
                        .initiateScan()
            }
            else ->
                result.notImplemented()
        }

    }

    private val listener = object : PluginRegistry.ActivityResultListener {
        var result: Result? = null
        override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?): Boolean {
            val intentResult = IntentIntegrator.parseActivityResult(requestCode, intent)
            result?.success(intentResult.contents)
            return true
        }

    }
}
