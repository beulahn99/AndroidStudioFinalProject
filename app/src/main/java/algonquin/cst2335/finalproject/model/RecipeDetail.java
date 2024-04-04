package algonquin.cst2335.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Purpose: Represents detailed information about a recipe, including its source name, source URL, summary, and image URL.
 * Implements Parcelable to enable transferring instances of this class between components.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class RecipeDetail implements Parcelable {
    /**
     * The name of the recipe source.
     */
    private String sourceName;

    /**
     * The URL of the recipe source.
     */
    private String sourceUrl;

    /**
     * A brief summary of the recipe.
     */
    private String summary;

    /**
     * The URL of the image associated with the recipe.
     */
    private String imageUrl;

    /**
     * Constructs a new RecipeDetail object with the specified details.
     *
     * @param sourceName The name of the recipe source.
     * @param sourceUrl  The URL of the recipe source.
     * @param summary    A brief summary of the recipe.
     * @param imageUrl   The URL of the image associated with the recipe.
     */
    public RecipeDetail(String sourceName, String sourceUrl, String summary, String imageUrl) {
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.summary = summary;
        this.imageUrl = imageUrl;
    }

    /**
     * Constructs a RecipeDetail object by reading from a Parcel.
     *
     * @param in The Parcel containing the data for the RecipeDetail object.
     */
    protected RecipeDetail(Parcel in) {
        sourceName = in.readString();
        sourceUrl = in.readString();
        summary = in.readString();
        imageUrl = in.readString();
    }

    /**
     * Creator constant for Parcelable support.
     */
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

    /**
     * Retrieves the name of the recipe source.
     *
     * @return The name of the recipe source.
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Retrieves the URL of the recipe source.
     *
     * @return The URL of the recipe source.
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * Retrieves a brief summary of the recipe.
     *
     * @return A brief summary of the recipe.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Retrieves the URL of the image associated with the recipe.
     *
     * @return The URL of the image associated with the recipe.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled representation.
     *
     * @return A bitmask indicating the set of special object types marshaled by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sourceName);
        dest.writeString(sourceUrl);
        dest.writeString(summary);
        dest.writeString(imageUrl);
    }
}
