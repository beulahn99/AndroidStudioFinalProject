package algonquin.cst2335.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.adapter.SearchTermAdapter;
import algonquin.cst2335.finalproject.api.VolleyRequestHandler;
import algonquin.cst2335.finalproject.databinding.ActivityRecipeSearchBinding;
import algonquin.cst2335.finalproject.model.Recipe;

/**
 * Purpose: This activity allows users to search for recipes using a search term. It displays a list of previous
 * search terms and allows users to perform new searches. Users can also access their favorite recipes
 * and view instructions for using the app.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class RecipeSearchActivity extends AppCompatActivity implements SearchTermAdapter.OnDeleteClickListener, SearchTermAdapter.OnClickListener {

    /**
     * The binding object for the activity layout.
     */
    private ActivityRecipeSearchBinding binding;

    /**
     * SharedPreferences object for storing search terms.
     */
    private SharedPreferences prefs;

    /**
     * Editor object for modifying SharedPreferences.
     */
    private SharedPreferences.Editor editor;

    /**
     * List to store the searched words.
     */
    private final ArrayList<String> searchedWords = new ArrayList<>();

    /**
     * Adapter for displaying search terms in RecyclerView.
     */
    private SearchTermAdapter adapter;

    /**
     * Initializes the activity layout and sets up necessary components.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar);
        adapter = new SearchTermAdapter(searchedWords);
        adapter.setOnDeleteClickListener(this);
        adapter.setOnClickListener(this); // Set item click listener
        binding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recipeRecyclerView.setAdapter(adapter);

        prefs = getSharedPreferences(getString(R.string.my_data_prefs), Context.MODE_PRIVATE);
        editor = prefs.edit(); // Initialize the editor object
        // Search button click listener
        binding.searchButton.setOnClickListener(v -> {
            try {

                String searchTerm = binding.recipeEditText.getText().toString();

                // Add the search term to the list
                searchedWords.add(searchTerm);

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();

                // Save the list to SharedPreferences
                saveSearchTermsToPreferences(searchedWords);
                performSearch(searchTerm);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any exceptions that may occur during search operation
            }
        });

        // Display the list of previous search terms
        displaySearchTerms();
    }

    /**
     * Method to retrieve search terms from SharedPreferences
     */
    private List<String> getSearchTermsFromPreferences() {
        String searchTermsJson = prefs.getString(getString(R.string.searched_recipe_key), "[]");
        try {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            return new Gson().fromJson(searchTermsJson, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            // Handle JSON parsing error, e.g., log the error or return an empty list
            return new ArrayList<>();
        }
    }

        private void saveSearchTermsToPreferences(List<String> searchTerms) {
            String searchTermsJson = new Gson().toJson(searchTerms);
            editor.putString(getString(R.string.searched_recipe_key), searchTermsJson).apply();
        }

        // Method to display search terms in RecyclerView
        private void displaySearchTerms() {
            List<String> searchTerms = getSearchTermsFromPreferences();
            adapter.setData(searchTerms);
        }

        // Method to handle click on delete button in RecyclerView item
        @Override
        public void onDeleteClick(String searchTerm) {
            searchedWords.remove(searchTerm);
            saveSearchTermsToPreferences(searchedWords);
            adapter.notifyDataSetChanged();
        }

        // Method to create options menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.recipe_menu, menu);
            return true;
        }

    /**
     * Method to handle options menu item selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fav_list) {
            // Start the SavedRecipeActivity
            Intent intent = new Intent(RecipeSearchActivity.this, SavedRecipeActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.home_page) {
            // Start the MainActivity
            Intent intent = new Intent(RecipeSearchActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.instructions) {
            // Show the help dialog for the app
            showInstructionsDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Method to perform search operation
     */
    private void performSearch(String searchTerm) {
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + searchTerm + "&apiKey=e4c024ec9c464355907b51ff82634f78";

        VolleyRequestHandler requestHandler = new VolleyRequestHandler(this);
        requestHandler.getData(url, new VolleyRequestHandler.VolleyResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray resultsArray = response.getJSONArray("results");
                    if (resultsArray.length() > 0) {
                        List<Recipe> recipes = new ArrayList<>();
                        for (int i = 0; i < resultsArray.length(); i++) {
                            JSONObject recipeObject = resultsArray.getJSONObject(i);
                            int id = recipeObject.getInt("id");
                            String title = recipeObject.getString("title");
                            String image = recipeObject.getString("image");
                            String imageType = recipeObject.getString("imageType");
                            Recipe recipe = new Recipe(id, title, image, imageType);
                            recipes.add(recipe);
                        }
                        // Start the RecipeListActivity with the list of recipes
                        Intent intent = new Intent(RecipeSearchActivity.this, RecipeListActivity.class);
                        intent.putExtra("searchWord", searchTerm);
                        intent.putParcelableArrayListExtra("recipes", new ArrayList<>(recipes));
                        startActivity(intent);
                    } else {
                        Toast.makeText(RecipeSearchActivity.this, R.string.error_no_recipes, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RecipeSearchActivity.this, R.string.error_parsing_response, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(String errorMessage) {
                Toast.makeText(RecipeSearchActivity.this, getString(R.string.error_failed, errorMessage), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to handle item click in RecyclerView
     */
    @Override
    public void onItemClick(String searchTerm) {
        performSearch(searchTerm);
    }

    /**
     * Displays an AlertDialog containing instructions for using the app.
     */
    private void showInstructionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_instructions);
        builder.setMessage(Html.fromHtml(getString(R.string.recipe_instructions_message)));
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}