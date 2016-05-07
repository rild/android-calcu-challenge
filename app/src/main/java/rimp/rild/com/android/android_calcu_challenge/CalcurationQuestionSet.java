package rimp.rild.com.android.android_calcu_challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rild on 16/05/07.
 */
public class CalcurationQuestionSet {
    List<CalcurationQuestion> questions;

    public CalcurationQuestionSet(int number, Difficulty difficulty) {
        questions = new ArrayList<>();

        generate(number, difficulty);
    }

    private void generate(int number, Difficulty difficulty) {
        for (int i = 0; i < number; i++) {
            questions.add(new CalcurationQuestion(difficulty));
        }
    }

    public CalcurationQuestion getItem(int index) {
        return questions.get(index);
    }

}
