package algonquin.cst2335.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.RecipeDatabase;
import algonquin.cst2335.finalproject.adapter.RecipeAdapter;
import algonquin.cst2335.finalproject.adapter.SavedRecipeAdapter;
import algonquin.cst2335.finalproject.dao.SavedRecipeDAO;
import algonquin.cst2335.finalproject.databinding.ActivitySavedRecipesBinding;
import algonquin.cst2335.finalproject.model.Recipe;
import algonquin.cst2335.finalproject.model.SavedRecipe;

public class SavedRecipeActivity extends AppCompatActivity {

    private SavedRecipeDAO savedRecipeDAO;
    private SavedRecipeAdapter adapter;
    private ActivitySavedRecipesBinding binding;
    private Executor executor;
    private List<SavedRecipe> savedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedRecipesBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_saved_recipes);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Saved Recipe");

        savedRecipeDAO = RecipeDatabase.getInstance(this).savedRecipeDAO();

        executor = Executors.newSingleThreadExecutor();

        savedRecipes = new ArrayList<>();

        adapter = new SavedRecipeAdapter(savedRecipes);

        binding.recyclerViewFavRecipes.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewFavRecipes.setAdapter(adapter);

        loadSavedRecipes();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_recipe_summary);
        TextView summaryTextView = dialog.findViewById(R.id.recipeSummaryTextView);

        adapter.setOnItemClickListener(new SavedRecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SavedRecipe savedRecipe) {
                // Display the summary in the dialog
                summaryTextView.setText(Html.fromHtml(savedRecipe.getSummary(), Html.FROM_HTML_MODE_COMPACT));

                dialog.show();
            }
        });

        adapter.setOnLinkClickListener(new SavedRecipeAdapter.OnLinkClickListener() {
            @Override
            public void onLinkClick(SavedRecipe savedRecipe) {
                String sourceUrl = savedRecipe.getSourceUrl();
                if (sourceUrl != null && !sourceUrl.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl));
                    startActivity(browserIntent);
                }
            }
        });

        adapter.setOnUnSaveClickListener(new SavedRecipeAdapter.OnUnSaveClickListener() {
            @Override
            public void OnUnSaveClickListener(SavedRecipe savedRecipe) {
                Snackbar.make(findViewById(android.R.id.content), "Are you sure you want to remove this recipe from favorites?", Snackbar.LENGTH_LONG)
                        .setAction("REMOVE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                executor.execute(() -> {
                                    savedRecipeDAO.delete(savedRecipe);
                                    runOnUiThread(() -> {
                                        // Remove the recipe from the RecyclerView
                                        savedRecipes.remove(savedRecipe);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(SavedRecipeActivity.this, "Recipe removed from favorites", Toast.LENGTH_SHORT).show();
                                    });
                                });
                            }
                        }).show();
            }
        });
    }

    private void loadSavedRecipes() {
        executor.execute(() -> {
            savedRecipes.clear();
            savedRecipes.addAll(savedRecipeDAO.getAllSavedRecipes());
            runOnUiThread(() -> adapter.notifyDataSetChanged());
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