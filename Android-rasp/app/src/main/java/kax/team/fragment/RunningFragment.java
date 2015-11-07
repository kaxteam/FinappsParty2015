package kax.team.fragment;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kax.team.PitchYawRoll;
import kax.team.Position;
import kax.team.R;
import kax.team.service.RaspberryService;
import xml.restfuldroid.core.WebService;
import xml.restfuldroid.core.WebServicesBuilder;
import xml.restfuldroid.core.model.Response;
import xml.restfuldroid.core.resource.Resource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunningFragment extends Fragment implements View.OnClickListener {

    public static final String REST = "http://192.168.0.102:8001/api/";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ImageButton startButton;
    private boolean startShowed = true;
    private WebService webService;
    private int currentId;

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
        webService = WebServicesBuilder.create().build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_running, container, false);

        startButton = (ImageButton) v.findViewById(R.id.start_btn);
        startButton.setOnClickListener(this);

        setUpMapIfNeeded();

        new BackgroundData().execute();

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Location location = getLastKnownLocation();
        if (location == null) return;
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        LatLng coordinate = new LatLng(lat, lng);
        //mMap.addMarker(new MarkerOptions().position(coordinate).title("Marker"));
        CameraUpdate cu = CameraUpdateFactory.newLatLng(coordinate);
        mMap.moveCamera(cu);
        cu = CameraUpdateFactory.zoomTo(14f);
        mMap.moveCamera(cu);

    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onClick(View v) {
        float density = v.getResources().getDisplayMetrics().density;
        if(startShowed) {
            startButton.setImageResource(R.drawable.pausa);
            startButton.setMaxWidth((int) (50 * density));
            startButton.setMaxHeight((int) (50 * density));
            //startTracking();
        } else {
            startButton.setImageResource(R.drawable.start_big_green);
            startButton.setMaxWidth((int) (175 * density));
            startButton.setMaxHeight((int) (175 * density));
            //stopTracking();
        }

        startShowed = !startShowed;
    }

    private void startTracking() {
        // Llamar a start de la API
        new Thread(new Runnable() {
            @Override
            public void run() {
                Location l = getLastKnownLocation();
                Response<Integer> response = webService.post(Integer.class, new Position(l.getLatitude(), l.getLongitude()), REST + "start");
                currentId = response.data;

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Location l = getLastKnownLocation();
                        //webService.post(null, new Position(l.getLatitude(), l.getLongitude()), REST + "position", "", "");
                    }
                }, 2000);
                new TrackingTask().execute();
            }
        });
        // Llamar a un AsynTask k haga el tracking
    }

    private void stopTracking() {

    }

    private class TrackingTask extends AsyncTask<Void, Void, PitchYawRoll> {

        @Override
        protected PitchYawRoll doInBackground(Void... params) {

            //Enviar constantemente mi localización
            RaspberryService myService = (RaspberryService) Resource.createResource(RaspberryService.class, webService);
            //while(true){
            return myService.isFall();
            //}
        }

        @Override
        protected void onPostExecute(PitchYawRoll result) {
            super.onPostExecute(result);
        }
    }

    private class BackgroundData extends AsyncTask<Void, Void, PitchYawRoll> {

        @Override
        protected PitchYawRoll doInBackground(Void... params) {

            RaspberryService myService = (RaspberryService) Resource.createResource(RaspberryService.class, webService);
            //while(true){
               return myService.isFall();
            //}
        }

        @Override
        protected void onPostExecute(PitchYawRoll result) {
            super.onPostExecute(result);
        }
    }






}
