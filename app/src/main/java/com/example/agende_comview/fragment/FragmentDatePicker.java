package com.example.agende_comview.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.agende_comview.activity.MainActivity;

import java.util.Calendar;

public class FragmentDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private MainActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(requireContext(), this, ano, mes, dia);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String dataFormatada = String.valueOf(day) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year);
        mActivity.setDataSelecionada(dataFormatada);
    }
}
