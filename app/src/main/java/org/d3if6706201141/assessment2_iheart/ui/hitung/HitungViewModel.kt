package org.d3if6706201141.assessment2_iheart.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if6706201141.assessment2_iheart.db.IHeartDao
import org.d3if6706201141.assessment2_iheart.db.IHeartEntity
import org.d3if6706201141.assessment2_iheart.model.HasilDetakJantung
import org.d3if6706201141.assessment2_iheart.model.KategoriDetakJantung
import org.d3if6706201141.assessment2_iheart.model.iheartApp

class HitungViewModel(private val db: IHeartDao) : ViewModel() {
    private val hasilDetakJantung = MutableLiveData <HasilDetakJantung?>()
    private val navigasi = MutableLiveData<KategoriDetakJantung?>()

    fun iheartApp(denyut: Float, isAtlet: Boolean) {
        val dataDetakJantung = IHeartEntity(
            denyut = denyut,
            isAtlet = isAtlet
        )

        hasilDetakJantung.value = dataDetakJantung.iheartApp()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                db.insert(dataDetakJantung)
            }
        }

    }
    fun getHasilDetakJantung(): LiveData<HasilDetakJantung?> = hasilDetakJantung

    fun mulaiNavigasi() {
        navigasi.value = hasilDetakJantung.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }
    fun getNavigasi() : LiveData<KategoriDetakJantung?> = navigasi
}