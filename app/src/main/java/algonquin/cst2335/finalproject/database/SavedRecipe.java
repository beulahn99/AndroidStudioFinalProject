package algonquin.cst2335.finalproject.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Purpose: Represents a saved recipe entity with details such as title, source URL, image URL, and summary.
 * This class is annotated with @Entity to define it as a Room database entity with the table name "saved_recipes".
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
@Entity(tableName = "saved_recipes")
public class SavedRecipe {

    /**
     * The unique identifier of the saved recipe.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * The title of the saved recipe.
     */
    private String title;

    /**
     * The source URL of the saved recipe.
     */
    private String sourceUrl;

    /**
     * The image URL of the saved recipe.
     */
    private String imageUrl;

    /**
     * A summary of the saved recipe.
     */
    private String summary;

    /**
     * Constructs an empty SavedRecipe object.
     */
    @Ignore
    public SavedRecipe(){
        // Empty constructor required by Room
    }

    /**
     * Constructs a SavedRecipe object with the specified details.
     *
     * @param title     The title of the saved recipe.
     * @param sourceUrl The source URL of the saved recipe.
     * @param imageUrl  The image URL of the saved recipe.
     * @param summary   A summary of the saved recipe.
     */
    public SavedRecipe(String title, String sourceUrl, String imageUrl, String summary){
        this.title = title;
        this.sourceUrl = sourceUrl;
        this.imageUrl = imageUrl;
        this.summary = summary;
    }

    /**
     * Retrieves the unique identifier of the saved recipe.
     *
     * @return The id of the saved recipe.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the saved recipe.
     *
     * @param id The id of the saved recipe.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the saved recipe.
     *
     * @return The title of the saved recipe.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the saved recipe.
     *
     * @param title The title of the saved recipe.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the source URL of the saved recipe.
     *
     * @return The source URL of the saved recipe.
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * Sets the source URL of the saved recipe.
     *
     * @param sourceUrl The source URL of the saved recipe.
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
     * Retrieves the image URL of the saved recipe.
     *
     * @return The image URL of the saved recipe.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image URL of the saved recipe.
     *
     * @param imageUrl The image URL of the saved recipe.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Retrieves the summary of the saved recipe.
     *
     * @return The summary of the saved recipe.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary of the saved recipe.
     *
     * @param summary The summary of the saved recipe.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
