package org.d3if6706201141.assessment2_iheart.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detakJantung")
data class IHeartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var denyut: Float,
    var isAtlet: Boolean
)
