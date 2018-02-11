package evgeny.manko.schedule.feed;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import evgeny.manko.schedule.R;

/**
 * Created by pinasol on 2/8/18.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.PostViewHolder>{

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView postTitleTextView;
        TextView showTextView;
        public PostViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.feed_card_view);
            postTitleTextView = (TextView) itemView.findViewById(R.id.wall_textView);
            showTextView = (TextView) itemView.findViewById(R.id.show_text);
        }
    }

    ArrayList<PostModel> postTitles;

    FeedAdapter(ArrayList<PostModel> postTitles) {
        this.postTitles = postTitles;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        String postText = postTitles.get(position).title;
        if (postText.length() < 180) {
            holder.postTitleTextView.setText(Html.fromHtml(postText));
        } else {
            holder.showTextView.setVisibility(View.VISIBLE);
            holder.postTitleTextView.setText(Html.fromHtml(postText.substring(0, 180)));
        }
    }

    @Override
    public int getItemCount() {
        return postTitles.size();
    }
}
