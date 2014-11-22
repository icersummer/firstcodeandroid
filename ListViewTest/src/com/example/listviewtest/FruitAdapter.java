package com.example.listviewtest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends ArrayAdapter<Fruit>{

	private int resourceId;
	public FruitAdapter(Context context, int textViewResourceId,
			List<Fruit> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		resourceId = textViewResourceId;
	}
	/**
	 * convertView is cached View
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Fruit fruit = this.getItem(position);
		View view ;
		// it makes : even you scroll the screen very fast, it can perform good.  
		if(convertView==null){
			view = LayoutInflater.from(this.getContext()).inflate(resourceId, null);
		} else {
			view = convertView;
		}
		ImageView fruitImage=(ImageView)view.findViewById(R.id.fruit_image);
		TextView fruitName=(TextView)view.findViewById(R.id.fruit_name);
		fruitImage.setImageResource(fruit.getImageId());
		fruitName.setText(fruit.getName());
		return view;
	}
	
	

}
