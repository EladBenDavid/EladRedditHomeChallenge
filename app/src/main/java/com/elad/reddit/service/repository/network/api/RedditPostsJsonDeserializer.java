package com.elad.reddit.service.repository.network.api;

import android.util.Log;
import com.elad.reddit.service.model.RedditPost;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Elad on 5/30/2018.
 */

class RedditPostsJsonDeserializer implements JsonDeserializer {

    private static String TAG = RedditPostsJsonDeserializer.class.getSimpleName();


    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            ArrayList<RedditPost> posts = new ArrayList<>();
            JsonObject jsonObject = json.getAsJsonObject();
            // removing few wrapper tags from the RedditPosts
            JsonArray postsArray = jsonObject.getAsJsonObject("data").getAsJsonArray("children");
            for (int i = 0; i < postsArray.size(); i++) {
                // each item in the array contain wrapper tag of data
                JsonObject obj = postsArray.get(i).getAsJsonObject().getAsJsonObject("data");
                // adding the converted wrapper to our container
                RedditPost deserialized = context.deserialize(obj, RedditPost.class);
                posts.add(deserialized);
            }
            return posts;
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize Reddit element: %s", json.toString()));
            return null;
        }
    }
}
