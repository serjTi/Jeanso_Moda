package tst.tertj.denimstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import tst.tertj.denimstore.data.Const;
import tst.tertj.denimstore.data.Offer;
import tst.tertj.denimstore.data.XMLPullParserHandler;
import tst.tertj.denimstore.fragments.CatalogFragment;
import tst.tertj.denimstore.fragments.CategoriesFragment;
import tst.tertj.denimstore.fragments.SelectedOfferFragment;
import tst.tertj.denimstore.interfaces.GetProdactDatabase;
import tst.tertj.denimstore.network.DownloadXML;


public class MainActivity extends AppCompatActivity implements GetProdactDatabase,
        CategoriesFragment.IOnMyCatalogClickListener, CatalogFragment.IOnMyOfferListClickListener,
        SelectedOfferFragment.IOnMyOfferClickListener {


    private LinkedList<Offer> offersList;
    private LinkedList<Offer> menCatalog;
    private LinkedList<Offer> womenCatalog;
    private LinkedList<Offer> shortsCatalog;

    SelectedOfferFragment selectedOfferFragment;
    CategoriesFragment categoriesFragment;
    CatalogFragment catalogFragment;
    private FragmentTransaction fTrans;
    private SharedPreferences myPreferences;
    private String currentXML = "";
    private boolean isCurrentXML = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        offersList = new LinkedList<Offer>();
        new DownloadXML(this).execute();

        myPreferences = getSharedPreferences(Const.MY_PREFERENCES, Context.MODE_PRIVATE);
        if (myPreferences.contains(Const.SAVED_XML_DATABASE)) {
            currentXML = myPreferences.getString(Const.SAVED_XML_DATABASE, "");
        }

    }


    @Override
    public void downloadXMLisDone(String string_XML) {
        Log.d(Const.TAG, "MA string_XML = " + string_XML);

        compareXMLS(currentXML, string_XML);
        XMLPullParserHandler parser = new XMLPullParserHandler();
        offersList = parser.parse(string_XML);
//        downloadPhotoBase(offersList);
        categoriesFragment = new CategoriesFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, categoriesFragment);
        fTrans.addToBackStack("CategoriesFragment");
        fTrans.commit();
    }

    private boolean compareXMLS(String currentXML, String string_xml) {
        if(currentXML.equals(string_xml))
            isCurrentXML = true;
        else
            isCurrentXML = false;

        return isCurrentXML;
    }

    private void downloadPhotoBase(LinkedList<Offer> offersList) {
        Toast.makeText(this, "Подождите пожалуйста. Идёт загрузка данных", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < offersList.size(); i++) {
            Toast.makeText(this, "Загружено " + i + " фотографий из " + String.valueOf(offersList.size()), Toast.LENGTH_SHORT).show();
            Log.d(Const.TAG, "i = " + i);
            final Offer currentOffer = offersList.get(i);
            Glide
                    .with(getApplicationContext())
                    .load(currentOffer.getPicture_1_URL())
                    .asBitmap()
                    .toBytes(Bitmap.CompressFormat.JPEG, 75)
                    .into(new SimpleTarget<byte[]>() {
                        @Override
                        public void onResourceReady(final byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... params) {
                                    File sdcard = Environment.getExternalStorageDirectory();
                                    File file = new File(sdcard + "/Denim Store/" + currentOffer.getName() + "_1.jpg");
                                    File dir = file.getParentFile();
                                    try {
                                        if (!dir.mkdirs() && (!dir.exists() || !dir.isDirectory())) {
                                            throw new IOException("Cannot ensure parent directory for file " + file);
                                        } else if (!file.exists()) {
                                            Log.d(Const.TAG_SIZE, "download pic");
                                            BufferedOutputStream s = new BufferedOutputStream(new FileOutputStream(file));
                                            s.write(resource);
                                            s.flush();
                                            s.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                            }.execute();
                        }
                    })
            ;
        }


    }

    @Override
    public void onMenCatalogClick() {
        menCatalog = new LinkedList<Offer>();
        int size = offersList.size();
        for (int i = 0; i < size; i++) {
            Offer off = offersList.get(i);
            if (off.getCategoryId().equals(Const.MEN_CATEGORY))
                menCatalog.add(off);
        }
        catalogFragment = CatalogFragment.newInstance(menCatalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onWomenCatalogClick() {
        womenCatalog = new LinkedList<Offer>();
        int size = offersList.size();
        for (int i = 0; i < size; i++) {
            Offer off = offersList.get(i);
            if (off.getCategoryId().equals(Const.WOMEN_CATEGORY))
                womenCatalog.add(off);
        }
        catalogFragment = CatalogFragment.newInstance(womenCatalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onShortsCatalogClick() {
        shortsCatalog = new LinkedList<Offer>();
        int size = offersList.size();
        for (int i = 0; i < size; i++) {
            Offer off = offersList.get(i);
            if (off.getCategoryId().equals(Const.SHORTS_CATEGORY))
                shortsCatalog.add(off);
        }
        catalogFragment = CatalogFragment.newInstance(shortsCatalog);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onCatalogClick() {
        catalogFragment = CatalogFragment.newInstance(offersList);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.flFragmentContainer, catalogFragment);
        fTrans.addToBackStack("CatalogFragment");
        fTrans.commit();
    }

    @Override
    public void onOfferClick(Offer selectedOffer) {
        downloadCurrentOfferPhotos(selectedOffer);
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

    }

    private void downloadCurrentOfferPhotos(Offer offersList) {
        for (int i = 2; i <= offersList.getHowMuchPhotos(); i++) {
            if (i == 2) {
                ddd(offersList.getName(), offersList.getPicture_2_URL(), i);

            } else if (i == 3) {
                ddd(offersList.getName(), offersList.getPicture_3_URL(), i);
            } else if (i == 4) {
                ddd(offersList.getName(), offersList.getPicture_4_URL(), i);
            } else if (i == 5) {
                ddd(offersList.getName(), offersList.getPicture_5_URL(), i);
            }
            Log.d(Const.TAG, "download image task for " + i + " offer image");
        }
    }


    public void ddd(final String name, String url, final int l) {
        Glide
                .with(getApplicationContext())
                .load(url)
                .asBitmap()
                .toBytes(Bitmap.CompressFormat.JPEG, 75)
                .into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(final byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected Void doInBackground(Void... params) {
                                File sdcard = Environment.getExternalStorageDirectory();
                                File file = new File(sdcard + "/Denim Store/" + name + "_" + l + ".jpg");
                                File dir = file.getParentFile();
                                try {
                                    if (!dir.mkdirs() && (!dir.exists() || !dir.isDirectory())) {
                                        throw new IOException("Cannot ensure parent directory for file " + file);
                                    } else if (!file.exists()) {
                                        Log.d(Const.TAG_SIZE, "download pic");
                                        BufferedOutputStream s = new BufferedOutputStream(new FileOutputStream(file));
                                        s.write(resource);
                                        s.flush();
                                        s.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();
                    }
                })
        ;
    }
}
