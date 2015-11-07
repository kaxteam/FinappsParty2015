package kax.team.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kax.team.Orientation;
import kax.team.PitchYawRoll;
import kax.team.R;
import kax.team.RaspberryService;
import xml.restfuldroid.core.WebService;
import xml.restfuldroid.core.WebServicesBuilder;
import xml.restfuldroid.core.resource.Resource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunningFragment extends Fragment {


    private TextView tv;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RunningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RunningFragment newInstance() {
        return new RunningFragment();
    }

    public RunningFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_running, container, false);

        tv = (TextView) v.findViewById(R.id.textView);

        new BackgroundData().execute();

        return v;
    }




    private class BackgroundData extends AsyncTask<Void, Void, PitchYawRoll> {

        @Override
        protected PitchYawRoll doInBackground(Void... params) {
            WebServicesBuilder builder = WebServicesBuilder.create();
            WebService w = builder.build();
            RaspberryService myService = (RaspberryService) Resource.createResource(RaspberryService.class, w);
            return myService.isFall();
        }

        @Override
        protected void onPostExecute(PitchYawRoll result) {
            super.onPostExecute(result);
            tv.setText(result.toString());
        }
    }






}
