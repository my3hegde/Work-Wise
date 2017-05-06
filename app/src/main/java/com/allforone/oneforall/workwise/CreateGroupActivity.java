package com.allforone.oneforall.workwise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by Maithri on 2017-05-05.
 */

public class CreateGroupActivity extends Activity implements View.OnClickListener,AsyncResponse {

    private Button mSubmitButton = null;
    private EditText mPartnerEmail = null;
    private String mPartnerEmailStr = "";
    HTTPAsyncTask asyncTask =new HTTPAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_group_screen);

        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(this);

        mPartnerEmail = (EditText) findViewById(R.id.partneremail);



    }

    @Override
    public void onClick(View v){
        try{
        Properties properties = new Properties();
        properties.load(getApplicationContext().getAssets().open("workwise.properties"));
            String requestType = "creategroup";
            String urlstr = properties.getProperty("server_url") + requestType;

            mPartnerEmailStr = mPartnerEmail.getText().toString();

        asyncTask.delegate = this;

        asyncTask.execute(urlstr, requestType, LoginScreenActivity.sCurrentUserEmail, mPartnerEmailStr)
                .get();


        Intent createGroup = new Intent("com.allforone.oneforall.workwise.TaskAddAndViewActivity");
        startActivity(createGroup);

    } catch(IOException ex1){}
        catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }

    }

    @Override
    public void processResponse(String output){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        Log.d("CreateGroupActivity: ", "Response received: " + output);

        if(output.isEmpty()){
            Log.d("CreateGroupActivity: ", "Empty Response received");
            return;
        }

    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
