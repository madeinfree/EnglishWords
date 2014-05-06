package tony.app.englishwords;

import tony.app.englishwords.tool.DatabaseHelper;
import tony.app.englishwords.tool.Tool;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WordsList_info extends Activity{
	private static final String TAG = "EnglishWords::WordsList_info";
	private Tool tool;
	private TextView wordTV,wordclassTV,wordmeanTV,wordsentenceTV,dayTV;
	private LinearLayout starLayout;
	private int wordfrequency;
	private String word,wordclass,wordmean,wordsentence,day;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordslist_info);
        //tool
        tool = new Tool(this);
        //opendb
        tool.openDB();
        //取得前一個Activity傳過來的Bundle物件
        Bundle bundle = getIntent().getExtras();
        //
        getSQLiteData(bundle.getString("word"));
        //setview
        SetView();
	}
	private void SetView() {
		starLayout = (LinearLayout)findViewById(R.id.starlayout);
		wordTV = (TextView)findViewById(R.id.wordTV);
		wordclassTV = (TextView)findViewById(R.id.wordclassTV);
		wordmeanTV = (TextView)findViewById(R.id.wordmeanTV);
		wordsentenceTV = (TextView)findViewById(R.id.wordsentenceTV);
		dayTV = (TextView)findViewById(R.id.dayTV);
		
		wordTV.setText(word.replace("*", ""));
		wordclassTV.setText(wordclass);
		wordmeanTV.setText(wordmean);
		wordsentenceTV.setText(wordsentence);
		dayTV.setText(day);
		
		SetStar();
		
	}
	
	private void SetStar() {
		int star = R.drawable.star;
		int starw = R.drawable.starw;
		//setImageResource
		ImageView star1 = new ImageView(this);
		ImageView star2 = new ImageView(this);
		ImageView star3 = new ImageView(this);
		ImageView star4 = new ImageView(this);
		ImageView star5 = new ImageView(this);
		ImageView[] imageview = {star1,star2,star3,star4,star5};
		
		//星星數量
		/*
		int starcount=0;
		for(int i=0;i<word.length();i++){
			if(String.valueOf(word.charAt(i)).equals("*"))
				starcount++;
		}
		*/
		
		for(int i=0;i<imageview.length;i++){
			if(i<=(wordfrequency-1))
				imageview[i].setImageResource(star);
			else 
				imageview[i].setImageResource(starw);
			starLayout.addView(imageview[i]);
		}
	}
	
	private void getSQLiteData(String theword) {
		String SQL = "SELECT day,word,wordclass,wordfrequency,wordmean,wordsentence,keyword,topicfocus,pron FROM "
					+DatabaseHelper.TABLE_NAME
					+" WHERE word = '"+theword+"';";
		Cursor c = tool.getDB().rawQuery(SQL, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			day = c.getString(0);
			word = c.getString(1);
			wordmean = c.getString(4);
			wordsentence = c.getString(5);
			wordclass = c.getString(2);
			day = c.getString(0);
			wordfrequency = Integer.valueOf(c.getString(3));
		}
	}
	
}
