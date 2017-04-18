package downing.pearce.db2p3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by asus on 4/13/17.
 */

public class Index extends AppCompatActivity {

    // This should be the main activity that links between the other queries.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
    }

    // Runs when query 1 is selected
    public void runQuery1(View arg0) {

        // Launch query 1
        Intent intent = new Intent(Index.this, AuthorPurch_Q1.class);
        startActivity(intent);
    }

    // Runs when query 2 is selected
    public void runQuery2(View arg0) {

        // Launch query 2
        Intent intent = new Intent(Index.this, InputAuthors_Q2.class);
        startActivity(intent);
    }

    // Runs when query 3 is selected
    public void runQuery3(View arg0) {

        // Launch query 3
        Intent intent = new Intent(Index.this, InputCustomers_Q3.class);
        startActivity(intent);
    }

    // Runs when query 4 is selected
    public void runQuery4(View arg0) {

        // Launch query 4
        Intent intent = new Intent(Index.this, InputTitle_Q4.class);
        startActivity(intent);
    }

    // Runs when query 5 is selected
    public void runQuery5(View arg0) {

        // Launch query 5
        Intent intent = new Intent(Index.this, BestSellers_Q5.class);
        startActivity(intent);
    }

}
