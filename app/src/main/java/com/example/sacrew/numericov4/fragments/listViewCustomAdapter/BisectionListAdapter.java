package com.example.sacrew.numericov4.fragments.listViewCustomAdapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sacrew.numericov4.R;
import com.example.sacrew.numericov4.fragments.oneVariableFragments.incrementalSearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/14/2017.
 */

public class BisectionListAdapter extends ArrayAdapter<Bisection> {

    private static final String TAG = "BisectionListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView textViewN;
        TextView textViewXi;
        TextView textViewXs;
        TextView textViewXm;
        TextView textViewFXm;
        TextView textViewError;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public BisectionListAdapter(Context context, int resource, ArrayList<Bisection> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String n = getItem(position).getN();
        String xi = getItem(position).getXi();
        String xs = getItem(position).getXs();
        String xm = getItem(position).getXm();
        String fXm = getItem(position).getFXm();
        String error = getItem(position).getError();

        //Create the person object with the information
        Bisection bisection = new Bisection(n,xi,xs,xm,fXm, error);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.textViewN = convertView.findViewById(R.id.textViewN);
            holder.textViewXi = convertView.findViewById(R.id.textViewXi);
            holder.textViewXs = convertView.findViewById(R.id.textViewXs);
            holder.textViewXm = convertView.findViewById(R.id.textViewXm);
            holder.textViewFXm = convertView.findViewById(R.id.textViewFXm);
            holder.textViewError = convertView.findViewById(R.id.textViewError);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.textViewN.setText(bisection.getN());
        holder.textViewXi.setText(bisection.getXi());
        holder.textViewXs.setText(bisection.getXs());
        holder.textViewXm.setText(bisection.getXm());
        holder.textViewFXm.setText(bisection.getFXm());
        holder.textViewError.setText(bisection.getError());


        return convertView;
    }
}
