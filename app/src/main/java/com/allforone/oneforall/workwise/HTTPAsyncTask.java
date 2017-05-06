package com.allforone.oneforall.workwise;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by Ricky on 2017-05-06.
 */

class HTTPAsyncTask extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        //Initialise connection to server
        Properties properties = new Properties();
        HttpURLConnection urlConnection  = null;
        BufferedReader reader=null;
        String line = null;
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(urls[1], "UTF-8");
            //String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("notest@test.com", "UTF-8");
            Log.d("Ripul: ", "" + data);

            OutputStreamWriter outputPost = new OutputStreamWriter (urlConnection.getOutputStream());

            if(outputPost != null){
                Log.d("Ripul: ", "output stream created");
            }

            Log.d("Ripul: ", urlConnection.getRequestMethod() + " request made");
            outputPost.write(data);
            outputPost.flush();
            outputPost.close();

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
            line = sb.toString();

            // Signed in successfully, show authenticated UI.
            //GoogleSignInAccount acct = result.getSignInAccount();
            //TODO : create Intent for new Activity - to join existing group or create a new group

        } catch(MalformedURLException ex){

        } catch(IOException ex1){

        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            try {
                reader.close();
            }
            catch(Exception ex) {}
        }
        return line;
    }

    protected void onPostExecute(String result) {
        delegate.processResponse(result);
    }
}
