package algonquin.cst2335.finalproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    /**
     View binding instance for the main activity.
     */
    private ActivityMainBinding binding;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_dictionary) {
            //TODO
        } else if (item.getItemId() == R.id.item_recipes) {
            Intent intent = new Intent(MainActivity.this, RecipeSearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.item_songs) {
            Intent intent = new Intent(MainActivity.this, DeezerMainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.item_sunrise) {
            //TODO
        } else if (item.getItemId() == R.id.item_about) {
            // Retrieve the about text from string resources
            String aboutText = getString(R.string.about_text);

            // Create a Snackbar with about info
            Snackbar.make(binding.getRoot(), aboutText, Snackbar.LENGTH_LONG).show();
        } else {
            //TODO
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu); // Inflate the menu layout
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // Initialize view binding
        setContentView(binding.getRoot()); // Set the content view using view binding

        setSupportActionBar(binding.mainToolbar);
    }
}