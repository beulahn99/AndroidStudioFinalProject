package algonquin.cst2335.finalproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import algonquin.cst2335.finalproject.dao.SavedRecipeDAO;
import algonquin.cst2335.finalproject.model.Recipe;
import algonquin.cst2335.finalproject.model.SavedRecipe;

@Database(entities = {SavedRecipe.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {


    public abstract SavedRecipeDAO savedRecipeDAO();

    private static RecipeDatabase INSTANCE;

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
