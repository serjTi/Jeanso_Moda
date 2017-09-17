package tst.tertj.denimstore.manager;

import java.util.LinkedList;

import tst.tertj.denimstore.POJO.Offer;

/**
 * Created by sergey_tertychenko on 11.08.17.
 */

public class DataManager {

    private static DataManager _instance;

    public String xml_base;
    public LinkedList<Offer> offersList;
    public LinkedList<Offer> menCatalog;
    public LinkedList<Offer> womenCatalog;
    public LinkedList<Offer> shortsCatalog;
    public Offer choosen_offer;

    public static DataManager getInstance() {
        if (_instance == null) {
            _instance = new DataManager();
        }
        return _instance;
    }

    private DataManager() {
        xml_base = "";
        offersList = new LinkedList<>();
        menCatalog = new LinkedList<>();
        womenCatalog = new LinkedList<>();
        shortsCatalog = new LinkedList<>();
        choosen_offer = new Offer();
    }
}
