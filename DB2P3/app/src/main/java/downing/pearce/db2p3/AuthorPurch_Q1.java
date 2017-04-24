package downing.pearce.db2p3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthorPurch_Q1 extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_purch);
    }
    // Triggers when the "Run Query" button is clicked
    public void runQuery(View arg0) {

        // Run the PHP query
        new AsyncQuery().execute();
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
                //      QUERY #1
                //      Using 10.0.2.2 to access localhost from within the Android Emulator
                //      MAKE SURE TO SETUP THE XAMP DIRECTORY CORRECTLY!!
                //      https://stackoverflow.com/questions/5528850/how-to-connect-localhost-in-android-emulator
                // *********************************************************************************
                url = new URL("http://10.0.2.2/Books/php/1_author_purch.php");

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

                // Connect to MySQL DB
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

                } else {
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
            TextView query_results;

            //TODO: This is for debugging purposes. REMOVE IT BEFORE SUBMITTING!
            //Toast.makeText(AuthorPurch_Q1.this, "Result is: " + result, Toast.LENGTH_LONG).show();

            if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(AuthorPurch_Q1.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                return;

            }

            // It worked! So let's display the results! It is in a string "result" formatted as HTML.
            // We will set the result TextView to the results :-)
            // From: https://stackoverflow.com/questions/15198567/display-html-formatted-text-in-android-app
            query_results = (TextView) findViewById(R.id.txtv_query1_queryResults);
            query_results.setText(Html.fromHtml(result));
        }
    }
}
