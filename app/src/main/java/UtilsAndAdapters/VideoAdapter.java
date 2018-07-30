package UtilsAndAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.mymovieapp.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MovieAdapterViewHolder> {

    public void setMovieData(List<MovieVideos> movieVideos) {
        mMovieVideos =movieVideos;
        if (mMovieVideos ==null){
            arrayL=0;
        }else{
            arrayL=movieVideos.size();
            notifyDataSetChanged();}
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex,String movieKey);
        }

    final private ListItemClickListener mOnClickListener;
    private List<MovieVideos> mMovieVideos;
    private int arrayL=0;

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
       //Declare all items that belong to the View
        private TextView mVideoTitle;
        private ImageButton mLaunchVideo;


        //2.CONSTRUCTOR for the view holder for a single item of the recycler
        MovieAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mVideoTitle= (TextView)itemView.findViewById(R.id.video_name_tv);
            mLaunchVideo= (ImageButton)itemView.findViewById(R.id.play_video_but);
            mLaunchVideo.setOnClickListener(this);
            }

        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
            MovieVideos VideoID=mMovieVideos.get(clickedPosition);
            String videoKey=VideoID.getMkey();
            mOnClickListener.onListItemClick(clickedPosition,videoKey);
        }

    }
    //1.CONSTRUCTOR for the Movie Adapter, gets the Arraylist of movies and they activity
    public VideoAdapter(List<MovieVideos> movieVideos, ListItemClickListener listener ){
        mMovieVideos =movieVideos;
        mOnClickListener=listener;
        notifyDataSetChanged();
    }
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_vid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        MovieVideos mSingleMovieVideo= mMovieVideos.get(position);
        String mMovieTittleString= mSingleMovieVideo.getmName();
        TextView mMovieTittle=holder.mVideoTitle;
        mMovieTittle.setText(mMovieTittleString);
        String mMovieTypeString=(String)mSingleMovieVideo.getmVideoType();
        //TODO: May need to set up Button info
    }

    @Override
    public int getItemCount() {
       return arrayL;

    }


}
