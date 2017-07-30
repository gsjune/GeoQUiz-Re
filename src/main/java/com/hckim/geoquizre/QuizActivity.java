package com.hckim.geoquizre;

import android.app.Activity;
import android.content.Intent;
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

    private Button mTrueButton; // (1)
    private Button mFalseButton; // (1)'
    private Button mNextButton; // (4)
    private Button mCheatButton; // B(1)
    private TextView mQuestionTextView; // (4)'
    private static final int REQUEST_CODE_CHEAT = 0; // C(1)
    private boolean mIsCheater; // D(1)

    private Question[] mQuestionBank = new Question[]{ // (5)
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0; // (5)'

    private void updateQuestion() { // (8) 하나의 메소드로
        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) { // (10)
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();

        int messageReId = 0;

        if (mIsCheater) { // D(3) 수정
            messageReId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageReId = R.string.correct_toast;
            } else {
                messageReId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageReId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mCheatButton = (Button) findViewById(R.id.cheat_button); // B(2)
        mCheatButton.setOnClickListener(new View.OnClickListener() { // B(3)
            @Override
            public void onClick(View v) {
                // CheatActivity가 시작되는 곳
//                Intent intent = new Intent(QuizActivity.this, CheatActivity.class); // B(4)
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue(); // B(5) B(4)가 B(5) 두 줄로 대체
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
//                startActivity(intent); // C(2)로 변경
                startActivityForResult(intent, REQUEST_CODE_CHEAT); // C(2)
            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); // (6)
//        int question = mQuestionBank[mCurrentIndex].getmTextResId(); // (8)의 결과
//        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button); // (2)
        mTrueButton.setOnClickListener(new View.OnClickListener() { // (3)
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show(); // (10)의 결과
                checkAnswer(true); // (11)
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button); // (2)'
        mFalseButton.setOnClickListener(new View.OnClickListener() { // (3)'
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show(); // (10)의 결과
                checkAnswer(false); // (12)
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button); // (7)
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
//                int question = mQuestionBank[mCurrentIndex].getmTextResId(); // (8)의 결과
//                mQuestionTextView.setText(question);
                updateQuestion(); // (9)
            }
        });
    }

    @Override // D(2)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
}
