package UtilsAndAdapters;

public class Reviews {
    private String mReview;
    private String mauthor;
    private String mreviewURL;

    public Reviews(String review,String author,String reviewURL){
        mReview =review;
        mauthor =author;
        mreviewURL =reviewURL;

    }

    public String getMauthor() {
        return mauthor;
    }

    public String getmReview() {
        return mReview;
    }

    public String getMreviewURL() {
        return mreviewURL;
    }

    public void setMauthor(String mauthor) {
        this.mauthor = mauthor;
    }

    public void setmReview(String mReview) {
        this.mReview = mReview;
    }

    public void setMreviewURL(String mreviewURL) {
        this.mreviewURL = mreviewURL;
    }


}
