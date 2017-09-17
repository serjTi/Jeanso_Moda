package tst.tertj.denimstore.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tst.tertj.denimstore.R;

public class CategoriesFragment extends Fragment implements View.OnClickListener {

    Button btnMenCatalog, btnWomenCatalog, btnShortsCatalog, btnCatalog;
    private IOnMyCatalogClickListener catalogClickListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        catalogClickListener = (IOnMyCatalogClickListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        btnMenCatalog = (Button) view.findViewById(R.id.btnMenCatalog);
        btnWomenCatalog = (Button) view.findViewById(R.id.btnWomenCatalog);
        btnShortsCatalog = (Button) view.findViewById(R.id.btnShortsCatalog);
        btnCatalog = (Button) view.findViewById(R.id.btnCatalog);

        btnMenCatalog.setOnClickListener(this);
        btnWomenCatalog.setOnClickListener(this);
        btnShortsCatalog.setOnClickListener(this);
        btnCatalog.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenCatalog:
                catalogClickListener.onMenCatalogClick();
                break;
            case R.id.btnWomenCatalog:
                catalogClickListener.onWomenCatalogClick();
                break;
            case R.id.btnShortsCatalog:
                catalogClickListener.onShortsCatalogClick();
                break;
            default:
                break;
        }
    }
    public interface IOnMyCatalogClickListener {
        void onMenCatalogClick();
        void onWomenCatalogClick();
        void onShortsCatalogClick();
    }

}
