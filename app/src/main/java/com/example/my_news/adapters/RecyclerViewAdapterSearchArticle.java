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
import com.example.my_news.model.DocsItem;
import com.example.my_news.model.SearchArticle;
import java.util.ArrayList;
import java.util.List;

//RecyclerViewAdapterSearchArticles: respective RecyclerView adapter for the SearchArticle section and activity
public class RecyclerViewAdapterSearchArticle
        extends RecyclerView.Adapter<RecyclerViewAdapterSearchArticle.ItemViewHolder> {

    private ArrayList<DocsItem> mList;
    private OnItemClickListener mListener;
    private RequestManager mGlide;

    //Instantiating an empty list for the TopStories articles to be stored in
    public RecyclerViewAdapterSearchArticle(ArrayList<DocsItem> docsList, RequestManager glide) {
        mList = docsList;
        mGlide = glide;
    }

    //onCreate: view is inflated and a new itemViewHolder is returned
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ItemViewHolder(view, mListener);
    }

    // Returns itemViewHolders and their position
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.updateSearchArticleUI(this.mList.get(position), mGlide);
    }

    //Returns RecyclerView item count after checking if the result is null
    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        else return mList.size();
    }

    //setting item clickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void updateArticles(List<DocsItem> searchDocs) {
        mList.clear();
        mList.addAll(searchDocs);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //Creating ItemViewHolder class
    public static class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public Context mContext;

        //Casting RecyclerView components to their respective views
        RelativeLayout mRelativeLayout;
        TextView mTextView;
        ImageView mImageView;
        TextView mDateTV;
        TextView mSummaryTV;

        String NYT_URI = "https://www.nytimes.com/";

        public ItemViewHolder(View itemView, final OnItemClickListener listener) {
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
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
        }

        public void updateSearchArticleUI(DocsItem searchArticle, RequestManager glide) {

            //Updates texts and media of the the RecyclerView items using getters and Glide for media
            this.mTextView.setText(searchArticle.getNewsDesk());
            if (searchArticle.getPubDate() != null) {
                this.mDateTV.setText(searchArticle.getPubDate());
                this.mSummaryTV.setText(searchArticle.getSnippet());
                if (searchArticle.getMultimedia() != null &&
                        (!searchArticle.getMultimedia().isEmpty())) {
                    glide.load(NYT_URI + searchArticle.getMultimedia()
                    .get(0).getUrl()).into(mImageView);
                }
            }
        }

        @Override
        public void onClick(View v) {
            //Do nothing; empty constructor
        }
    }
}
