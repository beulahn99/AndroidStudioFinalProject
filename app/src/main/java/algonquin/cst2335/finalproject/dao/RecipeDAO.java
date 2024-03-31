package algonquin.cst2335.finalproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.model.Recipe;

/**
 * Purpose: Data Access Object (DAO) interface for interacting with the Recipe entity in the Room database.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
@Dao
public interface RecipeDAO {

    /**
     * Inserts a new recipe into the database.
     *
     * @param recipe The recipe to be inserted.
     */
    @Insert
    void insertRecipe(Recipe recipe);

    /**
     * Deletes a recipe from the database.
     *
     * @param recipe The recipe to be deleted.
     */
    @Delete
    void deleteRecipe(Recipe recipe);

    /**
     * Retrieves all recipes from the database.
     *
     * @return A LiveData object containing a list of all recipes stored in the database.
     */
    @Query("SELECT * FROM recipes")
    LiveData<ArrayList<Recipe>> getAllRecipes();
}
