package tony.app.englishwords.tool;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class Tool {
	//TAG
	private static final String TAG = "EnglishWords::Tool";
	//Context
	private Context context;
	//db
	private SQLiteDatabase db;
	//file
	private static String fileurl; 
	
	public Tool(Context context) {
		this.context = context;
	}
	
	public void setFileUrl(String fileurl) {
		this.fileurl = fileurl;
	}
	
	public String getFileUrl() {
		return fileurl;
	}
	
	public SQLiteDatabase getDB() {
		return db;
	}
	
	public void openDB() {
		DatabaseHelper dbhelper;
		dbhelper = new DatabaseHelper(context);
		db = dbhelper.getWritableDatabase();
	}
	
	public void closeDB() {
		db.close();
	}
	
	public void DB_insert(ContentValues values) {
		db.insert(DatabaseHelper.TABLE_NAME, null, values);
	}
	
	
	
	/*
	public StringBuilder DB_query() {
		StringBuilder sb = new StringBuilder();
		String SQL = "select * from "+DatabaseHelper.TABLE_NAME;
		Log.i(TAG,"SQL:"+SQL);
		Cursor c = db.rawQuery(SQL, null);
		Log.i(TAG, String.valueOf(c.getCount()));
		
		if (c.getCount() != 0) {
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				String _id = c.getString(0);
				String day = c.getString(1);
				String word = c.getString(2);
				String wordclass = c.getString(3);
				String wordfrequency = c.getString(4);
				String wordmean = c.getString(5);
				String keyword = c.getString(6);
				String topicfocus = c.getString(7);
				String pron = c.getString(8);
				Log.i(TAG, _id);
				Log.i(TAG, day);
				Log.i(TAG, word);
				Log.i(TAG, wordclass);
				Log.i(TAG, wordfrequency);
				Log.i(TAG, wordmean);
				Log.i(TAG, keyword);
				Log.i(TAG, topicfocus);
				Log.i(TAG, pron);
				sb.append(_id+"\t");
				sb.append(day+"\t");
				sb.append(word+"\t");
				sb.append(wordclass+"\t");
				sb.append(wordfrequency+"\t");
				sb.append(wordmean+"\t");
				sb.append(keyword+"\t");
				sb.append(topicfocus+"\t");
				sb.append(pron+"\n");
				c.moveToNext();
			}
		}
		
		return sb;
	}
	*/

	public void DB_delete() {
		db.execSQL("DELETE FROM "+DatabaseHelper.SQLITE_SEQUENCE+" WHERE name='"+DatabaseHelper.TABLE_NAME+"';");
		db.execSQL("DELETE FROM "+DatabaseHelper.TABLE_NAME+" WHERE _id!=-1;");
	}
	
	public void Toast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public void readExcel() {
		try {
			
			Workbook book = Workbook.getWorkbook(new File("/data/data/tony.app.englishwords/files/toeic.xls"));
			book.getNumberOfSheets();
			//��o�u�@���H
			Sheet sheet = book.getSheet(0);
			int Rows = sheet.getRows();
			int Cols = sheet.getColumns();
			System.out.println("��e�u�@���W�r:" + sheet.getName());  
            System.out.println("�`���:" + Rows);  
            System.out.println("�`�C��:" + Cols);
            
            for (int i = 0; i < Rows; i++) {
            	if(String.valueOf(sheet.getCell(0, i).getContents().charAt(0)).equals("#"))
            		continue;
            	
            	ContentValues values = new ContentValues();
        		values.put(DatabaseHelper.DAY, sheet.getCell(8, i).getContents());
        		values.put(DatabaseHelper.WORD, sheet.getCell(0, i).getContents());
        		values.put(DatabaseHelper.WORDCLASS, sheet.getCell(2, i).getContents());
        		values.put(DatabaseHelper.WORDFREQUENCY, sheet.getCell(3, i).getContents());
        		values.put(DatabaseHelper.WORDMEAN, sheet.getCell(1, i).getContents());
        		values.put(DatabaseHelper.WORDSENTENCE, sheet.getCell(4, i).getContents());
        		values.put(DatabaseHelper.KEYWORD, sheet.getCell(5, i).getContents());
        		values.put(DatabaseHelper.TOPICFOCUS, sheet.getCell(6, i).getContents());
        		values.put(DatabaseHelper.PRON, sheet.getCell(7, i).getContents());
            	
            	DB_insert(values);
            			
					
            	Log.i(TAG,sheet.getCell(8, i).getContents()+" "+
    			sheet.getCell(0, i).getContents()+" "+
    			sheet.getCell(2, i).getContents()+" "+
    			sheet.getCell(3, i).getContents()+" "+
    			sheet.getCell(1, i).getContents()+" "+
    			sheet.getCell(4, i).getContents()+" "+
    			sheet.getCell(5, i).getContents()+" "+
    			sheet.getCell(6, i).getContents()+" "+
    			sheet.getCell(7, i).getContents());
						
            	//Log.i(TAG,sheet.getCell(0, i).getContents());
            	//System.out.print((sheet.getCell(i, 6)).getContents() + "\t");
            }  
              
            //�o��Ĥ@�C�Ĥ@�檺�椸��  
            //Cell cell1 = sheet.getCell(0, 0);  
            //String result = cell1.getContents();  
            //System.out.println(result);  
            book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/******************************
	//filename
	private static String filename;
	//filepath
	private static String filepath;
	//file
	private static File file;
	******************************/
	
	
	/******************************
	public String getFilename() {
		Log.i(TAG,filename);
		return filename;
	}
	public String getFilepath() { 
		Log.i(TAG,filepath);
		return filepath; 
	}
	public File getFile() { 
		Log.i(TAG,"filepath: "+filepath);
		return file;
	}
	*******************************/
	
	/**********************************************************************
	
	// �T�{�~���x�s�����A
	public int CheckEntenalStatus() {
		int status;
		// ���o�~���x�s�C�骺���A
		String state = Environment.getExternalStorageState();
		// �P�_���A
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			status = 2;
			Log.d(TAG, "�i�HŪ�g");
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Log.d(TAG, "�u�i�HŪ���A�L�k�g�J");
			status = 1;
		} else {
			Log.d(TAG, "�L�kŪ�g");
			status = 0;
		}
		return status;
	}

	// �]�w�ɮצ�m
	public void SetFilePath() {
		
		// �����osdcard�ؿ�
		String path = Environment.getExternalStorageDirectory().getPath();
		// �Q��File�ӳ]�w�ؿ����W��(myappdir)
		File dir = new File(path + "/EnglishWords");
		// ���ˬd�ӥؿ��O�_�s�b
		if (!dir.exists()) {
			// �Y���s�b�h�إߥ�
			dir.mkdir();
		}
		// �ɮצW��
		filename = "toeic.csv";
		// �ɮ׸��|
		filepath = dir + "/" + filename;
		file = new File(filepath);
	}

	// �P�_�ɮ׬O�_�s�b
	public boolean fileIsExists(File f) {
		if (!f.exists()) {
			return false;
		}
		return true;
	}
	
	public StringBuilder readEnglishWordsCSVfile() {
		DB_delete();
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(getFilepath()) , "UTF-8"));
			String data = "";
			//int a=0;
			while ((data = br.readLine()) != null) {
				
				if(String.valueOf(data.charAt(0)).equals("#") || String.valueOf(data.charAt(0)).equals(","))
					continue;
				//a++;
				String[] sarray = data.split(",");
				Log.i(TAG,"value:"+String.valueOf(sarray[0])+" length:"+String.valueOf(sarray.length));
				//tool.DB_insert(DAY,WORD,WORDCLASS,WORDFREQUENCY,WORDMEAN,WORDSENTENCE,KEYWORD,TOPICFOCUS,PRON);
				DB_insert(sarray[8],sarray[0],sarray[2],sarray[3],sarray[1],sarray[4],sarray[5],sarray[6],sarray[7]);
				//if(a==3) break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb;
	}
	
	*********************************/
}
