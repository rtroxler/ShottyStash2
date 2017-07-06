package com.ryantroxler.shottystash;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListenerCreateShotgun());

//        readRecords();
        readRecycledRecords();
    }

    public void readRecycledRecords() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        List<ObjectShotgun> shotguns = new TableControllerShotgun(this).read();
        RVAdapter adapter = new RVAdapter(shotguns);

        recyclerView.setAdapter(adapter);
    }

    public void updateRecyclerView(ObjectShotgun shotgun) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        RVAdapter adapter = (RVAdapter) recyclerView.getAdapter();

        adapter.addItem(shotgun);
    }
    public void updateRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        RVAdapter adapter = (RVAdapter) recyclerView.getAdapter();

        List<ObjectShotgun> shotguns = new TableControllerShotgun(this).read();
        adapter.update(shotguns);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

