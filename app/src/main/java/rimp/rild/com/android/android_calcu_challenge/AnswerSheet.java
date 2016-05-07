package rimp.rild.com.android.android_calcu_challenge;

/**
 * Created by rild on 16/05/07.
 */
public class AnswerSheet {
    private int correctAnswer;
    private int answer;

    public AnswerSheet(int correctAnswer, int answer) {
        this.correctAnswer = correctAnswer;
        this.answer = answer;
    }

    public boolean checkAnswer() {
        return correctAnswer == answer;
    }
}
