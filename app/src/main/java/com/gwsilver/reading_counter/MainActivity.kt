package com.gwsilver.reading_counter


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwsilver.reading_counter.Frgments.AddNewFrag
import com.gwsilver.reading_counter.databinding.ActivityMainBinding
import com.gwsilver.reading_counter.databinding.FragmentListBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var BindingA : ActivityMainBinding
    lateinit var BindingL : FragmentListBinding
    var pref: SharedPreferences? = null

    private val adapter = CounterAdapter()
    var List = ArrayList<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        MAIN = this
        BindingA = ActivityMainBinding.inflate(layoutInflater)
        BindingL = FragmentListBinding.inflate(layoutInflater)

        loadingData()
        RunList()
        button()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
    }

    fun RunList(){

        setContentView(BindingL.root)

        BindingL.rcViev.layoutManager = LinearLayoutManager(this)
        BindingL.rcViev.adapter = adapter
        adapter.addAll(List)
    }

    private fun button(){

        BindingL.btAdd.setOnClickListener {

            setContentView(BindingA.root)
            supportFragmentManager.beginTransaction().
            replace(R.id.frZone, AddNewFrag.newInstance()).commit()
        }
    }

    fun SaveAdd(counterInt: Int,tarifFloat: Float) {
        val ListReversed = ArrayList<ListItem>()
        val rashodC = if (List.size > 0) {counterInt - List[0].counterInt
        }else 0
        val rashodM = rashodC * tarifFloat

        val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
        val date: String = sdf.format(Date())

        ListReversed.addAll(List.reversed())
        ListReversed.add(ListItem(counterInt, rashodC, tarifFloat,rashodM,date))
        List.clear()
        List.addAll(ListReversed.reversed())
        RunList()
    }

    fun onCreateAdd() {
        val prevCounter = if (List.size > 0) {List[0].counterInt
        }else 0

        val prevTarif = if (List.size > 0) {List[0].tarifFloat
        }else 0.00f

        val prevDate = if (List.size > 0) {List[0].dateString
        }else "- -  - - -.  - - - -  -  - - : - -"

        ADDNEWFRAG.ReadAaaFrag(prevCounter, prevTarif,prevDate)
    }

    fun DeleteElement(position: Int) {

        if(position > 1 && position < List.size - 2) {
            List[position - 1].rashodCounterInt =
                List[position - 1].counterInt - List[position + 1].counterInt
            List[position - 1].rashodMoneyFloat =
                List[position - 1].rashodCounterInt * List[position - 1].tarifFloat
        }

        List.removeAt(position)
        RunList()
    }

    private fun loadingData() {
        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)
        val ListSize = pref?.getInt(List_Size,0)!!

        if (ListSize > 0) {
            for (i in 0 until ListSize) {
                List.add(
                    ListItem(
                        pref?.getInt("counter$i", 0)!!,
                        pref?.getInt("rashodCounter$i", 0)!!,
                        pref?.getFloat("tarif$i", 0.0f)!!,
                        pref?.getFloat("rashodMoney$i", 0.0f)!!,
                        pref?.getString("date$i", "- -  - - -.  - - - -  -  - - : - -")!!
                    )
                )
            }
        }
    }
    private fun saveData() {
        val editor = pref?.edit()
        editor?.putInt(List_Size, List.size)

       if (List.size > 0) {
            for (i in 0 until List.size) {
                editor?.putInt("counter$i", List[i].counterInt)
                editor?.putInt("rashodCounter$i", List[i].rashodCounterInt)
                editor?.putFloat("tarif$i", List[i].tarifFloat)
                editor?.putFloat("rashodMoney$i", List[i].rashodMoneyFloat)
                editor?.putString("date$i", List[i].dateString)
            }
        }
        editor?.apply()
    }
}