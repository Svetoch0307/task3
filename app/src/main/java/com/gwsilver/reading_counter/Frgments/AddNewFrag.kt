package com.gwsilver.reading_counter.Frgments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.gwsilver.reading_counter.*
import com.gwsilver.reading_counter.databinding.FragmentAddNewBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNewFrag : Fragment() {

    lateinit var binding: FragmentAddNewBinding
    private var PrevCounter = 0
    private var PrevTaif = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddNewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ADDNEWFRAG = this
        MAIN.onCreateAdd()
        button()
    }
    companion object {
        @JvmStatic
        fun newInstance() = AddNewFrag()
    }

    private fun button(){

        binding.btSave.setOnClickListener {

        val NewCounter = if (binding.etNewCounter.text.toString().toIntOrNull() != null) {
            binding.etNewCounter.text.toString().toInt()
        } else 0

        val etTarif = if (binding.etTarif.text.toString().toFloatOrNull() != null) {
            binding.etTarif.text.toString().toFloat()
        }else PrevTaif

        if (NewCounter > PrevCounter) {
            MAIN.SaveAdd(NewCounter, etTarif)
            } else{
                binding.cdEror.visibility = View.VISIBLE

            binding.etNewCounter.setOnClickListener {
                binding.cdEror.visibility = View.GONE
            }
            binding.cdEror.setOnClickListener {
                binding.cdEror.visibility = View.GONE
            }
            binding.flAdd.setOnClickListener {
                binding.cdEror.visibility = View.GONE
            }
        }
        }
        binding.btCancel.setOnClickListener {
            MAIN.RunList()
        }
    }
    fun ReadAaaFrag(prevCounter: Int, prevTarif: Float, prevDate: String) {

        PrevCounter = prevCounter
        PrevTaif = prevTarif
        val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
        val date: String = sdf.format(Date())

        var CountInt = prevCounter
        var CountString = ""
        val CA = IntArray(6)

        for (i in 0..5) {
            CA[i] = CountInt % 10
            CountInt /= 10
        }
        for (i in 5 downTo 0) CountString += "${CA[i]}"

        binding.tvPrevCouter.text = CountString
        binding.tvPrevData.text = prevDate
        binding.tvNewData.text = date
        binding.etTarif.hint = PrevTaif.toString()
    }
}