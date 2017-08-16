package tst.tertj.denimstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;

import tst.tertj.denimstore.custom.OrderingTypeDialog;
import tst.tertj.denimstore.data.Const;
import tst.tertj.denimstore.data.Offer;
import tst.tertj.denimstore.data.XMLPullParserHandler;
import tst.tertj.denimstore.fragments.CatalogFragment;
import tst.tertj.denimstore.fragments.CategoriesFragment;
import tst.tertj.denimstore.fragments.SelectedOfferFragment;
import tst.tertj.denimstore.interfaces.GetProdactDatabase;
import tst.tertj.denimstore.manager.DataManager;
import tst.tertj.denimstore.network.DownloadXML;


public class MainActivity extends AppCompatActivity implements GetProdactDatabase,
        CategoriesFragment.IOnMyCatalogClickListener, CatalogFragment.IOnMyOfferListClickListener,
        SelectedOfferFragment.IOnMyOfferClickListener, OrderingTypeDialog.ChoosenOrderingType {


    SelectedOfferFragment selectedOfferFragment;
    CategoriesFragment categoriesFragment;
    CatalogFragment catalogFragment;
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
        Log.d(Const.TAG, "MA string_XML = " + string_XML);
        dataManager.xml_base = string_XML;
        XMLPullParserHandler parser = new XMLPullParserHandler();
        if(dataManager.xml_base != null) {
            dataManager.offersList = parser.parse();
            categoriesFragment = new CategoriesFragment();
            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.replace(R.id.flFragmentContainer, categoriesFragment);
            fTrans.addToBackStack("CategoriesFragment");
            fTrans.commit();
        }
    }

    @Override
    public void onMenCatalogClick() {
        dataManager.menCatalog.clear();
        int size = dataManager.offersList.size();
        for (int i = 0; i < size; i++) {
            Offer off = dataManager.offersList.get(i);
            if (off.getCategoryId().equals(Const.MEN_CATEGORY))
                dataManager.menCatalog.add(off);
        }
        Collections.reverse(dataManager.menCatalog);
        catalogFragment = CatalogFragment.newInstance(dataManager.menCatalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onWomenCatalogClick() {
        dataManager.womenCatalog.clear();
        int size = dataManager.offersList.size();
        for (int i = 0; i < size; i++) {
            Offer off = dataManager.offersList.get(i);
            if (off.getCategoryId().equals(Const.WOMEN_CATEGORY))
                dataManager.womenCatalog.add(off);
        }
        Collections.reverse(dataManager.womenCatalog);
        catalogFragment = CatalogFragment.newInstance(dataManager.womenCatalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onShortsCatalogClick() {
        dataManager.shortsCatalog.clear();
        int size = DataManager.getInstance().offersList.size();
        for (int i = 0; i < size; i++) {
            Offer off = dataManager.offersList.get(i);
            if (off.getCategoryId().equals(Const.SHORTS_CATEGORY))
                dataManager.shortsCatalog.add(off);
        }
        Collections.reverse(dataManager.shortsCatalog);
        catalogFragment = CatalogFragment.newInstance(dataManager.shortsCatalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onCatalogClick() {
        catalogFragment = CatalogFragment.newInstance(dataManager.offersList);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onOfferClick(Offer selectedOffer) {
        LinkedList<Offer> currentOfferList = new LinkedList<Offer>();
        currentOfferList.add(selectedOffer);
        selectedOfferFragment = SelectedOfferFragment.newInstance(getApplicationContext(), currentOfferList);
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
                dataManager.choosen_offer.getName() + ".";
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




}
