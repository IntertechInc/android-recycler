package lollipop.intertech.com.recycler.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lollipop.intertech.com.recycler.activity.R;

/**
 * Created by jwhite on 12/7/2014.
 */
public class QuoteViewHolder extends RecyclerView.ViewHolder {

    protected TextView symbolTextView;
    protected TextView priceTextView;
    protected TextView timestampTextView;
    protected ImageView directionImageView;

    public QuoteViewHolder(View itemView) {
        super(itemView);
        directionImageView = (ImageView) itemView.findViewById(R.id.directionImageView);
        symbolTextView = (TextView) itemView.findViewById(R.id.symbolTextView);
        priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
        timestampTextView = (TextView) itemView.findViewById(R.id.timestampTextView);
    }
}
