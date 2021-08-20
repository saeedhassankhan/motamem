package org.matamem

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class G : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}