package tst.tertj.denimstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

import tst.tertj.denimstore.POJO.Offer;
import tst.tertj.denimstore.constants.Const;
import tst.tertj.denimstore.custom.OrderingTypeDialog;
import tst.tertj.denimstore.fragments.CatalogFragment;
import tst.tertj.denimstore.fragments.CategoriesFragment;
import tst.tertj.denimstore.fragments.SelectedOfferFragment;
import tst.tertj.denimstore.interfaces.GetProdactDatabase;
import tst.tertj.denimstore.manager.DataManager;
import tst.tertj.denimstore.network.DownloadXML;
import tst.tertj.denimstore.parser.AbvParser;


public class MainActivity extends AppCompatActivity implements GetProdactDatabase,
        CategoriesFragment.IOnMyCatalogClickListener, CatalogFragment.IOnMyOfferListClickListener,
        SelectedOfferFragment.IOnMyOfferClickListener, OrderingTypeDialog.ChoosenOrderingType {


    SelectedOfferFragment selectedOfferFragment;
    private FragmentTransaction fTrans;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataManager = DataManager.getInstance();
        new DownloadXML(this).execute();

    }


    @Override
    public void downloadXMLisDone(String string_XML) {
        dataManager.xml_base = string_XML;
        AbvParser parser = new AbvParser();
        LinkedList<Offer> offers = new LinkedList<>();
        if (dataManager.xml_base != null) {
            offers = parser.parse();
            parseOffersByCategories(offers);

        }
    }

    private void parseOffersByCategories(LinkedList<Offer> offers) {
        int size = offers.size();
        for (int i = 0; i < size; i++) {
            Offer off = offers.get(i);
            if (off.categoryId.equals(Const.MEN_CATEGORY)) {
                dataManager.menCatalog.add(off);
            } else if (off.categoryId.equals(Const.WOMEN_CATEGORY)) {
                dataManager.womenCatalog.add(off);
            } else if (off.categoryId.equals(Const.SHORTS_CATEGORY)) {
                dataManager.shortsCatalog.add(off);
            }
        }
        startCategoriesFragment();
    }

    private void startCategoriesFragment() {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, categoriesFragment);
        fTrans.addToBackStack("CategoriesFragment");
        fTrans.commit();
    }


    @Override
    public void onOfferClick(Offer selectedOffer) {
        dataManager.choosen_offer = selectedOffer;
        selectedOfferFragment = new SelectedOfferFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, selectedOfferFragment);
        fTrans.addToBackStack("SelectedOfferFragment");
        fTrans.commit();
    }

    @Override
    public void onOrderButtonClick() {

        OrderingTypeDialog orderingTypeDialog = new OrderingTypeDialog(this);
        orderingTypeDialog.show();

    }

    @Override
    public void orderBySMS() {
        String sms_text = "Здравствуйте. Меня интересует товар: " +
                dataManager.choosen_offer.name + ".";
        Uri uri = Uri.parse("smsto:" + Const.PHONE_NUMBER);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, uri);
        sms_intent.putExtra("sms_body", sms_text);
        startActivity(Intent.createChooser(sms_intent, "Send message"));
    }

    @Override
    public void orderByCall() {
        Uri uri = Uri.parse("tel:" + Const.PHONE_NUMBER);
        Intent call_intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(call_intent);
    }


    @Override
    public void onMenCatalogClick() {
        showCatalogFragment(dataManager.menCatalog);
    }

    @Override
    public void onWomenCatalogClick() {
        showCatalogFragment(dataManager.womenCatalog);
    }

    @Override
    public void onShortsCatalogClick() {
        showCatalogFragment(dataManager.shortsCatalog);
    }

    private void showCatalogFragment(LinkedList<Offer> catalog) {
        CatalogFragment catalog_fragment = new CatalogFragment(catalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalog_fragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }
}