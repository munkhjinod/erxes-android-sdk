package com.newmedia.erxeslibrary.configuration;

import android.content.Context;
import android.support.annotation.RawRes;

import com.google.gson.JsonObject;
import com.newmedia.erxeslibrary.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Language {
    final private HashMap<String, Integer> rawmap = new HashMap<String, Integer>() {{
        put("de", R.raw.de);
        put("en", R.raw.en);
        put("es", R.raw.es);
        put("fr", R.raw.fr);
        put("jp", R.raw.en);
        put("kr", R.raw.en);
        put("mn", R.raw.en);
        put("np", R.raw.en);
        put("pt-br", R.raw.pt_br);
        put("ru", R.raw.ru);
        put("vn", R.raw.vn);
        put("zh", R.raw.zh);
    }};
    final public String[] keys =
            {
                    "Welcome!",
                    "Close",
                    "Offline",
                    "Online",
                    "Send a message",
                    "Send",
                    "Support",
                    "Faq",
                    "Start new conversation",
                    "Write a reply",
                    "Conversation",
                    "with Support staff",
                    "Click to select a date",
                    "Thanks for your message. We will respond as soon as we can.",
                    "Create new",
                    "Written by",
                    "Modified ",
                    "Created ",
                    "There are ",
                    "articles in this category",
                    "Back to categories",
                    "Back to top",
                    "Back to articles",
                    "Contact",
                    "Give us your contact information",
                    "Email",
                    "SMS",
                    "email@domain.com",
                    "phone number",
                    "Support staff",
                    "Conversations",
                    "Recent conversations",
                    "with Support staffs",
                    "Talk with support staff",
                    "Do you want to end this conversation ?",
                    "Welcome description",
                    "Failed",
                    "No articles found",
                    "Search",
                    "End conversation"
            };

    public void read(Context context,String lan){
        String translate = readTextFile(context,rawmap.get(lan));
        try {
            JSONObject json = new JSONObject(translate);
            json.getString(keys[0]);
            json.getString(context.getString(R.string.Welcome));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        R.string.connnect

    }
    public static String readTextFile(Context context, @RawRes int id){
        InputStream inputStream = context.getResources().openRawResource(id);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buffer[] = new byte[1024];
        int size;
        try {
            while ((size = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, size);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

}
