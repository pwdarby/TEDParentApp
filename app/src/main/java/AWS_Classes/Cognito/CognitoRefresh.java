package AWS_Classes.Cognito;

import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;


/**
 * Created by Niko on 1/19/2016.
 */
public class CognitoRefresh extends AsyncTask<CognitoCachingCredentialsProvider, Void, CognitoCachingCredentialsProvider> {

    public RefreshResponse delegate = null;

    public CognitoRefresh() {

    }

    @Override
    protected CognitoCachingCredentialsProvider doInBackground(CognitoCachingCredentialsProvider... args) {

        args[0].refresh();

        return args[0];
    }


    @Override
    protected void onPostExecute(CognitoCachingCredentialsProvider result) {
        if(result == null){
            return;
        }

        delegate.RefreshFinish(result);
    }
}
