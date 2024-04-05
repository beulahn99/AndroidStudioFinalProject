package algonquin.cst2335.finalproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import algonquin.cst2335.finalproject.database.SavedRecipe;

/**
 * Purpose: Data Access Object (DAO) interface for interacting with the SavedRecipe entity in the Room database.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
@Dao
public interface SavedRecipeDAO {

    /**
     * Inserts a new saved recipe into the database.
     *
     * @param savedRecipe The saved recipe to be inserted.
     */
    @Insert
    void insert(SavedRecipe savedRecipe);

    /**
     * Updates an existing saved recipe in the database.
     *
     * @param savedRecipe The saved recipe to be updated.
     */
    @Update
    void update(SavedRecipe savedRecipe);

    /**
     * Deletes a saved recipe from the database.
     *
     * @param savedRecipe The saved recipe to be deleted.
     */
    @Delete
    void delete(SavedRecipe savedRecipe);

    /**
     * Retrieves all saved recipes from the database.
     *
     * @return A list of all saved recipes stored in the database.
     */
    @Query("SELECT * FROM saved_recipes")
    List<SavedRecipe> getAllSavedRecipes();

    /**
     * Retrieves a saved recipe from the database by its ID.
     *
     * @param id The ID of the saved recipe to retrieve.
     * @return The saved recipe with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM saved_recipes WHERE id = :id")
    SavedRecipe getSavedRecipeById(int id);
}
