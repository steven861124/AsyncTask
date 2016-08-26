package com.example.asusgl502vy.aslynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {

    private TextView mDonnLoadStateTextView;
    private FileDownLoadTask mFileDownLoadTask;




    private class FileDownLoadTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            mDonnLoadStateTextView = (TextView) findViewById(R.id.download);
            mDonnLoadStateTextView.setText("FileDownLoad...");
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... downLoadInfos) {
            int totolCount = downLoadInfos.length;
            for (int i = 0; i <= totolCount; i++) {
                publishProgress(i, totolCount);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return false;
                }
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... downLoadInfos) {
            int currentCount = downLoadInfos[0];
            int totoalCount = downLoadInfos[1];

            mDonnLoadStateTextView.setText("DownLoading : " + currentCount + " / "+totoalCount);
            super.onProgressUpdate(downLoadInfos);
        }

        @Override
        protected void onCancelled() {
            mDonnLoadStateTextView.setText("DonwnLoad cancel" );
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(true == result){
                mDonnLoadStateTextView.setText("DownLoad finish");
            }else{
                mDonnLoadStateTextView.setText("DownLoad Fail");
            }
            super.onPostExecute(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFileDownLoadTask = new FileDownLoadTask();
        mFileDownLoadTask.execute("FileUrl_1","FileUrl_2","FileUrl_3","FileUrl_4");
    }
    public void onClick(View v) {
        if (mFileDownLoadTask != null && mFileDownLoadTask.getStatus() != AsyncTask.Status.FINISHED) {
            mFileDownLoadTask.cancel(true);
        }
    }
}
