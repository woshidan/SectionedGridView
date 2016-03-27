package com.example.woshidan.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.woshidan.sectionedgridview.*;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random rand = new Random();
    private SectionedGroupAdapter parentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Frist.importTest();

        ArrayList<Content> items = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            items.add(getRandomImageContent());
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        parentAdapter = new SectionedGroupAdapter.Builder(items)
                .withContentAdapter(new ImageContentAdapter(this))
                .withHeaderAdapter(new HeaderAdapter(this))
                .withFooterAdapter(new FooterAdapter(this))
                .withRecyclerView(recyclerView)
                .build();

        recyclerView.setAdapter(parentAdapter);
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

       switch (item.getItemId()) {
           case R.id.action_insert_1_item:
               ImageContent content = getRandomImageContent();
               int insertPosition = parentAdapter.getInsertContentPosition(content);
               Log.d("onOptionsItemSelected", "insertPosition: " + insertPosition);
               parentAdapter.contents.add(content);
               parentAdapter.sortContents();
               // parentAdapter.so
               parentAdapter.insertedContentAt(parentAdapter.contents.indexOf(content));
               // parentAdapter.notifyDataSetChanged();
               break;
           case R.id.action_remove_1_item:
//               parentAdapter.contents.remove(2);
               parentAdapter.removeContentAt(2);
//               parentAdapter.contents.remove(2);
//               parentAdapter.notifyContentSetChanged();
               break;
           case R.id.action_insert_5_items:
               break;
           case R.id.action_remove_5_items:
               break;
       }

        return super.onOptionsItemSelected(item);
    }

    private ImageContent getRandomImageContent() {
        return new ImageContent(rand.nextInt(30) * 1000000000L, getRandomImageResourceId());
    }

    private int getRandomImageResourceId() {
        switch (rand.nextInt(15)) {
            case 0:
                return R.drawable.i1;
            case 1:
                return R.drawable.i2;
            case 2:
                return R.drawable.i3;
            case 3:
                return R.drawable.i4;
            case 4:
                return R.drawable.i5;
            case 5:
                return R.drawable.i6;
            case 6:
                return R.drawable.i7;
            case 7:
                return R.drawable.i8;
            case 8:
                return R.drawable.i9;
            case 9:
                return R.drawable.i10;
            case 10:
                return R.drawable.i11;
            case 11:
                return R.drawable.i12;
            case 12:
                return R.drawable.i13;
            case 13:
                return R.drawable.i14;
            case 14:
                return R.drawable.i15;
            default:
                return R.drawable.i1;
        }
    }
}
