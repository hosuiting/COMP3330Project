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

import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseVideosFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "btnValue";
    private String[] gifNameArray;

    private int btnValue;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
//    private ImageCapture imageCapture;
    private VideoCapture videoCapture;
    private ProcessCameraProvider cameraProvider;
    private boolean btnIsStart = true;
    private Button recordControlBtn;
    private TextView TimerTextView;
    private CountDownTimer timer;
    public ExerciseVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            btnValue = getArguments().getInt(ARG_PARAM1);
            gifNameArray = new String[] {"crab_toe_touch", "good_morning", "high_knees_poses", "inchworm", "leg_pull_in",
                    "lunge_with_twist", "russian_twist", "side_lunge", "spiderman_exercise", "narrow_push_up"};
//            testField.setText(Integer.toString(mParam1));

//            GifDrawable gif = new GifDrawable()
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
//        view.findViewById(R.id.stopBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "s", Toast.LENGTH_SHORT).show();
//                videoCapture.stopRecording();
//            }
//        });
        recordControlBtn = view.findViewById(R.id.dynamicAdd);
        recordControlBtn.setOnClickListener(new View.OnClickListener() {
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
                if (btnIsStart) {
                    recordVideo();
                    recordControlBtn.setText("Stop Recording");
                    btnIsStart = false;
                    timer = new CountDownTimer(60000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            TimerTextView.setText("Remaining seconds: "+millisUntilFinished/1000);
                        }

                        @Override
                        public void onFinish() {
                            TimerTextView.setText("Time's Up!");
                        }
                    }.start();
                } else {
                    videoCapture.stopRecording();
                    timer.cancel();
                    recordControlBtn.setText("Start Recording");
                    btnIsStart = true;
                }

            }
        });
//        testField = view.findViewById(R.id.testField);
//        testField.setText(Integer.toString(btnValue));
        Toast.makeText(getActivity(), Integer.toString(btnValue), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), Integer.toString(getResources().getIdentifier(gifNameArray[btnValue], "drawable", "hk.hku.cs.comp3330project")), Toast.LENGTH_SHORT).show();
        if ((GifImageView) view.findViewById(R.id.gifImageView) == null) {
            Toast.makeText(getActivity(), "Cannot find gif view!", Toast.LENGTH_SHORT).show();
        } else {
            ((GifImageView) view.findViewById(R.id.gifImageView)).setImageResource(getResources().getIdentifier(gifNameArray[btnValue], "drawable", "hk.hku.cs.comp3330project"));
        }
        ((TextView) view.findViewById(R.id.textView4)).setText(formatToHeading(gifNameArray[btnValue]));

        TimerTextView = (TextView) view.findViewById(R.id.textView5);
        return view;
    }

    private String formatToHeading(String name) {
        char[] chars = name.toCharArray();
        boolean needCapitalize = true;
        for (int i = 0; i < chars.length; ++i) {
            if(Character.isLetter(chars[i])) {
                if (needCapitalize) {
                    chars[i] = Character.toUpperCase(chars[i]);
                    needCapitalize = false;
                }
            } else {
                needCapitalize = true;
                chars[i] = ' ';
            }
        }
        return String.valueOf(chars);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("RestrictedApi")
    private void recordVideo() {
        if (videoCapture != null) {
            String name = "Exercise-recording-" +
                    (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS"))
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