package com.example.laowai.multiple_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
//important
//<activity
//        android:screenOrientation="portrait"
//        android:launchMode="singleTask" <<<< ???????
//        android:name=".blue_tooth"
//        android:label="title_blue_tooth">

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         //getSupportActionBar().setDisplayShowHomeEnabled(true);
         //getSupportActionBar().setLogo(R.drawable.icons_plus);
         //getSupportActionBar().setDisplayUseLogoEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            Toast.makeText(this, "action_settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item_1) {
            Toast.makeText(this, "item_1", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item_2) {
            Toast.makeText(this, "item_2", Toast.LENGTH_SHORT).show();
            return true;        } else if (id == R.id.item_3) {
            Toast.makeText(this, "3333333333333333333333333333333333", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.action_blue_tooth) {
            Toast.makeText(this, "bbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(),blue_tooth.class));
            return true;
        } else if (id == R.id.action_house) {
            Toast.makeText(this, "action_house", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            return true;
        } else if (id == R.id.action_heart) {
            Toast.makeText(this, "action_heart", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Main3Activity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
