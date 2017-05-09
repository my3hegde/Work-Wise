package com.allforone.oneforall.workwise;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by SumanNakshatri on 5/6/17.
 */

public class MyTasks extends Fragment implements AsyncResponse {

    //Overriden method onCreateView
    private View mMainView = null;
    private ListView mMyTasksList = null;
    private HTTPAsyncTask asyncTask = new HTTPAsyncTask();
    private List<ListItem> outputList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        mMainView = inflater.inflate(R.layout.my_tasks, container, false);

        mMyTasksList = (ListView) mMainView.findViewById(R.id.my_tasks_list);


        try {
            Log.d("Ripul: ", "Loading properties");
            Properties properties = new Properties();
            properties.load(getContext().getAssets().open("workwise.properties"));
            String requestType = "gettask";
            String urlstr = properties.getProperty("server_url") + requestType;

            asyncTask.delegate = this;

            asyncTask.execute(urlstr, requestType, LoginScreenActivity.sCurrentUserEmail)
                    .get();

        } catch (IOException ex1) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return mMainView;
    }

    @Override
    public void processResponse(String output) {
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        Log.d("CreateTaskDialog: ", "Response received: " + output);

        if (output == null || output.isEmpty()) {
            Log.d("CreateTaskDialog: ", "Empty Response received");
            return;
        } else {
            JSONArray mainObject = null;
            outputList = new ArrayList<ListItem>();

            if (output.startsWith("[")) {
                // This means we got output as JSON
                try {
                    mainObject = new JSONArray(output);
                    for (int i = 0; i < mainObject.length(); i++) {
                        JSONArray taskArray = mainObject.getJSONArray(i);
                        ListItem item = new ListItem(taskArray.getJSONArray(0).getInt(1),
                                taskArray.getJSONArray(1).getInt(1),
                                taskArray.getJSONArray(2).getInt(1),
                                taskArray.getJSONArray(3).getInt(1),
                                taskArray.getJSONArray(4).getString(1)
                        );
                        outputList.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayList<Task> taskList = new ArrayList<>();
                for(ListItem l : outputList){
                        Task temp = new Task((l.getName() + " " + l.getLength() + "min"), false, l.getId());
                        taskList.add(temp);
                }

//                mMyTasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        ListItem taskItem = (ListItem) parent.getItemAtPosition(position);
//
//                    }
//                });
                mMyTasksList.setAdapter(new MyListAdapter(getContext(), R.id.my_tasks_list, taskList));
            }
        }

    }

}

class Task {

    int ID;
    String name = null;
    boolean selected = false;

    public Task(String name, boolean selected, int id) {
        super();
        this.name = name;
        this.selected = selected;
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

}

class MyListAdapter extends ArrayAdapter<Task> implements AsyncResponse {

    private ArrayList<Task> taskList;
    private HTTPAsyncTask asyncTask1 = new HTTPAsyncTask();
    private class TaskListCheckBoxView {
        TextView mMyTask;
        CheckBox mCompleted;
    }

    public MyListAdapter(Context context, int listResID, ArrayList<Task> listItems) {
        super(context, listResID, listItems);
        this.taskList = new ArrayList<Task>();
        this.taskList.addAll(listItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TaskListCheckBoxView myView = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.task_list_checkbox_layout, null);

            myView = new TaskListCheckBoxView();
            myView.mMyTask = (TextView) convertView.findViewById(R.id.taskTitle);
            myView.mCompleted = (CheckBox) convertView.findViewById(R.id.completeCheck);
            convertView.setTag(myView);

            myView.mCompleted.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Task task = (Task) cb.getTag();
                    task.setSelected(cb.isChecked());
                    handleCompletedTaskResult(task);
                }
            });

        } else {
            myView = (TaskListCheckBoxView) convertView.getTag();
        }

        Task t = taskList.get(position);
        myView.mMyTask.setText(t.getName());
        myView.mCompleted.setChecked(t.isSelected());
        myView.mCompleted.setTag(t);

        return convertView;

    }

    public void handleCompletedTaskResult(Task t) {

        try {
            Log.d("Ripul: ", "Loading properties");
            Properties properties = new Properties();
            properties.load(getContext().getAssets().open("workwise.properties"));
            String requestType = "completedtask";
            String urlstr = properties.getProperty("server_url") + requestType;

            asyncTask1.delegate = this;
            if(!asyncTask1.isCancelled()) {
                asyncTask1.execute(urlstr, requestType, "" + t.getID())
                        .get();
            }

        } catch (IOException ex1) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            asyncTask1.cancel(true);
        }

    }

    @Override
    public void processResponse(String output) {
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        Log.d("CreateTaskDialog: ", "Response received: " + output);

        if (output == null || output.isEmpty()) {
            Log.d("CreateTaskDialog: ", "Empty Response received");
            return;
        }
    }
}