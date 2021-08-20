package org.matamem.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _link = MutableLiveData<String>().apply {
        value = "https://motamem.org/%d9%86%d9%85%d8%a7%db%8c%d8%b4-%d9%be%d8%b1%d9%88%d9%81%d8%a7%db%8c%d9%84-%da%a9%d8%a7%d8%b1%d8%a8%d8%b1%db%8c/"
    }
    val link: LiveData<String> = _link
}