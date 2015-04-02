package com.norriors.java.mtbfreeride.Controllers;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norriors.java.mtbfreeride.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilUsuariFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilUsuariFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUsuariFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUsuariFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilUsuariFragment newInstance(String param1, String param2) {
        PerfilUsuariFragment fragment = new PerfilUsuariFragment();

        return fragment;
    }

    public PerfilUsuariFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewPerfil =  inflater.inflate(R.layout.fragment_perfil_usuari, container, false);






        // Inflate the layout for this fragment
        return viewPerfil;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        public void onFragmentInteraction(Uri uri);
    }

}
