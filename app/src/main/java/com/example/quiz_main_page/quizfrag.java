package com.example.quiz_main_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class quizfrag extends Fragment {

    private TextView quizQuestionTextView;
    TextView scoreTextView;
    private RadioButton option1RadioButton;
    private RadioButton option2RadioButton;
    private RadioButton option3RadioButton;
    private RadioButton option4RadioButton;
    private TextView categoryTextView;
    private RadioGroup radioGroupoptions;
    private String correctAnswer;
    private CountDownTimer countDownTimer;
    TextView result;
    Button submitButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startCountdownTimer();
        View view = inflater.inflate(R.layout.fragment_quizfrag, container, false);

        scoreTextView = view.findViewById(R.id.scoreTextView);
        result= view.findViewById(R.id.result);
        quizQuestionTextView = view.findViewById(R.id.quizquestionmain);
        option1RadioButton = view.findViewById(R.id.option1);
        option2RadioButton = view.findViewById(R.id.option2);
        option3RadioButton = view.findViewById(R.id.option3);
        option4RadioButton = view.findViewById(R.id.option4);
        categoryTextView = view.findViewById(R.id.catogaryname);
        radioGroupoptions = view.findViewById(R.id.radioGroupoptions);
        submitButton = view.findViewById(R.id.submitButton);
        // Retrieve quiz response from shared preferences
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("myfile2", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("Catogary", Context.MODE_PRIVATE);

        String quizResponse = sharedPreferences.getString("quiz_response", null);
        String quizscore = sharedPreferences2.getString("score", null);

        scoreTextView.setText("Score "+quizscore);
        // Parse JSON response
        try {
            JSONObject quizObject = new JSONObject(quizResponse);
            JSONArray quizResults = quizObject.getJSONArray("results");
            JSONObject quizResult = quizResults.getJSONObject(0); // Assuming only one question in the response

            // Set category name
            String category = sharedPreferences.getString("quiz_cat", null);
            categoryTextView.setText(category);

            // Set quiz question
            String question = quizResult.getString("question");
            quizQuestionTextView.setText(question);

            // Set quiz options
            correctAnswer = quizResult.getString("correct_answer");
            JSONArray incorrectAnswers = quizResult.getJSONArray("incorrect_answers");

            // Add correct and incorrect answers to an ArrayList
            ArrayList<String> options = new ArrayList<>();
            options.add(correctAnswer);
            options.add(incorrectAnswers.getString(0));
            options.add(incorrectAnswers.getString(1));
            options.add(incorrectAnswers.getString(2));

            // Randomly rearrange the options
            Collections.shuffle(options);

            // Set shuffled options to RadioButtons
            option1RadioButton.setText(options.get(0));
            option2RadioButton.setText(options.get(1));
            option3RadioButton.setText(options.get(2));
            option4RadioButton.setText(options.get(3));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set click listener for Submit button
        view.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected radio button
                stopCountdownTimer();
                int selectedId = radioGroupoptions.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = view.findViewById(selectedId);
                int count= Integer.parseInt(sharedPreferences2.getString("count", null));
                int count2= Integer.parseInt(sharedPreferences2.getString("count2", null));
                if (selectedRadioButton != null) {
                    if (option1RadioButton.getText().toString().equals(correctAnswer)){
                        option1RadioButton.setTextColor(Color.GREEN);
                    } else {
                        option1RadioButton.setTextColor(Color.RED);
                    }
                    if (option2RadioButton.getText().toString().equals(correctAnswer)){
                        option2RadioButton.setTextColor(Color.GREEN);
                    } else {
                        option2RadioButton.setTextColor(Color.RED);
                    }
                    if (option3RadioButton.getText().toString().equals(correctAnswer)){
                        option3RadioButton.setTextColor(Color.GREEN);
                    } else {
                        option3RadioButton.setTextColor(Color.RED);
                    }
                    if (option4RadioButton.getText().toString().equals(correctAnswer)){
                        option4RadioButton.setTextColor(Color.GREEN);
                    } else {
                        option4RadioButton.setTextColor(Color.RED);
                    }

                    String selectedAnswer = selectedRadioButton.getText().toString();
                    // Check if selected answer is correct
                    if (selectedAnswer.equals(correctAnswer)) {
                        Integer Scoreadd;
                        Integer extractedscroe= Integer.parseInt(scoreTextView.getText().toString().replaceAll("[^0-9]", ""));
                        Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
                        String quizResponsediff = sharedPreferences2.getString("diff", null);
                        count2=count2+1;

                        if (count2<3){
                            Scoreadd=10;
                            result.setText( "Correct" );
                        }else if (count2<6){
                            Scoreadd=12;
                            result.setText( "Correct\n Combo "+count2+ "x" );
                        }else if (count2<8){
                            Scoreadd=14;
                            result.setText( "Correct\n Combo "+count2+ "x" );
                        }else{
                            Scoreadd=15 + extractedscroe;
                            result.setText( "Correct\n Combo "+count2+ "x" );
                        }
                        if (quizResponsediff=="medium"){
                            Log.d("100","here");
                            Scoreadd=Scoreadd*15;
                            Scoreadd=Scoreadd/10;
                            Log.d("100",""+Scoreadd);
                        }
                        else if(quizResponsediff=="hard"){
                            Scoreadd=Scoreadd*20;
                            Scoreadd=Scoreadd/10;
                        }
                        result.setTextColor(Color.GREEN);
                        scoreTextView.setText("Score: "+Scoreadd);
                        if (count!=count2){
                            SharedPreferences sharedPreferences = context.getSharedPreferences("Catogary", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("score",""+Scoreadd);
                            edit.putString("count2", ""+count2);
                            edit.commit();
                            Handler handler = new Handler();

                            // Call the intent with a 2-second delay
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                            Intent intent = new Intent(getContext(), MainActivity2.class);
                            startActivity(intent); }
                            }, 1000);
                        }
                        else{
                            SharedPreferences sharedPreferences = context.getSharedPreferences("Catogary", Context.MODE_PRIVATE);

                            Handler handler = new Handler();

                            // Call the intent with a 2-second delay
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                                }
                            }, 1000);

                        }
                    } else {
                        Toast.makeText(getActivity(), "Incorrect!", Toast.LENGTH_SHORT).show();
                        result.setText( "Wrong" );
                        result.setTextColor(Color.RED);
                        Handler handler = new Handler();

                        // Call the intent with a 2-second delay
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                            }
                        }, 1000);
                    }

                    // Disable the submit button to prevent multiple clicks
                    view.findViewById(R.id.submitButton).setEnabled(false);

                    // Start the countdown timer for 15 seconds

                } else {
                    Toast.makeText(getActivity(), "Please select an answer.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }


    private void stopCountdownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null; // Reset the countDownTimer instance
        }
    }
    private void startCountdownTimer() {
        // Cancel previous timer if it's running
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Start the countdown timer for 15 seconds
        countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer UI if needed
                // Example: timerTextView.setText("Time left: " + millisUntilFinished / 1000 + " seconds");
            }
            @Override
            public void onFinish() {
                // Reset the quiz options and enable the submit button
                option1RadioButton.setTextColor(Color.BLACK);
                option2RadioButton.setTextColor(Color.BLACK);
                option3RadioButton.setTextColor(Color.BLACK);
                option4RadioButton.setTextColor(Color.BLACK);
                radioGroupoptions.clearCheck();
                submitButton.setEnabled(false);
                result.setText("Time Up");
                result.setTextColor(Color.YELLOW);

                Handler handler = new Handler();

                // Call the intent with a 2-second delay
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);}
                }, 1000);
                // Update the quiz with a new question (assuming you have a method to do this)
                // updateQuiz();
            }
        }.start();
    }}