package downing.pearce.db2p3;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by asus on 4/13/17.
 */

public class InputTitle_Q4 extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_title);

        // Get a reference to the input box
        etTitle = (EditText) findViewById(R.id.edt_query4_queryInput);
    }

    // Triggers when the "Run Query" button is clicked
    public void runQuery(View arg0) {

        // Get the title input
        final String title = etTitle.getText().toString();

        // Run the PHP query, sending the title wildcard too
        new AsyncQuery().execute(title);
    }

    private class AsyncQuery extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // *********************************************************************************
                //      QUERY #4
                // *********************************************************************************
                url = new URL("http://192.168.0.158/Books/php/4_input_title.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("title", params[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //TextView query_results;
            WebView query_results;

            //TODO: This is for debugging purposes. REMOVE IT BEFORE SUBMITTING!
            //Toast.makeText(InputTitle_Q4.this, "Result is: " + result, Toast.LENGTH_LONG).show();

            if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(InputTitle_Q4.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                return;

            }

            // It worked! So let's display the results! It is in a string "result" formatted as HTML.
            // We will set the result TextView to the results :-)
            // From: https://stackoverflow.com/questions/15198567/display-html-formatted-text-in-android-app
//            query_results = (TextView) findViewById(R.id.txtv_query4_queryResults);
//            query_results.setText(Html.fromHtml(result));

            // Trying to display the results as a WebView
            // https://stackoverflow.com/questions/3525649/display-html-table-in-webview
            query_results = (WebView) findViewById(R.id.webv_query4_queryResults);
            query_results.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
        }
    }
}
