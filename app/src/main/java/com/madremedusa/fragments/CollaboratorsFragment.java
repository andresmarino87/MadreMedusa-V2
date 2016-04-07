package com.madremedusa.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madremedusa.R;
import com.madremedusa.adapters.CollaboratorAdapter;
import com.madremedusa.adapters.FullScreenAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollaboratorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CollaboratorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollaboratorsFragment extends Fragment {
    private RecyclerView colaboradores;
    private CollaboratorAdapter collaboratorAdapter;
    private GridLayoutManager gridLayoutManagerHorizontal;

    private OnFragmentInteractionListener mListener;

    public CollaboratorsFragment() {
        // Required empty public constructor
    }

    public static CollaboratorsFragment newInstance() {
        CollaboratorsFragment fragment = new CollaboratorsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_collaborators, container,false);

        colaboradores = (RecyclerView)rootView.findViewById(R.id.colaboradores);
        gridLayoutManagerHorizontal = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        colaboradores.setLayoutManager(gridLayoutManagerHorizontal);
        colaboradores.setHasFixedSize(true);
        collaboratorAdapter = new CollaboratorAdapter(getActivity());
        colaboradores.setAdapter(collaboratorAdapter);



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
//        if (context instanceof OnFragmentInteractionListener) {
  //          mListener = (OnFragmentInteractionListener) context;
   //     } else {
     //       throw new RuntimeException(context.toString()
       //             + " must implement OnFragmentInteractionListener");
        //}
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
