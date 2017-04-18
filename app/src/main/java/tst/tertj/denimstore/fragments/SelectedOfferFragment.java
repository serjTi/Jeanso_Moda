package tst.tertj.denimstore.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;

import java.util.LinkedList;

import tst.tertj.denimstore.R;
import tst.tertj.denimstore.adapters.ImageFlipperAdapter;
import tst.tertj.denimstore.data.Offer;

public class SelectedOfferFragment extends Fragment implements View.OnClickListener {

    static Context selectedOfferFragmentcontext;
    private ImageFlipperAdapter imageFlipperAdapter;
    private AdapterViewFlipper AVF;
    private TextView tvCurrentOfferName, tvCurrentOfferCountry_of_Origin, tvCurrentOfferPrice,
            tvCurrentOfferDescription;
    private Button btnToMakeAnOrder;
    private IOnMyOfferClickListener myOfferClickListener;
    private Offer currentOffer;
    private ScrollView svOffer;
    private int howMuchPicturesOnPosition = 0;

    public static SelectedOfferFragment newInstance(Context applicationContext, LinkedList<Offer> offer) {
        SelectedOfferFragment selectedOfferFragment = new SelectedOfferFragment();
        selectedOfferFragmentcontext = applicationContext;
        Bundle args = new Bundle();
        for (int i = 0; i < offer.size(); i++) {
            args.putParcelable("Offer " + i, offer.get(i));
        }
        selectedOfferFragment.setArguments(args);
        return selectedOfferFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myOfferClickListener = (IOnMyOfferClickListener) activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_offer, container, false);
        if (getArguments() != null) {
            for (int i = 0; i < getArguments().size(); i++) {
                currentOffer = getArguments().getParcelable("Offer " + i);
            }

        }
        howMuchPicturesOnPosition = currentOffer.getHowMuchPhotos();
        svOffer = (ScrollView) view.findViewById(R.id.svOffer);

        btnToMakeAnOrder = (Button) view.findViewById(R.id.btnToMakeAnOrder);
        btnToMakeAnOrder.setOnClickListener(this);

        tvCurrentOfferName = (TextView) view.findViewById(R.id.tvCurrentOfferName);
        tvCurrentOfferCountry_of_Origin = (TextView) view.findViewById(R.id.tvCurrentOfferCountry_of_Origin);
        tvCurrentOfferPrice = (TextView) view.findViewById(R.id.tvCurrentOfferPrice);
        tvCurrentOfferDescription = (TextView) view.findViewById(R.id.tvCurrentOfferDescription);

        tvCurrentOfferName.setText(currentOffer.getName());
        tvCurrentOfferCountry_of_Origin.setText(currentOffer.getCountry_of_origin());
        tvCurrentOfferPrice.setText(currentOffer.getPrice() + " " + currentOffer.getCurrencyId());
        tvCurrentOfferDescription.setText(Html.fromHtml(currentOffer.getDescription()));
        AVF = (AdapterViewFlipper) view.findViewById(R.id.AVF);
        imageFlipperAdapter = new ImageFlipperAdapter(selectedOfferFragmentcontext, currentOffer);
        AVF.setAdapter(imageFlipperAdapter);
        AVF.setFlipInterval(1500);
        AVF.setAutoStart(true);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToMakeAnOrder:
                myOfferClickListener.onOrderButtonClick();
                break;
            default:
                break;
        }
    }

    public interface IOnMyOfferClickListener {
        void onOrderButtonClick();
    }
}
