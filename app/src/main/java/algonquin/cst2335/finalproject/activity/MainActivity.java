package algonquin.cst2335.finalproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.DeezerMainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

/**
 * Purpose: This activity represents the main screen of the application.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class MainActivity extends AppCompatActivity {

    /**
     View binding instance for the main activity.
     */
    private ActivityMainBinding binding;

    /**
     * Called when a menu item is selected.
     *
     * @param item The selected menu item.
     * @return True if the menu item selection is handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//          if (item.getItemId() == R.id.item_dictionary) {
//            //TODO
//        } else
        if (item.getItemId() == R.id.item_recipes) {
            Intent intent = new Intent(MainActivity.this, RecipeSearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.item_songs) {
            Intent intent = new Intent(MainActivity.this, DeezerMainActivity.class);
            startActivity(intent);
//        } else if (item.getItemId() == R.id.item_sunrise) {
//            //TODO
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

    /**
     * Called to initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return True for the menu to be displayed, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);
    }
}