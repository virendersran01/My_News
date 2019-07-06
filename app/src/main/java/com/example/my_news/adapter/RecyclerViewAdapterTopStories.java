package com.example.my_news.adapter;

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

public class RecyclerViewAdapterTopStories
        extends RecyclerView.Adapter<RecyclerViewAdapterTopStories.ItemViewHolder> {

    private ArrayList<TopStories.Result> mTopStoriesResult;
    private OnItemClickListener mListener;
    private RequestManager mGlide;

    public RecyclerViewAdapterTopStories(ArrayList<TopStories.Result> topStoriesResult, RequestManager glide) {
        mTopStoriesResult = topStoriesResult;
        mGlide = glide;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ItemViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.updateTopStoriesUI(this.mTopStoriesResult.get(position), mGlide);
    }

    @Override
    public int getItemCount() {
        if (mTopStoriesResult == null) return 0;
        else return mTopStoriesResult.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public Context mContext;

        RelativeLayout mRelativeLayout;
        TextView mTextView;
        ImageView mImageView;
        TextView mDateTV;
        TextView mSummaryTV;

        public ItemViewHolder(View itemView,
                              final OnItemClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
        }

        public void updateTopStoriesUI(TopStories.Result result, RequestManager glide) {

            mRelativeLayout = mRelativeLayout.findViewById(R.id.item_layout);
            mTextView = mTextView.findViewById(R.id.item_title);
            mImageView = mImageView.findViewById(R.id.article_image);
            mDateTV = mDateTV.findViewById(R.id.item_date);
            mSummaryTV = mSummaryTV.findViewById(R.id.item_summary);

            this.mTextView.setText(result.getSection());
            this.mDateTV.setText(result.getPublishedDate());
            this.mSummaryTV.setText(result.getTitle());
            if ((result.getMultimedia() != null) &&
                    (!result.getMultimedia().isEmpty())) {
                glide.load(result.getMultimedia().get(0).getUrl())
                .into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
            //Do nothing
        }
    }
}
