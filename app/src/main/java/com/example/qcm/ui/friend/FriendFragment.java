package com.example.qcm.ui.friend;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm.R;
import com.example.qcm.models.FriendsIoT;
import com.example.qcm.repositories.Api;
import com.example.qcm.ui.options.OptionsViewModel;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendFragment extends Fragment {
    private FriendViewModel friendViewModel;

    Retrofit retrofit;
    Api api;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        View root = inflater.inflate(R.layout.friend_fragment, container, false);
        TextView tv =  root.findViewById(R.id.tvJsonFriend);


        String urlString =
                "http://192.168.10.111/iot/api/getFriends.php?key=iot1235&token=26d11c8e68899f2fef671e3331da3a8f";
        Ion.with(this)
                .load(urlString)
                .asString().withResponse()
                .setCallback((e, response) -> {
                    if (response.getHeaders().code() == 200) {
                        System.out.println(response.getResult());
                        tv.setText(response.getResult());
                    }
                });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
