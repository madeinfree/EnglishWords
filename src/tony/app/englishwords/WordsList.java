package tony.app.englishwords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tony.app.englishwords.tool.DatabaseHelper;
import tony.app.englishwords.tool.Tool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WordsList extends Activity{
	private static final String TAG = "EnglishWords::WordsList";
	//private View v;
	private Context cxt;
	private ListView listView;
    private SimpleAdapter simpleAdapter;
    
    private List<String> titleText = new ArrayList<String>();
    private List<String> infoText = new ArrayList<String>();
    private List<String> c1Text = new ArrayList<String>();
   
    private Tool tool;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordslist);
        cxt = getApplicationContext();
        //tool
        tool = new Tool(cxt);
        //opendb
        tool.openDB();
        //取得前一個Activity傳過來的Bundle物件
        Bundle bundle = getIntent().getExtras();
        //getsqlitedata
        getSQLiteData(bundle.getString("day"));
        //
        SetView();
    }
	
	private void SetView(){
    	listView = (ListView) findViewById(R.id.words_listview);
    	simpleAdapter = new SimpleAdapter(cxt,
    			getData(), R.layout.simple_adapter, new String[]{"image", "title", "info", "c1"},
                new int[]{R.id.image, R.id.title , R.id.info , R.id.c1});
    	listView.setAdapter(simpleAdapter);
    	listView.setOnItemClickListener(lvlistener);
	}
	
	private List<HashMap<String, Object>> getData() { 
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		for(int i=0;i<titleText.size();i++){
			map = new HashMap<String, Object>();
			map.put("image", R.drawable.book);  
			map.put("title", titleText.get(i));  
            map.put("info", infoText.get(i));  
            map.put("c1", c1Text.get(i));
            list.add(map);  
		}
		return list;
	}
	
	private OnItemClickListener lvlistener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String titleName = titleText.get(position);
			//tool.Toast(titleName);
			Intent intent = new Intent(cxt, WordsList_info.class);
			Bundle bundle = new Bundle();
			bundle.putString("word", titleName);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	};
	
	private void getSQLiteData(String theday) {
		String SQL = "SELECT word,wordmean,day FROM "+DatabaseHelper.TABLE_NAME
				+" WHERE day='"+theday+"';";
		Cursor c = tool.getDB().rawQuery(SQL, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				String word = c.getString(0);
				String wordmean = c.getString(1);
				String day = c.getString(2);
				titleText.add(word);
				infoText.add(wordmean);
				c1Text.add(day);
				//Log.i(TAG,word+" , "+wordmean+" , "+day);
				c.moveToNext();
			}
		}
	}
}
