package algonquin.cst2335.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.model.Recipe;

/**
 * Adapter class for the RecyclerView that displays a list of recipes.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    /**
     * Context of the application/activity.
     */
    private Context context;

    /**
     * List of recipes to be displayed.
     */
    private List<Recipe> recipes;

    /**
     * Listener for item click events.
     */
    private OnItemClickListener listener;

    /**
     * Constructor to initialize the RecipeAdapter with a list of recipes.
     *
     * @param recipes The list of recipes to be displayed.
     */
    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Getter method to retrieve the list of recipes.
     *
     * @return The list of recipes.
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Method to set the item click listener for the RecyclerView items.
     *
     * @param listener The item click listener.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        // Load image using Picasso
        Picasso.get().load(recipe.getImage()).into(holder.imageView);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    /**
     * Interface definition for a callback to be invoked when a recipe item is clicked.
     */
    public interface OnItemClickListener {
        /**
         * Callback method to be invoked when a recipe item is clicked.
         *
         * @param view     The view that was clicked.
         * @param position The position of the item that was clicked.
         */
        void onItemClick(View view, int position);
    }

    /**
     * ViewHolder class to hold the views for each recipe item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * ImageView to display the recipe image.
         */

        ImageView imageView;

        /**
         * TextView to display the recipe title.
         */
        TextView titleTextView;

        /**
         * Constructor to initialize the ViewHolder with the item view.
         *
         * @param itemView The item view for a recipe in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, position);
                }
            }
        }
    }

}