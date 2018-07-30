package UtilsAndAdapters;

public class MovieVideos {
    private String mkey;
    private String mSize;
    private String mName;
    private String mId;
    private String mSite;
    private String mVideoType;
    public MovieVideos(String key, String size, String name, String id, String site,String videType){
        mkey =key;
        mSize =size;
        mName =name;
        mId =id;
        mSite=site;
        mVideoType=videType;
    }

    public String getmSize() {
        return mSize;
    }

    public String getMkey() {
        return mkey;
    }

    public String getmName() {
        return mName;
    }

    public void setmSize(String mSize) {
        this.mSize = mSize;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmSite() {
        return mSite;
    }

    public void setmSite(String mSite) {
        this.mSite = mSite;
    }

    public Object getmVideoType() {
        return mVideoType;
    }

    public void setmVideoType(String mVideoType) {
        this.mVideoType = mVideoType;
    }
}
