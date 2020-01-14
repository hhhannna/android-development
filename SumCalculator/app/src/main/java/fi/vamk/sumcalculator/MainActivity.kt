package fi.vamk.sumcalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun sum(view: View?){
        val num1 = editText.text.toString().toInt()
        val num2 = editText2.text.toString().toInt()
        tulos.text = (num1 + num2).toString()
    }
}
