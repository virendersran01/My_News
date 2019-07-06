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

import com.bumptech.glide.Glide;
import com.example.my_news.R;
import com.example.my_news.model.SearchArticle;

import java.util.ArrayList;

public class RecyclerViewAdapterSearchArticle
        extends RecyclerView.Adapter<RecyclerViewAdapterSearchArticle.ItemViewHolder> {

    private ArrayList<SearchArticle.Docs> mList;
    private OnItemClickListener mListener;

    public RecyclerViewAdapterSearchArticle(ArrayList<SearchArticle.Docs> docsList) {
        mList = docsList;
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
        holder.updateSearchArticleUI(this.mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        else return mList.size();
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

        String NYT_URI = "https://www.nytimes.com/";

        public ItemViewHolder(View itemView, final OnItemClickListener listener) {
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

        public void updateSearchArticleUI(SearchArticle.Docs searchArticle) {

            mRelativeLayout = mRelativeLayout.findViewById(R.id.item_layout);
            mTextView = mTextView.findViewById(R.id.item_title);
            mImageView = mImageView.findViewById(R.id.article_image);
            mDateTV = mDateTV.findViewById(R.id.item_date);
            mSummaryTV = mSummaryTV.findViewById(R.id.item_summary);

            this.mTextView.setText(searchArticle.getNewsDesk());
            if (searchArticle.getPubDate() != null) {
                this.mDateTV.setText(searchArticle.getPubDate());
                this.mSummaryTV.setText(searchArticle.getSnippet());
                if (searchArticle.getMultimedia() != null &&
                        (!searchArticle.getMultimedia().isEmpty())) {
                    Glide.with(mContext).load(NYT_URI + searchArticle.getMultimedia()
                    .get(0).getUrl()).into(mImageView);
                }
            }
        }

        @Override
        public void onClick(View v) {
            //Do nothing
        }
    }
}
