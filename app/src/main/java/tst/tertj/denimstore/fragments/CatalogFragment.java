package tst.tertj.denimstore.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;

import tst.tertj.denimstore.R;
import tst.tertj.denimstore.adapters.ProductsAdapter;
import tst.tertj.denimstore.data.Offer;

public class CatalogFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ProductsAdapter productsAdapter;
    private ListView lvOffers;
    private LinkedList<Offer> offersList = new LinkedList<Offer>();
    private IOnMyOfferListClickListener myOfferListClickListener;

    public static CatalogFragment newInstance(LinkedList<Offer> offersLinkedList) {
        CatalogFragment catalogFragment = new CatalogFragment();
        Bundle args = new Bundle();
        for (int i = 0; i < offersLinkedList.size(); i++) {
            args.putParcelable("Offer " + i, offersLinkedList.get(i));
        }
        catalogFragment.setArguments(args);
        return catalogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myOfferListClickListener = (IOnMyOfferListClickListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        lvOffers = (ListView) view.findViewById(R.id.lvOffers);
        lvOffers.setOnItemClickListener(this);

        if (getArguments() != null) {
            for (int i = 0; i < getArguments().size(); i++) {
                Offer offer = getArguments().getParcelable("Offer " + i);
                if (offer != null) {
                    offersList.add(offer);
                }
            }

        }
        productsAdapter = new ProductsAdapter(getActivity(), offersList);
        lvOffers.setAdapter(productsAdapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Offer offer = (Offer) parent.getItemAtPosition(position);
        myOfferListClickListener.onOfferClick(offer);
    }
    public interface IOnMyOfferListClickListener {
        void onOfferClick(Offer selectedOffer);
    }
}
