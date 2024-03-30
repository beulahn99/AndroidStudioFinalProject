package algonquin.cst2335.finalproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.model.Recipe;


@Dao
public interface RecipeDAO {
    @Insert
    void insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("SELECT * FROM recipes")
    LiveData<ArrayList<Recipe>> getAllRecipes();
}
