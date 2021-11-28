package hk.hku.cs.comp3330project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
//        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.menu);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
////                        startActivity(new Intent(CalendarActivity.this,HomeActivity.class));
//                        break;
//                    case R.id.profile:
////                        startActivity(new Intent(CalendarActivity.this,RegisterActivity.class));
//                        break;
//                    case R.id.chatbot:
////                        startActivity(new Intent(CalendarActivity.this,ChatbotActivity.class));
//                        break;
//                }
//
//                return false;
//            }
//        });
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
        View view = inflater.inflate(R.layout.fragment_body_main, container, false);
//        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_testFragment_to_homeActivity2);
//            }
//        });

        orderedTextView = new TextView[5];
        for (int i = 0; i < preference_keys.length; ++i) {
            orderedTextView[i] = (TextView)view.findViewById(textViewIds[i]);
        }
        updateTextFields();
//        view.findViewById(R.id.button4).setOnClickListener(v -> {
//            ArrayList<String> records = sqliteHelper.getRecordFromDateRange("2021-11-20", "2021-11-20");
//            for (String record : records) {
//                Toast.makeText(getActivity(), record, Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//        view.findViewById(R.id.button2).setOnClickListener(view1 -> {
//            Context context = getActivity();
//            String[] latest = sqliteHelper.getLatestStatistics();
//            for (String received: latest) {
//                Toast.makeText(context, received, Toast.LENGTH_SHORT).show();
//            }
//
//        });
        view.findViewById(R.id.button3).setOnClickListener(view12 -> {
            Context context = getActivity();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Latest Measurement");
//                dialog.setMessage("Something");

            LinearLayout layout = new LinearLayout(context);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(70, 10, 70, 0);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText heightBox = new EditText(context);
            heightBox.setHint("Height (cm)");
            heightBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layout.addView(heightBox, layoutParams);

            final EditText weightBox = new EditText(context);
            weightBox.setHint("Weight (kg)");
            weightBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layout.addView(weightBox, layoutParams);

            final EditText fatBox = new EditText(context);
            fatBox.setHint("Body Fat (% Fat)");
            fatBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layout.addView(fatBox, layoutParams);

            dialog.setView(layout);
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
//                        Toast.makeText(context, defaultInputs[i1], Toast.LENGTH_SHORT).show();
                    }
                    if (sqliteHelper.addStatistics(defaultInputs) == -1) {
                        Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(context, "Succeed", Toast.LENGTH_SHORT).show();
                        updateTextFields();
                        updateGraph(null);

//                        Toast.makeText(context, "End", Toast.LENGTH_LONG).show();
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
        });
//        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_testFragment_to_fullscreenFragment);
//            }
//        });
        graphView = view.findViewById(R.id.idGraphView);
        graphView.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Context context = getActivity();
//                Toast.makeText(context, "Graph clicked", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Graph Settings");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);


                EditText fromDate = editable_date(context, "Date From");
                EditText toDate = editable_date(context, "Date To");

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(70, 10, 70, 0);

                layout.addView(fromDate, layoutParams);
                layout.addView(toDate, layoutParams);
                dialog.setView(layout);
                dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // positive
                        EditText[] userInputs = {fromDate, toDate};
                        String[] defaultInputs = new String[2];
                        for (int i1 = 0; i1 < userInputs.length; ++i1) {
                            String inputValue = userInputs[i1].getText().toString();
                            if (inputValue.matches("")) {
                                Toast.makeText(context, "Please select both dates", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            defaultInputs[i1] = inputValue;
                        }
                        ArrayList<String> records = sqliteHelper.getRecordFromDateRange(defaultInputs[0], defaultInputs[1]);
                        Toast.makeText(context, Integer.toString(records.size())+" records to be shown", Toast.LENGTH_SHORT).show();
                        updateGraph(records);

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.show();
//                showDatePickerDialog(v);
            }
        });
        updateGraph(null);
        return view;
    }

    private EditText editable_date(Context context, String hint) {
        final EditText date = new EditText(context);
        date.setClickable(false);
        date.setFocusable(false);
        date.setFocusableInTouchMode(false);
//                fromDate.setCursorVisible(false);
//                android:clickable="false"
//                android:cursorVisible="false"
//                android:focusable="false"
//                android:focusableInTouchMode="false"

        date.setHint(hint);
        date.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, date);
            }
        });
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePickerDialog(View v, EditText modifyDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formatDate = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
//                tvSelectDate.setText(formatDate);
//                timePicker(formatDate);
//                Toast.makeText(v.getContext(), formatDate, Toast.LENGTH_SHORT).show();
                modifyDate.setText(formatDate);
            }
        }, year, month, dayOfMonth);
//        calendar.add(Calendar.MONTH, 1);
        long now = System.currentTimeMillis();
//        long maxDate = calendar.getTimeInMillis();
//        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.getDatePicker().setMaxDate(now); //After one month from now
        datePickerDialog.show();
    }

    private void updateGraph(ArrayList<String> data) {
        ArrayList<Double> doubleData = new ArrayList<>();
        if (data == null) {
            data = sqliteHelper.get5LatestStatistics();
        }
        for(String weightString: data) {
            doubleData.add(Double.parseDouble(weightString));
//            Toast.makeText(getActivity(), weightString, Toast.LENGTH_SHORT).show();

        }
        setGraphWithArrayOfRecords(doubleData);
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

    private void updateTextFields() {
        String[] latest = sqliteHelper.getLatestStatistics();
        for (int i = 0; i < orderedTextView.length; ++i) {
            orderedTextView[i].setText(latest[i]);
        }
    }

}

