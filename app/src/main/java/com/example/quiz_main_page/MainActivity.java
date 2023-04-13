package com.example.quiz_main_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    TextView text;
    Button startbutton, increasebtn, Reducebtn;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    private Runnable runnable;
    LinearLayout firstphase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstphase = findViewById(R.id.firstphase);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                firstphase.setVisibility(View.GONE);            }
        };
        handler.postDelayed(runnable, 1500); // Delay the fetchDataFromApi() method by 5 seconds (5000 milliseconds)

        startbutton= findViewById(R.id.startbutton);
        RadioGroup difficulty = findViewById(R.id.difficulty_radio_group);
        RadioButton Easy = findViewById(R.id.easy_radio_button);
        RadioButton Hard = findViewById(R.id.hard_radio_button);
        RadioButton Medium = findViewById(R.id.medium_radio_button);
        CheckBox generalknowledgecheckbox = findViewById(R.id.general_knowledge_checkbox);
        CheckBox animeMangaCheckbox = findViewById(R.id.anime_manga_checkbox);
        CheckBox cartoonsCheckbox = findViewById(R.id.cartoons_checkbox);
        CheckBox computersCheckbox = findViewById(R.id.computers_checkbox);
        CheckBox filmCheckbox = findViewById(R.id.film_checkbox);
        CheckBox televisionCheckbox = findViewById(R.id.television_checkbox);
        CheckBox videoGamesCheckbox = findViewById(R.id.video_games_checkbox);
        CheckBox boardGamesCheckbox = findViewById(R.id.board_games_checkbox);
        CheckBox vehiclesCheckbox = findViewById(R.id.vehicles_checkbox);
        CheckBox booksCheckbox = findViewById(R.id.checkBoxBooks);
        CheckBox mathsCheckbox = findViewById(R.id.checkBoxMaths);
        CheckBox mythologyCheckbox = findViewById(R.id.checkBoxMythology);
        CheckBox sportsCheckbox = findViewById(R.id.checkBoxSports);
        CheckBox geographyCheckbox = findViewById(R.id.checkBoxGeography);
        CheckBox historyCheckbox = findViewById(R.id.checkBoxHistory);
        CheckBox politicsCheckbox = findViewById(R.id.checkBoxPolitics);
        CheckBox artCheckbox = findViewById(R.id.checkBoxArt);
        CheckBox celebritiesCheckbox = findViewById(R.id.checkBoxCelebrities);
        CheckBox natureCheckbox = findViewById(R.id.checkBoxNature);
        CheckBox comicsCheckbox = findViewById(R.id.checkBoxComics);
        CheckBox gadgetsCheckbox = findViewById(R.id.checkBoxGadgets);
        CheckBox animalsCheckbox = findViewById(R.id.checkBoxAnimals);
        CheckBox musicCheckbox = findViewById(R.id.checkBoxMusic);
        CheckBox musicalsTheatresCheckbox = findViewById(R.id.checkBoxMusicalsTheatres);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView selecttext= findViewById(R.id.selectioncounter);
        TextView questioncount = findViewById(R.id.questioncount);
        TextView incrementerror=findViewById(R.id.incrementerror);
        TextView tips=findViewById(R.id.tips);
        increasebtn = findViewById(R.id.increase);
        Reducebtn=findViewById(R.id.reduce);

        SharedPreferences sharedPreferences2 = getSharedPreferences("Catogary", Context.MODE_PRIVATE);

        String quizscore = sharedPreferences2.getString("score", "0");

        scoreTextView.setText("Score "+quizscore);

        increasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer temp= Integer.parseInt(questioncount.getText().toString());
                if (temp==10){
                    incrementerror.setText("Range 1-10");
                }
                else {
                    temp++;
                    questioncount.setText(temp.toString());
                    incrementerror.setText("");
                }
            }
        });


        Reducebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer temp= Integer.parseInt(questioncount.getText().toString());
                if (temp==1){
                    incrementerror.setText("Range 1-10");
                }
                else {
                    temp--;
                    questioncount.setText(temp.toString());
                    incrementerror.setText("");
                }
            }
        });
        View.OnClickListener checkBoxClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cast the view to a CheckBox
                CheckBox checkBox = (CheckBox) view;
                String[] tempcountselect = selecttext.getText().toString().split(" ");
                Integer tempcount = Integer.parseInt(tempcountselect[0]);
                if (checkBox.isChecked()) {
                    tempcount++;
                    selecttext.setText(tempcount + " Selected");
                } else {
                    // Checkbox is unchecked, decrement the counter
                    tempcount--;
                    selecttext.setText(tempcount + " Selected");
                }

            }
        };
        booksCheckbox.setOnClickListener(checkBoxClickListener);
        mathsCheckbox.setOnClickListener(checkBoxClickListener);
        mythologyCheckbox.setOnClickListener(checkBoxClickListener);
        sportsCheckbox.setOnClickListener(checkBoxClickListener);
        geographyCheckbox.setOnClickListener(checkBoxClickListener);
        historyCheckbox.setOnClickListener(checkBoxClickListener);
        politicsCheckbox.setOnClickListener(checkBoxClickListener);
        artCheckbox.setOnClickListener(checkBoxClickListener);
        celebritiesCheckbox.setOnClickListener(checkBoxClickListener);
        natureCheckbox.setOnClickListener(checkBoxClickListener);
        comicsCheckbox.setOnClickListener(checkBoxClickListener);
        gadgetsCheckbox.setOnClickListener(checkBoxClickListener);
        animalsCheckbox.setOnClickListener(checkBoxClickListener);
        musicCheckbox.setOnClickListener(checkBoxClickListener);
        musicalsTheatresCheckbox.setOnClickListener(checkBoxClickListener);
        generalknowledgecheckbox.setOnClickListener(checkBoxClickListener);
        animeMangaCheckbox.setOnClickListener(checkBoxClickListener);
        cartoonsCheckbox.setOnClickListener(checkBoxClickListener);
        computersCheckbox.setOnClickListener(checkBoxClickListener);
        filmCheckbox.setOnClickListener(checkBoxClickListener);
        televisionCheckbox.setOnClickListener(checkBoxClickListener);
        videoGamesCheckbox.setOnClickListener(checkBoxClickListener);
        boardGamesCheckbox.setOnClickListener(checkBoxClickListener);
        vehiclesCheckbox.setOnClickListener(checkBoxClickListener);

        View.OnClickListener radiobuttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cast the view to a CheckBox
                RadioButton radioButton = (RadioButton) view;
                int selectedRadioButtonId = difficulty.getCheckedRadioButtonId();
                switch (selectedRadioButtonId) {
                    case R.id.easy_radio_button:
                        tips.setText("1-2 x10 points\n2+ Combo x12 points\n5+ Combo x14 points\n8+ Combo x15 points");
                        tips.setTextColor(Color.parseColor("#ff669900"));
                        break;
                    case R.id.hard_radio_button:
                        tips.setText("1-2 x20 points\n2+ Combo x24 points\n5+ Combo x28 points\n8+ Combo x30 points");
                        tips.setTextColor(Color.RED);
                        break;
                    case R.id.medium_radio_button:
                        tips.setText("1-2 x15 points\n2+ Combo x18 points\n5+ Combo x21 points\n8+ Combo x22 points");
                        tips.setTextColor(Color.parseColor("#FFFF8800"));
                        break;

                }
            }};
        Easy.setOnClickListener(radiobuttonClickListener);
        Medium.setOnClickListener(radiobuttonClickListener);
        Hard.setOnClickListener(radiobuttonClickListener);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> quizids = new HashMap<>();
                if (generalknowledgecheckbox.isChecked()){
                    quizids.put("Gk", "9");
                }
                if (animeMangaCheckbox.isChecked()){
                    quizids.put("Amime Manga", "32");
                }
                if (cartoonsCheckbox.isChecked()){
                    quizids.put("Cartoons", "32");
                }
                if (computersCheckbox.isChecked()){
                    quizids.put("Computers", "18");
                }
                if (filmCheckbox.isChecked()){
                    quizids.put("Film", "11");
                }
                if (televisionCheckbox.isChecked()) {
                    quizids.put("Television", "14");
                }
                if (videoGamesCheckbox.isChecked()) {
                    quizids.put("Video Games", "15");
                }
                if (boardGamesCheckbox.isChecked()) {
                    quizids.put("Board Games", "16");
                }
                if (vehiclesCheckbox.isChecked()) {
                    quizids.put("Vehicles", "28");
                }
                if (booksCheckbox.isChecked()) {
                    quizids.put("Books", "10");
                }
                if (mathsCheckbox.isChecked()) {
                    quizids.put("Maths", "19");
                }
                if (mythologyCheckbox.isChecked()) {
                    quizids.put("Mythology", "20");
                }
                if (sportsCheckbox.isChecked()) {
                    quizids.put("Sports", "21");
                }
                if (geographyCheckbox.isChecked()) {
                    quizids.put("Geography", "22");
                }
                if (historyCheckbox.isChecked()) {
                    quizids.put("History", "23");
                }
                if (politicsCheckbox.isChecked()) {
                    quizids.put("Politics", "24");
                }
                if (artCheckbox.isChecked()) {
                    quizids.put("Art", "25");
                }
                if (celebritiesCheckbox.isChecked()) {
                    quizids.put("Celebrities", "26");
                }
                if (natureCheckbox.isChecked()) {
                    quizids.put("Nature", "17");
                }
                if (comicsCheckbox.isChecked()) {
                    quizids.put("Comics", "29");
                }
                if (gadgetsCheckbox.isChecked()) {
                    quizids.put("Gadgets", "30");
                }
                if (animalsCheckbox.isChecked()) {
                    quizids.put("Animals", "27");
                }
                if (musicCheckbox.isChecked()) {
                    quizids.put("Music", "12");
                }
                if (musicalsTheatresCheckbox.isChecked()) {
                    quizids.put("Musicals & Theatres", "13" );
                }
                if (quizids.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Select Catagory", Toast.LENGTH_SHORT).show();
                    selecttext.setTextColor(Color.RED);

                }
                else {

                    int selectedRadioButtonId = difficulty.getCheckedRadioButtonId();
                    String difficultystring = "";
                    switch (selectedRadioButtonId){
                        case R.id.easy_radio_button:
                            difficultystring="easy";
                            break;
                        case R.id.hard_radio_button:
                            difficultystring="hard";
                            break;
                        case R.id.medium_radio_button:
                            difficultystring="medium";
                            break;
                    }
                    Gson gson = new Gson();
                    String questioncounts= questioncount.getText().toString();
                    String extractedscroe = scoreTextView.getText().toString().replaceAll("[^0-9]", "");
                    String jsonString = gson.toJson(quizids);
                    SharedPreferences sharedPreferences = getSharedPreferences("Catogary", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("score",extractedscroe);
                    edit.putString("quizids", jsonString);
                    edit.putString("diff", difficultystring);
                    edit.putString("count", questioncounts);
                    edit.putString("count2", "0");
                    edit.commit();
                    Handler handler = new Handler();

                    // Call the intent with a 2-second delay
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                        }
                    }, 1000);
                }
            }
        });
    }
}
