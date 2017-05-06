package com.allforone.oneforall.workwise;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by Maithri on 2017-05-06.
 */

public class CreateTaskDialog extends Dialog implements View.OnClickListener, AsyncResponse {

    private static final int TASK_TYPE_HOME = 0;
    private static final int TASK_TYPE_WORK = 1;

    private static final int TASK_PRIORITY_LOW = 0;
    private static final int TASK_PRIORITY_MEDIUM = 1;
    private static final int TASK_PRIORITY_HIGH = 2;
    private static final int TASK_PRIORITY_CRITICAL = 3;

    private static final int TASK_NOT_REPEATED = 0;
    private static final int TASK_REPEATED = 1;

    private String mEnteredTaskName = "";
    private int mEnteredTaskDuration;
    private int mSelectedTaskType = TASK_TYPE_HOME;
    private int mSelectedPriority = TASK_PRIORITY_LOW;
    private int mTaskRepeated = TASK_NOT_REPEATED;


    private Button mOkButton = null;
    private Spinner mTaskTypeList = null;
    private Spinner mTaskPriorityList = null;
    private Switch mSwitch = null;
    private AutoCompleteTextView mTaskName = null;
    private EditText mTaskDuration = null;

    HTTPAsyncTask asyncTask = new HTTPAsyncTask();


    public CreateTaskDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View dialogView = getLayoutInflater().inflate(R.layout.create_task_dialog, null);

        setContentView(dialogView);

            mTaskName = (AutoCompleteTextView) findViewById(R.id.task_name);
            mTaskDuration = (EditText) findViewById(R.id.task_duration);

        mOkButton = (Button) findViewById(R.id.add_task_button);
        mOkButton.setOnClickListener(this);

        mTaskTypeList = (Spinner) findViewById(R.id.task_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.task_types_array, android.R.layout.simple_spinner_dropdown_item);
        mTaskTypeList.setAdapter(adapter);
        mTaskTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                int pos = parent.getSelectedItemPosition();
                if (pos == 0) {
                    mSelectedTaskType = TASK_TYPE_HOME;
                } else {
                    mSelectedTaskType = TASK_TYPE_WORK;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTaskPriorityList = (Spinner) findViewById(R.id.task_priority);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.task_priorities_array, android.R.layout.simple_spinner_dropdown_item);
        mTaskPriorityList.setAdapter(adapter);
        mTaskPriorityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                switch (pos) {
                    case 0:
                        mSelectedPriority = TASK_PRIORITY_LOW;
                        break;
                    case 1:
                        mSelectedPriority = TASK_PRIORITY_MEDIUM;
                        break;
                    case 2:
                        mSelectedPriority = TASK_PRIORITY_HIGH;
                        break;
                    case 3:
                        mSelectedPriority = TASK_PRIORITY_CRITICAL;
                        break;
                    default:
                        mSelectedPriority = TASK_PRIORITY_LOW;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSwitch = (Switch) findViewById(R.id.switch1);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTaskRepeated = TASK_REPEATED;
                } else {
                    mTaskRepeated = TASK_NOT_REPEATED;
                }
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
    public void onClick(View v) {
        try {
            Properties properties = new Properties();
            properties.load(getContext().getAssets().open("workwise.properties"));
            String requestType = "addtask";
            String urlstr = properties.getProperty("server_url") + requestType;

            mEnteredTaskName = mTaskName.getText().toString();

            try{
                mEnteredTaskDuration = Integer.parseInt(mTaskDuration.getText().toString());
            } catch (NumberFormatException ex) {

            }
            asyncTask.delegate = this;

            asyncTask.execute(urlstr, requestType, LoginScreenActivity.sCurrentUserEmail, ""+mEnteredTaskName,""+mEnteredTaskDuration,""+mTaskRepeated,""+mSelectedPriority,""+mSelectedTaskType )
                    .get();

        } catch (IOException ex1) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        dismiss();
    }
        @Override
        public void processResponse (String output){
            //Here you will receive the result fired from async class
            //of onPostExecute(result) method.
            Log.d("CreateTaskDialog: ", "Response received: " + output);

            if (output == null || output.isEmpty()) {
                Log.d("CreateTaskDialog: ", "Empty Response received");
                return;
            }

        }
    }

