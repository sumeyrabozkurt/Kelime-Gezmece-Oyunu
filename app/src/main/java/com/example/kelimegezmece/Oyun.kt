package com.example.kelimegezmece

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_oyun.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Suppress("DEPRECATION")
class Oyun : AppCompatActivity(), View.OnClickListener {

    var level1Games = HashMap<Int, ArrayList<String>>()
    var level2Games = HashMap<Int, ArrayList<String>>()
    var level3Games = HashMap<Int, ArrayList<String>>()

    var curentGameWords = ArrayList<String>()
    var visited = ArrayList<Boolean>(10)
    var choosenWord =""

    lateinit var tableLayout: TableLayout
    lateinit var buttonStr :String
    private var runningTime = false
    var gameFinish = 0
    var levelFinish = 0
    var curentWrongWords = 0

    companion object{

        var level =1
        var level1Skor = 0
        var level2Skor = 0
        var level3Skor = 0
        var pauseOffset: Long = 0
        var lvl1PauseOfset:Long = 0
        var lvl2PauseOfset:Long = 0
        var lvl3PauseOfset:Long = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyun)

        level1Games = intent.extras!!.get("level1Games") as HashMap<Int, ArrayList<String>>
        level2Games = intent.extras!!.get("level2Games") as HashMap<Int, ArrayList<String>>
        level3Games = intent.extras!!.get("level3Games") as HashMap<Int, ArrayList<String>>

        lvlText.text = "LEVEL "+ level

        setVisibilityforButtons(level)
        getAnotherGameToShow()

        startChronometer()

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        kelimeyiUygulaBtn.setOnClickListener(this)
        changeButton.setOnClickListener(this)
        backButton.setOnClickListener (this)

    }

    override fun onClick(p0: View?) {

        when(p0!!.id){

            R.id.button1 -> {
                button1.setBackgroundDrawable(resources.getDrawable(R.drawable.choosenbutton))
                choosenWord += button1.text
                secilenKelime.text = choosenWord

            }
            R.id.button2 ->{
                button2.setBackgroundDrawable(resources.getDrawable(R.drawable.choosenbutton))
                choosenWord += button2.text
                secilenKelime.text = choosenWord
            }
            R.id.button3 ->{

            button3.setBackgroundDrawable(resources.getDrawable(R.drawable.choosenbutton))
            choosenWord += button3.text
            secilenKelime.text = choosenWord
        }
            R.id.button4 ->{

                button4.setBackgroundDrawable(resources.getDrawable(R.drawable.choosenbutton))
                choosenWord += button4.text
                secilenKelime.text = choosenWord
            }
            R.id.button5 ->{

                button5.setBackgroundDrawable(resources.getDrawable(R.drawable.choosenbutton))
                choosenWord += button5.text
                secilenKelime.text = choosenWord
            }
            R.id.button6 ->{

                button6.setBackgroundDrawable(resources.getDrawable(R.drawable.choosenbutton))
                choosenWord += button6.text
                secilenKelime.text = choosenWord
            }

            R.id.kelimeyiUygulaBtn ->{

                checkCorrectWords()
                choosenWord = ""
                secilenKelime.text = choosenWord
                button1.setBackgroundDrawable(resources.getDrawable(R.drawable.circlelayout))
                button2.setBackgroundDrawable(resources.getDrawable(R.drawable.circlelayout))
                button3.setBackgroundDrawable(resources.getDrawable(R.drawable.circlelayout))
                button4.setBackgroundDrawable(resources.getDrawable(R.drawable.circlelayout))
                button5.setBackgroundDrawable(resources.getDrawable(R.drawable.circlelayout))
                button6.setBackgroundDrawable(resources.getDrawable(R.drawable.circlelayout))

            }

            R.id.changeButton->{

                extractCharToButtons(buttonStr,level)

            }

            R.id.backButton->{

                pauseChronometer()
                showAlertDialog()

            }

        }

    }

    private fun getAnotherGameToShow(){

        if(levelFinish ==6){
            levelFinish = 0
            Toast.makeText(this,"LEVEL  $level TAMAMLANDI ..",Toast.LENGTH_LONG).show()
            setPauseOffsets()
           setScoreforLevels()
            curentWrongWords = 0
            level++
            lvlText.text = "LEVEL "+ level
            setVisibilityforButtons(level)
        }

        when(level){
            1->{
                Log.e("BİLDİRİM","Level 1 oyun seçiliyor")
                curentGameWords = level1Games[levelFinish]!!
                buttonStr = level1Games[levelFinish]!![0]
                extractCharToButtons(buttonStr,level)
            }
            2->{
                Log.e("BİLDİRİM","Level 1 oyun seçiliyor")
                curentGameWords = level2Games[levelFinish]!!
                buttonStr = level2Games[levelFinish]!![0]
                extractCharToButtons(buttonStr,level)

            }
            3->{
                Log.e("BİLDİRİM","Level 1 oyun seçiliyor")
                curentGameWords = level3Games[levelFinish]!!
                buttonStr = level3Games[levelFinish]!![0]
                extractCharToButtons(buttonStr,level)
            }
        }


        levelFinish++
        createTable()
        fillVisited()

    }

    private fun setScoreforLevels() {
        when(level){
            1->{
                level1Skor = curentWrongWords
            }
            2->{
                level2Skor = curentWrongWords
            }
            3->{
                level3Skor = curentWrongWords
            }
        }
    }

    private fun setPauseOffsets() {

        when(level){
            1->{
                pauseChronometer()
                lvl1PauseOfset = pauseOffset
                resetChronometer()
                startChronometer()
            }
            2->{
                pauseChronometer()
                lvl2PauseOfset = pauseOffset
                resetChronometer()
                startChronometer()
            }
            3->{
                pauseChronometer()
                lvl3PauseOfset = pauseOffset
                resetChronometer()
                startChronometer()
            }
        }

    }

    private fun setVisibilityforButtons(lvl: Int) {

        when(lvl){

            1->{

                button2.visibility = View.INVISIBLE
                button5.visibility = View.INVISIBLE
                button6.visibility = View.INVISIBLE

            }

            2->{

                button1.visibility = View.INVISIBLE
                button5.visibility = View.INVISIBLE
                button2.visibility = View.VISIBLE
                button6.visibility = View.VISIBLE

            }

            3->{

                button5.visibility = View.INVISIBLE
                button1.visibility = View.VISIBLE

            }

        }

    }

    private fun checkCorrectWords(){

        if(curentGameWords.contains(choosenWord)){

            var v = curentGameWords.indexOf(choosenWord)
            if(visited[v]){
                Log.e("BİLGİ=", "$v.index"+visited[v])
                Toast.makeText(this,"DAHA ÖNCE BULMACAYA EKLENDİ!",Toast.LENGTH_SHORT).show()
            }else{
                visited[v] = true
                Log.e("BİLGİ=", "$v.index"+visited[v])
                addTable(choosenWord)
                Toast.makeText(this,"BULMACAYA EKLENİYOR",Toast.LENGTH_SHORT).show()
            }

        }else{

            var eksilenSkor = buttonStr.length * curentGameWords.size
            curentWrongWords += eksilenSkor
            Toast.makeText(this,"YANLIŞ",Toast.LENGTH_SHORT).show()

        }

    }

    private fun fillVisited(){

        for(i in 0..curentGameWords.size-1){
            visited.add(i,false)
            Log.e("Visited", "$i.index = false")
        }

    }

    private fun extractCharToButtons(buttonStr: String,lvl: Int) {

        var strSize = buttonStr.length

        var list = ArrayList<Int>()
        for (i in 0 until strSize) {
            list.add(i)
        }
        list.shuffle()

        when(lvl){

            1->{

                Log.e("BUTTONSTR =",buttonStr)

                button1.text = buttonStr[list[0]].toString()
                button3.text = buttonStr[list[1]].toString()
                button4.text = buttonStr[list[2]].toString()
            }

            2->{
                Log.e("BUTTONSTR =",buttonStr)

                button2.text = buttonStr[list[0]].toString()
                button3.text = buttonStr[list[1]].toString()
                button4.text = buttonStr[list[2]].toString()
                button6.text = buttonStr[list[3]].toString()

            }

            3->{
                Log.e("BUTTONSTR =",buttonStr)

                button1.text = buttonStr[list[0]].toString()
                button2.text = buttonStr[list[1]].toString()
                button3.text = buttonStr[list[2]].toString()
                button4.text = buttonStr[list[3]].toString()
                button6.text = buttonStr[list[4]].toString()


            }

        }

    }

    private fun createTable() {

        var rows =curentGameWords.size

        tableLayout = findViewById(R.id.myTable)
        tableLayout.isShrinkAllColumns = true
        tableLayout.isStretchAllColumns = true

        for (i in 0 until rows) {

            var cols = curentGameWords[i].length

            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)



            for (j in 0 until cols) {

                val button = Button(this)
                button.setHintTextColor(Color.BLACK)
                button.setBackgroundResource(R.drawable.buttonshape)
                button.apply {
                    layoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                }
                row.addView(button)
            }
            tableLayout.addView(row)

        }
    }

    private fun addTable(nChoosenWord: String) {

        var index = curentGameWords.indexOf(nChoosenWord)
        var myRow = myTable.get(index) as TableRow
        for(i in 0 until nChoosenWord.length){
            var myButton = myRow.getVirtualChildAt(i) as Button
            myButton.hint = nChoosenWord[i].toString()
        }
        gameFinish++
        if(gameFinish == curentGameWords.size){
            myTable.removeAllViews()
            getAnotherGameToShow()
            gameFinish = 0
        }


    }

    private fun showAlertDialog() {

        var builder = AlertDialog.Builder(this)
        builder.setTitle("Oyundan Çıkmak üzeresiniz..")
        builder.setMessage("Seviyenizi kaydetmek ister misiniz?")
        builder.setPositiveButton("EVET") { dialogInterface: DialogInterface, i: Int ->

            Toast.makeText(this, "Oyun Kaydedildi!", Toast.LENGTH_LONG).show()

        }
        builder.setNegativeButton("HAYIR") { dialogInterface: DialogInterface, i: Int ->

            var intent = Intent (this@Oyun,MainActivity::class.java).apply {
                putExtra("tutulanZaman",pauseOffset)
            }
            startActivity(intent)

        }

        var dialog = builder.create()

        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.color1)))

    }

    private fun startChronometer (){

        if(!runningTime){
            timer.base = SystemClock.elapsedRealtime() - pauseOffset
            timer.start()
            runningTime = true
        }

    }

    private fun pauseChronometer (){

        if(runningTime){
            timer.stop()
            pauseOffset = SystemClock.elapsedRealtime() - timer.base
            runningTime = false
        }

    }

    private fun resetChronometer(){

        timer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0

    }

}
