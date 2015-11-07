package kax.team.fragment;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kax.team.Position;
import kax.team.R;
import xml.restfuldroid.core.WebService;
import xml.restfuldroid.core.WebServicesBuilder;
import xml.restfuldroid.core.model.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunningFragment extends Fragment implements View.OnClickListener {

    public static final String REST = "http://192.168.0.102:8001/api/tracking/";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ImageButton startButton;
    private boolean startShowed = true;
    private WebService webService;
    private int currentId;
    private Timer trackingTimer;
    private int count;
    private RelativeLayout counterLayout;
    private TextView counterView1;
    private TextView counterView2;
    private TextView kmTv;

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

        counterLayout = (RelativeLayout) v.findViewById(R.id.counterlayout);

        counterView1 = (TextView) v.findViewById(R.id.counterview1);
        counterView2 = (TextView) v.findViewById(R.id.counterview2);
        kmTv = (TextView) v.findViewById(R.id.km_tv);


        startButton.setOnClickListener(this);


        count = 0;
        changeTimerAndKm();

        setUpMapIfNeeded();

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
            startTracking();
        } else {
            startButton.setImageResource(R.drawable.start_big_green);
            startButton.setMaxWidth((int) (175 * density));
            startButton.setMaxHeight((int) (175 * density));
            stopTracking();
        }

        startShowed = !startShowed;
    }

    private String placeZeroIfNeeded(int number) {
        return (number >=10)? Integer.toString(number):String.format("0%s",Integer.toString(number));
    }

    private String[] secondsToString(int pTime) {
        final int hour = pTime / 3600;
        final int min = (pTime % 3600) / 60;
        final int sec = pTime % 60;

        final String strHour = placeZeroIfNeeded(hour);
        final String strMin = placeZeroIfNeeded(min);
        final String strSec = placeZeroIfNeeded(sec);

        return new String[]{strHour, strMin, strSec};
    }


    private void startTracking() {
        // Llamar a start de la API
        new Thread(new Runnable() {
            @Override
            public void run() {
                Location l = getLastKnownLocation();
                Response<Integer> response = webService.post(Integer.class, new Position(l.getLatitude(), l.getLongitude()), REST + "start/");
                currentId = response.data;

                if (trackingTimer != null)
                    trackingTimer.cancel();
                trackingTimer = new Timer();
                trackingTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        changeTimerAndKm();




                        Location l = getLastKnownLocation();
                        webService.post(Void.class, new Position(l.getLatitude(), l.getLongitude()), REST + "position/?id="+currentId);
                    }
                }, 0, 1000);
            }
        }).start();
        // Llamar a un AsynTask k haga el tracking
    }

    private void changeTimerAndKm() {
        final String[] time = secondsToString(count);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                counterView1.setText(String.format("%s:%s", time[0], time[1]));
                counterView2.setText(String.format("%s", time[2]));
            }
        });

        final DecimalFormat df = new DecimalFormat("#0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                kmTv.setText(df.format(count / 500.0f));
            }
        });

        count++;

    }

    private void stopTracking() {
        if(trackingTimer != null)
            trackingTimer.cancel();
        count = 0;
        changeTimerAndKm();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Location l = getLastKnownLocation();
                webService.post(Void.class, new Position(l.getLatitude(), l.getLongitude()), REST + "stop/?id="+currentId);
            }
        }).start();

    }
}
