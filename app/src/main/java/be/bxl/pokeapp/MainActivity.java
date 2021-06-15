package be.bxl.pokeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import be.bxl.pokeapp.api.RequestPokeDetails;
import be.bxl.pokeapp.db.PokemonDAO;
import be.bxl.pokeapp.fragments.ListOfPokemonFragment;
import be.bxl.pokeapp.fragments.MainFragment;
import be.bxl.pokeapp.fragments.PokeDetailFragment;
import be.bxl.pokeapp.models.Pokemon;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;

    MainFragment mainFragment;
    PokeDetailFragment pokeDetailFragment;
    ListOfPokemonFragment listOfPokemonFragment;

    PokemonDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get fragments
        fm = getSupportFragmentManager();

        mainFragment = MainFragment.newInstance();
        pokeDetailFragment = PokeDetailFragment.newInstance();
        listOfPokemonFragment = ListOfPokemonFragment.newInstance();

        // Get DB
        db = new PokemonDAO(getApplicationContext());

        // Set main fragment
        swapFragment(mainFragment);

        // Set button in main fragment to navigate between fragment
        mainFragment.setOnBtnSearchClickListener(() -> {
            pokeDetailFragment.updateBooleanForDisplay(true, false, true);
            swapFragment(pokeDetailFragment);
        });

        mainFragment.setOnBtnFavClickListener(() -> {
            listOfPokemonFragment.updateListStatus(false);
            listOfPokemonFragment.updatePokemonList(db.openReadable().getAllFavPokemon());
            db.close();
            swapFragment(listOfPokemonFragment);
        });

        mainFragment.setOnBtnTeamClickListener(() -> {
            listOfPokemonFragment.updateListStatus(true);
            listOfPokemonFragment.updatePokemonList(db.openReadable().getAllTeamPokemon());
            db.close();
            swapFragment(listOfPokemonFragment);
        });

        // Set button for Detail Fragment

        pokeDetailFragment.setOnSearchButtonClickListener(searchText -> {
            RequestPokeDetails requestPokeDetails = new RequestPokeDetails();
            requestPokeDetails.setPokemonListener(pokemonResult -> {

                if (pokemonResult != null) {
                    pokeDetailFragment.updatePokemon(pokemonResult);
                    pokeDetailFragment.updateFavBoolean(db.openReadable().isFav(pokemonResult.getId()));
                    pokeDetailFragment.updateBooleanForDisplay(true, false, true);

                    updateFragment(pokeDetailFragment);

                    db.close();
                }
                else {
                    Toast.makeText(this, "J'ai pas trouvé ton pokémon !", Toast.LENGTH_SHORT).show();
                }


            });

            requestPokeDetails.execute(searchText);
        });

        // Button accessing DB

        pokeDetailFragment.setOnFavButtonClickListener(pokemonFav -> {
            db.openWritable();

            if (db.getPokemonByID(pokemonFav.getId()) == null || !db.isFav(pokemonFav.getId())) {
                db.openWritable().insert(pokemonFav, false);
                pokeDetailFragment.updateFavBoolean(true);
                pokeDetailFragment.updateFavButton();
                updatePokemonListFragment();

                Toast.makeText(this, "Pokemon ajouté !", Toast.LENGTH_SHORT).show();
            }
            else {
                db.openWritable().delete(pokemonFav.getId(), false);
                pokeDetailFragment.updateFavBoolean(false);
                pokeDetailFragment.updateFavButton();
                updatePokemonListFragment();

                Toast.makeText(this, "Pokemon supprimé !", Toast.LENGTH_SHORT).show();
            }

            db.close();
        });

        pokeDetailFragment.setOnTeamButtonClickListener(pokemonTeam -> {
            db.openWritable();

            if (db.getPokemonByID(pokemonTeam.getId()) == null || !db.isTeam(pokemonTeam.getId())) {
                db.openWritable().insert(pokemonTeam, true);
                updatePokemonListFragment();
                Toast.makeText(this, "Pokemon ajouté !", Toast.LENGTH_SHORT).show();
            }
            else {
                db.openWritable().delete(pokemonTeam.getId(), true);
                updatePokemonListFragment();
                Toast.makeText(this, "Pokemon supprimé !", Toast.LENGTH_SHORT).show();
            }
            db.close();
        });

        // set On Item Click list fragment
        listOfPokemonFragment.setOnItemClickListener(pokemonID -> {

            db.openReadable();

            Pokemon pokemonFromDB = db.getPokemonByID(pokemonID);
            pokeDetailFragment.updatePokemon(pokemonFromDB);
            pokeDetailFragment.updateBooleanForDisplay(true, true, false);
            pokeDetailFragment.updateFavBoolean(db.isFav(pokemonFromDB.getId()));
            db.close();

            swapFragment(pokeDetailFragment);
        });

    }

    private void swapFragment(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame_layout_main_activity_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void updateFragment(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.detach(fragment);
        transaction.replace(R.id.frame_layout_main_activity_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        fm.popBackStack();
    }

    private void updatePokemonListFragment() {
        if (listOfPokemonFragment.isTeamList()) {
            listOfPokemonFragment.updatePokemonList(db.getAllTeamPokemon());
        }
        else {
            listOfPokemonFragment.updatePokemonList(db.getAllFavPokemon());
        }
    }

}