package com.allforone.oneforall.workwise;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class TaskAddAndViewActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    private ImageButton mAddTaskButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add_and_view);

        //Adding toolbar to the activity
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("All Tasks"));
        tabLayout.addTab(tabLayout.newTab().setText("My Tasks"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

        mAddTaskButton = (ImageButton) findViewById(R.id.addButton);
        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTaskDialog newDialog = new CreateTaskDialog(TaskAddAndViewActivity.this);
                newDialog.show();
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
