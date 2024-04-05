package algonquin.cst2335.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.adapter.RecipeAdapter;
import algonquin.cst2335.finalproject.api.VolleyRequestHandler;
import algonquin.cst2335.finalproject.databinding.ActivityRecipeListBinding;
import algonquin.cst2335.finalproject.model.Recipe;
import algonquin.cst2335.finalproject.model.RecipeDetail;

/**
 * Purpose: This activity displays a list of recipes based on the search term.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    /**
     * Adapter for the RecyclerView that displays the list of recipes.
     */
    private RecipeAdapter adapter;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        algonquin.cst2335.finalproject.databinding.ActivityRecipeListBinding binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String titleName = getIntent().getStringExtra("searchTerm");
        getSupportActionBar().setTitle(titleName);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Recipe> recipes = getIntent().getParcelableArrayListExtra("recipes");
        if (recipes != null) {
            adapter = new RecipeAdapter(recipes);
            binding.recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(this);
        } else {
            Toast.makeText(this, R.string.error_no_recipes, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method invoked when a recipe item is clicked in the RecyclerView.
     *
     * @param view     The view that was clicked.
     * @param position The position of the clicked item in the RecyclerView.
     */
    @Override
    public void onItemClick(View view, int position) {
        Recipe recipe = adapter.getRecipes().get(position);
        // Make API call to get detailed information about the recipe
        String apiUrl = String.valueOf("https://api.spoonacular.com/recipes/" + recipe.getId() + "/information?apiKey=e4c024ec9c464355907b51ff82634f78&includeNutrition=true");

        VolleyRequestHandler requestHandler = new VolleyRequestHandler(this);
        requestHandler.getData(apiUrl, new VolleyRequestHandler.VolleyResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    // Parse the JSON response to get detailed recipe information
                    String sourceName = response.getString("sourceName");
                    String sourceUrl = response.getString("sourceUrl");
                    String summary = response.getString("summary");
                    String imageUrl = response.getString("image");

                    // Create a RecipeDetail object with the retrieved information
                    RecipeDetail recipeDetail = new RecipeDetail(sourceName, sourceUrl, summary, imageUrl);

                    // Start RecipeDetailActivity with the clicked recipe and detailed information
                    Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                    intent.putExtra("recipe", recipe);
                    intent.putExtra("recipeDetail", recipeDetail);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RecipeListActivity.this, R.string.error_parsing_response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(RecipeListActivity.this, R.string.error_failed_details + errorMessage, Toast.LENGTH_SHORT).show();
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