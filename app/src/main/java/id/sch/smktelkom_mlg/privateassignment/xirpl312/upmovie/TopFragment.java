package id.sch.smktelkom_mlg.privateassignment.xirpl312.upmovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl312.upmovie.adapter.MyTop;
import id.sch.smktelkom_mlg.privateassignment.xirpl312.upmovie.model.ResultRespons;
import id.sch.smktelkom_mlg.privateassignment.xirpl312.upmovie.model.Results;
import id.sch.smktelkom_mlg.privateassignment.xirpl312.upmovie.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl312.upmovie.service.VolleySingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {


    ArrayList<Results> mlist = new ArrayList<>();
    MyTop myTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_top);
        rv.setHasFixedSize(true);
        myTop = new MyTop(this, mlist, getContext());
        rv.setAdapter(myTop);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        downloadDataResource();

        return rootView;
    }

    private void downloadDataResource() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=cc75981bfea7ffbfc93767470c7c0f28&language=en-US&page=1";

        GsonGetRequest<ResultRespons> myRequest = new GsonGetRequest<ResultRespons>
                (url, ResultRespons.class, null, new Response.Listener<ResultRespons>() {

                    @Override
                    public void onResponse(ResultRespons response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        //fillColor(response.results);
                        mlist.addAll(response.results);
                        myTop.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(myRequest);
    }

    //private void fillColor(List<Results> results) {
    //    for (int i = 0; i < results.size(); i++)
    //        results.get(i).color = ColorUtil.getRandomColor();
    //}

}
