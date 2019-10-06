package com.example.my_news.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.my_news.R;
import com.example.my_news.model.TopStories;

import java.util.ArrayList;

//RecyclerViewAdapterTopStories: respective RecyclerView adapter for the Top Stories section and activity
public class RecyclerViewAdapterTopStories
        extends RecyclerView.Adapter<RecyclerViewAdapterTopStories.ItemViewHolder> {

    private ArrayList<TopStories.Result> mTopStoriesResult;
    private OnItemClickListener mListener;
    private RequestManager mGlide;

    //Instantiating an empty list for the TopStories articles to be stored in
    public RecyclerViewAdapterTopStories(ArrayList<TopStories.Result> topStoriesResult, RequestManager glide) {
        mTopStoriesResult = topStoriesResult;
        mGlide = glide;
    }

    //onCreate: view is inflated and a new itemViewHolder is returned
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ItemViewHolder(view);
    }

    // Returns itemViewHolders and their position
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.updateTopStoriesUI(this.mTopStoriesResult.get(position), mGlide);
    }

    //Returns RecyclerView item count after checking if the result is null
    @Override
    public int getItemCount() {
        if (mTopStoriesResult == null) return 0;
        else return mTopStoriesResult.size();
    }

    //setting item clickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //Creating ItemViewHolder class
    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public Context mContext;

        //Casting RecyclerView components to their respective views
        RelativeLayout mRelativeLayout;
        TextView mTextView;
        ImageView mImageView;
        TextView mDateTV;
        TextView mSummaryTV;

        public ItemViewHolder(View itemView) {
            super(itemView);

            //Casting RecyclerView components to their respective views
            mRelativeLayout = itemView.findViewById(R.id.item_layout);
            mTextView = itemView.findViewById(R.id.item_title);
            mImageView = itemView.findViewById(R.id.article_image);
            mDateTV = itemView.findViewById(R.id.item_date);
            mSummaryTV = itemView.findViewById(R.id.item_summary);

            //Check if the listener is null and return position
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            mListener.onItemClick(position);
                    }
                }
            });
        }

        public void updateTopStoriesUI(TopStories.Result result, RequestManager glide) {

            //Updates texts and media of the the RecyclerView items using getters and Glide for media
            this.mTextView.setText(result.getSection());
            this.mDateTV.setText(result.getPublishedDate().substring(0, 10));
            this.mSummaryTV.setText(result.getTitle());
            if ((result.getMultimedia() != null) &&
                    (!result.getMultimedia().isEmpty())) {
                glide.load(result.getMultimedia().get(0).getUrl())
                .into(mImageView);
            }
        }


        @Override
        public void onClick(View v) {
            //Do nothing; empty constructor
        }
    }
}
