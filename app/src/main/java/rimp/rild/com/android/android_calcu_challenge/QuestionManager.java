package rimp.rild.com.android.android_calcu_challenge;

import android.content.res.Resources;
import android.util.Log;

import java.util.Random;

/**
 * Created by rild on 16/05/07.
 */
public class QuestionManager {
    private final int ANS_NUMBER_LIST = 4;

    int currentQuestionIndex = 0;
    int questionNumber = 10;
    int[] currentAnsNumberList = new int[ANS_NUMBER_LIST];

    CalcurationQuestion currentQuestion;
    CalcurationQuestionSet questionSet;

    public QuestionManager() {
        initializeQuestionSet();
    }

    public QuestionManager(Difficulty difficulty) {
        initializeQuestionSet(difficulty);
    }

    public QuestionManager(int questionNumber, Difficulty difficulty) {
        initializeQuestionSet(difficulty);
        this.questionNumber = questionNumber;
    }


    private void initializeQuestionSet() {
        questionSet = new CalcurationQuestionSet(questionNumber, Difficulty.Easy);
        currentQuestion = questionSet.getItem(currentQuestionIndex);
    }

    private void initializeQuestionSet(Difficulty difficulty) {
        questionSet = new CalcurationQuestionSet(questionNumber, difficulty);
        currentQuestion = questionSet.getItem(currentQuestionIndex);
    }

    private int diceroll(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber);
    }

    public void updateQuestion() {
        if (currentQuestionIndex < questionNumber - 1) {
            currentQuestionIndex++;
            currentQuestion = questionSet.getItem(currentQuestionIndex);
        }
    }

    public String getQestionText(Resources res) {
        return res.getString(R.string.item_question_text,
                currentQuestion.numberA,
                currentQuestion.operatorChar,
                currentQuestion.numberB);
    }

    public boolean continueQuestion() {
        return currentQuestionIndex < questionNumber;
    }

    public void updateCurrentQuestion() {
        currentQuestion = questionSet.getItem(currentQuestionIndex);
    }

    public void updateAnserList() {
        int correctAnsIndex = diceroll(4);
        Log.d("MainActivity", "dice: " + correctAnsIndex);
        for (int i = 0; i < ANS_NUMBER_LIST; i++) {
            if (correctAnsIndex == i) {
                currentAnsNumberList[i] = currentQuestion.correctAnswer;
            } else {
                currentAnsNumberList[i] = currentQuestion.getIncorrectAns();
            }
        }
    }

    public int getAnswerAt(int index) {
        return currentAnsNumberList[index];
    }

    public boolean checkResult(int index) {
        return currentQuestion.correctAnswer == getAnswerAt(index);
    }
}
