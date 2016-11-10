package by.it_academy.hw_009_onlinerparcer_zviagov;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>>, RecyclerItemClickSupport.OnItemClickListener {
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
        getClass();
        RecyclerItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new MyRssLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> data) {
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        NewsItem item = mAdapter.getItem(position);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.newsLink));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_auto:
                finish();
                startActivity(getIntent());
                MyRssLoader.setURL("https://auto.onliner.by/feed");
                return true;
            case R.id.menu_peoples:
                finish();
                startActivity(getIntent());
                MyRssLoader.setURL("https://people.onliner.by/feed");
                return true;
            case R.id.menu_tech:
                finish();
                startActivity(getIntent());
                MyRssLoader.setURL("https://tech.onliner.by/feed");
                return true;
            case R.id.menu_realty:
                finish();
                startActivity(getIntent());
                MyRssLoader.setURL("https://realt.onliner.by/feed");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
