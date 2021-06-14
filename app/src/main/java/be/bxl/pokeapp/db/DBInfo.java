package be.bxl.pokeapp.db;

public class DBInfo {
    public static final String DBname = "db_task";
    public static final int version = 2;

    public static class Pokemon {
        public static final String TABLE_NAME = "pokemon";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_HP = "hp";
        public static final String COLUMN_ATT = "att";
        public static final String COLUMN_DEF = "def";
        public static final String COLUMN_ATT_SPE = "att_spe";
        public static final String COLUMN_DEF_SPE = "def_spe";
        public static final String COLUMN_SPEED = "speed";
        public static final String COLUMN_IMG_URL = "img_url";
        public static final String COLUMN_IS_FAV = "is_fav";
        public static final String COLUMN_IS_TEAM = "is_team";

        public static final String CREATE_REQUEST = "CREATE TABLE " + TABLE_NAME + "( "
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_TYPE + " TEXT, "
                + COLUMN_HP + " INTEGER, "
                + COLUMN_ATT + " INTEGER, "
                + COLUMN_DEF + " INTEGER, "
                + COLUMN_ATT_SPE + " INTEGER, "
                + COLUMN_DEF_SPE + " INTEGER, "
                + COLUMN_SPEED + " INTEGER, "
                + COLUMN_IMG_URL + " TEXT, "
                + COLUMN_IS_FAV + " INTEGER DEFAULT 0, "
                + COLUMN_IS_TEAM + " INTEGER DEFAULT 0);";

        public static final String UPGRADE_REQUEST = "DROP TABLE " + TABLE_NAME;
    }
}
