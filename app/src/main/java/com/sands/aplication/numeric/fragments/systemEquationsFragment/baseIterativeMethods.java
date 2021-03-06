package com.sands.aplication.numeric.fragments.systemEquationsFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.sands.aplication.numeric.fragments.MainActivityTable;
import com.sands.aplication.numeric.fragments.tableview.TableViewModel;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sacrew on 27/05/18.
 */

public abstract class baseIterativeMethods extends baseSystemEquations {
    List<List<String>> totalInformation = new LinkedList<>();
    List<String> lisTitles = new LinkedList<>();
    boolean calc = false;

    double[] minus(double[] x, double[] y) {
        double[] aux = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            aux[i] = x[i] - y[i];
        }
        return aux;
    }

    double rule(double[] values) {
        double[] aux = new double[values.length];
        for (int i = 0; i < values.length; i++)
            aux[i] = Math.abs(values[i]);
        Arrays.sort(aux);
        return aux[values.length - 1];
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void executeChart(Context context) {
        if (calc) {
            Intent i = new Intent(context, MainActivityTable.class);
            startActivity(i);
            TableViewModel.getTitles(lisTitles);
            TableViewModel.getCeldas(totalInformation);
        }
    }

    String cientificTransformation(double val) {
        Locale.setDefault(Locale.US);
        DecimalFormat num = new DecimalFormat("#.##E0");
        return num.format(val);
    }

}
