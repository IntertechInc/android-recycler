package lollipop.intertech.com.recycler.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import lollipop.intertech.com.recycler.domain.Quote;
import lollipop.intertech.com.recycler.recycler.QuoteAdapter;

/**
 * Created by jwhite on 12/7/2014.
 */
public class MainActivity extends Activity {

    PlaceholderFragment fragment;
    Intent serviceIntent;
    BroadcastReceiver quoteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
        quoteReceiver = new QuoteReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(quoteReceiver, new IntentFilter("lollipop.intertech.com.newquote"));
        serviceIntent = new Intent(getApplicationContext(), lollipop.intertech.com.recycler.service.StockQuoteService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(quoteReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addOrUpdateQuote(Quote q) {
        if (fragment != null) {
            fragment.addOrUpdateQuote(q);
        }
    }

    private class QuoteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String symbol = intent.getStringExtra("symbol");
            long timestamp = intent.getLongExtra("timestamp", 0);
            double price = intent.getDoubleExtra("price", 0.0);
            Quote q = new Quote(timestamp, price, symbol);
            addOrUpdateQuote(q);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView quoteRecycler;
        private QuoteAdapter quoteAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            quoteRecycler = (RecyclerView) rootView.findViewById(R.id.feedRecyclerView);
            quoteRecycler.setHasFixedSize(true);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            android.support.v7.widget.GridLayoutManager layoutManager = new android.support.v7.widget.GridLayoutManager(getActivity(),2);
            quoteRecycler.setLayoutManager(layoutManager);
//            RecyclerView.ItemAnimator animator = quoteRecycler.getItemAnimator();
//            animator.setAddDuration(2000);
//            animator.setRemoveDuration(1000);
            quoteAdapter = new QuoteAdapter();
            quoteRecycler.setAdapter(quoteAdapter);
            return rootView;
        }

        public void addOrUpdateQuote(Quote q) {
            quoteAdapter.addOrUpdateQuote(q);
        }
    }
}
