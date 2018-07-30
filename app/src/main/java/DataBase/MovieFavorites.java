package DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ListofMovies")
public class MovieFavorites {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String mMovieISAdult;
    public String mMovieHasVideo;
    public String mMovieBackDropPath;
    public String mMovieLanguage;
    public String mMovieOriginalTittle;
    public String mMovieOverview;
    public Long mMoviePopularity;
    public String mMovieTittle;
    public double mMovieVoteAverage;
    public double mMovieVoteCount;
    public String mMoviePoster;
    public String mMovieReleaseDate;

    public MovieFavorites() {
    }

    @Ignore
    public MovieFavorites(String MovieISAdult, String MovieHasVideo, String MovieBackDropPath,
                          String MovieLanguage, String MovieOriginalTittle, String MovieOverview,
                          Long MoviePopularity, String MovieTittle, double MovieVoteAverage,
                          double MovieVoteCount, String moviePoster, String MovieReleaseDate) {
        this.mMovieISAdult = MovieISAdult;
        this.mMovieHasVideo = MovieHasVideo;
        this.mMovieBackDropPath = MovieBackDropPath;
        this.mMovieLanguage = MovieLanguage;
        this.mMovieOriginalTittle = MovieOriginalTittle;
        this.mMovieOverview = MovieOverview;
        this.mMoviePopularity = MoviePopularity;
        this.mMovieTittle = MovieTittle;
        this.mMovieVoteAverage = MovieVoteAverage;
        this.mMovieVoteCount = MovieVoteCount;
        this.mMoviePoster = moviePoster;
        this.mMovieReleaseDate = MovieReleaseDate;

    }

    public String getMovieOverview() {
        return mMovieOverview;
    }

    public String getmMovieBackDropPath() {
        return mMovieBackDropPath;
    }

    public double getmMovieVoteAverage() {
        return mMovieVoteAverage;
    }

    public double getmMovieVoteCount() {
        return mMovieVoteCount;
    }

    public Long getmMoviePopularity() {
        return mMoviePopularity;
    }

    public String getmMovieHasVideo() {
        return mMovieHasVideo;
    }

    public String getmMovieISAdult() {
        return mMovieISAdult;
    }

    public String getmMovieLanguage() {
        return mMovieLanguage;
    }

    public String getmMovieOriginalTittle() {
        return mMovieOriginalTittle;
    }

    public String getmMovieOverview() {
        return mMovieOverview;
    }

    public String getmMovieTittle() {
        return mMovieTittle;
    }

    public String getMovieTittle() {
        return mMovieTittle;
    }

    public double getMovieVoteAverage() {
        return mMovieVoteAverage;
    }

    public String getmMoviePoster() {
        return mMoviePoster;
    }

    public String getmMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public int getId() {
        return id;
    }


    public void setId(int idin) {
        id = idin;
    }

    public void setmMovieBackDropPath(String mMovieBackDropPath) {
        this.mMovieBackDropPath = mMovieBackDropPath;
    }

    public void setmMovieHasVideo(String mMovieHasVideo) {
        this.mMovieHasVideo = mMovieHasVideo;
    }

    public void setmMovieISAdult(String mMovieISAdult) {
        this.mMovieISAdult = mMovieISAdult;
    }

    public void setmMovieLanguage(String mMovieLanguage) {
        this.mMovieLanguage = mMovieLanguage;
    }

    public void setmMovieOriginalTittle(String mMovieOriginalTittle) {
        this.mMovieOriginalTittle = mMovieOriginalTittle;
    }

    public void setmMovieOverview(String mMovieOverview) {
        this.mMovieOverview = mMovieOverview;
    }

    public void setmMoviePopularity(Long mMoviePopularity) {
        this.mMoviePopularity = mMoviePopularity;
    }

    public void setmMoviePoster(String mMoviePoster) {
        this.mMoviePoster = mMoviePoster;
    }

    public void setmMovieTittle(String mMovieTittle) {
        this.mMovieTittle = mMovieTittle;
    }

    public void setmMovieReleaseDate(String mMovieReleaseDate) {
        this.mMovieReleaseDate = mMovieReleaseDate;
    }

    public void setmMovieVoteAverage(double mMovieVoteAverage) {
        this.mMovieVoteAverage = mMovieVoteAverage;
    }

    public void setmMovieVoteCount(double mMovieVoteCount) {
        this.mMovieVoteCount = mMovieVoteCount;
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
