package com.alirezatr.uwcalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.models.ListHeader;
import com.alirezatr.uwcalendar.models.ListItem;
import com.alirezatr.uwcalendar.views.PinnedSectionListView;

import java.util.List;

public class SubjectsListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
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
                view = (LinearLayout) inflater.inflate(R.layout.subjects_row_item, null, false);
            }

            ListItem item = (ListItem) getItem(i);
            TextView textView = (TextView) view.findViewById(R.id.section_title);
            textView.setText(item.subject.getSubject());
            TextView textView2 = (TextView) view.findViewById(R.id.subjects_description);
            textView2.setText(item.subject.getDescription());
        }
        else {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_section, null, false);
            }

            ListHeader section = (ListHeader) getItem(i);
            TextView textView = (TextView) view.findViewById(R.id.section_title);
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
        if(getItem(position) instanceof ListHeader) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        if(getItem(position) instanceof ListHeader) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    public static abstract class Row {}

    public void setRows(List rows) {
        this.rows = rows;
    }
}
