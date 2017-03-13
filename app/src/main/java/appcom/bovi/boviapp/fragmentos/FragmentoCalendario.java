package appcom.bovi.boviapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import appcom.bovi.boviapp.R;


public class FragmentoCalendario extends Fragment {

    CalendarView simpleCalendarView;
    long selectedDate;

    public FragmentoCalendario() {
        // Required empty public constructor
    }

    public static FragmentoCalendario newInstance() {
        FragmentoCalendario fragment = new FragmentoCalendario();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_calendario, container, false);

        simpleCalendarView = (CalendarView) view.findViewById(R.id.calendarView); // get the reference of CalendarView
        selectedDate = simpleCalendarView.getDate(); // get selected date in milliseconds

        return view;
    }

}
