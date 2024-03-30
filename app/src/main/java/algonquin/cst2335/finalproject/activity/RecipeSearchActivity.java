package algonquin.cst2335.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.adapter.RecipeAdapter;
import algonquin.cst2335.finalproject.databinding.ActivityRecipeSearchBinding;
import algonquin.cst2335.finalproject.model.Recipe;

public class RecipeSearchActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private Context context;
    private ActivityRecipeSearchBinding binding;
    private SharedPreferences preferences;
    private ArrayList<String> previousSearchTerms = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes = new ArrayList<>();
    // API key
    private static final String API_KEY = "e4c024ec9c464355907b51ff82634f78";
    private static final String SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch?query=" + recipeName + "&apiKey=" + apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        recipeAdapter = new RecipeAdapter(context, recipes, this);
        binding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recipeRecyclerView.setAdapter(recipeAdapter);

        preferences = getSharedPreferences(getString(R.string.recipe_prefs), MODE_PRIVATE);

        binding.recipeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchTerm = binding.recipeEditText.getText().toString();
                    previousSearchTerms.add(searchTerm);
                    recipeAdapter.notifyDataSetChanged();
                    saveSearchTermsToPreferences(previousSearchTerms);
                    if (searchTerm == null || searchTerm.isEmpty()) {
                        Toast.makeText(RecipeSearchActivity.this, getString(R.string.check_search_empty), Toast.LENGTH_SHORT).show();
                    } else {
                        searchRecipes(searchTerm);
                    }
                    return true;
                }
                return false;
            }
        });

        displaySearchTerms();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fav_list) {
            Intent intent = new Intent(RecipeSearchActivity.this, SavedRecipeActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.home_page) {
            // Start the MainActivity
            Intent intent = new Intent(RecipeSearchActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.instructions) {
            showInstructionsDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showInstructionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.RecipeDialogTheme);
        builder.setTitle(getString(R.string.recipe_menu_info))
                .setMessage(Html.fromHtml(getString(R.string.recipe_instructions_message)))
                .setPositiveButton(getString(R.string.alert_dialog_ok), null)
                .show();
    }

    private void searchRecipes(String searchTerm) {
        String apiUrl = String.format(SEARCH_URL, searchTerm, API_KEY);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray resultsArray = jsonResponse.getJSONArray("results");
                            recipes.clear();
                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject recipeObject = resultsArray.getJSONObject(i);
                                int id = recipeObject.getInt("id");
                                String title = recipeObject.getString("title");
                                String imageUrl = recipeObject.getString("image");
                                Recipe recipe = new Recipe(id, title, imageUrl);
                                recipes.add(recipe);
                            }
                            recipeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RecipeSearchActivity.this, "Error fetching recipes", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

    private void displaySearchTerms() {
        List<String> searchTerms = getSearchTermsFromPreferences();
        recipeAdapter.setData(searchTerms);
    }

    private List<String> getSearchTermsFromPreferences() {
        String searchTermsJson = preferences.getString(getString(R.string.search_terms), "");
        Type type = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(searchTermsJson, type);
    }

    private void saveSearchTermsToPreferences(List<String> searchTerms) {
        String searchTermsJson = new Gson().toJson(searchTerms);
        preferences.edit().putString(getString(R.string.search_terms), searchTermsJson).apply();
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        binding.recipeTitleTextView.setText(recipe.getTitle());
        binding.recipeSummaryTextView.setText(recipe.getSummary());
        // Load image using Glide or another image loading library
        Glide.with(this).load(recipe.getImageUrl()).into(binding.recipeImageView);
        binding.recipeSourceUrlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceUrl = recipe.getSourceUrl();
                if (!TextUtils.isEmpty(sourceUrl)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl));
                    startActivity(intent);
                }
            }
        });
    }

}