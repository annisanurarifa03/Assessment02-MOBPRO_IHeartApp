package org.d3if6706201141.assessment2_iheart.ui

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if6706201141.assessment2_iheart.R
import org.d3if6706201141.assessment2_iheart.databinding.FragmentSaranBinding
import org.d3if6706201141.assessment2_iheart.model.KategoriDetakJantung


class SaranFragment : Fragment() {
    private lateinit var binding: FragmentSaranBinding
    private val args: SaranFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUI(kategori: KategoriDetakJantung) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriDetakJantung.CEPAT -> {
                actionBar?.title = getString(R.string.judul_cepat)
                binding.imageView.setImageResource(R.drawable.cepat)
                binding.textView.text = getString(R.string.saran_cepat)
            }
            KategoriDetakJantung.LAMBAT -> {
                actionBar?.title = getString(R.string.judul_lambat)
                binding.imageView.setImageResource(R.drawable.lambat)
                binding.textView.text = getString(R.string.saran_lambat)
            }
            KategoriDetakJantung.NORMAL -> {
                actionBar?.title = getString(R.string.judul_normal)
                binding.imageView.setImageResource(R.drawable.normal)
                binding.textView.text = getString(R.string.saran_normal)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI(args.kategori)
    }
}