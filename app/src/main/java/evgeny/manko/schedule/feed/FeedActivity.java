package evgeny.manko.schedule.feed;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import evgeny.manko.schedule.Lesson;
import evgeny.manko.schedule.R;
import evgeny.manko.schedule.UniversityInfo;


/**
 * Created by pinasol on 2/7/18.
 */

public class FeedActivity extends AppCompatActivity {

    private static JSONObject mJsonObject;
    private static JSONArray mResponseArray;

    private final static String VK_BASE_URL = "https://api.vk.com/method/wall.get";

    private static final String TAG_MAIN_TITLE = "text";
    private static final String TAG_VIDEO_ATTACH_TYPE = "video";
    private static final String TAG_PHOTO_ATTACH_TYPE = "photo";
    private static final String TAG_AUDIO_ATTACH_TYPE = "audio";

    private static TextView wallTextView;
    private RecyclerView recyclerView;

    private static ArrayList<PostModel> mPosts;
    private static JSONObject mPhotoJsonObject;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_feed);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        wallTextView = (TextView) findViewById(R.id.wall_textView);
        new FeedParseTask().execute();

    }



    private class FeedParseTask extends AsyncTask<URL, Void, String> {

        HttpURLConnection urlConnection = null;
        String resultJson = null;

        @Override
        protected String doInBackground(URL... params) {
            // получаем данные с внешнего ресурса
            try {
                Uri uri = Uri.parse(VK_BASE_URL).buildUpon()
                        .appendQueryParameter("owner_id", "-1388")
                        .appendQueryParameter("access_token", "444edb70444edb70444edb7059442fc6bb4444e444edb701e32c0885b2c8542cbd88f0d")
//                        .appendQueryParameter("offset", "2")
                        .appendQueryParameter("count", "100")
                        .build();

                URL url = new URL(uri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            if (strJson != null && !strJson.equals("")) {

                try {
                    mJsonObject = new JSONObject(strJson);

                    mPosts = new ArrayList<PostModel>();
                    mResponseArray = mJsonObject.getJSONArray("response");

                    for (int i = 1; i < mResponseArray.length(); i++) {
                        JSONObject post = mResponseArray.getJSONObject(i);


                        if (post.has("attachments")) {

                        JSONArray attachments = post.getJSONArray("attachments");


                            for (int j = 0; j < attachments.length(); j++) {
                                JSONObject attachment = attachments.getJSONObject(j);
                                if (attachment.getString("type").equals(TAG_PHOTO_ATTACH_TYPE)) {

                                    mPhotoJsonObject = attachment.getJSONObject(TAG_PHOTO_ATTACH_TYPE);
                                    mPosts.add(new PostModel(
                                            post.getString(TAG_MAIN_TITLE),
                                            mPhotoJsonObject.getString("src_big")));

                                }

                            }
                        }



                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(FeedActivity.this);
                    FeedAdapter adapter = new FeedAdapter(FeedActivity.this, mPosts);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }


    }
}
