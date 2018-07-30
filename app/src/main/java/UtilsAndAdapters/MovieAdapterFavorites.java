package UtilsAndAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mymovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import DataBase.MovieFavorites;

public class MovieAdapterFavorites extends RecyclerView.Adapter<MovieAdapterFavorites.MovieAdapterViewHolder> {

    public interface ListItemClickListenerfab {
        void onListItemClick(int clickedItemIndex, Boolean itWasClicked);
    }

    final private ListItemClickListenerfab mOnClickListener;
    private List<MovieFavorites> mMovies;
    private int arrayL;
    private String POSTER_BASE = "https://image.tmdb.org/t/p/w185/";
    private Context mContext;

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        //Declare all items that belong to the View
        private TextView mMovieTittle;
        private ImageView mMoviePoster;
        private Button mNoFabAnymoreBut;

        //2.CONSTRUCTOR for the view holder for a single item of the recycler
        MovieAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mMovieTittle = itemView.findViewById(R.id.tv_tittle_recycler);
            mMoviePoster = itemView.findViewById(R.id.iv_poster_item);
            mNoFabAnymoreBut = itemView.findViewById(R.id.fab_cb);
            mNoFabAnymoreBut.setText(mContext.getString(R.string.isNotFavorite));
            mNoFabAnymoreBut.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            int ViewID = v.getId();
            int ButtomId = mNoFabAnymoreBut.getId();
            Boolean itWasClicked = false;
            if (ViewID == ButtomId) {
                Log.e("On Click on Adapter", "This is the buttom" + v.getTag());
                itWasClicked = true;
            }
            mOnClickListener.onListItemClick(clickedPosition, itWasClicked);
        }
    }

        //1.CONSTRUCTOR for the Movie Adapter, gets the Arraylist of movies and they activity
        public MovieAdapterFavorites(List<MovieFavorites> movies, ListItemClickListenerfab listener) {
            mMovies = movies;
            mOnClickListener = listener;
            notifyDataSetChanged();
            mContext = (Context) listener;
        }

        @Override
        public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.movie_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;
            View view = inflater.inflate(layoutIdForListItem, parent, false);
            return new MovieAdapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
            //Getting dimensions for movie poster
            @SuppressLint("ResourceType") String pictureHString = mContext.getResources().getString(R.dimen.picture_h);
            pictureHString = pictureHString.replace("dip", "");
            pictureHString = pictureHString.replace(".0", "");
            int pictureH = Integer.parseInt(pictureHString.trim());

            @SuppressLint("ResourceType") String pictureWString = mContext.getResources().getString(R.dimen.picture_w);
            pictureWString = pictureWString.replace("dip", "");
            pictureWString = pictureWString.replace(".0", "");
            int pictureW = Integer.parseInt(pictureHString.trim());

            MovieFavorites mSingleMovie = mMovies.get(position);
            String mMovieTittleString = mSingleMovie.getMovieTittle();
            TextView mMovieTittle = holder.mMovieTittle;
            mMovieTittle.setText(mMovieTittleString);
            ImageView poster = holder.mMoviePoster;
            String imageAdress = mSingleMovie.getmMoviePoster();
            imageAdress = POSTER_BASE + imageAdress;
            Picasso.get().load(imageAdress)
                    .resize(pictureW, pictureH)
                    .centerInside()
                    .into(poster);
        }

        @Override
        public int getItemCount() {
            if (mMovies == null) {
                return 0;
            } else {
                return mMovies.size();
            }


        }

        public void setMovieData(List<MovieFavorites> movieData) {
            mMovies = movieData;
            if (mMovies == null) {
                arrayL = 0;
            } else {
                arrayL = movieData.size();
                notifyDataSetChanged();
            }
        }

    }

