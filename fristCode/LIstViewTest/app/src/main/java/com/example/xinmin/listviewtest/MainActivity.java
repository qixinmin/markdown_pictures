package com.example.xinmin.listviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"Apple","Banana", "Orange", "WaterMelon",
            "Apple1","Banana1", "Orange1", "WaterMelon1",
            "Apple2","Banana2", "Orange2", "WaterMelon2",
            "Apple3","Banana3", "Orange3", "WaterMelon3",
            "Apple2","Banana2", "Orange2", "WaterMelon2",
            "Apple3","Banana3", "Orange3", "WaterMelon3",};
    
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initFruit();

        /*FruitAdapter adapter = new FruitAdapter(MainActivity.this,
                R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });*/

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(25));
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, 10, getResources().getColor(R.color.colorPrimaryDark)));

        FruitAdapterRecycle adapterRecycle = new FruitAdapterRecycle(fruitList);
        recyclerView.setAdapter(adapterRecycle);
    }
    
    private void initFruit() {
        for(int i = 0; i<100; i++) {
            Fruit apple = new Fruit("Apple"+i, R.drawable.a1);
            fruitList.add(apple);
        }
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int mSpace;
        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = mSpace;
        }
    }
}
