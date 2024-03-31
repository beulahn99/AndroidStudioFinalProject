package algonquin.cst2335.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.database.SavedRecipe;

/**
 * Purpose: Adapter class for the RecyclerView that displays a list of saved recipes.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder> {
    /**
     * List of saved recipes to be displayed.
     */
    private final List<SavedRecipe> savedRecipes;

    /**
     * Listener for item click events.
     */
    private OnItemClickListener onItemClickListener;

    /**
     * Listener for link click events.
     */
    private OnLinkClickListener onLinkClickListener;

    /**
     * Listener for unsave click events.
     */
    private OnUnSaveClickListener onUnSaveClickListener;

    /**
     * Constructor to initialize the SavedRecipeAdapter with a list of saved recipes.
     *
     * @param favoriteRecipes The list of saved recipes to be displayed.
     */
    public SavedRecipeAdapter(List<SavedRecipe> favoriteRecipes) {
        this.savedRecipes = favoriteRecipes;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_recipe, parent, false);
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
        SavedRecipe savedRecipe = savedRecipes.get(position);
        holder.titleTextView.setText(savedRecipe.getTitle());
        Picasso.get().load(savedRecipe.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(savedRecipe);
                }
            }
        });

        holder.linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLinkClickListener != null) {
                    onLinkClickListener.onLinkClick(savedRecipe);
                }
            }
        });

        holder.unSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUnSaveClickListener != null) {
                    onUnSaveClickListener.OnUnSaveClickListener(savedRecipe);
                }
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return savedRecipes.size();
    }

    /**
     * Setter method to set the item click listener for the RecyclerView items.
     *
     * @param listener The item click listener.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when a saved recipe item is clicked.
     */
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when a saved recipe item is clicked.
         *
         * @param savedRecipe The saved recipe item that was clicked.
         */
        void onItemClick(SavedRecipe savedRecipe);
    }

    /**
     * Setter method to set the unsave click listener for the unsave button in the RecyclerView items.
     *
     * @param listener The unsave click listener.
     */
    public void setOnUnSaveClickListener(OnUnSaveClickListener listener) {
        this.onUnSaveClickListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when the unsave button is clicked.
     */
    public interface OnUnSaveClickListener {
        void OnUnSaveClickListener(SavedRecipe favoriteRecipe);
    }

    /**
     * Setter method to set the link click listener for the link button in the RecyclerView items.
     *
     * @param listener The link click listener.
     */
    public void setOnLinkClickListener(OnLinkClickListener listener) {
        this.onLinkClickListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when the link button is clicked.
     */
    public interface OnLinkClickListener {
        void onLinkClick(SavedRecipe savedRecipe);
    }

    /**
     * ViewHolder class to hold the views for each saved recipe item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView to display the recipe title.
         */
        TextView titleTextView;

        /**
         * ImageView to display the recipe image.
         */
        ImageView imageView;

        /**
         * ImageButton to open the link associated with the recipe.
         */
        ImageButton linkButton;

        /**
         * ImageButton to unsave the recipe.
         */
        ImageButton unSaveButton;

        /**
         * Constructor to initialize the ViewHolder with the item view.
         *
         * @param itemView The item view for a saved recipe in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
            linkButton = itemView.findViewById(R.id.linkButton);
            unSaveButton = itemView.findViewById(R.id.unSaveButton);
        }
    }
}
