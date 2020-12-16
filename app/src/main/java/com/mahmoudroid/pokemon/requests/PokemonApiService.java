package com.mahmoudroid.pokemon.requests;

import com.mahmoudroid.pokemon.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;


public interface PokemonApiService {

    @GET("pokemon?limit=100&offset=0")
    Observable<PokemonResponse> getPokemons();
}
