package be.bxl.pokeapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import be.bxl.pokeapp.R;
import be.bxl.pokeapp.models.Pokemon;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfPokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfPokemonFragment extends Fragment {

    private ListView lvPokemon;
    private TextView tvTitle;

    private boolean isTeamList;
    private ArrayList<Pokemon> pokemons;


    public ListOfPokemonFragment() {
        // Required empty public constructor
    }

    public static ListOfPokemonFragment newInstance() {
        ListOfPokemonFragment fragment = new ListOfPokemonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_of_pokemon, container, false);

        lvPokemon = v.findViewById(R.id.list_view_list_fragment_poke_list);
        tvTitle = v.findViewById(R.id.tv_list_fragment_title);

        ArrayList<String> pokemonNames = new ArrayList<>();

        for (Pokemon pokemon : pokemons) {
            pokemonNames.add(pokemon.getName().substring(0, 1).toUpperCase()
                    + pokemon.getName().substring(1));
        }


        lvPokemon.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                pokemonNames));

        lvPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClickListener.onItemClick(pokemons.get(position).getId());
            }
        });

        if (isTeamList) {
            tvTitle.setText("My Team");
        }
        else {
            tvTitle.setText("My Favorites");
        }

        return v;
    }

    public void updateListStatus(boolean isTeamList) {
        this.isTeamList = isTeamList;
    }

    public boolean isTeamList() {
        return isTeamList;
    }

    public void updatePokemonList(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public interface OnItemClickListener {
        void onItemClick(int pokemonID);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}