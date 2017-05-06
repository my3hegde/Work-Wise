package com.allforone.oneforall.workwise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Maithri on 2017-05-05.
 */

public class CreateGroupActivity extends Activity implements View.OnClickListener {

    private Button mSubmitButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_group_screen);

        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent openTabbedView = new Intent("com.allforone.oneforall.workwise.TaskAddAndViewActivity");
        startActivity(openTabbedView);
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
