package org.d3if6706201141.assessment2_iheart.model

import org.d3if6706201141.assessment2_iheart.db.IHeartEntity

fun IHeartEntity.iheartApp(): HasilDetakJantung {
    val detakJantung = denyut * 4
    val kategori = if (isAtlet) {
        when {
            detakJantung < 40.0 -> KategoriDetakJantung.LAMBAT
            detakJantung > 60.0 -> KategoriDetakJantung.CEPAT
            else -> KategoriDetakJantung.NORMAL
        }
    } else {
        when {
            detakJantung < 60.0 -> KategoriDetakJantung.LAMBAT
            detakJantung > 100.0 -> KategoriDetakJantung.CEPAT
            else -> KategoriDetakJantung.NORMAL
        }
    }
    return HasilDetakJantung(detakJantung, kategori)
}