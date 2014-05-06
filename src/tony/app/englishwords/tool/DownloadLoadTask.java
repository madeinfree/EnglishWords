package tony.app.englishwords.tool;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DownloadLoadTask extends AsyncTask<URL, Integer, String> {
	
	private ProgressDialog progressDialog;
	private Context cxt;
	private Activity act;
	// Tool
	private Tool tool;
	
	public DownloadLoadTask(Context context, Activity activity) {
		this.cxt = context;
		this.act = activity;
	}
	
	@Override
	protected void onPreExecute() {
		tool = new Tool(cxt);
		tool.openDB();
		progressDialog = new ProgressDialog(act); 
		/*
		progressDialog.setTitle("提示");  
		progressDialog.setMessage("下載中，請稍後..");  
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
		progressDialog.show();
		*/  
		progressDialog = ProgressDialog.show(act,"讀取資料中","讀取檔案並匯入資料庫，請等待完成...",true);
	}
	
	
	@Override
	protected String doInBackground(URL... params) {
		try {  
            URL url = new URL(tool.getFileUrl());  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setConnectTimeout(10 * 1000);  
            connection.connect();  
            if (connection.getResponseCode() == HttpStatus.SC_OK) {  
                progressDialog.setMax(connection.getContentLength());  
                InputStream inputStream = connection.getInputStream();  
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
                byte[] buffer = new byte[10 * 1024];  
                while (true) {  
                    int len = inputStream.read(buffer);  
                    //publishProgress(len);  
                    if (len == -1) {  
                        break;  
                    }  
                    arrayOutputStream.write(buffer, 0, len);  
                }  
                arrayOutputStream.close();  
                inputStream.close();  
  
                byte[] data = arrayOutputStream.toByteArray();  
                FileOutputStream fileOutputStream = cxt.openFileOutput("toeic.xls", Context.MODE_PRIVATE);
                fileOutputStream.write(data);  
                fileOutputStream.close();  
            }  
  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		tool.readExcel();
		/*
		int total = 0;
		try {
			HttpURLConnection huc = (HttpURLConnection) new URL(tool.getFileUrl()).openConnection();  
	        huc.setRequestMethod("GET");  
	        Long musicLengh = (long) huc.getContentLength();  
	        InputStream in = huc.getInputStream();  
	        FileOutputStream fos = cxt.openFileOutput("toeic.xls", Context.MODE_PRIVATE);
	        byte[] b = new byte[512];  
	        int byte_count = 0;  
	        while (-1 != (byte_count = in.read(b))) {  
	            fos.write(b, 0, byte_count);  
	            total += byte_count;  
	            publishProgress((int) (120 * total / musicLengh));  
	        }  
		} catch (Exception e ) {
			e.printStackTrace();
		}
		*/
		
		/*
		String line = null;
		BufferedReader bufferedReader = null;
		URL url = null;
		try {
			url = new URL(tool.getFileUrl());
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			bufferedReader = new BufferedReader(
					new InputStreamReader(httpURLConnection
							.getInputStream()));
			while ((line = bufferedReader.readLine()) != null) {
				// stringBuffer.append(line);
				if (String.valueOf(line.charAt(0)).equals("#")
						|| String.valueOf(line.charAt(0))
								.equals(","))
					continue;
				String[] sarray = line.split(",");
				Log.i(TAG,
						"value:" + String.valueOf(sarray[0])
								+ " length:"
								+ String.valueOf(sarray.length));
				Log.i(TAG, line);
				tool.DB_insert(sarray[8], sarray[0], sarray[2],
						sarray[3], sarray[1], sarray[4],
						sarray[5], sarray[6], sarray[7]);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
		return "Loading finished";

	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		progressDialog.setProgress(values[0]);
	}
	
	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();
	}

}