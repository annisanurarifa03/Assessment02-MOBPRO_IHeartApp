package org.d3if6706201141.assessment2_iheart.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if6706201141.assessment2_iheart.R
import org.d3if6706201141.assessment2_iheart.databinding.ItemHistoriBinding
import org.d3if6706201141.assessment2_iheart.db.IHeartEntity
import org.d3if6706201141.assessment2_iheart.model.KategoriDetakJantung
import org.d3if6706201141.assessment2_iheart.model.iheartApp
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : ListAdapter<IHeartEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IHeartEntity>() {
            override fun areItemsTheSame(oldData: IHeartEntity, newData: IHeartEntity): Boolean {
                return oldData.id == newData.id
            }

            override fun areContentsTheSame(oldData: IHeartEntity, newData: IHeartEntity): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

        fun bind(item: IHeartEntity) = with(binding) {
            val hasilDetakJantung = item.iheartApp()
            kategoriTextView.text = hasilDetakJantung.kategori.toString().substring(0, 1)
            val colorRes = when(hasilDetakJantung.kategori) {
                KategoriDetakJantung.CEPAT -> R.color.cepat
                KategoriDetakJantung.LAMBAT -> R.color.lambat
                else -> R.color.normal
            }
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))

            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            detakJantungTextView.text = root.context.getString(R.string.hasil_x,
                hasilDetakJantung.detakJantung, hasilDetakJantung.kategori.toString())

            val tipe = root.context.getString(
                if (item.isAtlet) R.string.atlet else R.string.nonAtlet)
            dataTextView.text = root.context.getString(R.string.data_x,
                item.denyut, tipe)
        }
    }
}