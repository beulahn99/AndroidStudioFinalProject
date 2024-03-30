package algonquin.cst2335.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeDetail implements Parcelable {
    private String sourceName;
    private String sourceUrl;
    private String summary;
    private String imageUrl;

    public RecipeDetail(String sourceName, String sourceUrl, String summary, String imageUrl) {
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.summary = summary;
        this.imageUrl = imageUrl;
    }

    protected RecipeDetail(Parcel in) {
        sourceName = in.readString();
        sourceUrl = in.readString();
        summary = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<RecipeDetail> CREATOR = new Creator<RecipeDetail>() {
        @Override
        public RecipeDetail createFromParcel(Parcel in) {
            return new RecipeDetail(in);
        }

        @Override
        public RecipeDetail[] newArray(int size) {
            return new RecipeDetail[size];
        }
    };

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSummary() {
        return summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sourceName);
        dest.writeString(sourceUrl);
        dest.writeString(summary);
        dest.writeString(imageUrl);
    }
}
