package by.it_academy.hw_009_onlinerparcer_zviagov;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by dissa on 18.09.2016.
 */

public class NewsItem {

    public final Spanned title;
    public final Spanned description;
    public final String newsLink;
    public final String imageLink;

    @SuppressWarnings("deprecation")
    public NewsItem(String title, String description, String newsLink, String imageLink) {
        this.title = Html.fromHtml(title) ;
        this.description = Html.fromHtml(description.replaceAll("\\<img.*?>",""));
        this.newsLink = newsLink;
        this.imageLink = imageLink;
    }

}
