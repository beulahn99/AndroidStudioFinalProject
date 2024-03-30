package algonquin.cst2335.finalproject.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import algonquin.cst2335.finalproject.model.SavedRecipe;

public interface SavedRecipeDAO {
    @Insert
    void insert(SavedRecipe savedRecipe);

    @Update
    void update(SavedRecipe savedRecipe);

    @Delete
    void delete(SavedRecipe savedRecipe);

    @Query("SELECT * FROM saved_recipes")
    List<SavedRecipe> getAllSavedRecipes();

    @Query("SELECT * FROM saved_recipes WHERE id = :id")
    SavedRecipe getSavedRecipeById(int id);
}
