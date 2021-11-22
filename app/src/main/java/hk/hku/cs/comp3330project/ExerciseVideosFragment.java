package hk.hku.cs.comp3330project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseVideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseVideosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExerciseVideosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseVideosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseVideosFragment newInstance(String param1, String param2) {
        ExerciseVideosFragment fragment = new ExerciseVideosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_videos, container, false);

//        View c = view.findViewById(R.id.listContainer);
//        container.removeAllViewsInLayout();
//        container.addView(videoBox);
        view.findViewById(R.id.dynamicAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout container = getView().findViewById(R.id.listContainer);
                final View videoBox = LayoutInflater.from(getActivity()).inflate(R.layout.video_box, container, false);
                videoBox.findViewById(R.id.btOtherActivity).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TextView) videoBox.findViewById(R.id.display)).setText("1");
                    }
                });
                container.addView(videoBox);
            }
        });
        return view;
    }
}