package com.example.qcm.ui.camera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm.R;
import com.example.qcm.ui.camera.CameraViewModel;
import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;

public class CameraFragment extends Fragment implements VlcListener {

    private CameraViewModel camerasViewModel;
    String mrl = "rtsp://admin:tenvis-58@192.168.10.15:554/12";
    ///String mrl = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
    VlcVideoLibrary vlcPlayer;
    SurfaceView previewSV;
    Button bouton;
    Boolean playing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        camerasViewModel =
                new ViewModelProvider(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.layout_fragment_cameras, container, false);

        final TextView textView = root.findViewById(R.id.textGalleryCam);
        camerasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        bouton = (Button) root.findViewById(R.id.buttonCam);
        bouton.setText("LANCER");
        previewSV = (SurfaceView) root.findViewById(R.id.previewSV);
        vlcPlayer = new VlcVideoLibrary(this.getContext(), this, previewSV);

        playing = false;
        bouton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!playing) {
                    playing = true;
                    vlcPlayer.play(mrl);
                    bouton.setText("COUPER");
                }
                else  {
                    playing = false;
                    vlcPlayer.stop();
                    bouton.setText("LANCER");
                }
            }
        });
        return root;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError() {

    }
}