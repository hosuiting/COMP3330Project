package hk.hku.cs.comp3330project;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import java.io.File;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseVideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseVideosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "btnValue";

    // TODO: Rename and change types of parameters
    private int btnValue;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
//    private ImageCapture imageCapture;
    private VideoCapture videoCapture;
    private ProcessCameraProvider cameraProvider;
    private TextView testField;
    public ExerciseVideosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ExerciseVideosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseVideosFragment newInstance(int param1) {
        ExerciseVideosFragment fragment = new ExerciseVideosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            btnValue = getArguments().getInt(ARG_PARAM1);
//            testField.setText(Integer.toString(mParam1));
            Toast.makeText(getActivity(), Integer.toString(mParam1), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
        }

//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button event
//                if (videoCapture != null) {
//                    videoCapture.stopRecording();
//                    cameraProvider.unbindAll();
//                    Navigation.findNavController(getView()).navigate(R.id.action_exerciseVideosFragment_to_exerciseListFragment);
//                }
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_exercise_videos, container, false);

        previewView = view.findViewById(R.id.preview_view);
        view.findViewById(R.id.stopBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "s", Toast.LENGTH_SHORT).show();
                videoCapture.stopRecording();
            }
        });
//        previewView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "f", Toast.LENGTH_SHORT).show();
////                openCamera();
//                recordVideo();
//            }
//        });
//        openCamera();
        cameraProviderFuture =
                ProcessCameraProvider.getInstance(getActivity());
        cameraProviderFuture.addListener(() -> {
            try {
                // Camera provider is now guaranteed to be available
                cameraProvider = cameraProviderFuture.get();

                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();

                // Set up the capture use case to allow users to take photos
//                imageCapture = new ImageCapture.Builder()
//                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                        .build();

//                Recorder recorder = Recorder.Builder()
                videoCapture = new VideoCapture.Builder()
                        .setVideoFrameRate(30)
                        .build();

//
//                mediaStoreOutput = MediaStoreOutputOptions.Builder(this.contentResolver,
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
//                        .setContentValues(contentValues)
//                        .build()

                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();

                // Attach use cases to the camera with the same lifecycle owner
                Camera camera = cameraProvider.bindToLifecycle(
                        ((LifecycleOwner) getActivity()),
                        cameraSelector,
                        preview,
                        videoCapture);

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());
            } catch (InterruptedException | ExecutionException e) {
                // Currently no exceptions thrown. cameraProviderFuture.get()
                // shouldn't block since the listener is being called, so no need to
                // handle InterruptedException.
            }
        }, ContextCompat.getMainExecutor(getActivity()));

//        View c = view.findViewById(R.id.listContainer);
//        container.removeAllViewsInLayout();
//        container.addView(videoBox);
        view.findViewById(R.id.dynamicAdd).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
//                LinearLayout container = getView().findViewById(R.id.listContainer);
//                final View videoBox = LayoutInflater.from(getActivity()).inflate(R.layout.video_box, container, false);
//                videoBox.findViewById(R.id.btOtherActivity).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((TextView) videoBox.findViewById(R.id.display)).setText("1");
//                    }
//                });
//                container.addView(videoBox);
                recordVideo();
            }
        });
        testField = view.findViewById(R.id.testField);
        testField.setText(Integer.toString(btnValue));


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("RestrictedApi")
    private void recordVideo() {
        if (videoCapture != null) {
            String name = "Exercise-recording-" +
                    (new SimpleDateFormat("yyyy-MM-ddHH-mm-ss.SSS"))
                            .format(System.currentTimeMillis()) + ".mp4";
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            File movieDir = new File(path, "/Exercises");

            if (!movieDir.exists())
                movieDir.mkdir();

            File videoFile = new File(movieDir, name);
            Toast.makeText(getActivity(), videoFile.toString(), Toast.LENGTH_SHORT).show();
            videoCapture.startRecording(
                    new VideoCapture.OutputFileOptions.Builder(videoFile).build(),
                    getActivity().getMainExecutor(),
                    new VideoCapture.OnVideoSavedCallback() {
                        @Override
                        public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                            Toast.makeText(getActivity(), "Video saved", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                            Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoCapture != null) {
            videoCapture.stopRecording();
            cameraProvider.unbindAll();
        }
    }
}