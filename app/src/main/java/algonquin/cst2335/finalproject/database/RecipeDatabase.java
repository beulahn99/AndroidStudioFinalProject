package algonquin.cst2335.finalproject.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2335.finalproject.dao.SavedRecipeDAO;

/**
 * Purpose: Room database class for managing the SavedRecipe entity.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
@Database(entities = {SavedRecipe.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {

    /**
     * Retrieves the Data Access Object (DAO) for interacting with the SavedRecipe entity.
     *
     * @return The SavedRecipeDAO instance.
     */
    public abstract SavedRecipeDAO savedRecipeDAO();

    /**
     * Singleton instance of the RecipeDatabase class.
     */
    private static RecipeDatabase INSTANCE;

    /**
     * Creates a singleton instance of the RecipeDatabase class.
     *
     * @param context The application context.
     * @return The singleton instance of RecipeDatabase.
     */
    public static synchronized RecipeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }
}
