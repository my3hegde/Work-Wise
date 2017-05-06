package com.allforone.oneforall.workwise;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by SumanNakshatri on 5/6/17.
 */

public class AllTasks extends Fragment {

    //Overriden method onCreateView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.all_tasks, container, false);
    }

}