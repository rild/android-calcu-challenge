package rimp.rild.com.android.android_calcu_challenge;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final int ANS_NUMBER = 4;

    int currentQuestionIndex = 0;
    int questionNumber = 10;
    int[] currentAnsNumberList = new int[ANS_NUMBER];

    List<TextView> answerButtons;
    TextView questionTextView;
    TextView currectAnswerTextView;
    TextView questionCountTextView;

    CalcurationQuestion currentQuestion;
    CalcurationQuestionSet questionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerButtons = new ArrayList<>();

        initializeQuestionSet();

        getViews();
        updateAnsNumberTexts();
        setOnClickToAnswerButtons();
        updateDisplayText();

        questionCountTextView.setText((currentQuestionIndex + 1) + "問目");
        currectAnswerTextView.setText("Result");

    }

    private void initializeQuestionSet() {
        questionSet = new CalcurationQuestionSet(questionNumber, Difficulty.Easy);
        currentQuestion = questionSet.getItem(currentQuestionIndex);
    }

    private void updateQuestion() {
        if (currentQuestionIndex < questionNumber - 1) {
            currentQuestionIndex++;
            currentQuestion = questionSet.getItem(currentQuestionIndex);
        }
    }

    private void updateDisplayText() {
        String text = getResources().getString(R.string.item_question_text,
                currentQuestion.numberA,
                currentQuestion.operatorChar,
                currentQuestion.numberB);
        questionTextView.setText(text);
        questionCountTextView.setText((currentQuestionIndex + 1) + "問目");
    }

    private void setResultText(boolean isCorrect) {
        if (isCorrect) {
            currectAnswerTextView.setText("Correct");
        } else {
            currectAnswerTextView.setText("Wrong");
        }
    }

    private void setAnsToListFromCurrentQuestion() {
        int correctAnsIndex = diceroll(4);
        Log.d("MainActivity", "dice: " + correctAnsIndex);
        for (int i = 0; i < ANS_NUMBER; i++) {
            if (correctAnsIndex == i) {
                currentAnsNumberList[i] = currentQuestion.correctAnswer;
            } else {
                currentAnsNumberList[i] = currentQuestion.getIncorrectAns();
            }
        }
    }

    private int diceroll(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber);
    }

    private void updateAnsNumberTexts() {
        setAnsToListFromCurrentQuestion();
        for (int i = 0; i < ANS_NUMBER; i++) {
            answerButtons.get(i).setText(String.valueOf(currentAnsNumberList[i]));
        }
    }

    private void getViews() {
        questionTextView = (TextView) findViewById(R.id.main_question_text);
        currectAnswerTextView = (TextView) findViewById(R.id.main_currect_answer_text);
        questionCountTextView = (TextView) findViewById(R.id.main_count_text);

        answerButtons.add((TextView) findViewById(R.id.answer_textview_0));
        answerButtons.add((TextView) findViewById(R.id.answer_textview_1));
        answerButtons.add((TextView) findViewById(R.id.answer_textview_2));
        answerButtons.add((TextView) findViewById(R.id.answer_textview_3));
    }

    private void setOnClickToAnswerButtons() {
        for (int i = 0; i < ANS_NUMBER; i++) {
            answerButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentQuestionIndex < questionNumber) {
                        currentQuestion = questionSet.getItem(currentQuestionIndex);
                        int selectedTextViewIndex = getIndexWithId(v.getId());
                        boolean result = currentQuestion.correctAnswer == currentAnsNumberList[selectedTextViewIndex];
                        setResultText(result);
                        if (result) {
                            updateQuestion();
                            questionCountTextView.setText((currentQuestionIndex + 1) + "問目");
                            updateAnsNumberTexts();
                            updateDisplayText();
                        }
                    }
                }
            });
        }
    }

    private int getIndexWithId(int viewId) {
        int index = 0;
        switch (viewId) {
            case R.id.answer_textview_0:
                index = 0;
                break;
            case R.id.answer_textview_1:
                index = 1;
                break;
            case R.id.answer_textview_2:
                index = 2;
                break;
            case R.id.answer_textview_3:
                index = 3;
                break;
        }
        return index;
    }
}
