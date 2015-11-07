package kax.team;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Pc on 07/11/2015.
 */
public class HistorialAdapter extends ArrayAdapter<HistoryClass>{
    int textViewResourceId;
    HistoryClass[] objects;

    public HistorialAdapter(Context context, int textViewResourceId, HistoryClass[] objects) {
        super(context, textViewResourceId, objects);
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoryHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            row = inflater.inflate(textViewResourceId, parent, false);

            holder = new HistoryHolder();
            holder.fecha = (TextView)row.findViewById(R.id.textView1);
            holder.duration = (TextView)row.findViewById(R.id.textView2);
            holder.time = (TextView)row.findViewById(R.id.textView3);
            holder.distance = (TextView)row.findViewById(R.id.textView4);
            holder.recorrida = (TextView)row.findViewById(R.id.textView5);
            holder.vprom = (TextView)row.findViewById(R.id.textView6);
            holder.vel = (TextView)row.findViewById(R.id.textView7);
            holder.km = (TextView)row.findViewById(R.id.textView8);
            holder.kmH = (TextView)row.findViewById(R.id.textView9);

            row.setTag(holder);
        }
        else
        {
            holder = (HistoryHolder)row.getTag();
        }

        HistoryClass historial = objects[position];
        holder.fecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(historial.getData()));
        holder.time.setText(historial.getDuration().toString());
        holder.recorrida.setText(historial.getDistance()+"");
        holder.vel.setText(historial.getAverageSpeed()+"");

        return row;
    }
    static class HistoryHolder
    {
        TextView fecha;
        TextView duration;
        TextView time;
        TextView distance;
        TextView recorrida;
        TextView vprom;
        TextView vel;
        TextView km;
        TextView kmH;
    }
}
