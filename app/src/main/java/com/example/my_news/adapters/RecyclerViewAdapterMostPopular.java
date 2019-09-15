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
import com.example.my_news.model.MostPopular;

import java.util.ArrayList;

//RecyclerViewAdapterMostPopular: respective RecyclerView adapter for the Most Popular section and activity
public class RecyclerViewAdapterMostPopular
        extends RecyclerView.Adapter<RecyclerViewAdapterMostPopular.ItemViewHolder> {

    private ArrayList<MostPopular.Result> mMostPopularResult;
    private OnItemClickListener mListener;
    private RequestManager mGlide;

    //Instantiating an empty list for the MostPopular articles to be stored in
    public RecyclerViewAdapterMostPopular(ArrayList<MostPopular.Result> mostPopularResult, RequestManager glide) {
        mMostPopularResult = mostPopularResult;
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
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.updateMostPopularUI(this.mMostPopularResult.get(position), mGlide);
    }

    //Returns RecyclerView item count after checking if the result is null
    @Override
    public int getItemCount() {
        if (mMostPopularResult == null) return 0;
        else return mMostPopularResult.size();
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

        //RecyclerView UI components
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

        public void updateMostPopularUI(MostPopular.Result result, RequestManager glide) {

            //Updates texts and media of the the RecyclerView items using getters and Glide for media
            this.mTextView.setText(result.getSection());
            this.mDateTV.setText(result.getPublishedDate());
            this.mSummaryTV.setText(result.getTitle());
            if ((result.getMedia() != null) &&
                    (!result.getMedia().isEmpty()) &&
                    (!result.getMedia().get(0).getMediaMetadata().isEmpty())) {
                glide.load(result.getMedia().get(0)
                .getMediaMetadata().get(0).getUrl()).into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
            //Do nothing; empty constructor
        }
    }
}
