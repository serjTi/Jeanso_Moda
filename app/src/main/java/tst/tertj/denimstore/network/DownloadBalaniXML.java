package tst.tertj.denimstore.network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import tst.tertj.denimstore.MainActivity;
import tst.tertj.denimstore.constants.BalaniConstants;
import tst.tertj.denimstore.constants.Const;
import tst.tertj.denimstore.interfaces.GetProdactDatabase;

/**
 * Created by sergey_tertychenko on 18.09.17.
 */

public class DownloadBalaniXML extends AsyncTask<String, Void, String> {
    private GetProdactDatabase delegate;
    private final String LOG_TAG = getClass().getSimpleName();

    public DownloadBalaniXML(GetProdactDatabase listener) {
        delegate = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuffer buffer = new StringBuffer();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet;
        try {
            httpGet = new HttpGet(BalaniConstants.BASE_URL);
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            Log.d(LOG_TAG, "status code = " + statusCode);
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String strXML) {
        super.onPostExecute(strXML);

        Log.d(LOG_TAG, "onPostExecute");
        if (delegate != null) {
            Log.d(LOG_TAG, "onPostExecute delegate != null");
            delegate.download_balani_xml(strXML);
        }
    }
}
