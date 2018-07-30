package UtilsAndAdapters;

public class Movie {

    private String mMovieISAdult;
    private String mMovieHasVideo;
    private String mMovieBackDropPath;
    private String mMovieLanguage;
    private String mMovieOriginalTittle;
    private String mMovieOverview;
    private Long mMoviePopularity;
    private String mMovieTittle;
    private double mMovieVoteAverage;
    private double mMovieVoteCount;
    private String  mMoviePoster;
    private String mMovieReleaseDate;
    private int mMovieID;

    public Movie(String MovieISAdult,String MovieHasVideo,String MovieBackDropPath,
                 String MovieLanguage,String MovieOriginalTittle,String MovieOverview,
                 Long MoviePopularity,String MovieTittle,double MovieVoteAverage,
                 double MovieVoteCount,String moviePoster,String MovieReleaseDate,int movieID) {

        this.mMovieISAdult=MovieISAdult;
        this.mMovieHasVideo=MovieHasVideo;
        this.mMovieBackDropPath=MovieBackDropPath;
        this.mMovieLanguage=MovieLanguage;
        this.mMovieOriginalTittle=MovieOriginalTittle;
        this.mMovieOverview=MovieOverview;
        this.mMoviePopularity=MoviePopularity;
        this.mMovieTittle=MovieTittle;
        this.mMovieVoteAverage=MovieVoteAverage;
        this.mMovieVoteCount=MovieVoteCount;
        this.mMoviePoster=moviePoster;
        this.mMovieReleaseDate=MovieReleaseDate;
        this.mMovieID=movieID;
    }

    public Movie() {
    }


    public String getMovieISAdult(){return mMovieISAdult; }
    public String getMovieHasVideo(){return mMovieHasVideo;}
    public String getMovieBackDropPath(){return mMovieBackDropPath;}
    public String getMovieLanguage(){return mMovieLanguage;}
    public String getMovieOriginalTittle(){return mMovieOriginalTittle;}
    public String getMovieOverview(){return mMovieOverview;}
    public Long getMoviePopularity(){return mMoviePopularity;}
    public String getMovieTittle(){return mMovieTittle;}
    public double getMovieVoteAverage(){return mMovieVoteAverage;}
    public double getMovieVoteCount (){return mMovieVoteCount;}
    public String getmMoviePoster(){return mMoviePoster;}
    public String getmMovieReleaseDate(){return mMovieReleaseDate;}
    public int getmMovieID() { return mMovieID; }

    public void setMovieISAdult(String MovieISAdult){mMovieISAdult=MovieISAdult; }
    public void setMovieHasVideo(String MovieHasVideo){mMovieHasVideo=MovieHasVideo;}
    public void setMovieBackDropPath(String MovieBackDropPath){mMovieBackDropPath=MovieBackDropPath;}
    public void setMovieLanguage(String MovieLanguage){mMovieLanguage=MovieLanguage;}
    public void setMovieOriginalTittle(String MovieOriginalTittle){mMovieOriginalTittle=MovieOriginalTittle;}
    public void setMovieOverview(String MovieOverview){mMovieOverview=MovieOverview;}
    public void setMoviePopularity(Long MoviePopularity){mMoviePopularity=MoviePopularity;}
    public void setMovieTittle(String MovieTittle){mMovieTittle=MovieTittle;}
    public void setMovieVoteAverage(double MovieVoteAverage){mMovieVoteAverage=MovieVoteAverage;}
    public void setMovieVoteCount (double MovieVoteCount){mMovieVoteCount=MovieVoteCount;}
    public void setmMoviePoster(String MoviePoster){mMoviePoster=MoviePoster;}
    public void setmMovieReleaseDate(String MovieReleaseDate){mMovieReleaseDate=MovieReleaseDate;}
    public void setmMovieID(int movieID){mMovieID=movieID;}
}
