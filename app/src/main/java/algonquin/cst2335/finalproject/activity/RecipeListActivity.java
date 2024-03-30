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

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    private RecipeAdapter adapter;
    private ActivityRecipeListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
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
            Toast.makeText(this, "No recipes found", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemClick(View view, int position) {
        Recipe recipe = adapter.getRecipes().get(position);
        // Make API call to get detailed information about the recipe
        String apiUrl = "https://api.spoonacular.com/recipes/" + recipe.getId() + "/information?apiKey=e4c024ec9c464355907b51ff82634f78&includeNutrition=true";

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
                    Toast.makeText(RecipeListActivity.this, "Error parsing recipe details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(RecipeListActivity.this, "Failed to fetch recipe details: " + errorMessage, Toast.LENGTH_SHORT).show();
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