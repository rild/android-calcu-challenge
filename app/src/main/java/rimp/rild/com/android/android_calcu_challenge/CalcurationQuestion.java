package rimp.rild.com.android.android_calcu_challenge;

import android.graphics.Path;
import android.media.Image;
import android.util.Log;

import java.util.Random;

/**
 * Created by rild on 16/05/07.
 */
public class CalcurationQuestion {
    private final int BAFFA_SIZE = 3;
    private final int OPERATOR_VARIATION = 3;
    private final int QUSETION_EASY_MAX = 10;
    private final int QUSETION_NOMAL_MAX = 20;
    private final int QUSETION_HARD_MAX = 30;

    Difficulty difficulty;
    Operator operator = Operator.Plus;

    int numberA;
    int numberB;
    int correctAnswer;
    private int incorrectAnswer;
    private int[] incorrectAnswerBaffa = new int[BAFFA_SIZE];

    char operatorChar;

    Random random;


    public CalcurationQuestion(Difficulty difficulty) {
        this.difficulty = difficulty;

        random = new Random();

        initializeNumbers(random, difficulty);
        setOperator(random);
        setAnswer();

        Log.d("CalcurationQuestion", "A:" + numberA + ", B:" + numberB + ", ope:" + operatorChar);
    }

    private void setAnswer() {
        switch (operator) {
            case Plus:
                correctAnswer = numberA + numberB;
                break;
            case Minus:
                correctAnswer = numberA - numberB;
                break;
            case Times:
                correctAnswer = numberA * numberB;
                break;
            case Divide:
                //TODO 割り算どうしよう: answer int->float？
                correctAnswer = numberA / numberB;
                break;
        }

    }

    private void setOperator(Random random) {

        int randomInt = random.nextInt(OPERATOR_VARIATION);
        Log.d("CalcurationQuestion", "randomInt: " + randomInt);

        switch (randomInt) {
            case 0:
                operator = Operator.Plus;
                operatorChar = '+';
                break;
            case 1:
                operator = Operator.Minus;
                operatorChar = '-';
                break;
            case 2:
                operator = Operator.Times;
                operatorChar = '×';
                break;
//            case 3:
//                operator = Operator.Divide;
//                operatorChar = '÷';
//                break;
        }
    }

    private void initializeNumbers(Random random, Difficulty difficulty) {
        switch (difficulty) {
            case Easy:
                numberA = random.nextInt(QUSETION_EASY_MAX);
                numberB = random.nextInt(QUSETION_EASY_MAX);
                break;
            case Nomal:
                numberA = random.nextInt(QUSETION_NOMAL_MAX);
                numberB = random.nextInt(QUSETION_NOMAL_MAX);
                break;
            case Hard:
                numberA = random.nextInt(QUSETION_HARD_MAX);
                numberB = random.nextInt(QUSETION_HARD_MAX);
                break;
        }
    }

    public int getIncorrectAns() {
        do {
            incorrectAnswer = generateIncorrectAns();
        } while (hasSameValueInBaffaAs(incorrectAnswer));
        pushToBaffa(incorrectAnswer);

        return incorrectAnswer;
    }

    private int generateIncorrectAns() {
        int incAns = 0;
        do {
            int randomInt = diceroll(OPERATOR_VARIATION);
            switch (randomInt) {
                case 0:
                    incAns = numberA + numberB + addJidda();
                    break;
                case 1:
                    incAns = numberA - numberB + addJidda();
                    break;
                case 2:
                    incAns = numberA * numberB + addJidda();
                    break;
//                case 3:
//                    incAns = numberA / numberB + addJidda();
//                    break;
            }
        } while (incAns == correctAnswer);

        return incAns;
    }

    private boolean hasSameValueInBaffaAs(int value) {
        boolean hasValue = false;
        for (int i = 0; i < BAFFA_SIZE; i++) {
            if (incorrectAnswerBaffa[i] == value) hasValue = true;
        }
        return hasValue;
    }

    private int addJidda() {
        int jidda = diceroll(3) - 1;
        return jidda;
    }

    private int diceroll(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber);
    }

    private void pushToBaffa(int value) {
        for (int i = BAFFA_SIZE - 1; i > 0; i--) {
            incorrectAnswerBaffa[i] = incorrectAnswerBaffa[i - 1];
        }
        incorrectAnswerBaffa[0] = value;
        Log.d("CalcurationQuestion", "baffa: " + incorrectAnswerBaffa[0] + ", " + incorrectAnswerBaffa[1] + ", " + incorrectAnswerBaffa[2]);
    }
}
