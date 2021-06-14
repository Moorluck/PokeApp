package be.bxl.pokeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Pokemon implements Parcelable {
    private int id;
    private String name;
    private ArrayList<String> types;
    private int hp;
    private int att;
    private int def;
    private int attSpe;
    private int defSpe;
    private int speed;
    private String urlImage;

    public Pokemon(int id, String name, ArrayList<String> types, int hp, int att, int def,
                   int attSpe, int defSpe, int speed, String urlImage) {
        this.id = id;
        this.name = name;
        this.types = types;
        this.hp = hp;
        this.att = att;
        this.def = def;
        this.attSpe = attSpe;
        this.defSpe = defSpe;
        this.speed = speed;
        this.urlImage = urlImage;
    }

    protected Pokemon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        types = in.createStringArrayList();
        hp = in.readInt();
        att = in.readInt();
        def = in.readInt();
        attSpe = in.readInt();
        defSpe = in.readInt();
        speed = in.readInt();
        urlImage = in.readString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtt() {
        return att;
    }

    public void setAtt(int att) {
        this.att = att;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAttSpe() {
        return attSpe;
    }

    public void setAttSpe(int attSpe) {
        this.attSpe = attSpe;
    }

    public int getDefSpe() {
        return defSpe;
    }

    public void setDefSpe(int defSpe) {
        this.defSpe = defSpe;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeStringList(types);
        dest.writeInt(hp);
        dest.writeInt(att);
        dest.writeInt(def);
        dest.writeInt(attSpe);
        dest.writeInt(defSpe);
        dest.writeInt(speed);
        dest.writeString(urlImage);
    }

    // Méthode

    public String getTypesString() {

        StringBuilder builder = new StringBuilder();

        for(String type : types){
            builder.append("- ").append(type).append("\n");
        }

        return builder.toString();

    }

    public static ArrayList<String> convertStringTypeToArray(String type) {
        String typeWithoutChar = type.replace("- ", "");

        ArrayList<String> result = new ArrayList<>();

        Scanner scanner = new Scanner(typeWithoutChar);
        while (scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        scanner.close();

        return result;
    }
}
