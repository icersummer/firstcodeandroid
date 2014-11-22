package com.example.listviewtest;

//import android.R;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
			"Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };

	private List<Fruit> fruitList=new ArrayList<Fruit>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFruits();
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//				MainActivity.this, android.R.layout.simple_list_item_1, data);
		FruitAdapter adapter=new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
		ListView listView = (ListView) this.findViewById(R.id.list_view);
		listView.setAdapter(adapter); 
		
		// make the ListView to be Clickable
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Fruit fruit = fruitList.get(position); // the position is equal to List index ?
				Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
			}
			
		});
	}

	//TODO for every fruit's pic here, should use their own, in here just use ic_launcher 
	private void initFruits() {
		Fruit Banana = new Fruit("Banana", R.drawable.ic_launcher);
		fruitList.add(Banana);
		Fruit Orange = new Fruit("Orange", R.drawable.ic_launcher);
		fruitList.add(Orange);
		Fruit Watermelon = new Fruit("Watermelon", R.drawable.ic_launcher);
		fruitList.add(Watermelon);
		Fruit Pear = new Fruit("Pear", R.drawable.ic_launcher);
		fruitList.add(Pear);
		Fruit Grape = new Fruit("Grape", R.drawable.ic_launcher);
		fruitList.add(Grape);
		Fruit Pineapple = new Fruit("Pineapple", R.drawable.ic_launcher);
		fruitList.add(Pineapple);
		Fruit Strawberry = new Fruit("Strawberry", R.drawable.ic_launcher);
		fruitList.add(Strawberry);
		Fruit Cherry = new Fruit("Cherry", R.drawable.ic_launcher);
		fruitList.add(Cherry);
		Fruit Mango = new Fruit("Mango", R.drawable.ic_launcher);
		fruitList.add(Mango);
		Fruit apple = new Fruit("Apple", R.drawable.ic_launcher);
		fruitList.add(apple);
	}
}
