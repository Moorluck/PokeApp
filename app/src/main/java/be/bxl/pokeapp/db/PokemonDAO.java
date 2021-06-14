package be.bxl.pokeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import be.bxl.pokeapp.models.Pokemon;

public class PokemonDAO {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public PokemonDAO(Context context) {
        this.context = context;
    }

    public PokemonDAO openWritable() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public PokemonDAO openReadable() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public ContentValues convertPokemonToContentValue(Pokemon pokemon) {
        ContentValues cv = new ContentValues();

        cv.put(DBInfo.Pokemon.COLUMN_ID, pokemon.getId());
        cv.put(DBInfo.Pokemon.COLUMN_NAME, pokemon.getName());
        cv.put(DBInfo.Pokemon.COLUMN_TYPE, pokemon.getTypesString());
        cv.put(DBInfo.Pokemon.COLUMN_HP, pokemon.getHp());
        cv.put(DBInfo.Pokemon.COLUMN_ATT, pokemon.getAtt());
        cv.put(DBInfo.Pokemon.COLUMN_DEF, pokemon.getDef());
        cv.put(DBInfo.Pokemon.COLUMN_ATT_SPE, pokemon.getAttSpe());
        cv.put(DBInfo.Pokemon.COLUMN_DEF_SPE, pokemon.getDefSpe());
        cv.put(DBInfo.Pokemon.COLUMN_SPEED, pokemon.getSpeed());
        cv.put(DBInfo.Pokemon.COLUMN_IMG_URL, pokemon.getUrlImage());

        return cv;
    }

    public Pokemon convertCursorToPokemon(Cursor c) {

        int id = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_ID));
        String name = c.getString(c.getColumnIndex(DBInfo.Pokemon.COLUMN_NAME));
        ArrayList<String> types = Pokemon.convertStringTypeToArray(
                c.getString(c.getColumnIndex(DBInfo.Pokemon.COLUMN_TYPE)
                ));
        int hp = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_HP));
        int att = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_ATT));
        int def = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_DEF));
        int attSpe = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_ATT_SPE));
        int defSpe = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_DEF_SPE));
        int speed = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_SPEED));
        String urlImg = c.getString(c.getColumnIndex(DBInfo.Pokemon.COLUMN_IMG_URL));

        return new Pokemon(id, name, types, hp, att, def, attSpe, defSpe, speed, urlImg);




    }

    public void insert(Pokemon pokemon, boolean inTeam) {

        if (getPokemonByID(pokemon.getId()) == null) {
            ContentValues cv = convertPokemonToContentValue(pokemon);

            if (inTeam) {
                cv.put(DBInfo.Pokemon.COLUMN_IS_TEAM, 1);
            }
            else {
                cv.put(DBInfo.Pokemon.COLUMN_IS_FAV, 1);
            }

            db.insert(DBInfo.Pokemon.TABLE_NAME, null, cv);
        } else {
            ContentValues cv = new ContentValues();
            if (inTeam) {
                cv.put(DBInfo.Pokemon.COLUMN_IS_TEAM, 1);
                db.update(DBInfo.Pokemon.TABLE_NAME, cv,
                        DBInfo.Pokemon.COLUMN_ID + "=" + pokemon.getId(), null);
            }
            else {
                cv.put(DBInfo.Pokemon.COLUMN_IS_FAV, 1);
                db.update(DBInfo.Pokemon.TABLE_NAME, cv, DBInfo.Pokemon.COLUMN_ID + "=" + pokemon.getId(), null);
            }
        }



    }

    public ArrayList<Pokemon> getAllFavPokemon() {
        ArrayList<Pokemon> pokemons = new ArrayList<>();

        Cursor c = db.query(DBInfo.Pokemon.TABLE_NAME, null, DBInfo.Pokemon.COLUMN_IS_FAV + "=" + 1, null,
                null, null, null);

        if(c.moveToFirst()) {
            while (!c.isAfterLast()) {
                pokemons.add(convertCursorToPokemon(c));
                c.moveToNext();
            }
        }

        return pokemons;
    }

    public ArrayList<Pokemon> getAllTeamPokemon() {
        ArrayList<Pokemon> pokemons = new ArrayList<>();

        Cursor c = db.query(DBInfo.Pokemon.TABLE_NAME, null, DBInfo.Pokemon.COLUMN_IS_TEAM + "=" + 1, null,
                null, null, null);

        if(c.moveToFirst()) {
            while (!c.isAfterLast()) {
                pokemons.add(convertCursorToPokemon(c));
                c.moveToNext();
            }
        }

        return pokemons;
    }

    public Pokemon getPokemonByID(int pokemonID) {

        Cursor c = db.query(DBInfo.Pokemon.TABLE_NAME, null, DBInfo.Pokemon.COLUMN_ID + "=" + pokemonID, null,
                null, null, null);

        c.moveToFirst();

        try {
            Pokemon pokemon = convertCursorToPokemon(c);
            c.close();
            return pokemon;
        }
        catch (Exception e) {
            c.close();
            return null;
        }

    }

    public void delete(int pokemonID, boolean fromTeam) {

        if (fromTeam) {
            if (isFav(pokemonID)) {
                ContentValues cv = new ContentValues();
                cv.put(DBInfo.Pokemon.COLUMN_IS_TEAM, 0);
                db.update(DBInfo.Pokemon.TABLE_NAME, cv,
                        DBInfo.Pokemon.COLUMN_ID + "=" + pokemonID, null);
            }
            else {
                db.delete(DBInfo.Pokemon.TABLE_NAME, DBInfo.Pokemon.COLUMN_ID +
                        "=" + pokemonID, null);
            }
        }
        else {
            if (isTeam(pokemonID)) {
                ContentValues cv = new ContentValues();
                cv.put(DBInfo.Pokemon.COLUMN_IS_FAV, 0);
                db.update(DBInfo.Pokemon.TABLE_NAME, cv,
                        DBInfo.Pokemon.COLUMN_ID + "=" + pokemonID, null);
            }
            else {
                db.delete(DBInfo.Pokemon.TABLE_NAME, DBInfo.Pokemon.COLUMN_ID +
                        "=" + pokemonID, null);
            }
        }

    }

    public boolean isTeam(int pokemonID) {
        try {
            Cursor c = db.query(DBInfo.Pokemon.TABLE_NAME, null, DBInfo.Pokemon.COLUMN_ID + "=" + pokemonID, null,
                    null, null, null);

            c.moveToFirst();

            int isTeam = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_IS_TEAM));

            c.close();

            return isTeam == 1;
        }
        catch (Exception e) {
            return false;
        }

    }

    public boolean isFav(int pokemonID) {
        try {
            Cursor c = db.query(DBInfo.Pokemon.TABLE_NAME, null, DBInfo.Pokemon.COLUMN_ID + "=" + pokemonID, null,
                    null, null, null);

            c.moveToFirst();

            int isFav = c.getInt(c.getColumnIndex(DBInfo.Pokemon.COLUMN_IS_FAV));

            c.close();

            return isFav == 1;
        }
        catch (Exception e) {
            return false;
        }

    }
}
