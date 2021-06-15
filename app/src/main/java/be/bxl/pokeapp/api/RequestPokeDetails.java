package be.bxl.pokeapp.api;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import be.bxl.pokeapp.models.Pokemon;

public class RequestPokeDetails extends AsyncTask<String, Void, Pokemon> {

    private final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/__id_or_name__";

    @Override
    protected Pokemon doInBackground(String... pokemonNameOrId) {
        if (pokemonNameOrId == null || pokemonNameOrId.length != 1) {
            throw new RuntimeException("Request issue");
        }

        String requestResult = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(BASE_URL.replace("__id_or_name__", pokemonNameOrId[0]));

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            reader.close();
            streamReader.close();

            requestResult = builder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        Pokemon pokemon = null;

        if (requestResult != null) {
            pokemon = parsePokemonResult(requestResult);
        }

        return pokemon;
    }

    public interface PokemonListener {
        void onDataReceived(Pokemon pokemon);
    }

    PokemonListener pokemonListener;

    public void setPokemonListener(PokemonListener pokemonListener) {
        this.pokemonListener = pokemonListener;
    }

    @Override
    protected void onPostExecute(Pokemon pokemon) {
        super.onPostExecute(pokemon);

        pokemonListener.onDataReceived(pokemon);
    }

    private Pokemon parsePokemonResult(String requestResult) {

        Pokemon pokemon = null;

        try {
            JSONObject json = new JSONObject(requestResult);
            int id = json.getInt("id");
            String name = json.getString("name");

            JSONObject sprites = json.getJSONObject("sprites");
            JSONObject other = sprites.getJSONObject("other");
            JSONObject dreamWorld = other.getJSONObject("official-artwork");
            String imgURL = dreamWorld.getString("front_default");

            JSONArray stats = json.getJSONArray("stats");
            JSONObject hpObject = stats.getJSONObject(0);
            int hp = hpObject.getInt("base_stat");

            JSONObject attObject = stats.getJSONObject(1);
            int att = attObject.getInt("base_stat");

            JSONObject defObject = stats.getJSONObject(2);
            int def = defObject.getInt("base_stat");

            JSONObject attSpeObject = stats.getJSONObject(3);
            int attSpe = attSpeObject.getInt("base_stat");

            JSONObject defSpeObject = stats.getJSONObject(4);
            int defSpe = defSpeObject.getInt("base_stat");

            JSONObject speedObject = stats.getJSONObject(5);
            int speed = speedObject.getInt("base_stat");

            ArrayList<String> types = new ArrayList<>();
            JSONArray typesArray = json.getJSONArray("types");
            for (int i = 0; i < typesArray.length(); i++) {
                JSONObject typeObject = typesArray.getJSONObject(i);
                JSONObject typeNameObject = typeObject.getJSONObject("type");
                String typeName = typeNameObject.getString("name");

                types.add(typeName);
            }

            pokemon = new Pokemon(id, name, types, hp, att, def, attSpe, defSpe, speed, imgURL);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return pokemon;
    }
}
