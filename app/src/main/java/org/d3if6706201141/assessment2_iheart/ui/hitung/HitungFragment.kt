package org.d3if6706201141.assessment2_iheart.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if6706201141.assessment2_iheart.R
import org.d3if6706201141.assessment2_iheart.databinding.FragmentHitungBinding
import org.d3if6706201141.assessment2_iheart.db.IHeartDb
import org.d3if6706201141.assessment2_iheart.model.HasilDetakJantung
import org.d3if6706201141.assessment2_iheart.model.KategoriDetakJantung

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = IHeartDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
            R.id.menu_foodrink -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_foodrinkFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {


        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.hitungButton.setOnClickListener {
            iheartApp()
        }

        binding.saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
        binding.shareButton.setOnClickListener { shareData() }

        viewModel.getHasilDetakJantung().observe(requireActivity(), { showResult(it) })

        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(HitungFragmentDirections
                .actionHitungFragmentToSaranFragment(it))
            viewModel.selesaiNavigasi()
        })

        binding.resetButton.setOnClickListener {
            reset()
        }
    }

    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val tipe = if (selectedId == R.id.atletRadioButton)
            getString(R.string.atlet)
        else
            getString(R.string.nonAtlet)

        val message = getString(R.string.bagikan_template,
            binding.denyutInp.text,
            tipe,
            binding.detakJantungTextView.text,
            binding.kategoriTextView.text
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

    private fun iheartApp(denyut: Float, isAtlet: Boolean): HasilDetakJantung{
        val detakJantung = denyut * 4
        val kategori = getKategori(detakJantung, isAtlet)
        return HasilDetakJantung(detakJantung, kategori)
    }

    private fun showResult(result: HasilDetakJantung?) {
        if (result == null) return
        binding.detakJantungTextView.text = getString(R.string.detakJantung_x, result.detakJantung)
        binding.kategoriTextView.text = getString(R.string.kategori_x,
            getKategoriLabel(result.kategori))
        binding.buttonGroup.visibility = View.VISIBLE
    }


    private fun iheartApp() {
        val denyut = binding.denyutInp.text.toString()
        if (TextUtils.isEmpty(denyut)) {
            Toast.makeText(context, R.string.denyut_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.tipe_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.iheartApp(
            denyut.toFloat(),
            selectedId == R.id.atletRadioButton
        )
    }

    private fun getKategori(detakJantung: Float, isAtlet: Boolean): KategoriDetakJantung {
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
        return kategori
    }

    private fun getKategoriLabel(kategori: KategoriDetakJantung): String {
        val stringRes = when (kategori) {
            KategoriDetakJantung.LAMBAT-> R.string.lambat
            KategoriDetakJantung.CEPAT -> R.string.cepat
            KategoriDetakJantung.NORMAL-> R.string.normal
        }
        return getString(stringRes)
    }

    private fun reset() {
        val denyut = binding.denyutInp.text.toString()
        val selectedId = binding.radioGroup.checkedRadioButtonId

        binding.denyutInp.setText("")
        binding.radioGroup.clearCheck()
        binding.detakJantungTextView.setText("")
        binding.kategoriTextView.setText("")

        binding.denyutInp.requestFocus()
        binding.denyutInp.clearFocus()
    }
}