package ted.tedparent;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.*;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

import AWS_Classes.Dynamo.AsyncResponse;
import AWS_Classes.Dynamo.BearStateUpdate;
import AWS_Classes.Dynamo.Metrics;


public class HomeActivity extends AppCompatActivity implements AsyncResponse {

    TextView topicBox;
    TextView languageBox;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Populate topic and language boxes
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        topicBox = (TextView) findViewById(R.id.CurrTopic);
        languageBox = (TextView) findViewById(R.id.CurrLang);
        updateFields();


        // SideDrawer Update
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

    }

    public void updateFields() {

        // Check if we are connected to wifi
        if (isNetworkAvailable()) {
            //Get our credentials in order to talk to our AWS database
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    "us-east-1:b0b7a95e-1afe-41d6-9465-1f40d1494014", // Identity Pool ID
                    Regions.US_EAST_1 // Region
            );



            BearStateUpdate myMapper = new BearStateUpdate(credentialsProvider);
            myMapper.delegate = this;
            myMapper.execute();

        }

    }

    private void addDrawerItems() {
        String[] osArray = { "Home", "Statistics", "Accounts", "Settings" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    public void processFinish(Metrics output){

        topicBox.append(output.getTopic());
        languageBox.append(output.getLanguage());

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


