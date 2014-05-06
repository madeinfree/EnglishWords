package tony.app.englishwords.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG="EnglishWords::DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "englishwords.db";
	public static final String TABLE_NAME = "words";
	public static final String SQLITE_SEQUENCE = "sqlite_sequence";
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
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG,"onCreate");
		//day              第幾天         /(日期/類別)
		//word             主題單字    /(英文單字)
		//wordclass        出題類別    /(類別)
		//wordfrequency    出題頻率    /(重要性)
		//wordmean         字義
		//wordsentence     英文例句和中文翻譯
		//keyword          關鍵詞         /(類似單字)
		//topicfocus       出題重點    /(備註)
		//pron             發音              /(音標)
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
				_ID + " integer primary key autoincrement, " +
				DAY + " text, " +
				WORD + " text, " +
				WORDCLASS + " text," +
				WORDFREQUENCY + " int, "+
				WORDMEAN + " text, " +
				WORDSENTENCE + " text, " +
				KEYWORD + " text, " +
				TOPICFOCUS + " text, " +
				PRON + " text"+
				");");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG,"onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}