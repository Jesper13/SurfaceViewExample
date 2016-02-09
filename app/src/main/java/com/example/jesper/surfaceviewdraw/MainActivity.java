package com.example.jesper.surfaceviewdraw;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;

public class MainActivity extends AppCompatActivity{
    private Menu menu;
    private CustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customView = new CustomView(this);
        setContentView(customView);
        //Default Paint Setup
        customView.setPaint(Color.WHITE, 3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.drawing_menu, menu);
        menu.getItem(1).getIcon().setAlpha(80);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.eraser){
            item.setChecked(true);
            menu.getItem(0).getIcon().setAlpha(80);
            item.getIcon().setAlpha(255);
            customView.setPaint(Color.BLACK, 70);
        }else{
            item.setChecked(true);
            item.getIcon().setAlpha(255);
            menu.getItem(1).getIcon().setAlpha(80);
            customView.setPaint(Color.WHITE, 3);
        }
        return super.onOptionsItemSelected(item);
    }

}
