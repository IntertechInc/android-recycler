package lollipop.intertech.com.recycler.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import lollipop.intertech.com.recycler.domain.Quote;

/**
 * Created by jwhite on 12/7/2014.
 */
public class StockQuoteService extends Service {

    private static final int QUOTE_CYCLE = 3000; // every three seconds
    private static final String TAG = "Quote Service";

    StockQuoteSimulator simulator;
    QuoteTask task;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG,"service and task starting");
        simulator = new StockQuoteSimulator();
        task = new QuoteTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        task.execute();
        Log.v(TAG,"Service and task started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v(TAG,"Service and task stopping");
        task.cancel(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class QuoteTask extends AsyncTask<Void, Quote, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Quote quote;
            while (true) {
                if (isCancelled()) {
                    Log.v(TAG, "Exiting quote task...");
                    break;
                }
                try {
                    Thread.sleep(QUOTE_CYCLE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }                Log.v(TAG, "Getting quote...");
                quote = simulator.generateQuote();
                Log.v(TAG, "..." + quote.toString());
                publishProgress(quote);
            }
            Log.v(TAG,"...quote task exited");
            return null;
        }

        @Override
        protected void onProgressUpdate(Quote... quotes) {
            super.onProgressUpdate(quotes);
            sendBroadcast(quotes[0]);
        }

        private void sendBroadcast(Quote quote){
            Intent intent = new Intent("lollipop.intertech.com.newquote");
            intent.putExtra("symbol", quote.getSymbol());
            intent.putExtra("price", quote.getPrice());
            intent.putExtra("timestamp", quote.getTimestamp());
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }

    }
}
