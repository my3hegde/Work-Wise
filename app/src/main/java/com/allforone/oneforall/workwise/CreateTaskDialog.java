package com.allforone.oneforall.workwise;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by Maithri on 2017-05-06.
 */

public class CreateTaskDialog extends Dialog implements View.OnClickListener{

    private static final int TASK_TYPE_HOME = 0;
    private static final int TASK_TYPE_WORK = 1;

    private int mSelectedTaskType =  TASK_TYPE_HOME;
    private Button mOkButton = null;
    private Spinner mTaskTypeList = null;
    public CreateTaskDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View dialogView = getLayoutInflater().inflate(R.layout.create_task_dialog, null);

        setContentView(dialogView);

        mOkButton = (Button) findViewById(R.id.add_task_button);
        mOkButton.setOnClickListener(this);

        mTaskTypeList = (Spinner) findViewById(R.id.task_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.task_types_array, android.R.layout.simple_spinner_item);
        mTaskTypeList.setAdapter(adapter);
        mTaskTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                int pos = parent.getSelectedItemPosition();
                if(pos == 0){
                    mSelectedTaskType = TASK_TYPE_HOME;
                } else {
                    mSelectedTaskType = TASK_TYPE_WORK;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void create() {
        super.create();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View v){

    }
}
