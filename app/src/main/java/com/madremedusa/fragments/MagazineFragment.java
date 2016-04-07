package com.madremedusa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.madremedusa.items.ItemMagazine;
import com.madremedusa.utils.AppConstant;
import com.madremedusa.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MagazineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MagazineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MagazineFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView magazineView;
    private ProgressBar progressLayout;
    private MagazineViewAdapter magazineAdapter;
    private LinearLayoutManager linearLayoutManagerHorizontal;
    private ArrayList <ItemMagazine> magazines;

    public MagazineFragment() {
        // Required empty public constructor
    }

    public static MagazineFragment newInstance() {
        MagazineFragment fragment = new MagazineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        magazines = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_magazine, container, false);
        magazineView = (RecyclerView)rootView.findViewById(R.id.magazineView);
        progressLayout = (ProgressBar)rootView.findViewById(R.id.progressLayout);
        linearLayoutManagerHorizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        magazineView.setLayoutManager(linearLayoutManagerHorizontal);
        magazineAdapter = new MagazineViewAdapter(getContext(), magazines);
        magazineView.setAdapter(magazineAdapter);
        new JSONGetMagazines().execute(AppConstant.MagazineURLPage);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class JSONGetMagazines extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
                progressLayout.setVisibility(LinearLayout.VISIBLE);
                magazineView.setVisibility(LinearLayout.GONE);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONObject result = null;
            String url = urls[0];
            if(Utils.checkConn(getContext())) {
                try {
                    result = Utils.readJsonFromUrl(url);
                    result.put("isConnectionValid", true);
                } catch (JSONException e) {
                    Log.e(getContext().getPackageName(), "Error doInBackground JSONException " + e);
                }
            }else {
                try {
                    result = new JSONObject();
                    result.put("isConnectionValid", false);
                }catch(JSONException e){
                    Log.e(getContext().getPackageName(), "Error doInBackground JSONException " + e);
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
                JSONObject content, magazine;
                JSONArray array, auxCovers;
                ArrayList<String> auxImages;
                try {
                    content = new JSONObject(result.getString("content").replaceAll("\n", ""));
                    array = content.getJSONArray("magazines");
                    for (int i = 0; i < array.length(); i++) {
                        magazine = array.getJSONObject(i);
                        auxCovers = magazine.getJSONArray("imagesCovers");
                        auxImages = new ArrayList<>();
                        for (int j = 0; j < auxCovers.length(); j++) {
                            auxImages.add(auxCovers.getJSONObject(j).getString("image"));
                        }
                        magazines.add(new ItemMagazine(magazine.getString("magazineIssue"), auxImages));
                    }
                } catch (JSONException e) {
                    Log.e(getContext().getPackageName(), "Error doInBackground JSONException " + e);
                }
                magazineAdapter.notifyDataSetChanged();
                progressLayout.setVisibility(LinearLayout.GONE);
                magazineView.setVisibility(LinearLayout.VISIBLE);

            }else{
                Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            }
        }
    }
}
