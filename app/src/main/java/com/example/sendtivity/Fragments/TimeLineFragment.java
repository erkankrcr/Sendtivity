package com.example.sendtivity.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sendtivity.Adapters.TimeLineAdapter;
import com.example.sendtivity.Class.GetTimeLineClass;
import com.example.sendtivity.Class.Post;
import com.example.sendtivity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TimeLineFragment extends Fragment {

    FirebaseAuth mAuth;
    ArrayList<Post> Result = new ArrayList<>();
    Gson gson;
    RecyclerView recyclerView;
    FirebaseFunctions firebaseFunctions;
    RecyclerView.Adapter adapter;
    String value;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.time_line_fragment,container,false);

        recyclerView = view.findViewById(R.id.TL_recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Deneysel
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        firebaseFunctions = FirebaseFunctions.getInstance();
        mAuth = FirebaseAuth.getInstance();
        gson = new Gson();

         value = Get("https://us-central1-sendtivity.cloudfunctions.net/GetTimeLineWeb?mAuthID="+mAuth.getUid());
         GetTimeLineClass timeLineClass = gson.fromJson(value,GetTimeLineClass.class);
         adapter = new TimeLineAdapter(getActivity(),timeLineClass.Result);

         recyclerView.setAdapter(adapter);





        return view;
    }


    String Get(String url) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            response  = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
