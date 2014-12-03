package com.vjia.jokeking;

public class Joke {

	/**
	 * the detail content of Joke
	 */
	private String content;

	/**
	 * how many people have seen it
	 */
	private int review;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}

	public Joke(String content, int review) {
		// TODO Auto-generated constructor stub
		this.content = content;
		this.review = review;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s ;
		s = String.format("Joke ( content [%s], review [%d] )", content, review);
		return s;
	}
	
	

}
