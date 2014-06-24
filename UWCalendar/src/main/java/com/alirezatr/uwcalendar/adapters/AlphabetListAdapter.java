package com.alirezatr.uwcalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.models.Subject;

import java.util.List;

public class AlphabetListAdapter extends BaseAdapter {
    private List rows;

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int i) {
        return rows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view  = convertView;

        if(getItemViewType(i) == 0) {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_item, null, false);
            }

            Item item = (Item) getItem(i);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(item.title);
            TextView textView2 = (TextView) view.findViewById(R.id.textView2);
            textView2.setText(item.description);
        }
        else {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_section, null, false);
            }

            Section section = (Section) getItem(i);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(section.text);
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position) instanceof Section) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public static abstract class Row {}

    public void setRows(List rows) {
        this.rows = rows;
    }

    public static final class Section extends Row {
        public final String text;

        public Section(String text) {
            this.text = text;
        }
    }

    public static final class Item extends Row {
        public final String title;
        public final String description;

        public Item(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }
}
