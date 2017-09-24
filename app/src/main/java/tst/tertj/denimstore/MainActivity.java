package tst.tertj.denimstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Collections;
import java.util.LinkedList;

import tst.tertj.denimstore.POJO.Category;
import tst.tertj.denimstore.POJO.Offer;
import tst.tertj.denimstore.constants.Const;
import tst.tertj.denimstore.custom.OrderingTypeDialog;
import tst.tertj.denimstore.fragments.CatalogFragment;
import tst.tertj.denimstore.fragments.CategoriesFragment;
import tst.tertj.denimstore.fragments.SelectedOfferFragment;
import tst.tertj.denimstore.interfaces.GetProdactDatabase;
import tst.tertj.denimstore.manager.DataManager;
import tst.tertj.denimstore.network.DownloadABVXML;
import tst.tertj.denimstore.network.DownloadTosXml;
import tst.tertj.denimstore.parser.AbvParser;
import tst.tertj.denimstore.parser.TosParser;


public class MainActivity extends AppCompatActivity implements GetProdactDatabase,
        CategoriesFragment.IOnMyCatalogClickListener, CatalogFragment.IOnMyOfferListClickListener,
        SelectedOfferFragment.IOnMyOfferClickListener, OrderingTypeDialog.ChoosenOrderingType {

    public final String LOG_TAG = getClass().getSimpleName();

    SelectedOfferFragment selectedOfferFragment;
    private FragmentTransaction fTrans;
    private DataManager dataManager;
    private ProgressDialog progressSpinner;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataManager = DataManager.getInstance();
        showSpinner();
        if (!dataManager.is_base_download) {
            new DownloadABVXML(this).execute();
            new DownloadTosXml(this).execute();
//        new DownloadBalaniXML(this).execute();
        }
    }

    public void showSpinner() {
        progressSpinner = new ProgressDialog(this);
        progressSpinner.show();
        if (progressSpinner.getWindow() != null) {
            progressSpinner.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressSpinner.setContentView(R.layout.progress_dialog);
        progressSpinner.setIndeterminate(true);
        progressSpinner.setCancelable(false);
        progressSpinner.setCanceledOnTouchOutside(false);
    }

    public void dismissSpinner() {
        if (progressSpinner != null) {
            progressSpinner.dismiss();
        }
    }

    @Override
    public void download_abv_xml_is_done(String string_XML) {
        dataManager.abv_xml_base = string_XML;
        AbvParser parser = new AbvParser();
        LinkedList<Offer> offers = new LinkedList<>();
        if (dataManager.abv_xml_base != null) {
            if (parser.parse()) {
                checkForDismissProgressDialog();
            }
        }
    }

    private void checkForDismissProgressDialog() {
        counter++;
        if (counter == 2) {
            dismissSpinner();
            startCategoriesFragment();
            dataManager.is_base_download = true;
        }
    }

    @Override
    public void download_tos_xml_is_done(String string_XML) {
        dataManager.tos_xml_base = string_XML;
        Log.d(LOG_TAG, "tos_xml = " + string_XML);
        TosParser parser = new TosParser();
        LinkedList<Offer> offers = new LinkedList<>();
        if (dataManager.tos_xml_base != null) {
            if (parser.parse()) {
                checkForDismissProgressDialog();
            }
        }
    }

    @Override
    public void download_balani_xml(String string_XML) {
//        Log.d(LOG_TAG, "balani_xml = " + string_XML);
//        dataManager.balani_xml_base = string_XML;
//        BalaniParser parser = new BalaniParser();
//        LinkedList<Offer> offers = new LinkedList<>();
//        if (dataManager.balani_xml_base != null) {
//            offers = parser.parse();
//            dataManager.women_dresses = offers;
//        }
//        startCategoriesFragment();
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

    private void showCatalogFragment(LinkedList<Offer> catalog) {
        CatalogFragment catalog_fragment = new CatalogFragment(catalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalog_fragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onCatalogClick(Category category) {
        int _id = category._id;
        switch (_id) {
            case 401:
                showCatalogFragment(dataManager.women_underwear);
                break;
            case 402:
                showCatalogFragment(dataManager.women_blouse_shirt);
                break;
            case 403:
                showCatalogFragment(dataManager.women_trousers);
                break;
            case 404:
                showCatalogFragment(dataManager.women_jeans);
                break;
            case 405:
                showCatalogFragment(dataManager.women_waistcoat);
                break;
            case 406:
                showCatalogFragment(dataManager.women_cardigans);
                break;
            case 407:
                showCatalogFragment(dataManager.women_overalls);
            case 408:
                showCatalogFragment(dataManager.women_swimsuit);
                break;
            case 409:
                showCatalogFragment(dataManager.women_jackets);
                break;
            case 410:
                showCatalogFragment(dataManager.women_leggins);
                break;
            case 411:
                showCatalogFragment(dataManager.women_t_shirt);
                break;
            case 412:
                showCatalogFragment(dataManager.women_dresses);
                break;
            case 413:
                showCatalogFragment(dataManager.women_sweaters);
                break;
            case 414:
                showCatalogFragment(dataManager.women_tunic);
                break;
            case 415:
                showCatalogFragment(dataManager.shortsCatalog);
                break;
            case 416:
                showCatalogFragment(dataManager.women_skirts);
                break;
            case 417:
                showCatalogFragment(dataManager.women_shoes);
                break;
            case 301:
                showCatalogFragment(dataManager.child_shorts);
                break;
            case 302:
                showCatalogFragment(dataManager.child_t_shorts);
                break;
            case 303:
                showCatalogFragment(dataManager.child_sweaters);
                break;
            case 304:
                showCatalogFragment(dataManager.child_skirts);
                break;


            default:
                break;
        }
    }
}
