package com.ryantroxler.shottystash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.ryantroxler.shottystash.DAO.ShotgunDAO;
import com.ryantroxler.shottystash.DAO.TransactionDAO;
import com.ryantroxler.shottystash.listeners.OnClickListenerCreateShotgun;
import com.ryantroxler.shottystash.listeners.OnClickListenerDeposit;
import com.ryantroxler.shottystash.models.Shotgun;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.fam);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.fam_item_1);
        floatingActionButton1.setOnClickListener(new OnClickListenerCreateShotgun());

        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.fam_item_2);
        floatingActionButton2.setOnClickListener(new OnClickListenerDeposit());
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.fam_item_3);
        floatingActionButton3.setOnClickListener(new OnClickListenerDeposit()); //validations on withdraw amount, gross

        updateBalance();
        readRecycledRecords();
    }

    public void readRecycledRecords() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        List<Shotgun> shotguns = new ShotgunDAO(this).read();
        Double balance = new TransactionDAO(this).currentBalance();
        RVAdapter adapter = new RVAdapter(shotguns, balance);

        recyclerView.setAdapter(adapter);
    }

    public void updateRecyclerView(Shotgun shotgun) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        RVAdapter adapter = (RVAdapter) recyclerView.getAdapter();

        adapter.addItem(shotgun);
    }
    public void updateRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        RVAdapter adapter = (RVAdapter) recyclerView.getAdapter();

        List<Shotgun> shotguns = new ShotgunDAO(this).read();
        adapter.update(shotguns);
    }

    public void updateBalance() {
        TextView balanceView = (TextView) findViewById(R.id.currentBalance);
        Double balance = new TransactionDAO(this).currentBalance();
        balanceView.setText("Current Stash: $" + balance.toString());
    }

    public void updateDatabase() {
//        DatabaseHandler dbh = new DatabaseHandler(this);
//        SQLiteDatabase db = dbh.getWritableDatabase();
//        dbh.onUpgrade(db, 0,1); NOPE
//        dbh.createTransactions(db); // Whatevs
//        db.close();
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

