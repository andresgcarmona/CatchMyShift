package catchmyshift.catchmyshift;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventsFragment extends Fragment {
     LinearLayout applyjob, futurejobs, jobsdone, paymentdone;

    public MyEventsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_events, container, false);
        applyjob = (LinearLayout)v.findViewById(R.id.id_applyjob);
        futurejobs = (LinearLayout) v.findViewById(R.id.id_futurejob);
        jobsdone = (LinearLayout) v.findViewById(R.id.id_jobsdone);
        paymentdone = (LinearLayout) v.findViewById(R.id.id_paymentdone);

        applyjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMethods.Success(getView(),"Trabajos Aplicados",getContext()).show();
            }
        });

        futurejobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMethods.Success(getView(),"Trabajos Futuros",getContext()).show();
            }
        });

        jobsdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMethods.Success(getView(),"Trabajos Realizados",getContext()).show();
            }
        });

        paymentdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMethods.Success(getView(),"Pagos Realizados",getContext()).show();
            }
        });
        return  v;
    }

}
