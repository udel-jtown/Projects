package edu.udel.jtown.EscapeGame;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SimpleTextViewArrayAdapter<T> extends ArrayAdapter<T> {
    private int textSize;
    public SimpleTextViewArrayAdapter(Context context, T[] objects, int textSize) {
        super(context, 0, objects);
        this.textSize = textSize;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView)convertView;
        if (tv == null) {
            tv = new TextView(getContext());
            tv.setTextSize(textSize);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLACK);
        }
        tv.setText(getItem(position).toString());
        return tv;
    }
}