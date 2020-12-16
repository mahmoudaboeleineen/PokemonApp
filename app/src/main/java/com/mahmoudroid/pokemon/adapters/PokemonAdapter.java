package com.mahmoudroid.pokemon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmoudroid.pokemon.R;
import com.mahmoudroid.pokemon.model.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private ArrayList<Pokemon> pokemonsList = new ArrayList<>();
    private Context mContext;

    public PokemonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.pokemonName.setText(pokemonsList.get(position).getName());

        Glide.with(mContext).load(pokemonsList.get(position).getUrl())
                .into(holder.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return pokemonsList.size();
    }

    public void setList(ArrayList<Pokemon> postsList) {
        this.pokemonsList = postsList;
        notifyDataSetChanged();
    }

    public Pokemon getPokemonAt(int position) {
        return pokemonsList.get(position);
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private ImageView pokemonImage;
        private TextView pokemonName;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.pokemon_image);
            pokemonName = itemView.findViewById(R.id.pokemon_name);
        }
    }
}
