package com.sands.aplication.numeric.fragments.interpolationFragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sands.aplication.numeric.R;
import com.sands.aplication.numeric.fragments.customPopUps.popUpLinearSpline;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Objects;

import static com.sands.aplication.numeric.fragments.homeFragment.poolColors;


/**
 * A simple {@link Fragment} subclass.
 */
public class linearSpline extends baseSpliners {

    public linearSpline() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linear_spline, container, false);
        Button run = view.findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                calc = false;
                cleanGraph();
                execute();

            }
        });
        Button showEquations = view.findViewById(R.id.showEquations);
        showEquations.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (calc) {
                    Intent i = new Intent(getContext(), mathExpressions.class);
                    Bundle b = new Bundle();

                    b.putString("key", "$${p(x) = \\begin{cases}" + latexText + "\\end{cases}\\qquad \\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad\\qquad}$$"); //Your id
                    mathExpressions.equations = equations;


                    i.putExtras(b); //Put your id to your next Intent
                    startActivity(i);
                }
            }
        });
        Button runHelp = view.findViewById(R.id.runHelp);
        runHelp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                executeHelp();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void executeHelp() {
        Intent i = new Intent(Objects.requireNonNull(getContext()).getApplicationContext(), popUpLinearSpline.class);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void execute() {
        boolean errorValues = bootStrap();

        if (errorValues) {
            createInequality();
            boolean checkError = linealSpline();
            if (checkError) {
                calc = true;
                updateGraph();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean linealSpline() {
        latexText = "";
        equations = new LinkedList<>();
        StringWriter stw;
        ExprEvaluator util = new ExprEvaluator();
        EvalEngine engine = new EvalEngine(false);
        TeXUtilities texUtil = new TeXUtilities(engine, false);
        int color = poolColors.remove(0);
        poolColors.add(color);
        for (int i = 0; i < inequality.length; i++) {
            Pair<Pair<Double, Double>, Pair<Double, Double>> aux = inequality[i];
            double check = (aux.second.first - aux.first.first);
            if (check == 0) {
                styleWrongMessage();
                return false;
            }
            double numerator = (aux.second.second - aux.first.second);


            IExpr functionSimplfied = util.evaluate(F.ExpandAll(util.evaluate(aux.second.second + "+(" + numerator + "/" + "(" + check + "))*(x-(" + aux.second.first + "))")));
            if (Build.VERSION.SDK_INT > 19) {
                functionSimplfied = util.evaluate(F.FullSimplify(functionSimplfied));
            }

            equations.add(new Pair<>(functionSimplfied.toString(), new Pair<>(color, new Pair<>(aux.first.first, aux.second.first))));

            stw = new StringWriter();
            texUtil.toTeX(functionSimplfied, stw);
            latexText += stw.toString() + " & " + aux.first.first + " \\lt x \\leq " + aux.second.first;
            if (i < inequality.length - 1)
                latexText += "\\\\";

        }
        return true;
    }
}
