package com.example.grigo.ab4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView lst;
    String[] names = new String[10];
    Integer[] imageId = {R.drawable.poza2016, R.drawable.poza2016, R.drawable.poza2016, R.drawable.poza2016, R.drawable.poza2016, R.drawable.poza2016, R.drawable.poza2016, R.drawable.poza2016,
            R.drawable.poza2016, R.drawable.poza2016};
    String[] imageSources = new String[10];

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow";

    ArrayList<HashMap<String, String>> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personList = new ArrayList<>();
        lst = (ListView) findViewById(R.id.listview);
        CustomListView customListView = new CustomListView(this, names, imageId);
        lst.setAdapter(customListView);
    }

    private class GetPersons extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonOBj = new JSONObject(jsonStr);

                    JSONArray persons = jsonOBj.getJSONArray("peresons");

                    for (int i = 0; i < persons.length(); i++) {
                        JSONObject c = persons.getJSONObject(i);
                        String name = c.getString("display_name");
                        String imageSource = c.getString("profile_image");

                        names[i] = name;
                        imageSources[i] = imageSource;
                        HashMap<String, String> person = new HashMap<>();
                        person.put("display_name", name);
                        person.put("profile_image", imageSource);

                        personList.add(person);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, personList,
                    R.layout.listview_layout, new String[]{"name"},
                    new int[]{R.id.name});
            lst.setAdapter(adapter);
        }

    }


}
