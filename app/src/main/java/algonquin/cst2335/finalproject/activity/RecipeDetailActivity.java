package algonquin.cst2335.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.RecipeDatabase;
import algonquin.cst2335.finalproject.dao.SavedRecipeDAO;
import algonquin.cst2335.finalproject.databinding.ActivityRecipeDetailBinding;
import algonquin.cst2335.finalproject.model.Recipe;
import algonquin.cst2335.finalproject.model.RecipeDetail;
import algonquin.cst2335.finalproject.model.SavedRecipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private ActivityRecipeDetailBinding binding;
    private SavedRecipeDAO savedRecipeDAO;
    private Executor executor;
    private RecipeDetail recipeDetail;
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
            binding.recipeSummaryTextView.setText(Html.fromHtml(recipeDetail.getSummary(), Html.FROM_HTML_MODE_COMPACT));
            Picasso.get().load(recipeDetail.getImageUrl()).into(binding.recipeImageView);

            // Set OnClickListener for the source URL button
            binding.sourceUrlButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open the source URL in a browser
                    String sourceUrl = recipeDetail.getSourceUrl();
                    if (sourceUrl != null && !sourceUrl.isEmpty()) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl));
                        startActivity(browserIntent);
                    } else {
                        // Handle case where source URL is not available
                        Toast.makeText(RecipeDetailActivity.this, "Source URL not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Set OnClickListener for the favorite button
            binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle favorite state
                    toggleSavedState(recipe);
                }
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
            Toast.makeText(this, "No recipe detail found", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to toggle favorite state
    private void toggleSavedState(Recipe recipe) {
        executor.execute(() -> {
            // Check if the recipe is already saved as favorite
            SavedRecipe existingFavorite = savedRecipeDAO.getSavedRecipeById(recipe.getId());

            if (existingFavorite != null) {
                // Recipe is already saved as favorite, so remove it from favorites
                savedRecipeDAO.delete(existingFavorite);
                runOnUiThread(() -> {
                    // Update button to show unfav state
                    binding.favoriteButton.setImageResource(R.drawable.ic_fav);
                    // Show toast message indicating the change
                    Toast.makeText(RecipeDetailActivity.this, "Recipe removed from favorites", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RecipeDetailActivity.this, "Recipe saved as favorite", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed(); // Navigate back to the previous activity (search page)
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}