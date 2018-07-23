package com.echeck.echeck;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class sampleArrayAdapter<T> extends ArrayAdapter {
    public sampleArrayAdapter (Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
