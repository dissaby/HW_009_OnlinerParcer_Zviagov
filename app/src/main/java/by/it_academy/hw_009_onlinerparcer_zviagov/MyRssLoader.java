package by.it_academy.hw_009_onlinerparcer_zviagov;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by dissa on 18.09.2016.
 */

public class MyRssLoader extends AsyncTaskLoader<ArrayList<NewsItem>> {
    
    private ArrayList<NewsItem> mData;
    private static String URL;

    public MyRssLoader(Context context) {
        super(context);
    }

    public static void setURL(String URL) {
        MyRssLoader.URL = URL;
    }

    @Override
    public ArrayList<NewsItem> loadInBackground() {
        return doParse();
    }

    private static ArrayList<NewsItem> doParse() {
        ArrayList<NewsItem> data = new ArrayList<>();
        try {
            if (URL == null) {
                URL = "https://people.onliner.by/feed";
            }
            Document doc = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .parser(Parser.xmlParser())
                    .get();
            doc.select("script, style, .hidden").remove();

            for (Element item : doc.select("item")) {
                final String title = item.select("title").first().text();
                final String link = item.select("link").first().text();
                final String description = item.select("description").text();
                Element linksInner = item.select("media|thumbnail ").first();
                final String imageUrl = linksInner.attr("URL").trim();
                data.add(new NewsItem(title, description, link, imageUrl));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void deliverResult(ArrayList<NewsItem> data) {
        mData = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            super.deliverResult(mData);
        } else {
            forceLoad();
        }
    }
}
