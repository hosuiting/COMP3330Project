package hk.hku.cs.comp3330project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BodyMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BodyMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private GraphView graphView;
    private LineGraphSeries<DataPoint> series;
    private BodyStatSQLiteHelper sqliteHelper;
    private TextView[] orderedTextView;
    private static final String[] preference_keys = {"height", "weight", "bmi", "body_fat", "record_date_time"};
    private static final int[] textViewIds = {R.id.heightTextView, R.id.weightTextView, R.id.bmiTextField, R.id.bodyFatTextField, R.id.recordTimeTextField};

    public BodyMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BodyMainFragment newInstance(String param1, String param2) {
//        BodyMainFragment fragment = new BodyMainFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//
//        No parameter is needed
//        return fragment;
        return new BodyMainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (sqliteHelper == null) {
            sqliteHelper = new BodyStatSQLiteHelper(getActivity());
        }
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sqliteHelper != null) {
            sqliteHelper.close();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_body_main, container, false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_testFragment_to_homeActivity2);
            }
        });

        orderedTextView = new TextView[5];
        for (int i = 0; i < preference_keys.length; ++i) {
            orderedTextView[i] = (TextView)view.findViewById(textViewIds[i]);
            orderedTextView[i].setText(sharedPref.getString(preference_keys[i], "No Data"));
        }

        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                String[] latest = sqliteHelper.getLatestStatistics();
                for (String received: latest) {
                    Toast.makeText(context, received, Toast.LENGTH_SHORT).show();
                }

            }
        });
        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Latest Measurement");
//                dialog.setMessage("Something");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText heightBox = new EditText(context);
                heightBox.setHint("Height (cm)");
                layout.addView(heightBox);

                final EditText weightBox = new EditText(context);
                weightBox.setHint("Weight (kg)");
                layout.addView(weightBox);

                final EditText fatBox = new EditText(context);
                fatBox.setHint("Body Fat (% Fat)");
                layout.addView(fatBox);

                dialog.setView(layout); // Again this is a set method, not add
                dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // positive
                        EditText[] userInputs = {heightBox, weightBox, fatBox};
                        String[] defaultInputs = {"160", "60", "0.6"};
                        for (int i1 = 0; i1 < userInputs.length; ++i1) {
                            String inputValue = userInputs[i1].getText().toString();
                            if (!inputValue.matches("")) {
                                defaultInputs[i1] = inputValue;
                            }
                            Toast.makeText(context, defaultInputs[i1], Toast.LENGTH_SHORT).show();
                        }
                        if (sqliteHelper.addStatistics(defaultInputs) == -1) {
                            Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Succeed", Toast.LENGTH_SHORT).show();
                            String[] latest = sqliteHelper.getLatestStatistics();
                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            for (int i1 = 0; i1 < preference_keys.length; ++i1) {
                                editor.putString(preference_keys[i1], latest[i1]);
                                orderedTextView[i1].setText(latest[i1]);
                            }
                            updateGraph();

                            Toast.makeText(context, "End", Toast.LENGTH_LONG).show();

                            editor.apply();
                        }
                    }
                });
                dialog.setNegativeButton("Cancel   ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.show();
            }
        });
//        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_testFragment_to_fullscreenFragment);
//            }
//        });
        graphView = view.findViewById(R.id.idGraphView);
        updateGraph();
        return view;
    }

    private void updateGraph() {
        ArrayList<Double> testData = new ArrayList<>();
        ArrayList<String> latest5 = sqliteHelper.get5LatestStatistics();
        for(String weightString: latest5) {
            testData.add(Double.parseDouble(weightString));
//            Toast.makeText(getActivity(), weightString, Toast.LENGTH_SHORT).show();

        }
        setGraphWithArrayOfRecords(testData);
    }

    /**
     * Use this method to set the data in the graph.
     *
     * @param seriesData The data to be displayed in the graph.
     */
    private void setGraphWithArrayOfRecords(ArrayList<Double> seriesData) {
//        graphView
        DataPoint[] dataPoints = new DataPoint[seriesData.size()];
        for (int i = 0; i < seriesData.size(); ++i) {
            dataPoints[i] = new DataPoint(i, seriesData.get(i));
        }
        if (series == null) {
            series = new LineGraphSeries<DataPoint>(dataPoints);
        } else {
            series.resetData(dataPoints);
        }
        graphView.setTitle("My Weight History");
        graphView.addSeries(series);

    }
}