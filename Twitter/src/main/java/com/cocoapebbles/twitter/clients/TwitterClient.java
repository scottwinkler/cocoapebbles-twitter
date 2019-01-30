package com.cocoapebbles.twitter.clients;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TwitterClient {
    private static TwitterClient _instance = null;
    public Twitter twitter;

    private TwitterClient(){
        twitter = new TwitterFactory().getInstance();
    }

    public static TwitterClient getInstance(){
        if(_instance == null){
            _instance = new TwitterClient();
        }
        return _instance;
    }
}
