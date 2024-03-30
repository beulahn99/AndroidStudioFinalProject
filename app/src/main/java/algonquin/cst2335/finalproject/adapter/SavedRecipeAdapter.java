package algonquin.cst2335.finalproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.model.Recipe;
import algonquin.cst2335.finalproject.RecipeDatabase;
import algonquin.cst2335.finalproject.model.SavedRecipe;

public class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder> {
    private List<SavedRecipe> savedRecipes;
    private OnItemClickListener onItemClickListener;
    private OnLinkClickListener onLinkClickListener;

    private OnUnSaveClickListener onUnSaveClickListener;

    public SavedRecipeAdapter(List<SavedRecipe> favoriteRecipes) {
        this.savedRecipes = favoriteRecipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_recipe, parent, false);
        return new ViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return savedRecipes.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(SavedRecipe savedRecipe);
    }

    public void setOnUnSaveClickListener(OnUnSaveClickListener listener) {
        this.onUnSaveClickListener = listener;
    }

    public interface OnUnSaveClickListener {
        void OnUnSaveClickListener(SavedRecipe favoriteRecipe);
    }

    public void setOnLinkClickListener(OnLinkClickListener listener) {
        this.onLinkClickListener = listener;
    }

    public interface OnLinkClickListener {
        void onLinkClick(SavedRecipe savedRecipe);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        ImageButton linkButton;
        ImageButton unSaveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipeTitleTextView);
            imageView = itemView.findViewById(R.id.recipeImageView);
            linkButton = itemView.findViewById(R.id.linkButton);
            unSaveButton = itemView.findViewById(R.id.unSaveButton);
        }
    }
}
