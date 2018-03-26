package com.example.grigo.ab4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.grigo.ab4.models.UserModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Top10page extends AppCompatActivity {

    private ListView listView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.top10_layout);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        new GetPersons().execute("https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow");

        listView = (ListView) findViewById(R.id.listView);

    }


    public class GetPersons extends AsyncTask<String, String, List<UserModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<UserModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("items");

                List<UserModel> userModelList = new ArrayList<>();

                //take the info from JSON
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject each = parentArray.getJSONObject(i);
                    UserModel userModel = new UserModel();

                    JSONObject badgeCounts = each.getJSONObject("badge_counts");
                    UserModel.Rating rating = new UserModel.Rating();
                    rating.setBronze(badgeCounts.getInt("bronze"));
                    rating.setSilver(badgeCounts.getInt("silver"));
                    rating.setGold(badgeCounts.getInt("gold"));
                    userModel.setBadge_counts(rating);

                    userModel.setBadge_counts(rating);
                    userModel.setAccount_id(each.getInt("account_id"));
                    userModel.setLocation(each.getString("location"));
                    userModel.setDisplay_name(each.getString("display_name"));
                    userModel.setProfile_image(each.getString("profile_image"));

                    userModelList.add(userModel);
                }
                return userModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<UserModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.listview_layout, result);
            listView.setAdapter(adapter);
        }
    }

}