package tst.tertj.denimstore.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;
import tst.tertj.denimstore.R;
import tst.tertj.denimstore.adapters.ImageSliderAdapter;
import tst.tertj.denimstore.POJO.Offer;
import tst.tertj.denimstore.manager.DataManager;

public class SelectedOfferFragment extends Fragment implements View.OnClickListener {

    private ImageSliderAdapter imageSliderAdapter;
    private ViewPager slider;
    private CircleIndicator slider_indicator;
    private TextView tvCurrentOfferName, tvCurrentOfferCountry_of_Origin, tvCurrentOfferPrice,
            tvCurrentOfferDescription;
    private Button btnToMakeAnOrder;
    private IOnMyOfferClickListener myOfferClickListener;
    private Offer offer;
    private ScrollView svOffer;
    private int howMuchPicturesOnPosition = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myOfferClickListener = (IOnMyOfferClickListener) activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_offer, container, false);
        offer = DataManager.getInstance().choosen_offer;
        howMuchPicturesOnPosition = offer.images.size();
        svOffer = (ScrollView) view.findViewById(R.id.svOffer);

        btnToMakeAnOrder = (Button) view.findViewById(R.id.btnToMakeAnOrder);
        btnToMakeAnOrder.setOnClickListener(this);

        tvCurrentOfferName = (TextView) view.findViewById(R.id.tvCurrentOfferName);
        tvCurrentOfferCountry_of_Origin = (TextView) view.findViewById(R.id.tvCurrentOfferCountry_of_Origin);
        tvCurrentOfferPrice = (TextView) view.findViewById(R.id.tvCurrentOfferPrice);
        tvCurrentOfferDescription = (TextView) view.findViewById(R.id.tvCurrentOfferDescription);

        tvCurrentOfferName.setText(offer.name);
        tvCurrentOfferCountry_of_Origin.setText(getText(R.string.tv_country_of_origin) +
                offer.country_of_origin);
        tvCurrentOfferPrice.setText(offer.price + " " + offer.currencyId);
        tvCurrentOfferDescription.setText(Html.fromHtml(offer.description));
        slider = (ViewPager) view.findViewById(R.id.vp_slider);
        slider_indicator = (CircleIndicator) view.findViewById(R.id.vp_circleindicator);
        imageSliderAdapter = new ImageSliderAdapter(getContext(), offer);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        slider.setAdapter(imageSliderAdapter);
        slider_indicator.setViewPager(slider);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToMakeAnOrder:
                DataManager.getInstance().choosen_offer = offer;
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
