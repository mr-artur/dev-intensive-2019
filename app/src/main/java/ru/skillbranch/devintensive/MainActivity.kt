package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object Const {

        private val ARG_STATUS = "STATUS"
        private val ARG_QUESTION = "QUESTION"
    }

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString(ARG_STATUS) ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString(ARG_QUESTION) ?: Bender.Question.NAME.name

        val (r, g, b) = Bender.Status.valueOf(status).color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

        benderObj = Bender(status = Bender.Status.valueOf(status), question = Bender.Question.valueOf(question))

        textTxt.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                answerQuestion()
                true
            } else {
                false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(ARG_STATUS, benderObj.status.name)
        outState.putString(ARG_QUESTION, benderObj.question.name)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            answerQuestion()
        }
    }

    private fun answerQuestion() {
        hideKeyboard()
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }
}
