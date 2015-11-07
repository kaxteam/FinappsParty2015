package kax.team.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;

import kax.team.HistorialAdapter;
import kax.team.HistoryClass;
import kax.team.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    ListView listview;
    ArrayAdapter<String> adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RunningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        HistoryClass histori_data[] = new HistoryClass[]
                {
                        new HistoryClass(11.30, "32:54", 5.32 , new Date()),
                        new HistoryClass(11.30, "32:54", 5.32 , new Date()),
                        new HistoryClass(11.30, "32:54", 5.32 , new Date()),
                        new HistoryClass(11.30, "32:54", 5.32 , new Date()),
                        new HistoryClass(11.30, "32:54", 5.32 , new Date())
                };

        HistorialAdapter adapter = new HistorialAdapter(getActivity(),
                R.layout.listview_hist_rows, histori_data);


        listview = (ListView) v.findViewById(R.id.listViewHistory);


        View header = (View)getLayoutInflater(null).inflate(R.layout.listview_hist_header, null);
        listview.addHeaderView(header);

        listview.setAdapter(adapter);
        return v;
    }




}
