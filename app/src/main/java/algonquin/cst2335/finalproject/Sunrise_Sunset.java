package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Sunrise_Sunset extends AppCompatActivity {

    private EditText editTextLat, editTextLng;
    private Button btnLookup, btnSave, btnViewFavorites;
    private TextView tvResult;
    private RecyclerView recyclerViewFavorites;
    private SharedPreferences sharedPreferences;
    private String lastSearchTerm;

    private SQLiteDatabase database;
    private FavoritesAdapter favoritesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunrise_sunset);




        editTextLat = findViewById(R.id.editTextLat);
        editTextLng = findViewById(R.id.editTextLng);
        btnLookup = findViewById(R.id.btnLookup);
        btnSave = findViewById(R.id.btnSave);
        btnViewFavorites = findViewById(R.id.btnViewFavorites);
        tvResult = findViewById(R.id.editTextLng);
        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        lastSearchTerm = sharedPreferences.getString("lastSearchTerm", "");
        editTextLat.setText(lastSearchTerm);

        btnLookup.setOnClickListener(v -> lookupSunriseSunset());

        btnSave.setOnClickListener(v -> saveLocationToFavorites());

        btnViewFavorites.setOnClickListener(v -> viewFavorites());

        setupDatabase();
        setupRecyclerView();
    }


    private void setupDatabase() {
        database = openOrCreateDatabase("FavoritesDB", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS favorites (id INTEGER PRIMARY KEY AUTOINCREMENT, lat TEXT, lng TEXT)");
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewFavorites.setLayoutManager(layoutManager);

        Cursor cursor = database.rawQuery("SELECT * FROM favorites", null);
        favoritesAdapter = new FavoritesAdapter(cursor);
        recyclerViewFavorites.setAdapter(favoritesAdapter);
    }

    private void lookupSunriseSunset() {
        String lat = editTextLat.getText().toString();
        String lng = editTextLng.getText().toString();

        String url = "https://api.sunrisesunset.io/json?lat=" + lat + "&lng=" + lng + "&timezone=UTC&date=today";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject results = response.getJSONObject("results");
                        String sunrise = results.getString("sunrise");
                        String sunset = results.getString("sunset");

                        String resultText = "Sunrise: " + sunrise + "\nSunset: " + sunset;
                        tvResult.setText(resultText);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("lastSearchTerm", lat);
                        editor.apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    tvResult.setText("Error fetching data");
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void saveLocationToFavorites() {
        String lat = editTextLat.getText().toString();
        String lng = editTextLng.getText().toString();

        ContentValues values = new ContentValues();
        values.put("lat", lat);
        values.put("lng", lng);

        database.insert("favorites", null, values);
        favoritesAdapter.swapCursor(database.rawQuery("SELECT * FROM favorites", null));
    }

    private void viewFavorites() {
        Cursor cursor = database.rawQuery("SELECT * FROM favorites", null);
        favoritesAdapter.swapCursor(cursor);
    }
    private void showDeleteConfirmationDialog(String lat, String lng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete this location?");
        builder.setPositiveButton("Yes", (dialog, id) -> deleteLocation(lat, lng));
        builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteLocation(String lat, String lng) {
        database.delete("favorites", "lat=? AND lng=?", new String[]{lat, lng});
        Toast.makeText(this, "Location deleted", Toast.LENGTH_SHORT).show();
        viewFavorites(); // Refresh the favorites list
    }

    private static class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

        private Cursor cursor;

        public FavoritesAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        public void swapCursor(Cursor newCursor) {
            if (cursor != null) {
                cursor.close();
            }

            cursor = newCursor;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (cursor.moveToPosition(position)) {
                String lat = cursor.getString(cursor.getColumnIndex("lat"));
                String lng = cursor.getString(cursor.getColumnIndex("lng"));
                holder.textView.setText(lat + ", " + lng);

                holder.itemView.setOnClickListener(v -> {
                    Context context = v.getContext();
                    if (context instanceof Sunrise_Sunset) {
                        Sunrise_Sunset activity = (Sunrise_Sunset) context;
                        activity.showDeleteConfirmationDialog(lat, lng);
                    }

                    if (context instanceof Sunrise_Sunset) {
                        Sunrise_Sunset activity = (Sunrise_Sunset) context;
                        if (activity != null) {
                            if (activity.database != null && activity.database.isOpen()) {
                                @SuppressLint("Range") String selectedLat = cursor.getString(cursor.getColumnIndex("lat"));
                                @SuppressLint("Range") String selectedLng = cursor.getString(cursor.getColumnIndex("lng"));
                                activity.lookupSunriseSunset(selectedLat, selectedLng);
                            }
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return cursor == null ? 0 : cursor.getCount();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }



    private void lookupSunriseSunset(String selectedLat, String selectedLng) {
    }
}