package com.example.kelimegezmece

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    var level1Oyunlari =  HashMap<Int, ArrayList<String>>()
    var level2Oyunlari =  HashMap<Int, ArrayList<String>>()
    var level3Oyunlari =  HashMap<Int, ArrayList<String>>()

    var lvl1CurrentGames =  HashMap<Int, ArrayList<String>>()
    var lvl2CurrentGames =  HashMap<Int, ArrayList<String>>()
    var lvl3CurrentGames =  HashMap<Int, ArrayList<String>>()

    var MAX_SCORE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readyToGameHashMap(1)
        readyToGameHashMap(2)
        readyToGameHashMap(3)

        readyToCurrentGames(lvl1CurrentGames,level1Oyunlari)
        readyToCurrentGames(lvl2CurrentGames,level2Oyunlari)
        readyToCurrentGames(lvl3CurrentGames,level3Oyunlari)

        startButton.setOnClickListener {

            var intent = Intent(this@MainActivity, Oyun::class.java).apply {

                putExtra("level1Games", lvl1CurrentGames)
                putExtra("level2Games", lvl2CurrentGames)
                putExtra("level3Games", lvl3CurrentGames)

            }
            startActivity(intent)
        }

    }

    private fun readyToCurrentGames(myHashMap:  HashMap<Int, ArrayList<String>>, extractHashmap: HashMap<Int, ArrayList<String>>) {

        var list = ArrayList<Int>()
        for (i in 0..9) {
            list.add(i)
        }
        list.shuffle()
        var key =0
        for(j in 0..5){
            var rndNumber = list[j]
            if (myHashMap.containsKey(key)){

                var getAl = extractHashmap[rndNumber]
                myHashMap[key] = getAl!!

            }else{
               myHashMap[key] = extractHashmap[rndNumber]!!

            }
            key++

        }

    }

    private fun readyToGameHashMap(level :Int ) {

        var oyunKelimeleri = HashMap<Int,ArrayList<String>>()
        var r = resources

        lateinit var inputStream: InputStream

        when(level){
            1->{
                inputStream = r.openRawResource(R.raw.level1kelimeler)
                oyunKelimeleri = level1Oyunlari
            }
            2->{
                inputStream = r.openRawResource(R.raw.level2kelimeler)
                oyunKelimeleri = level2Oyunlari
            }
            3->{
                inputStream = r.openRawResource(R.raw.level3kelimeler)
                oyunKelimeleri = level3Oyunlari
            }
        }

        var nScanner = Scanner(inputStream)

        var line = nScanner.next()
        var word = ""
        var keyCount =0

        while (line != null) {
            try {

                for (i in 0 until line.length){

                    if(line[i] == ';'){

                        if (oyunKelimeleri.containsKey(keyCount)){

                            var getAl = oyunKelimeleri[keyCount]
                            getAl!!.add(word)
                            word = ""
                            oyunKelimeleri[keyCount] = getAl

                        }else{

                            var newAl = ArrayList<String>()
                            newAl.add(word)
                            word=""
                            oyunKelimeleri[keyCount] = newAl

                        }
                    }else{
                        word += line[i]
                    }

                }
                keyCount++
                line = nScanner.next()

        } catch (e: NoSuchElementException) {
            Log.e("HATA", e.message.toString())
            line = null
        }
    }
        nScanner.close()

    }

    override fun onCreateOptionsMenu(menu : Menu?): Boolean {

        menuInflater.inflate(R.menu.anamenu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId

        when(id){

            R.id.level1Skor ->{

                var time = Oyun.lvl1PauseOfset.toInt()
                var saniye =time/1000
                var score = MAX_SCORE - saniye - Oyun.level1Skor

                Toast.makeText(this, "LEVEL 1 SÜRE==$saniye saniye// SCORE = $score",Toast.LENGTH_LONG).show()

            }

            R.id.level2Skor ->{

                var time = Oyun.lvl2PauseOfset.toInt()
                var saniye =time/1000
                var score = MAX_SCORE - saniye - Oyun.level2Skor

                Toast.makeText(this, "LEVEL 1 SÜRE==$saniye saniye// SCORE = $score",Toast.LENGTH_LONG).show()

            }

            R.id.level3Skor ->{

                var time = Oyun.lvl3PauseOfset.toInt()
                var saniye =time/1000
                var score = MAX_SCORE - saniye - Oyun.level3Skor

                Toast.makeText(this, "LEVEL 1 SÜRE==$saniye saniye// SCORE = $score",Toast.LENGTH_LONG).show()

            }

        }

        return super.onOptionsItemSelected(item)

    }

}
