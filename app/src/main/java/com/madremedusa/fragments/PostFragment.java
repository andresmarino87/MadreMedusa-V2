package com.madremedusa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.madremedusa.R;
import com.madremedusa.adapters.MagazineViewAdapter;
import com.madremedusa.adapters.PostViewAdapter;
import com.madremedusa.items.ItemMagazine;
import com.madremedusa.items.ItemPost;
import com.madremedusa.utils.AppConstant;
import com.madremedusa.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView postsView;
    private ProgressBar progressLayout;
    private PostViewAdapter postAdapter;
    private LinearLayoutManager linearLayoutManagerVertical;
    private ArrayList<ItemPost> posts;
    static private String tokenForMore="-1";
    static private boolean isLoading=false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance() {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        posts = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        postsView = (RecyclerView)rootView.findViewById(R.id.posts);
        progressLayout = (ProgressBar)rootView.findViewById(R.id.progressLayout);
        linearLayoutManagerVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        postsView.setLayoutManager(linearLayoutManagerVertical);
        postAdapter = new PostViewAdapter(getContext(), posts);
        postsView.setAdapter(postAdapter);
        postsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManagerVertical.getChildCount();
                    totalItemCount = linearLayoutManagerVertical.getItemCount();
                    pastVisiblesItems = linearLayoutManagerVertical.findFirstVisibleItemPosition();

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoading = true;
                            Log.i(getContext().getPackageName(), "Last Item Wow !");
                            new JSONGetPosts().execute(AppConstant.PostURL + "&pageToken=" + tokenForMore);

                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }

            //           private void isScrollCompleted() {
            //             int lastItem = this.currentFirstVisibleItem + this.currentVisibleItemCount;
            //           if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE && lastItem == this.currentTotalItemCount) {
            //             if (!isLoading) {
            //               isLoading = true;
            //             spinnerProg.setVisibility(LinearLayout.VISIBLE);
            //           new JSONGetPosts().execute(AppConstant.PostURL + "&pageToken=" + tokenForMore);
            //         adapter.notifyDataSetChanged();
            //   }
//                }
            //          }

        });
        new JSONGetPosts().execute(AppConstant.PostURL);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class JSONGetPosts extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONObject result=null;
            String url=urls[0];
            if(Utils.checkConn(getContext())) {
                try {
                    result=Utils.readJsonFromUrl(url);
                    result.put("isConnectionValid", true);
                    tokenForMore=result.getString("nextPageToken");
                } catch (JSONException e) {
                    Log.e(getActivity().getPackageName(),"Error doInBackground JSONException "+e);
                }
            }else {
                try {
                    result = new JSONObject();
                    result.put("isConnectionValid", false);
                }catch(JSONException e){
                    Log.e(getActivity().getPackageName(), "Error doInBackground JSONException " + e);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            boolean hasConnection=false;
            try{
                hasConnection = result.getBoolean("isConnectionValid");
            }catch(JSONException e){}

            if(hasConnection) {
                String title;
                String author;
                String thumbnailUrl;
                String content;
                JSONObject item,aux;
                JSONArray array;
                try {
                    array = result.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        item = array.getJSONObject(i);
                        title = item.getString("title");
                        content = item.getString("content");
                        aux = item.getJSONObject("author");
                        author = aux.getString("displayName");
                        aux = aux.getJSONObject("image");
                        thumbnailUrl = aux.getString("url");
                        posts.add(new ItemPost(title, author, thumbnailUrl, content));
                    }
                }catch(JSONException e){
                    Log.e(getActivity().getPackageName(),"Error doInBackground JSONException "+e);
                }

                isLoading=false;
                progressLayout.setVisibility(LinearLayout.GONE);
                postsView.setVisibility(LinearLayout.VISIBLE);
//                spinnerProg.setVisibility(LinearLayout.GONE);
                postAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            }
        }
    }

}
