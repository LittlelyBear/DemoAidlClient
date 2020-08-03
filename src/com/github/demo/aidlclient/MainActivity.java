package com.github.demo.aidlclient;

import com.github.demo.aidl.Book;
import com.github.demo.aidl.IBookManager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String TAG = getClass().getSimpleName();
	private EditText mEditText;
	private Button mButton_show, mButton_add;
	private TextView mTextView;
	private IBookManager mIBookManager;

	public final static String ACTION = "android.intent.action.BookService";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButton_add = (Button) findViewById(R.id.add_button);
		mButton_show = (Button) findViewById(R.id.show_button);
		mEditText = (EditText) findViewById(R.id.edit);
		mTextView = (TextView) findViewById(R.id.textview);

		bindService(new Intent(ACTION), mConnection, Context.BIND_AUTO_CREATE);
	}

	ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "onServiceDisconnected");
			mIBookManager = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "onServiceConnected");
			mIBookManager = IBookManager.Stub.asInterface(service);
		}
	};

	@Override
	protected void onStart() {
		Log.d(TAG, "onStart()");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume()");
		super.onResume();

		mButton_show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "mButton_show onClick");
				try {
					if (mIBookManager != null) {
						if (mIBookManager.getBookList() != null) {
							int size = mIBookManager.getBookList().size();
							Log.d(TAG, "size = " + size);
							if (size != 0) {
								Toast.makeText(MainActivity.this, "书架有" + size + "书", Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(MainActivity.this, "书架没有书", Toast.LENGTH_LONG).show();
							}
						}
					} else {
						Log.d(TAG, "mIBookManager = null");
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mButton_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Book book = new Book();
				book.setBookName(mEditText.getText().toString());
				Log.d(TAG, "mEditText = " + mEditText.getText().toString());
				try {
					if (mIBookManager != null) {
						mIBookManager.addBook(book);
						mTextView.setText(mIBookManager.getBookList().toString());
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop()");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
		unbindService(mConnection);
		mConnection = null;
	}
}
