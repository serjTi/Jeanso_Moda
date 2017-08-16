package tst.tertj.denimstore.custom;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import tst.tertj.denimstore.MainActivity;
import tst.tertj.denimstore.R;

/**
 * Created by sergey_tertychenko on 12.08.17.
 */

public class OrderingTypeDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Dialog my_dialog;
    private Activity activity;
    private ChoosenOrderingType choosenOrderingType;
    private ImageView btn_sms, btn_call;

    public OrderingTypeDialog(MainActivity mainActivity) {
        super(mainActivity);
        activity = mainActivity;
        choosenOrderingType = (ChoosenOrderingType) mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ordering_type_dialog_layout);
        btn_sms = (ImageView) findViewById(R.id.btn_sms);
        btn_call = (ImageView) findViewById(R.id.btn_call);

        btn_sms.setOnClickListener(this);
        btn_call.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sms:
                this.hide();
                choosenOrderingType.orderBySMS();
                break;
            case R.id.btn_call:
                this.hide();
                choosenOrderingType.orderByCall();
                break;
            default:
                break;
        }
    }

    public interface ChoosenOrderingType {
        void orderBySMS();

        void orderByCall();
    }
}