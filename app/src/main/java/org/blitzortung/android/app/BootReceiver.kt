/*

   Copyright 2015 Andreas Würl

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

package org.blitzortung.android.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import org.blitzortung.android.app.Main.Companion.LOG_TAG

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            Log.v(LOG_TAG, "BootReceiver.onReceive() intent action: ${intent.action}")

            val bootIntent = Intent(context, AppService::class.java)
            bootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(bootIntent);
                } else {
                    context.startService(bootIntent);
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "BootReceiver.onReceive() start service failed after boot completed", e)
            }
        } else {
            Log.v(LOG_TAG, "BootReceiver.onReceive() invalid intent action: ${intent.action}")
        }
    }
}