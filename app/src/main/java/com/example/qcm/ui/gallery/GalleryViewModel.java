package com.example.qcm.ui.gallery;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Veuillez entrer ci-dessous les options du QCM");
    }

    public LiveData<String> getText() {
        return mText;
    }
}