package algonquin.cst2335.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Purpose: Class representing a recipe.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class Recipe implements Parcelable {

    /**
     * Unique identifier of the recipe.
     */
    private int id;

    /**
     * Title of the recipe.
     */
    private String title;

    /**
     * URL of the image associated with the recipe.
     */
    private String image;

    /**
     * Type of the image associated with the recipe.
     */
    private String imageType;

    /**
     * Constructs a Recipe object with the specified parameters.
     *
     * @param id        The unique identifier of the recipe.
     * @param title     The title of the recipe.
     * @param image     The URL of the image associated with the recipe.
     * @param imageType The type of the image associated with the recipe.
     */
    public Recipe(int id, String title, String image, String imageType) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
    }

    /**
     * Constructs a Recipe object from a Parcel.
     *
     * @param in The Parcel containing the recipe data.
     */
    protected Recipe(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        imageType = in.readString();
    }

    /**
     * Creator constant for Parcelable.
     */
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    /**
     * Retrieves the unique identifier of the recipe.
     *
     * @return The unique identifier of the recipe.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the title of the recipe.
     *
     * @return The title of the recipe.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the URL of the image associated with the recipe.
     *
     * @return The URL of the image associated with the recipe.
     */
    public String getImage() {
        return image;
    }

    /**
     * Retrieves the type of the image associated with the recipe.
     *
     * @return The type of the image associated with the recipe.
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled representation.
     *
     * @return a bitmask indicating the set of special object types marshaled by this Parcelable object instance. Value of 0 means no special types.
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
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(imageType);
    }
}
