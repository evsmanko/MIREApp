package evgeny.manko.schedule.feed;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import evgeny.manko.schedule.R;
import evgeny.manko.schedule.utils.NetworkUtils;


/**
 * Created by pinasol on 2/7/18.
 */

public class FeedActivity extends AppCompatActivity {

    private static TextView wallTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private static ArrayList<PostModel> mPosts;
    private static Boolean addPosts = false;
    private static Integer offset = 5;
    private static Boolean isLoading = false;
    private int visibleThreshold = 6;

    private static LinearLayoutManager layoutManager;


    private static FeedAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_feed);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        wallTextView = (TextView) findViewById(R.id.wall_textView);

        if (NetworkUtils.isNetworkOnline(this)) {

            FeedParser.setPostCount(5);

            layoutManager = new LinearLayoutManager(FeedActivity.this);

            new FeedParseTask().execute();

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new FeedParseTask().execute();
                }
            });


            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        isLoading = true;
                        new FeedAddPostsTask().execute();
                    }

                }
            });


        } else {
            Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show();
        }

    }


    private class FeedParseTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            return FeedParser.getJsonResponse(0);
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            mPosts = FeedParser.parseJsonResponse(strJson);

            adapter = new FeedAdapter(FeedActivity.this, mPosts);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

//            if (!addPosts) {
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
//            } else {
//                adapter.notifyDataSetChanged();
//            }

            mSwipeRefreshLayout.setRefreshing(false);


        }
    }

    private class FeedAddPostsTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            return FeedParser.getJsonResponse(offset);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            offset += 5;

//            mPosts.add(FeedParser.parseJsonResponse(s));
            ArrayList<PostModel> addedPosts = FeedParser.parseJsonResponse(s);

            for (PostModel post : addedPosts) {
                mPosts.add(post);
            }

            adapter.notifyDataSetChanged();
            addPosts = false;

            mSwipeRefreshLayout.setRefreshing(false);
            isLoading = false;
        }
    }

}
