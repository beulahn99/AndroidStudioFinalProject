package algonquin.cst2335.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.database.RecipeDatabase;
import algonquin.cst2335.finalproject.dao.SavedRecipeDAO;
import algonquin.cst2335.finalproject.databinding.ActivityRecipeDetailBinding;
import algonquin.cst2335.finalproject.model.Recipe;
import algonquin.cst2335.finalproject.model.RecipeDetail;
import algonquin.cst2335.finalproject.database.SavedRecipe;

/**
 * Purpose: This activity displays detailed information about a recipe.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class RecipeDetailActivity extends AppCompatActivity {

    /**
     * View binding instance for the recipe detail activity.
     */
    private ActivityRecipeDetailBinding binding;

    /**
     * DAO for accessing saved recipes from the database.
     */
    private SavedRecipeDAO savedRecipeDAO;

    /**
     * Executor for performing database operations in a background thread.
     */
    private Executor executor;

    /**
     * Recipe detail object containing detailed information about the recipe.
     */
    private RecipeDetail recipeDetail;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize your FavoriteRecipeDao
        savedRecipeDAO = RecipeDatabase.getInstance(this).savedRecipeDAO();

        // Initialize Executor
        executor = Executors.newSingleThreadExecutor();

        // Retrieve recipe detail data from intent extras
        Recipe recipe = getIntent().getParcelableExtra("recipe");
        getSupportActionBar().setTitle(recipe.getTitle());

        recipeDetail = getIntent().getParcelableExtra("recipeDetail");

        // Populate UI elements with recipe detail data
        if (recipeDetail != null) {
            binding.summaryTextView.setText(Html.fromHtml(recipeDetail.getSummary(), Html.FROM_HTML_MODE_COMPACT));
            Picasso.get().load(recipeDetail.getImageUrl()).into(binding.imageView);

            // Set OnClickListener for the source URL button
            binding.sourceUrlButton.setOnClickListener(v -> {
                // Open the source URL in a browser
                String sourceUrl = recipeDetail.getSourceUrl();
                if (sourceUrl != null && !sourceUrl.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl));
                    startActivity(browserIntent);
                } else {
                    // Handle case where source URL is not available
                    Toast.makeText(RecipeDetailActivity.this, R.string.unavailable_url, Toast.LENGTH_SHORT).show();
                }
            });

            // Set OnClickListener for the favorite button
            binding.favoriteButton.setOnClickListener(v -> {
                // Toggle favorite state
                toggleSavedState(recipe);
            });

            // Set initial state of favorite button based on database
            executor.execute(() -> {
                SavedRecipe existingSave = savedRecipeDAO.getSavedRecipeById(recipe.getId());
                runOnUiThread(() -> {
                    if (existingSave != null) {

                        binding.favoriteButton.setImageResource(R.drawable.ic_delete);
                    } else {

                        binding.favoriteButton.setImageResource(R.drawable.ic_fav);
                    }
                });
            });
        } else {
            // Handle case where recipe detail data is not available
            Toast.makeText(this, R.string.error_no_details, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to toggle the saved state of a recipe (add/remove from favorites).
     *
     * @param recipe The recipe object.
     */
    private void toggleSavedState(Recipe recipe) {
        executor.execute(() -> {
            // Check if the recipe is already saved as favorite
            SavedRecipe existingFavorite = savedRecipeDAO.getSavedRecipeById(recipe.getId());

            if (existingFavorite != null) {
                // Recipe is already saved as favorite, so remove it from favorites
                savedRecipeDAO.delete(existingFavorite);
                runOnUiThread(() -> {
                    // Update button to show save state
                    binding.favoriteButton.setImageResource(R.drawable.ic_fav);
                    // Show toast message indicating the change
                    Toast.makeText(RecipeDetailActivity.this, R.string.removed_favorite, Toast.LENGTH_SHORT).show();
                });
            } else {
                // Recipe is not saved as favorite, so save it as favorite
                SavedRecipe newFavorite = new SavedRecipe();
                newFavorite.setId(recipe.getId());
                newFavorite.setTitle(recipe.getTitle());
                newFavorite.setSourceUrl(recipeDetail.getSourceUrl());
                newFavorite.setImageUrl(recipeDetail.getImageUrl());
                newFavorite.setSummary(recipeDetail.getSummary());
                savedRecipeDAO.insert(newFavorite);
                runOnUiThread(() -> {
                    // Update button to show fav state
                    binding.favoriteButton.setImageResource(R.drawable.ic_delete);
                    // Show toast message indicating the change
                    Toast.makeText(RecipeDetailActivity.this, R.string.saved_favorite, Toast.LENGTH_SHORT).show();
                });
            }
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