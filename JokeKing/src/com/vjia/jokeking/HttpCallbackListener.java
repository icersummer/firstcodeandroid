package com.vjia.jokeking;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);
}
