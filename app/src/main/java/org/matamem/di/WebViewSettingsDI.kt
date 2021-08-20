package org.matamem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.matamem.WebViewSettings


@Module
@InstallIn(ActivityComponent::class)
class WebViewSettingsDI {

    @Provides
    fun getWebViewSettings() : WebViewSettings {
        return WebViewSettings()
    }
}