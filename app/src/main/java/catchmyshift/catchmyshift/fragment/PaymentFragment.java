package catchmyshift.catchmyshift.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.MetodoPagoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {


    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.button_nuevometodo)
    public void NewPayment(){
        Intent intent = new Intent().setClass(getContext(), MetodoPagoActivity.class);
        startActivity(intent);
    }


}
