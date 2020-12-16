package com.mahmoudroid.pokemon.repository;


import androidx.lifecycle.LiveData;

import com.mahmoudroid.pokemon.db.PokemonDao;
import com.mahmoudroid.pokemon.model.Pokemon;
import com.mahmoudroid.pokemon.model.PokemonResponse;
import com.mahmoudroid.pokemon.requests.PokemonApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private PokemonApiService pokemonApiService;
    private PokemonDao pokemonDao;

    @Inject
    public Repository(PokemonApiService pokemonApiService, PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao = pokemonDao;
    }

    public Observable<PokemonResponse> getPokemons() {
        return pokemonApiService.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon) {
        pokemonDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        pokemonDao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getFavouritePokemon() {
        return pokemonDao.getPokemons();
    }

    public void deleteAllPokemons() {
        pokemonDao.deleteAllPokemons();
    }
}
