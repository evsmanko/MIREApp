package evgeny.manko.schedule.feed;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import evgeny.manko.schedule.utils.DateUtils;

/**
 * Created by pinasol on 3/8/18.
 */

public class FeedParser {

    private final static String VK_BASE_URL = "https://api.vk.com/method/wall.get";

    private static final String TAG_MAIN_TITLE = "text";
    private static final String TAG_VIDEO_ATTACH_TYPE = "video";
    private static final String TAG_PHOTO_ATTACH_TYPE = "photo";
    private static final String TAG_AUDIO_ATTACH_TYPE = "audio";

    private static ArrayList<PostModel> mPosts;
    private static JSONObject mPhotoJsonObject;
    private static JSONObject mJsonObject;
    private static JSONArray mResponseArray;

    private static Integer postCount;

    public static String getJsonResponse(Integer offset) {
        // получаем данные с внешнего ресурса
        HttpURLConnection urlConnection = null;
        String resultJson = null;

        postCount = getPostCount();

        try {
            Uri uri = Uri.parse(VK_BASE_URL).buildUpon()
                    .appendQueryParameter("owner_id", "-1388")
                    .appendQueryParameter("access_token", "444edb70444edb70444edb7059442fc6bb4444e444edb701e32c0885b2c8542cbd88f0d")
                    .appendQueryParameter("count", String.valueOf(postCount))
                    .appendQueryParameter("offset", String.valueOf(offset))
                    .appendQueryParameter("version", "5.73")
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

    public static ArrayList<PostModel> parseJsonResponse(String strJson) {

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
                                Date postDate = new Date(Long.valueOf(post.getString("date")) * 1000);
                                Date currentDate = Calendar.getInstance().getTime();

                                mPosts.add(new PostModel(
                                        post.getString(TAG_MAIN_TITLE),
                                        mPhotoJsonObject.getString("src_big"),
                                        DateUtils.ParseDate(postDate, currentDate)
                                ));

                            }

                        }
                    }



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return mPosts;
    }

    public static Integer getPostCount() {
        return postCount;
    }

    public static void setPostCount(Integer postCount) {
        FeedParser.postCount = postCount;
    }
}
