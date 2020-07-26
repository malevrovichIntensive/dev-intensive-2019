package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_main.*
import ru.skillbranch.devintensive.models.Bender
import ru.skillbranch.devintensive.models.Question
import ru.skillbranch.devintensive.models.Status

class MainActivity : AppCompatActivity() {

    val bender = Bender()
    lateinit var etMsg: EditText
    lateinit var ivSend: ImageView
    lateinit var tvAns: TextView
    lateinit var ivBender: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        etMsg = et_message
        ivSend = iv_send
        tvAns = tv_text
        ivBender = iv_bender

        val status = Status.values()[savedInstanceState?.getInt(STATUS_KEY) ?: 0]
        val question = Question.values()[savedInstanceState?.getInt(QUESTION_KEY) ?: 0]

        bender.status = status
        bender.question = question

        val (r, g, b) = status.color

        tvAns.text = question.question
        ivBender.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

        ivSend.setOnClickListener {
            val (ans, color) = bender.listenAnswer(etMsg.text.toString())
            tvAns.setText(ans)
            val (r, g, b) = color
            ivBender.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(STATUS_KEY, bender.status.ordinal)
        outState.putInt(QUESTION_KEY, bender.question.ordinal)
    }

    companion object {
        const val STATUS_KEY = "STATUS"
        const val QUESTION_KEY = "QUESTION"
    }
}