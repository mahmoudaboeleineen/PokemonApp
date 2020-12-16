package com.mahmoudroid.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private PokemonViewModel mPokemonViewModel;
    private RecyclerView mPokemonRecyclerView;
    private PokemonAdapter mPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPokemonRecyclerView = findViewById(R.id.pokemon_recyclerview);
        mPokemonAdapter = new PokemonAdapter(this);
        mPokemonRecyclerView.setAdapter(mPokemonAdapter);
        setUpSwipe();


        mPokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        mPokemonViewModel.getPokemons();

        mPokemonViewModel.getPokemonList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                mPokemonAdapter.setList(pokemons);
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
                Pokemon swipedPokemon = mPokemonAdapter.getPokemonAt(swipedPokemonPosition);
                mPokemonViewModel.insertPokemon(swipedPokemon);
                Log.d(TAG, "onSwipedTest: " + swipedPokemon.getName());
                mPokemonAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mPokemonRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favourite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favourite_list_menu) {
            startActivity(new Intent(MainActivity.this, FavouriteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}