package tony.app.englishwords;



import tony.app.englishwords.tool.DownloadLoadTask;
import tony.app.englishwords.tool.Tool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dropbox.chooser.android.DbxChooser;

public class DropboxChooser extends Fragment {
	private static final String TAG = "EnglishWords::DropboxChooser";

	private View v;

	private Context cxt;
	
	private static final String APP_KEY = "1udobjur7ypws66"/*
													 * This is for you to fill
													 * in!
													 */;
	private static final int DBX_CHOOSER_REQUEST = 0; // You can change this if needed

	private TextView filenameTV, filesizeTV, progressTV;
	private Button mChooserButton, submitBtn;
	private DbxChooser mChooser;
	
	private Activity act;
	
	// Tool
	private Tool tool;

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cxt = getActivity().getApplicationContext();
		mChooser = new DbxChooser(APP_KEY);
		// tool
		tool = new Tool(cxt);
		// opendb
		tool.openDB();
		//
		act = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		v = inflater.inflate(R.layout.dropboxchooser, container, false);
		mChooserButton = (Button) v.findViewById(R.id.chooser_button);
		mChooserButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DbxChooser.ResultType resultType;

				resultType = DbxChooser.ResultType.DIRECT_LINK;

				mChooser.forResultType(resultType).launch(DropboxChooser.this,
						DBX_CHOOSER_REQUEST);
			}
		});

		filenameTV = (TextView) v.findViewById(R.id.filenameTV);
		filesizeTV = (TextView) v.findViewById(R.id.filesizeTV);
		progressTV = (TextView) v.findViewById(R.id.progressTV);
		submitBtn = (Button) v.findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tool.DB_delete();
				new DownloadLoadTask(cxt,act).execute();
			}
		});
		submitBtn.setEnabled(false);

		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == DBX_CHOOSER_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				DbxChooser.Result result = new DbxChooser.Result(data);
				tool.setFileUrl(result.getLink().toString());
				filenameTV.setText(tool.getFileUrl());
				filesizeTV.setText(String.valueOf(result.getSize()));
				submitBtn.setEnabled(true);
			}
		}
	}
	
}
