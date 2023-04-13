package com.example.quiz_main_page;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Handler handler;
    private Runnable runnable;
    private int refreshCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("myfile2", Context.MODE_PRIVATE);

        // Create an instance of Fragment1
//        loadingfrag fragment1 = new loadingfrag();
        fragment_loadings fragment1= new fragment_loadings();
        // Add Fragment1 to the layout initially
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment1) // R.id.fragmentContainer is the ID of the container view in your activity's layout
                .commit();

        // Use a Handler to delay the API call by 5 seconds
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Fetch data from API
                fetchDataFromApi();
            }
        };
        handler.postDelayed(runnable, 1000); // Delay the fetchDataFromApi() method by 5 seconds (5000 milliseconds)
    }

    private void fetchDataFromApi() {
        SharedPreferences sharedPreferences2 = getSharedPreferences("Catogary", Context.MODE_PRIVATE);
        String quizResponseCatogary = sharedPreferences2.getString("quizids", null);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(quizResponseCatogary, JsonObject.class);

        // Convert the JSON object to a Map
        Map<String, JsonElement> map = gson.fromJson(jsonObject, Map.class);

        // Generate a random index
        Random random = new Random();
        int index = random.nextInt(map.size());

        // Get the key-value pair at the randomly generated index
        String selectedKey = (String) map.keySet().toArray()[index];
        String selectedValue = (String) map.values().toArray()[index]; // Remove double quotes from the value
        String quizResponsediff = sharedPreferences2.getString("diff", null);
        String url = "https://opentdb.com/api.php?amount=1&category="+selectedValue+"&difficulty="+quizResponsediff+"&type=multiple";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Save the response in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("quiz_response", unescapeHtml4(response.toString()));
                editor.putString("quiz_cat", selectedKey);
                editor.apply();

                // Create an instance of Fragment2
                quizfrag fragment2 = new quizfrag();

                // Replace Fragment1 with Fragment2
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment2) // Replace Fragment1 with Fragment2 in the container view
                        .commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(MainActivity2.this, "Failed to fetch data from API", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the callbacks from the handler when the activity is destroyed
        handler.removeCallbacks(runnable);
    }
}
