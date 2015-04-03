package com.norriors.java.mtbfreeride.Controllers;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import com.norriors.java.mtbfreeride.R;

import java.io.File;
import java.io.IOException;


public class ValoracionsFragment1 extends android.support.v4.app.Fragment  {


    private OnFragmentInteractionListener mListener;
    private MediaRecorder gravador;
    private String nomGravacio = null;
    private MediaPlayer mPlayer;
    private Button btnPlay;
    private ToggleButton btnGravar;
    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;





    // TODO: Rename and change types and number of parameters
    public static ValoracionsFragment1 newInstance() {
        ValoracionsFragment1 fragment = new ValoracionsFragment1();

        return fragment;
    }

    public ValoracionsFragment1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_valoracions_gravar, container, false);

        nomGravacio = getActivity().getApplicationContext().getApplicationInfo().dataDir + "/test.3gp";
        btnGravar = (ToggleButton) view.findViewById(R.id.btnGravar);
        btnGravar.setText("Start Recording");

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    btnGravar.setText("Stop recording");
                } else {
                    btnGravar.setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        });


        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        btnPlay.setText("Start playing");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    btnPlay.setText("Stop playing");
                } else {
                    btnPlay.setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;

            }
        });





        return view;
    }

    /**
     * Mètode que inicia la gravació d'àudio.
     */
    private void iniciGravacio() {
        gravador = new MediaRecorder();
        gravador.reset();
        gravador.setAudioSource(MediaRecorder.AudioSource.MIC);
        gravador.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        gravador.setOutputFile(nomGravacio);
        gravador.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            gravador.prepare();
        } catch (IOException e) {
            Log.e("Gravacio", "Error en la preparació de la gravació");
        }

        gravador.start();
    }

    /**
     * Mètode que ens para de gravar i ens deixa el gravador a null,
     * a punt per tornar a gravar
     */
    private void pararGravacio() {
        gravador.stop();
        gravador.release();
        gravador = null;
    }

    private void onRecord(boolean start) {
        if (start) {
            iniciGravacio();
        } else {
            pararGravacio();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {

        File path=new File(getActivity().getCacheDir()+"/musicfile.3gp");
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(nomGravacio);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("ERROR", "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
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
