package com.webmanager.prakashpatel.search;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.ListView;

public class RetainedFragment extends Fragment {

    // data object we want to retain
    private ListView listView;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public ListView getListView() {
        return listView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}