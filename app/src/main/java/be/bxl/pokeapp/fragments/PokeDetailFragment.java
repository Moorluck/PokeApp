package be.bxl.pokeapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import be.bxl.pokeapp.R;
import be.bxl.pokeapp.models.Pokemon;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokeDetailFragment extends Fragment {

    private boolean isBtnTeamDisplayed;
    private boolean isSearchBarDisplayed;
    private boolean isImgDiplayed;
    private boolean isFav;
    private Pokemon pokemon;

    // Views
    private RelativeLayout layoutSearch, layoutName;
    private LinearLayout layoutType, layoutStats;
    private EditText etSearch;
    private Button btnSearch;
    private FloatingActionButton btnFavorite, btnTeam;
    private TextView tvName, tvID, tvType, tvHp, tvAtt, tvDef, tvAttSpe, tvDefSpe, tvSpeed;
    private ImageView imgPokemon;
    private ProgressBar pbHp, pbAtt, pbDef, pbAttSpe, pbDefSpe, pbSpeed;

    // Pokemon Data


    public PokeDetailFragment() {
        // Required empty public constructor
    }


    public static PokeDetailFragment newInstance() {
        return new PokeDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poke_detail, container, false);

        // Initialise views

        //Layout
        layoutSearch = v.findViewById(R.id.layout_detail_fragment_search);
        layoutName = v.findViewById(R.id.layout_detail_fragment_name_and_btn);
        layoutStats = v.findViewById(R.id.layout_detail_fragment_stats);
        layoutType = v.findViewById(R.id.layout_detail_fragment_type);

        //EditText
        etSearch = v.findViewById(R.id.et_detail_fragment_search);

        //Image
        imgPokemon = v.findViewById(R.id.img_detail_fragment_poke_img);

        //Button
        btnTeam = v.findViewById(R.id.btn_detail_fragment_team);
        btnFavorite = v.findViewById(R.id.btn_detail_fragment_favorite);
        btnSearch = v.findViewById(R.id.btn_detail_fragment_search);

        //TextView
        tvName = v.findViewById(R.id.tv_detail_fragment_pokemon_name);
        tvID = v.findViewById(R.id.tv_fragment_detail_poke_id);
        tvType = v.findViewById(R.id.tv_detail_fragment_type);
        tvHp = v.findViewById(R.id.tv_fragment_detail_hp);
        tvAtt = v.findViewById(R.id.tv_fragment_detail_att);
        tvDef = v.findViewById(R.id.tv_fragment_detail_def);
        tvAttSpe = v.findViewById(R.id.tv_fragment_detail_att_spe);
        tvDefSpe = v.findViewById(R.id.tv_fragment_detail_def_spe);
        tvSpeed = v.findViewById(R.id.tv_fragment_detail_speed);

        //ProgressBar
        pbHp = v.findViewById(R.id.progress_fragment_detail_hp);
        pbAtt = v.findViewById(R.id.progress_fragment_detail_att);
        pbDef = v.findViewById(R.id.progress_fragment_detail_def);
        pbAttSpe = v.findViewById(R.id.progress_fragment_detail_att_spe);
        pbDefSpe = v.findViewById(R.id.progress_fragment_detail_def_spe);
        pbSpeed = v.findViewById(R.id.progress_fragment_detail_speed);

        //Setup of the layout in relation of the context

        updateDisplay();
        updateFavButton();

        //OnClick Button

        btnSearch.setOnClickListener(searchView -> {
            onSearchButtonClickListener.onSearchButtonClick(etSearch.getText().toString());
            hideSoftKeyboard(getActivity().getWindow().getDecorView());
        });

        btnFavorite.setOnClickListener(favView -> {
            onFavButtonClickListener.onFavButtonClick(pokemon);
        });

        btnTeam.setOnClickListener(teamView -> {
            onTeamButtonClickListener.onTeamButtonClick(pokemon);
        });


        return v;
    }

    public void updatePokemon(@NonNull Pokemon pokemon) {

        this.pokemon = pokemon;

    }

    public void updateBooleanForDisplay(boolean isBtnTeamDisplayed, boolean isImgDiplayed, boolean isSearchBarDisplayed) {

        this.isBtnTeamDisplayed = isBtnTeamDisplayed;
        this.isImgDiplayed = isImgDiplayed;
        this.isSearchBarDisplayed = isSearchBarDisplayed;

    }

    public void updateDisplay() {

        if (!isSearchBarDisplayed) {
            layoutSearch.setVisibility(View.GONE);
        } else {
            layoutSearch.setVisibility(View.VISIBLE);
        }

        //TODO à changer
        if (!isImgDiplayed) {
            imgPokemon.setVisibility(View.GONE);
        } else {
            imgPokemon.setVisibility(View.VISIBLE);
        }

        if (!isBtnTeamDisplayed) {
            btnTeam.setVisibility(View.GONE);
        } else {
            btnTeam.setVisibility(View.VISIBLE);
        }

        if(pokemon == null) {
            layoutType.setVisibility(View.GONE);
            layoutStats.setVisibility(View.GONE);
            layoutName.setVisibility(View.GONE);
        } else {
            layoutType.setVisibility(View.VISIBLE);
            layoutStats.setVisibility(View.VISIBLE);
            layoutName.setVisibility(View.VISIBLE);

            String nameString = this.pokemon.getName().substring(0, 1).toUpperCase() +
                    this.pokemon.getName().substring(1);
            tvName.setText(nameString);

            String idString = "ID : " + this.pokemon.getId();
            tvID.setText(idString);

            tvType.setText(this.pokemon.getTypesString());

            String hpString = "HP : " + String.valueOf(this.pokemon.getHp());
            tvHp.setText(hpString);
            pbHp.setProgress(convertToProgress(pokemon.getHp()), true);

            String attString = "ATT : " + String.valueOf(this.pokemon.getAtt());
            tvAtt.setText(attString);
            pbAtt.setProgress(convertToProgress(pokemon.getAtt()), true);

            String defString = "DEF : " + String.valueOf(this.pokemon.getDef());
            tvDef.setText(defString);
            pbDef.setProgress(convertToProgress(pokemon.getDef()), true);

            String attSpeString = "ASPE : " + String.valueOf(this.pokemon.getAttSpe());
            tvAttSpe.setText(attSpeString);
            pbAttSpe.setProgress(convertToProgress(pokemon.getAttSpe()), true);

            String defSpeString = "DSPE : " + String.valueOf(this.pokemon.getDefSpe());
            tvDefSpe.setText(defSpeString);
            pbDefSpe.setProgress(convertToProgress(pokemon.getDefSpe()), true);

            String speedString = "SPD : " + String.valueOf(this.pokemon.getSpeed());
            tvSpeed.setText(speedString);
            pbSpeed.setProgress(convertToProgress(pokemon.getSpeed()), true);

            Picasso.get()
                    .load(pokemon.getUrlImage())
                    .resize(300, 300)
                    .centerCrop()
                    .into(imgPokemon);
        }
    }

    // Transform stat in progress bar

    public int convertToProgress(int stat) {
        return (int) ((double) (stat) * (double) 100/252);
    }

    // Update fav button

    private void updateFavButton() {
        if (isFav) {
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        else {
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }

    public void updateFavBoolean(boolean fav) {
        this.isFav = fav;
        updateFavButton();
    }

    //Event Search Button
    public interface OnSearchButtonClickListener {
        void onSearchButtonClick(String searchText);
    }

    OnSearchButtonClickListener onSearchButtonClickListener;

    public void setOnSearchButtonClickListener(OnSearchButtonClickListener onSearchButtonClickListener) {
        this.onSearchButtonClickListener = onSearchButtonClickListener;
    }

    //Event Fav Button
    public interface OnFavButtonClickListener {
        void onFavButtonClick(Pokemon pokemon);
    }

    OnFavButtonClickListener onFavButtonClickListener;

    public void setOnFavButtonClickListener(OnFavButtonClickListener onFavButtonClickListener) {
        this.onFavButtonClickListener = onFavButtonClickListener;
    }

    //Event Team Button
    public interface OnTeamButtonClickListener {
        void onTeamButtonClick(Pokemon pokemon);
    }

    OnTeamButtonClickListener onTeamButtonClickListener;

    public void setOnTeamButtonClickListener(OnTeamButtonClickListener onTeamButtonClickListener) {
        this.onTeamButtonClickListener = onTeamButtonClickListener;
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}