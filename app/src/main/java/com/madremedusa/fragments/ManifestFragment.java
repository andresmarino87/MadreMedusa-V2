package com.madremedusa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madremedusa.R;
import com.madremedusa.utils.AppConstant;
import com.madremedusa.utils.DrawableManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManifestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManifestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManifestFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ManifestFragment() {
        // Required empty public constructor
    }

    public static ManifestFragment newInstance() {
        ManifestFragment fragment = new ManifestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manifest, container,false);
        final TextView text_part_1=(TextView)rootView.findViewById(R.id.text_part_1);
        final TextView text_part_2=(TextView)rootView.findViewById(R.id.text_part_2);
        final ImageView image=(ImageView)rootView.findViewById(R.id.image);
        final DrawableManager DM=new DrawableManager(getActivity(), ResourcesCompat.getDrawable(getActivity().getResources(),R.mipmap.ic_launcher,null));
        text_part_1.setText(R.string.manifest_text_1);
        text_part_2.setText(R.string.manifest_text_2);
        DM.DisplayImage(AppConstant.ManifestImageUrl, image);
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

}
