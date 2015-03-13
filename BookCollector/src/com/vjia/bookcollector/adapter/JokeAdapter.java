package com.vjia.bookcollector.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vjia.bookcollector.R;
import com.vjia.bookcollector.pojo.Joke;

public class JokeAdapter extends BaseAdapter {
	
	private List<Joke> lists;
	private Context context;

	public JokeAdapter(List<Joke> lists_, Context context_){
		lists = lists_;
		context = context_;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Joke joke = lists.get(position);
		ViewHolder holder;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.fun_joke_api_listview, null);
			holder=new ViewHolder();
			
			holder.joke_author=(TextView)convertView.findViewById(R.id.textview_joke_author);
			holder.joke_content=(TextView)convertView.findViewById(R.id.textview_joke_content);
			
			holder.joke_author.setText("AUTHOR:"+joke.getAuthor());
			holder.joke_content.setText(joke.getContent());
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
			holder.joke_author.setText("AUTHOR:"+joke.getAuthor());
			holder.joke_content.setText("CONTENT: "+joke.getContent());
		}
		return convertView;
	}
	

	private static class ViewHolder{
		TextView joke_author;
		TextView joke_content;
	}

}
