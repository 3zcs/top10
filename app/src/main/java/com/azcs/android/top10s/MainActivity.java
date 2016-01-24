package com.azcs.android.top10s;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //TextView xmltext;
    String mFileContent ;
    private Button mBtnParse ;
    private ListView mListApps ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownlaodDate downlaodDate = new DownlaodDate();
        downlaodDate.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topTvSeasons/xml");
        //xmltext = (TextView)findViewById(R.id.xmltext);
        mBtnParse = (Button)findViewById(R.id.btnpaser);
        mListApps = (ListView)findViewById(R.id.xmlListView);
        mBtnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseMoves moves = new ParseMoves(mFileContent);
                moves.process();
                ArrayAdapter<moves> adapter = new ArrayAdapter<moves>
                        (getApplicationContext() , R.layout.list_item , moves.getMovies());
                mListApps.setAdapter(adapter);
            }
        });
    }

                //where store date , on progress , result
    private class DownlaodDate extends AsyncTask <String , Void , String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String Result) {
            super.onPostExecute(Result);
            Log.d("DownlaodDate", "downloading " + Result);
            //xmltext.setText(mFileContent.toString());
        }

        @Override
        protected String doInBackground(String... params) {
            mFileContent = downlaodXML(params[0]);
            if(mFileContent == null)
                Log.d("DownlaodDate" , "ERROR everything");
            return mFileContent;
        }

                    private String downlaodXML(String param) {
                        StringBuilder stringBuffer = new StringBuilder();
                        try {
                            URL url = new URL(param);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            int response = connection.getResponseCode();
                            Log.d("DownlaodDate" , "connection" + response);
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);

                            int charRead ;
                            char[] inputBuffer = new char[500];

                            while (true){
                                charRead = isr.read(inputBuffer);
                                if (charRead <= 0)
                                    break;
                                stringBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
                            }
                            return stringBuffer.toString() ;
                        }catch (IOException e){
                            Log.d("DownlaodDate" , "ERRoR"+e.getMessage());
                        }catch (SecurityException e){
                            Log.d("DownlaodDate" , "Securty "+e.getMessage());
                        }
                        return null ;
                    }
                }
}
