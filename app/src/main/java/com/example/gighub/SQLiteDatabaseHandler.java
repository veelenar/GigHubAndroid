package com.example.gighub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GigsDB";
    private static final String TABLE_NAME = "Gigs";
    private static final String KEY_ID = "id";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_VENUE = "venue";
    private static final String KEY_PRICE = "price";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_SHORT_DESC = "shortDesc";
    private static final String KEY_DATE = "date";


    private static final String[] COLUMNS = { KEY_ID, KEY_ARTIST, KEY_GENRE,
            KEY_VENUE, KEY_PRICE, KEY_ADDRESS, KEY_SHORT_DESC, KEY_DATE };


    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Gigs ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "artist TEXT, "
                + "genre TEXT, " + "venue TEXT, " + "price TEXT, " + "address TEXT, " + "shortDesc TEXT, "+ "date TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(int id) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public Gig getGig(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Gig gig = new Gig();
        gig.setId(Integer.parseInt(cursor.getString(0)));
        gig.setArtist(cursor.getString(1));
        gig.setGenre(cursor.getString(2));
        gig.setVenue(cursor.getString(3));
        gig.setPrice(cursor.getString(4));
        gig.setAddress(cursor.getString(5));
        gig.setShortDesc(cursor.getString(6));
        gig.setDate(cursor.getString(7));

        return gig;
    }

    public List<Gig> allGigs() {

        List<Gig> gigs = new LinkedList<Gig>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Gig gig = null;

        if (cursor.moveToFirst()) {
            do {
                gig = new Gig();
                gig.setId(Integer.parseInt(cursor.getString(0)));
                gig.setArtist(cursor.getString(1));
                gig.setGenre(cursor.getString(2));
                gig.setVenue(cursor.getString(3));
                gig.setPrice(cursor.getString(4));
                gig.setAddress(cursor.getString(5));
                gig.setShortDesc(cursor.getString(6));
                gig.setDate(cursor.getString(7));
                gigs.add(gig);
            } while (cursor.moveToNext());
        }

        return gigs;
    }

    public void addGig(Gig gig) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ARTIST, gig.getArtist());
        values.put(KEY_GENRE, gig.getGenre());
        values.put(KEY_VENUE, gig.getVenue());
        values.put(KEY_PRICE, gig.getPrice());
        values.put(KEY_ADDRESS, gig.getAddress());
        values.put(KEY_SHORT_DESC, gig.getShortDesc());
        values.put(KEY_DATE, gig.getDate());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updateGig(Gig gig) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ARTIST, gig.getArtist());
        values.put(KEY_GENRE, gig.getGenre());
        values.put(KEY_VENUE, gig.getVenue());
        values.put(KEY_PRICE, gig.getPrice());
        values.put(KEY_ADDRESS, gig.getAddress());
        values.put(KEY_SHORT_DESC, gig.getShortDesc());
        values.put(KEY_DATE, gig.getDate());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(gig.getId()) });

        db.close();

        return i;
    }

}