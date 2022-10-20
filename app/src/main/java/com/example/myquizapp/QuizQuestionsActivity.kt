package com.example.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat


// the class implemented the AppCompatActivity and the onclicklistener its nearly the same as
// inherit so and we need to inherit the members of the class
class QuizQuestionsActivity : AppCompatActivity(),View.OnClickListener {

    private var mCurrentPosition: Int = 1

    // we need this and desigend it out first
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0


    private var mUserName :String? = null
    private var mCorrectAnswer :Int = 0

    


    private var progressBar : ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion : TextView? = null
    private var ivimage : ImageView? = null

    private var tvOptionOne : TextView? = null
    private var tvOptionTwo : TextView? = null
    private var tvOptionThree : TextView? = null
    private var tvOptionFour : TextView? = null
    private var btnSubmit : Button?=  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)


        progressBar=findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.questions)
        ivimage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)


        // we set a onclicklistener on the class so if y do something in the class it will
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        // used this global variabl for our
        mQuestionsList = Constants.getQuestions()


        // called this function and create with strg alt m
        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionsView()
        Log.i("QuestionsList size is", "${mQuestionsList!!.size}")


        for (i in mQuestionsList!!) {
            Log.e("Questions,", i.question)
        }


        val question: Question = mQuestionsList!![mCurrentPosition - 1]

        // in our class Question we have stored the image as a int and in our constants we have stored the questions list
        ivimage?.setImageResource(question.image)


        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionsOne
        tvOptionTwo?.text = question.optionsTwo
        tvOptionThree?.text = question.optionsThree
        tvOptionFour?.text = question.optionsFour

        if(mCurrentPosition == mQuestionsList!!.size)
        {
            btnSubmit?.text = "Finish"
        }else{
            btnSubmit?.text = "Next"
        }
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        tvOptionOne?.let{
            options.add(0,it)
        }
        tvOptionTwo?.let{
            options.add(1,it)
        }
        tvOptionThree?.let{
            options.add(2,it)
        }
        tvOptionFour?.let{
            options.add(3,it)
        }

        for(option in options){
            // here we change the color in the textView
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                // now we can darw the textview background and borders i Think
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv:TextView,selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#7A8089"))

        // read again strange
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            // now we can darw the textview background and borders i Think
            R.drawable.selected_option_bg
        )



    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.tv_option_one -> {
               tvOptionOne?.let{
                   selectedOptionView(it,1)
               }
           }
           R.id.tv_option_two -> {
               tvOptionTwo?.let{
                   selectedOptionView(it,2)
               }
           }
           R.id.tv_option_three -> {
               tvOptionThree?.let{
                   selectedOptionView(it,3)
               }
           }
           R.id.tv_option_four -> {
               tvOptionFour?.let{
                   selectedOptionView(it,4)
               }
           }
           R.id.btn_submit -> {
               btnSubmit?.let{
                    if(mSelectedOptionPosition==0) {
                        mCurrentPosition++

                        when{
                            // again !! because of null in fun
                            mCurrentPosition <= mQuestionsList!!.size->{
                                setQuestion()
                            }
                            // when class lambda
                            else -> {
                                    //Toast.makeText(this,"You Are Done",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this,resualtActivity::class.java)
                                intent.putExtra(Constants.USER_NAME,mUserName)
                                intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswer)
                                intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
                                //alw do this step to get to teh next activity
                                startActivity(intent)
                                //finish
                                finish()
                            }
                        }

                    }else{
                        val question = mQuestionsList?.get(mCurrentPosition-1)
                        // again !! because of null in fun it must bet .let at the end but to complicated
                        if(question!!.correctAnswer != mSelectedOptionPosition){
                            answer(mSelectedOptionPosition,R.drawable.worng_answer_option_border_bg)
                        }else{
                            mCorrectAnswer++
                        }
                            answer(question.correctAnswer,R.drawable.correct_answer_option_border_bg)
                            if(mCurrentPosition ==mQuestionsList!!.size){
                                btnSubmit?.text ="Finish"
                            }else{
                                btnSubmit?.text = "Go To Next Question"
                            }
                            mSelectedOptionPosition=0

                    }
               }
           }
       }
    }

    private fun answer (answer:Int,drawableView:Int) {

        when(answer){
            1->{
                // i need the context and the the int
                tvOptionOne?.background= ContextCompat.getDrawable(this,drawableView)
            }
            2->{
                // i need the context and the the int
                tvOptionOne?.background= ContextCompat.getDrawable(this,drawableView)
            }
            3->{
                // i need the context and the the int
                tvOptionOne?.background= ContextCompat.getDrawable(this,drawableView)
            }
            4->{
                // i need the context and the the int
                tvOptionOne?.background= ContextCompat.getDrawable(this,drawableView)
            }
        }

    }
}