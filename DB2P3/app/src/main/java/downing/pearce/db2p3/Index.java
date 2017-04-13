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
    public void query_1(View arg0) {

        // Launch query 1
        Intent intent = new Intent(Index.this, AuthorPurch.class);
        startActivity(intent);
        Index.this.finish();
    }

    // Runs when query 2 is selected
    public void query_2() {

        // For now, display a toast.

    }

    // Runs when query 3 is selected
    public void query3() {

        // For now, display a toast.

    }

}
