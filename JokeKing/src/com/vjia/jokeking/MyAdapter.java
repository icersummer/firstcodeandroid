package com.vjia.jokeking;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private List<Joke> lists;

	public MyAdapter(List<Joke> lists, Context context) {
		// TODO Auto-generated constructor stub
		this.lists = lists;
		this.context = context;
	}

	@Override
	public int getCount() {

		return lists.size();
	}

	@Override
	public Object getItem(int position) {

		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.detail,
					null);
			holder = new ViewHolder();
			holder.contentTV = (TextView) convertView
					.findViewById(R.id.joke_content);
			holder.reviewTV = (TextView) convertView
					.findViewById(R.id.joke_review);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.contentTV.setText(lists.get(position).getContent());
			holder.reviewTV.setText(String.valueOf(lists.get(position).getReview()));
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView contentTV;
		TextView reviewTV;
	}

}
