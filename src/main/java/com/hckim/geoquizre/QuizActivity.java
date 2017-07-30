package com.hckim.geoquizre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// 컨트롤러 클래스
// 모델 클래스 Question 클래스: 두 개의 데이터(질문 텍스트와 정답). 값을 알려주는 게터 메소드와 값을 변경하는 세터 메소드가 필요
// 뷰(레이아웃)
// 스트링

/*
activity_quiz.xml: TextView 1개, Button 2개
QuizActivity.java: private Button 2개 -> m...Button = (Button) findViewId 2개 -> 각각 setOnClickListener 및 Override -> Toast
Question.java: class Question -> private int mTextResId, private boolean mAnswerTrue -> Constructor 및 getter and setter
strings.xml: 문자열 변경, next_button과 question_ocean 등 문자열 5개 추가
컨트롤러: 변수들과 Question 객체 배열 추가 -> TextView를 코드와 연결하기(mQuestionBank, mCurrentIndex, Question의 접근자 메소드 사용)
 */

/**
 * activity_quiz.xml의 Button, TextView 등 QuizActivity.java에서 private 전역변수(strings.xml도)
 *
 */

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
