package UtilsAndAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.mymovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import DataBase.AppDatabase;
import DataBase.AppExecutors;
import DataBase.MovieFavorites;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex, boolean viewID,boolean isFavMovie,Button thisButton);
        }

    final private ListItemClickListener mOnClickListener;
    private ArrayList<Movie> mMovies;
    private int arrayL=0;
    private String POSTER_BASE="https://image.tmdb.org/t/p/w185/";
    private Context mContext;
    private AppDatabase mDb;

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
       //Declare all items that belong to the View
        private TextView mMovieTittle;
        private ImageView mMoviePoster;
        private Button mIsfavButtom;


        //2.CONSTRUCTOR for the view holder for a single item of the recycler
        MovieAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mMovieTittle= itemView.findViewById(R.id.tv_tittle_recycler);
            mMoviePoster= itemView.findViewById(R.id.iv_poster_item);
            mIsfavButtom= itemView.findViewById(R.id.fab_cb);
            mIsfavButtom.setOnClickListener(this);

            }

        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
            int ViewID=v.getId();
            int ButtomId=mIsfavButtom.getId();

            Boolean itWasClicked=false;
            if(ViewID==ButtomId){
                Log.e("On Click on Adapter","This is the buttom"+v.getTag());
                itWasClicked=true;
            }

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Boolean isfavBool=false;
                    try {
                        MovieFavorites MovieFavCheck = mDb.Movie()
                                .loadMovieByID(mMovies.get(clickedPosition).getmMovieID());
                        int id = MovieFavCheck.getId();
                        isfavBool=true;
                        //Change the text
                        if (isfavBool){
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                mIsfavButtom.setText(R.string.Favorite);
                            }
                        });
                        }else{
                            AppExecutors.getInstance().mainThread.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mIsfavButtom.setText(R.string.isNotFavorite);

                                }
                            });
                        }
                    } catch (Exception e) {
                        isfavBool=false;
                        e.printStackTrace();
                    }

                }
            });
            Boolean isFavMovie=false;
            String textInButton=mIsfavButtom.getText().toString();
            textInButton.trim();
            String label=mContext.getString(R.string.isNotFavorite);
            label.trim();
            if (textInButton.equals(label)){
                isFavMovie=true;
            }
            mOnClickListener.onListItemClick(clickedPosition,itWasClicked,isFavMovie,mIsfavButtom);
        }

    }
    //1.CONSTRUCTOR for the Movie Adapter, gets the Arraylist of movies and they activity
    public MovieAdapter(ArrayList<Movie> movies, ListItemClickListener listener ){
        mMovies=movies;
        mOnClickListener=listener;
        notifyDataSetChanged();
        mContext=(Context)listener;
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
        @SuppressLint("ResourceType") String pictureHString= mContext.getResources().getString(R.dimen.picture_h);
        pictureHString=pictureHString.replace("dip","");
        pictureHString=pictureHString.replace(".0","");
        int pictureH=Integer.parseInt(pictureHString.trim());

        @SuppressLint("ResourceType") String pictureWString= mContext.getResources().getString(R.dimen.picture_w);
        pictureWString=pictureWString.replace("dip","");
        pictureWString=pictureWString.replace(".0","");
        int pictureW=Integer.parseInt(pictureHString.trim());

        Movie mSingleMovie=mMovies.get(position);
        int thisMovieID=mSingleMovie.getmMovieID();
        String mMovieTittleString= mSingleMovie.getMovieTittle();
        TextView mMovieTittle=holder.mMovieTittle;
        mMovieTittle.setText(mMovieTittleString);
        ImageView poster=holder.mMoviePoster;
        String imageAdress=mSingleMovie.getmMoviePoster();
        imageAdress=POSTER_BASE+imageAdress;
        Picasso.get().load(imageAdress)
                .resize(pictureW,pictureH)
                .centerInside()
                .into(poster);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb = AppDatabase.getInstance(mContext);
                Boolean isfavBool=false;
                try {
                    MovieFavorites MovieFavCheck = mDb.Movie()
                            .loadMovieByID(thisMovieID);
                    int id = MovieFavCheck.getId();
                    isfavBool=true;
                    //Change the text
                    if (isfavBool){
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                holder.mIsfavButtom.setText(R.string.isNotFavorite);
                            }
                        });
                    }else{
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                holder.mIsfavButtom.setText(R.string.Favorite);

                            }
                        });
                    }
                } catch (Exception e) {
                    isfavBool=false;
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
       return arrayL;

    }

    public void setMovieData(ArrayList<Movie> movieData){
      mMovies =movieData;
      if (mMovies ==null){
          arrayL=0;
      }else{
      arrayL=movieData.size();
      notifyDataSetChanged();}
    }


}
