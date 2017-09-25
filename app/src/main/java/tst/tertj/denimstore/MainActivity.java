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

import java.util.LinkedList;

import tst.tertj.denimstore.POJO.Category;
import tst.tertj.denimstore.POJO.Offer;
import tst.tertj.denimstore.constants.Categories;
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
        if (dataManager.tos_xml_base != null) {
            if (parser.parse()) {
                checkForDismissProgressDialog();
            }
        }
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
    public void onCategoryClick(Category category) {
        String parent_name = category.parent;
        switch (parent_name) {
            case Categories.WOMEN_CATEGORY:
                switch (category.child) {
                    case "Бельё":
                        showCatalogFragment(dataManager.women_underwear);
                        break;

                    case "Блузки, рубашки":
                        showCatalogFragment(dataManager.women_blouse_shirt);
                        break;

                    case "Брюки":
                        showCatalogFragment(dataManager.women_trousers);
                        break;

                    case "Джинсы":
                        showCatalogFragment(dataManager.women_jeans);
                        break;

                    case "Жилеты, корсеты":
                        showCatalogFragment(dataManager.women_waistcoat);
                        break;

                    case "Кардиганы":
                        showCatalogFragment(dataManager.women_cardigans);
                        break;

                    case "Комбинезоны":
                        showCatalogFragment(dataManager.women_overalls);
                        break;

                    case "Купальники":
                        showCatalogFragment(dataManager.women_swimsuit);
                        break;

                    case "Куртки, пиджаки":
                        showCatalogFragment(dataManager.women_jackets);
                        break;

                    case "Лосины":
                        showCatalogFragment(dataManager.women_leggins);
                        break;

                    case "Майки, футболки":
                        showCatalogFragment(dataManager.women_t_shirt);
                        break;

                    case "Платья":
                        showCatalogFragment(dataManager.women_dresses);
                        break;

                    case "Свитера":
                        showCatalogFragment(dataManager.women_sweaters);
                        break;

                    case "Туники":
                        showCatalogFragment(dataManager.women_tunic);
                        break;

                    case "Шорты":
                        showCatalogFragment(dataManager.shortsCatalog);
                        break;

                    case "Юбки":
                        showCatalogFragment(dataManager.women_skirts);
                        break;

                    default:
                        break;
                }
                break;

            case Categories.MEN_CATEGORY:
                switch (category.child) {
                    case "Джинсы":
                        showCatalogFragment(dataManager.men_jeans);
                        break;

                    case "Брюки":
                        showCatalogFragment(dataManager.men_trousers);
                        break;

                    case "Свитера":
                        showCatalogFragment(dataManager.men_sweaters);
                        break;

                    case "Куртки":
                        showCatalogFragment(dataManager.men_jackets);
                        break;

                    case "Жилетки":
                        showCatalogFragment(dataManager.men_waistcoat);
                        break;

                    case "Майки":
                        showCatalogFragment(dataManager.men_maiki);
                        break;

                    case "Рубашки":
                        showCatalogFragment(dataManager.men_shirts);
                        break;

                    case "Пиджаки":
                        showCatalogFragment(dataManager.men_pizdaki);
                        break;

                    case "Футболки":
                        showCatalogFragment(dataManager.men_t_shirts);
                        break;

                    case "Белье":
                        showCatalogFragment(dataManager.men_underwear);
                        break;

                    default:
                        break;

                }
                break;

            case Categories.CHILDREN_CATEGORY:
                switch (category.child) {
                    case "Шорты":
                        showCatalogFragment(dataManager.child_shorts);
                        break;
                    case "Футболки":
                        showCatalogFragment(dataManager.child_t_shorts);
                        break;
                    case "Батники":
                        showCatalogFragment(dataManager.child_sweaters);
                        break;
                    case "Юбки":
                        showCatalogFragment(dataManager.child_skirts);
                        break;

                    default:
                        break;
                }
                break;

            case Categories.ACCESSORIES_CATEGORY:
                switch (category.child) {
                    case "Ремни":
                        showCatalogFragment(dataManager.accessories_belt);
                        break;
                    case "Бабочки":
                        showCatalogFragment(dataManager.accessories_bow_tea);
                        break;
                    case "Подтяжки":
                        showCatalogFragment(dataManager.accessories_suspenders);
                        break;
                    case "Галстуки":
                        showCatalogFragment(dataManager.accessories_tie);
                        break;

                    case "Сумки, кошельки":
                        showCatalogFragment(dataManager.accessories_bags_wallets);
                        break;

                    case "Шапки":
                        showCatalogFragment(dataManager.accessories_hat);
                        break;

                    case "Шарфы":
                        showCatalogFragment(dataManager.accessories_scarf);
                        break;

                    case "Перчатки":
                        showCatalogFragment(dataManager.accessories_gloves);
                        break;

                    case "Очки":
                        showCatalogFragment(dataManager.accessories_sunglasses);
                        break;

                    case "Шляпы":
                        showCatalogFragment(dataManager.accessories_hat_2);
                        break;

                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }
}
