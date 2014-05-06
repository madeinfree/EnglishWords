package tony.app.englishwords;

import java.util.HashSet;

import tony.app.englishwords.tool.DatabaseHelper;
import tony.app.englishwords.tool.Tool;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ExamActivity extends Fragment {
	
	private static final String TAG = "EnglishWords::ExamActivity";
	private View v;
	private TextView chmeanTV,statusTV,resultTV;
	private Context cxt;
	private Tool tool;
	private String[] word,wordmean;
	private RadioGroup rgroup;
	private RadioButton ans1,ans2,ans3,ans4;
	private Button answerBtn;
	private String true_word,true_wordmean;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cxt = getActivity().getApplicationContext();
		//tool
        tool = new Tool(cxt);
        //opendb
        tool.openDB();
        //
        word = new String[4];
        wordmean = new String[4];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.exam, container, false);
		statusTV = (TextView)v.findViewById(R.id.statusTV);
		chmeanTV = (TextView)v.findViewById(R.id.chmeanTV);
		resultTV = (TextView)v.findViewById(R.id.resultTV);
		rgroup = (RadioGroup)v.findViewById(R.id.rgroup);
		ans1 = (RadioButton)v.findViewById(R.id.ans1);
		ans2 = (RadioButton)v.findViewById(R.id.ans2);
		ans3 = (RadioButton)v.findViewById(R.id.ans3);
		ans4 = (RadioButton)v.findViewById(R.id.ans4);
		answerBtn = (Button)v.findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(btnlistener);
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getSQLiteData();
		if(getSQLiteDataCount()==0){
			statusTV.setText("資料庫內沒有資料，無法進行自我考試。");
			chmeanTV.setText("");
			for (int i = 0; i < rgroup.getChildCount(); i++) {
				rgroup.getChildAt(i).setEnabled(false);
			}
			answerBtn.setEnabled(false);
		} else {
			statusTV.setText("");
			true_word=word[0];
			true_wordmean=wordmean[0];
			chmeanTV.setText(true_wordmean);

			int rnd;
			int[] random = new int[4];
			HashSet rndSet = new HashSet<Integer>(4);
			
			for(int i=0;i<4;i++){
				rnd = (int) (4*Math.random());
				while(!rndSet.add(rnd))
					rnd = (int) (4*Math.random());
				random[i]=rnd;
			}
			
			ans1.setText(word[random[0]]);
			ans2.setText(word[random[1]]);
			ans3.setText(word[random[2]]);
			ans4.setText(word[random[3]]);
		}
	}
	
	Button.OnClickListener btnlistener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			String result = null;
			if(ans1.isChecked())
				result = ans1.getText().toString();
			else if(ans2.isChecked())
				result = ans2.getText().toString();
			else if(ans3.isChecked())
				result = ans3.getText().toString();
			else if(ans4.isChecked())
				result = ans4.getText().toString();
			if(result.equals(true_word))
				resultTV.setText("答對了！\n答案是："+true_word);
			else 
				resultTV.setText("答錯了唷！\n答案是："+true_word);
		}
	};
	
	private int getSQLiteDataCount() {
		String SQL = "SELECT count(*) FROM "+DatabaseHelper.TABLE_NAME;
		Cursor c = tool.getDB().rawQuery(SQL, null);
		return c.getCount();
	}
	
	private void getSQLiteData() {
		String SQL = "SELECT word,wordmean FROM "+DatabaseHelper.TABLE_NAME+" ORDER BY RANDOM() LIMIT 4;";
		Cursor c = tool.getDB().rawQuery(SQL, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				word[i] = c.getString(0);
				wordmean[i] = c.getString(1);
				Log.i(TAG,c.getString(0));
				Log.i(TAG,c.getString(1));
				c.moveToNext();
			}
		}
	}
}
