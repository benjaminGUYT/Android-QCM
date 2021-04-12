package com.example.qcm.ui.iot;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class IoTFragment  extends Fragment implements  ZBarScannerView.ResultHandler{

    private static final String TAG = "OLA";
    private String token;
    private IoTViewModel iotViewModel;
    private ZBarScannerView mScannerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mScannerView = new ZBarScannerView(this.getContext());
        List<BarcodeFormat> format = new ArrayList<>();
        format.add(BarcodeFormat.QRCODE);
        mScannerView.setFormats(format);

        return mScannerView;

    }



    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this::handleResult);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }



    @Override
    public void handleResult(Result rawResult) {

        String qrcode = rawResult.getContents();
        Log.d(TAG, "QRcode " + qrcode);

        remoteSign(token, qrcode);
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] grantResults) {
        if (requestCode == 99 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission autorisée ...
        }

    }

    private boolean checkCameraHardware(Context ctx) {

        if (ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Cet appareil possède une caméra

            return true;
        } else { // Cet appareil ne possède pas une caméra

            return false;
        }
    }

    public void remoteSign(String token, String qrcode) {

        String urlString =
                "http://192.168.0.11/iot-server/api/qrcode-sign.php?key=iot1235&token=" + token + "&qrcode=" + qrcode; // URL à changer sinon ça  plante
        Ion.with(this)
                .load(urlString)
                .asString().withResponse()
                .setCallback((e, response) -> {
                    if (response.getHeaders().code() == 200) {  }
                });
    }
}
