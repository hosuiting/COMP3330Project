package hk.hku.cs.comp3330project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

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
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
//    private ImageCapture imageCapture;
    private VideoCapture videoCapture;

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
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

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
}