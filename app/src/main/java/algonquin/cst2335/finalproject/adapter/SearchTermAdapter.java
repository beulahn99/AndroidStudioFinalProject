package algonquin.cst2335.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;

/**
 * Purpose: Adapter class for the RecyclerView that displays previous search terms.
 *
 * Author: Beulah Nwokotubo
 * Section: 013
 * Creation Date: 28th March, 2024
 */
public class SearchTermAdapter extends RecyclerView.Adapter<SearchTermAdapter.ViewHolder> {

    /**
     * List of previous search terms to be displayed.
     */
    private ArrayList<String> searchTerms;

    /**
     * Listener for delete click events.
     */
    private OnDeleteClickListener onDeleteClickListener;

    /**
     * Listener for item click events.
     */
    private OnClickListener onClickListener;

    /**
     * Constructor to initialize the SearchTermAdapter with a list of previous search terms.
     *
     * @param searchTerms The list of previous search terms to be displayed.
     */
    public SearchTermAdapter(ArrayList<String> searchTerms) {
        this.searchTerms = searchTerms;
    }

    /**
     * Interface definition for a callback to be invoked when a search term is deleted.
     */
    public interface OnDeleteClickListener {
        /**
         * Callback method to be invoked when a search term is deleted.
         *
         * @param searchTerm The search term that was deleted.
         */
        void onDeleteClick(String searchTerm);
    }

    /**
     * Setter method to set the delete click listener for the delete button in the RecyclerView items.
     *
     * @param listener The delete click listener.
     */
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when a search term item is clicked.
     */
    public interface OnClickListener {
        /**
         * Callback method to be invoked when a search term item is clicked.
         *
         * @param searchTerm The search term item that was clicked.
         */
        void onItemClick(String searchTerm);
    }

    /**
     * Setter method to set the item click listener for the RecyclerView items.
     *
     * @param listener The item click listener.
     */
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_recipe, parent, false);
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
        String searchTerm = searchTerms.get(position);
        holder.textViewPreviousSearch.setText(searchTerm);

        // Set click listener for the entire item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(searchTerm);
                }
            }
        });

        // Set click listener for the delete button
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(searchTerm);
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
        return searchTerms.size();
    }

    /**
     * Updates the data set of search terms with a new list and notifies the adapter of the change.
     *
     * @param newSearchTerms The new list of search terms to be displayed.
     */
    public void setData(List<String> newSearchTerms) {
        searchTerms.clear();
        searchTerms.addAll(newSearchTerms);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class to hold the views for each search term item in the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView to display the previous search term.
         */
        TextView textViewPreviousSearch;

        /**
         * ImageView for the delete button.
         */
        ImageView imageViewDelete;

        /**
         * Constructor to initialize the ViewHolder with the item view.
         *
         * @param itemView The item view for a search term in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPreviousSearch = itemView.findViewById(R.id.textViewPreviousSearch);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }
    }
}
