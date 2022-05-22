package org.d3if6706201141.assessment2_iheart.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if6706201141.assessment2_iheart.db.IHeartDao

class HistoriViewModel(private val db: IHeartDao) : ViewModel() {
    val data = db.getLastDetakJantung()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}