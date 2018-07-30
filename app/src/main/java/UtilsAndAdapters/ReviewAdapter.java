package UtilsAndAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mymovieapp.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private int arrayL=0;
    private List<Reviews> mReviews;

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder
    {
       //Declare all items that belong to the View
        private TextView mAuthortv;
        private TextView mReviewtv;


        //2.CONSTRUCTOR for the view holder for a single item of the recycler
        ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            mAuthortv = itemView.findViewById(R.id.review_author);
            mReviewtv = itemView.findViewById(R.id.review);
            }


    }
    //1.CONSTRUCTOR for the Movie Adapter, gets the Arraylist of movies and they activity
    public ReviewAdapter(List<Reviews> reviews ){
        mReviews=reviews;
        notifyDataSetChanged();
    }
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {

        Reviews review=mReviews.get(position);
        String mReview= review.getmReview();
        TextView mReviewtv=holder.mReviewtv;
        mReviewtv.setText(mReview);
        String mAuthor= review.getMauthor();
        TextView mAuthortv=holder.mAuthortv;
        mAuthortv.setText(mAuthor);
    }

    @Override
    public int getItemCount() {
       return arrayL;

    }

    public void setReviewsData(List<Reviews> ReviewData){
        mReviews =ReviewData;
      if (mReviews ==null){
          arrayL=0;
      }else{
      arrayL=ReviewData.size();
      notifyDataSetChanged();}
    }


}
