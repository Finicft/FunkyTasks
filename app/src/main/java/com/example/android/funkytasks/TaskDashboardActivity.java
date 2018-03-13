package com.example.android.funkytasks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class TaskDashboardActivity extends AppCompatActivity {
    ArrayList<User> userArrayList = new ArrayList<User>();
    private String username;

    ListView listView;
    ListViewAdapter listViewAdapter;
    ArrayList <Task> taskList = new ArrayList<Task>();

    final int DELETECODE = 1;
    ArrayList<Task> requestedTasks;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_dashboard);
        Intent intent = getIntent();
        username = intent.getExtras().getString("username");

        ElasticSearchController.GetUser getUser = new ElasticSearchController.GetUser();
        getUser.execute(username);
        try{
            user = getUser.get();
            Log.e("Got the username: ", user.getUsername());

        } catch (Exception e) {
            Log.e("Error", "We arnt getting the user");
            return;
        }
        // Setting up adapter to listen for the respective list

        requestedTasks = user.getRequestedTasks();
        listView = (ListView) findViewById(R.id.myTasks);
        listViewAdapter = new ListViewAdapter(this, R.layout.listviewitem, requestedTasks);

        listView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TaskDashboardActivity.this,DashboardRequestedTask.class);
                intent.putExtra("username",username);
                Task detailedTask = user.getRequestedTasks().get(i);

                ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();

                getTask.execute(detailedTask.getId());
                try{
                    Task x = getTask.get();
                    Log.e("Return task title",x.getTitle());
                }
                catch(Exception e){
                    Log.e("Task get","not workng");
                }
                intent.putExtra("task",detailedTask);
                intent.putExtra("position",i);
                intent.putExtra("id",detailedTask.getId());
                startActivityForResult(intent, DELETECODE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainMenuActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == DELETECODE && resultCode == RESULT_OK) {
            ElasticSearchController.GetUser getUser = new ElasticSearchController.GetUser();
            getUser.execute(username);
            try{
                user = getUser.get();
                Log.e("Got the username: ", user.getUsername());

            } catch (Exception e) {
                Log.e("Error", "We arnt getting the user");
                return;
            }
            //TODO fix adapter to reflect the delete changes, (delete itself works but task shud be gone from listview)
            requestedTasks = user.getRequestedTasks();
            listViewAdapter.notifyDataSetChanged();
        }



    }


}
