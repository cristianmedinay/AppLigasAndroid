package com.example.practicafinal.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.practicafinal.R;
import com.example.practicafinal.utils.Equipo;

public class DialogoRedes  extends DialogFragment {

    private TextView txt1,txt2,txt3,txt4;
    private Button botonAdd;
    private View v;
    Equipo recuperado;


    public static DialogoRedes newInstance(Equipo texto) {
        Bundle args = new Bundle();
        args.putSerializable("key3",texto);
        DialogoRedes fragment = new DialogoRedes();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recuperado = (Equipo) this.getArguments().getSerializable("key3");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        v = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialogo_redes, null);
        txt1 = v.findViewById(R.id.texto_face);
        txt2 = v.findViewById(R.id.texto_twit);
        txt3 = v.findViewById(R.id.texto_insta);
        txt4 = v.findViewById(R.id.texto_web);

        builder.setView(v);

        return builder.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txt1.setText(recuperado.getFace());
        txt2.setText(recuperado.getTwit());
        txt3.setText(recuperado.getInsta());
        txt4.setText(recuperado.getWeb());

    }


}
