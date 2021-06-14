package be.bxl.pokeapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import be.bxl.pokeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    Button btnSearch, btnFav, btnTeam;

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        btnSearch = v.findViewById(R.id.btn_main_fragment_search);
        btnFav = v.findViewById(R.id.btn_main_fragment_fav);
        btnTeam = v.findViewById(R.id.btn_main_fragment_team);

        btnSearch.setOnClickListener(searchView -> {
            onBtnSearchClickListener.onBtnSearchClick();
        });

        btnFav.setOnClickListener(favView -> {
            onBtnFavClickListener.onBtnFavClick();
        });

        btnTeam.setOnClickListener(teamView -> {
            onBtnTeamClickListener.onBtnTeamClick();
        });

        return v;
    }

    public interface OnBtnSearchClickListener {
        void onBtnSearchClick();
    }

    OnBtnSearchClickListener onBtnSearchClickListener;

    public void setOnBtnSearchClickListener(OnBtnSearchClickListener onBtnSearchClickListener) {
        this.onBtnSearchClickListener = onBtnSearchClickListener;
    }

    public interface OnBtnFavClickListener {
        void onBtnFavClick();
    }

    OnBtnFavClickListener onBtnFavClickListener;

    public void setOnBtnFavClickListener(OnBtnFavClickListener onBtnFavClickListener) {
        this.onBtnFavClickListener = onBtnFavClickListener;
    }

    public interface OnBtnTeamClickListener {
        void onBtnTeamClick();
    }

    OnBtnTeamClickListener onBtnTeamClickListener;

    public void setOnBtnTeamClickListener(OnBtnTeamClickListener onBtnTeamClickListener) {
        this.onBtnTeamClickListener = onBtnTeamClickListener;
    }

}