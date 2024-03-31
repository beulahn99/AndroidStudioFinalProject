package algonquin.cst2335.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.database.RecipeDatabase;
import algonquin.cst2335.finalproject.adapter.SavedRecipeAdapter;
import algonquin.cst2335.finalproject.dao.SavedRecipeDAO;
import algonquin.cst2335.finalproject.databinding.ActivitySavedRecipesBinding;
import algonquin.cst2335.finalproject.database.SavedRecipe;

/**
 * Purpose: This activity displays the list of saved recipes.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class SavedRecipeActivity extends AppCompatActivity {

    /**
     * DAO for accessing saved recipes from the database.
     */
    private SavedRecipeDAO savedRecipeDAO;

    /**
     * Adapter for the RecyclerView that displays the list of saved recipes.
     */
    private SavedRecipeAdapter adapter;

    /**
     * View binding instance for the activity.
     */
    private ActivitySavedRecipesBinding binding;

    /**
     * Executor for performing database operations asynchronously.
     */
    private Executor executor;

    /**
     * List of saved recipes.
     */
    private List<SavedRecipe> savedRecipes;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedRecipesBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_saved_recipes);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.saved_recipe_title);

        savedRecipeDAO = RecipeDatabase.getInstance(this).savedRecipeDAO();

        executor = Executors.newSingleThreadExecutor();

        savedRecipes = new ArrayList<>();

        adapter = new SavedRecipeAdapter(savedRecipes);

        binding.recyclerViewFavRecipes.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewFavRecipes.setAdapter(adapter);

        loadSavedRecipes();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_recipe_summary);
        TextView summaryTextView = dialog.findViewById(R.id.summaryTextView);

        adapter.setOnItemClickListener(savedRecipe -> {
            // Display the summary in the dialog
            summaryTextView.setText(Html.fromHtml(savedRecipe.getSummary(), Html.FROM_HTML_MODE_COMPACT));

            dialog.show();
        });

        adapter.setOnLinkClickListener(savedRecipe -> {
            String sourceUrl = savedRecipe.getSourceUrl();
            if (sourceUrl != null && !sourceUrl.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl));
                startActivity(browserIntent);
            }
        });

        adapter.setOnUnSaveClickListener(savedRecipe -> Snackbar.make(findViewById(android.R.id.content), R.string.confirm_remove_recipe, Snackbar.LENGTH_LONG)
                .setAction(R.string.remove_action, view -> executor.execute(() -> {
                    savedRecipeDAO.delete(savedRecipe);
                    runOnUiThread(() -> {
                        // Remove the recipe from the RecyclerView
                        savedRecipes.remove(savedRecipe);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(SavedRecipeActivity.this, R.string.recipe_removed_from_favorites, Toast.LENGTH_SHORT).show();
                    });
                })).show());
    }

    /**
     * Load saved recipes from the database asynchronously.
     */
    private void loadSavedRecipes() {
        executor.execute(() -> {
            savedRecipes.clear();
            savedRecipes.addAll(savedRecipeDAO.getAllSavedRecipes());
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }

    /**
     * Called when a menu item is selected.
     *
     * @param item The selected menu item.
     * @return True if the menu item selection is handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed(); // Navigate back to the previous activity (search page)
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}