package lollipop.intertech.com.recycler.recycler;

        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import java.util.List;

        import lollipop.intertech.com.recycler.activity.R;
        import lollipop.intertech.com.recycler.domain.Quote;
        import lollipop.intertech.com.recycler.service.StockQuoteSimulator;

/**
 * Created by jwhite on 12/7/2014.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteViewHolder> {

    private static final int STARTING_QUOTES = 5;
    List<Quote> quotesDataSet;

    public QuoteAdapter() {
        quotesDataSet = (new StockQuoteSimulator()).initializeQuotes(STARTING_QUOTES);  // initialize the dataset
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.quote_item, viewGroup, false);
        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuoteViewHolder itemViewHolder, int i) {
        Quote quote = quotesDataSet.get(i);
        itemViewHolder.symbolTextView.setText(quote.getSymbol());
        itemViewHolder.priceTextView.setText(quote.getFormattedPrice());
        itemViewHolder.timestampTextView.setText(quote.getFormattedTimestamp());
        if (quote.getPrice() > 0) {
            itemViewHolder.directionImageView.setImageResource(R.drawable.up);
        } else {
            itemViewHolder.directionImageView.setImageResource(R.drawable.down);
        }
    }

    @Override
    public int getItemCount() {
        return quotesDataSet.size();
    }

    public void addOrUpdateQuote(Quote q) {
        int pos = quotesDataSet.indexOf(q);
        if (pos >= 0) {
            updateQuote(q, pos);
        } else {
            addQuote(q);
        }
    }

    private void updateQuote(Quote q, int pos) {
        quotesDataSet.remove(pos);
        notifyItemRemoved(pos);
        addQuote(q);
    }

    private void addQuote(Quote q) {
        quotesDataSet.add(q);
        notifyItemInserted(quotesDataSet.size()-1);
    }

}
