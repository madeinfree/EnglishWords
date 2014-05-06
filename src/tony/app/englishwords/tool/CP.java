package tony.app.englishwords.tool;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class CP extends ContentProvider{
	
	private static final String TAG = "EnglishWords::CP";
	
	
	public static final String PROVIDER_NAME = "tony.englishwords.provider";
	public static final String URL = "content://" + PROVIDER_NAME;
	public static final Uri CONTENT_URI = Uri.parse(URL);
	
	public static final String NAME = "name";
	
	public static final String _ID = "_id";
	public static final String DAY = "day";
	public static final String WORD = "word";
	public static final String WORDCLASS = "wordclass";
	public static final String WORDFREQUENCY = "wordfrequency";
	public static final String WORDMEAN = "wordmean";
	public static final String WORDSENTENCE = "wordsentence";
	public static final String KEYWORD = "keyword";
	public static final String TOPICFOCUS = "topicfocus";
	public static final String PRON = "pron";
	
	private static final UriMatcher mUriMatcher;
	private static final int URI_TYPE_TABLE1 = 1;
    private static final int URI_TYPE_TABLE2 = 2;
	static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(PROVIDER_NAME, DatabaseHelper.TABLE_NAME, URI_TYPE_TABLE1);
        mUriMatcher.addURI(PROVIDER_NAME, DatabaseHelper.SQLITE_SEQUENCE, URI_TYPE_TABLE2);
	}
	
	Context mCtx;
	SQLiteDatabase db;
	
	@Override
	public boolean onCreate() {
		Log.i(TAG, "onCreate");
		mCtx = getContext();
		DatabaseHelper dbHelper;
		dbHelper = new DatabaseHelper(mCtx);
		db = dbHelper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.i(TAG, "query");
		Cursor c = db.rawQuery("select * from "
				+ DatabaseHelper.TABLE_NAME, null);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.i(TAG, "update");
		return -1;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i(TAG, "insert");
		long rowID = db.insert(DatabaseHelper.TABLE_NAME, null, values);
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to add a record into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.i(TAG, "delete");
		int rowID = 0;
		switch(mUriMatcher.match(uri)) {
		case URI_TYPE_TABLE1:
			rowID = db.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
			break;
		case URI_TYPE_TABLE2:
			rowID = db.delete(DatabaseHelper.SQLITE_SEQUENCE, selection, selectionArgs);
			break;
		default:
            throw new IllegalArgumentException("Unknown URI " + uri);
		}
		return rowID;
	}

	@Override
	public String getType(Uri uri) {
		Log.i(TAG, "getType");
		return "";
	}
}
