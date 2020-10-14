package com.block.quiz;

public class QuizModel {
    private int mQuestion;
    private boolean mAnswer;

    public QuizModel(int mQuestion, boolean mAnswer) {
        this.mQuestion = mQuestion;
        this.mAnswer = mAnswer;
    }

    public int getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(int mQuestion) {
        this.mQuestion = mQuestion;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
