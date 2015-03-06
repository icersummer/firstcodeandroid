package com.vjia.bookcollector.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vjia.bookcollector.R;
import com.vjia.bookcollector.pojo.BookEntity;

public class BookAdapter extends BaseAdapter{
	
	private List<BookEntity> lists;
	private Context context;
//	private LinearLayout layout;

	public BookAdapter(List<BookEntity> lists, Context context){
		this.lists=lists;
		this.context=context;
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.book_list_inner, null);
			holder = new ViewHolder();
			BookEntity entity = lists.get(position);
			
			holder.book_title = (TextView) convertView.findViewById(R.id.book_title);
			holder.book_author = (TextView) convertView.findViewById(R.id.book_author);
			holder.book_isbn13 = (TextView) convertView.findViewById(R.id.book_isbn13);
			holder.book_price = (TextView) convertView.findViewById(R.id.book_price);
			holder.book_link = (TextView) convertView.findViewById(R.id.book_link);
			
			holder.book_title .setText("Title  : " + entity.getTitle());
			holder.book_author.setText("Author : " + entity.getAuthor());
			holder.book_isbn13.setText("ISBN13 : " + entity.getIsbn13());
			holder.book_price .setText("Price  : " + entity.getPrice());
			holder.book_link  .setText("Link   : " + entity.getLink());
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
			holder.book_title.setText(lists.get(position).getTitle());
			holder.book_author.setText(lists.get(position).getAuthor());
		}
		return convertView;
	}

	private static class ViewHolder{
		TextView book_title;
		TextView book_author;
		TextView book_isbn13;
		TextView book_link;
		TextView book_price;
	}

}
