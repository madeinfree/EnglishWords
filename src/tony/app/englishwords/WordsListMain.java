package tony.app.englishwords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tony.app.englishwords.tool.DatabaseHelper;
import tony.app.englishwords.tool.Tool;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class WordsListMain extends Fragment{
	private static final String TAG = "EnglishWords::WordsListMain";
	private View v;
	private Context cxt;
	private Tool tool;
	private ListView listView;
	private SimpleAdapter simpleAdapter;
	private List<String> titleText = new ArrayList<String>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        cxt = getActivity().getApplicationContext();
        //tool
        tool = new Tool(cxt);
        //opendb
        tool.openDB();
        //getsqlitedata
        getSQLiteData();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.wordslistmain, container, false);
		
		listView = (ListView) v.findViewById(R.id.wordslistmain_list);
		simpleAdapter = new SimpleAdapter(cxt,
    			getData(), R.layout.simple_adapter, new String[]{"image", "title", "info", "c1"},
                new int[]{R.id.image, R.id.title , R.id.info , R.id.c1});
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(lvlistener);
		return v;
	}
	
	private List<HashMap<String, Object>> getData() { 
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		for(int i=0;i<titleText.size();i++){
			map = new HashMap<String, Object>();
			map.put("image", R.drawable.dot);  
			map.put("title", titleText.get(i));  
            map.put("info", "");  
            map.put("c1", "");
            list.add(map);  
		}
		return list;
	}
	
	private OnItemClickListener lvlistener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String titleName = titleText.get(position);
			//tool.Toast(titleName);
			Intent intent = new Intent(getActivity().getBaseContext(), WordsList.class);
			Bundle bundle = new Bundle();
			bundle.putString("day", titleName);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	};
	
	private void getSQLiteData() {
		String SQL = "select distinct day from "+DatabaseHelper.TABLE_NAME;
		Cursor c = tool.getDB().rawQuery(SQL, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				titleText.add(c.getString(0));
				c.moveToNext();
			}
		}
	}
}
