package com.mahmoudroid.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mahmoudroid.pokemon.adapters.PokemonAdapter;
import com.mahmoudroid.pokemon.model.Pokemon;
import com.mahmoudroid.pokemon.viewmodels.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavouriteActivity extends AppCompatActivity {

    private static final String TAG = "FavouriteActivity";

    private PokemonViewModel mFavouritePokemonViewModel;
    private RecyclerView mFavouritePokemonRecyclerView;
    private PokemonAdapter mFavouritePokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().setTitle("Favourite List");
        mFavouritePokemonRecyclerView = findViewById(R.id.favourite_recyclerview);
        mFavouritePokemonAdapter = new PokemonAdapter(this);
        mFavouritePokemonRecyclerView.setAdapter(mFavouritePokemonAdapter);
        setUpSwipe();

        mFavouritePokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        mFavouritePokemonViewModel.getFavouritePokemon();

        mFavouritePokemonViewModel.getFavouriteList().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                ArrayList<Pokemon> list = new ArrayList<>(pokemons);
                mFavouritePokemonAdapter.setList(list);
                Log.d(TAG, "onChangedTest: " + list.size());
            }
        });
    }

    private void setUpSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon swipedPokemon = mFavouritePokemonAdapter.getPokemonAt(swipedPokemonPosition);
                mFavouritePokemonViewModel.deletePokemon(swipedPokemon.getName());
                mFavouritePokemonAdapter.notifyDataSetChanged();
                Toast.makeText(FavouriteActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mFavouritePokemonRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_menu) {
            mFavouritePokemonViewModel.deleteAllPokemons();
            Toast.makeText(this, "All Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
