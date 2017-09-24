package tst.tertj.denimstore.parser;

import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;

import tst.tertj.denimstore.POJO.Offer;
import tst.tertj.denimstore.constants.Const;
import tst.tertj.denimstore.manager.DataManager;

public class AbvParser {

    private LinkedList<Offer> offersList;
    private LinkedList<String> images_list;
    private Offer offer;
    private String text;
    String zpt = ", ";
    boolean duplicateOffers = false;
    private DataManager dataManager = DataManager.getInstance();

    public AbvParser() {
        offersList = new LinkedList<Offer>();

    }

    public boolean parse() {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(new StringReader(DataManager.getInstance().abv_xml_base));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(Const.OFFER)) {
                            offer = new Offer();
                            offer.diller = Const.Dillers.ABV;
                            images_list = new LinkedList<>();
                            duplicateOffers = false;
                            Log.d(Const.TAG_PARSER, "new offer" + offer.toString());

                            Log.d(Const.TAG_PARSER,
                                    "START_TAG: имя тега = " + parser.getName()
                                            + ", уровень = " + parser.getDepth()
                                            + ", число атрибутов = "
                                            + parser.getAttributeCount());
                            String tmp = "";
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                tmp = parser.getAttributeName(i);
                                if (tmp.equalsIgnoreCase(Const.ID_ATTRIBUTE)) {
                                    offer.offer_id = parser.getAttributeValue(i);
                                }
                            }
                            if (!TextUtils.isEmpty(tmp))
                                Log.d(Const.TAG_PARSER, "Атрибуты: " + tmp);


                        } else if (tagname.equalsIgnoreCase(Const.PARAM)) {
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                String attributeValue = parser.getAttributeValue(i);
                                Log.d(Const.TAG_SIZE, "attributeValue = " + attributeValue);
                                if (attributeValue.equalsIgnoreCase(Const.SIZE_ATTRIBUTE)) {
                                    int token = parser.nextToken();
                                    while (token != XmlPullParser.CDSECT) {
                                        token = parser.nextToken();
                                    }
                                    String cdata = parser.getText();
                                    if (offersList.size() >= 1) {
                                        Offer lastListOffer = offersList.getLast();
                                        if (lastListOffer.name.equals(offer.name)) {
                                            lastListOffer.sizes = (lastListOffer.sizes + zpt + cdata);
                                            duplicateOffers = true;
                                        } else {
                                            offer.sizes = cdata;
                                        }
                                    } else {
                                        offer.sizes = cdata;
                                    }

                                    Log.d(Const.TAG_SIZE, "cdata = " + cdata);
                                }
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (offer != null) {
                            if (tagname.equalsIgnoreCase(Const.OFFER)) {
                                if (!duplicateOffers) {
                                    offer.images = images_list;
                                    offersList.add(offer);
                                    addOfferByCategory(offer);
                                }
                                images_list = null;
                                offer = null;
                            } else if (tagname.equalsIgnoreCase(Const.PRICE)) {
                                offer.price = text;
                            } else if (tagname.equalsIgnoreCase(Const.CURRENCY_ID)) {
                                offer.currencyId = text;
                            } else if (tagname.equalsIgnoreCase(Const.CATEGORY_ID)) {
                                offer.categoryId = text;
                            } else if (tagname.equalsIgnoreCase(Const.PICTURE)) {
                                if(!text.equals("      ")){
                                images_list.add(text);
                                }
                            } else if (tagname.equalsIgnoreCase(Const.NAME)) {
                                offer.name = text;
                            } else if (tagname.equalsIgnoreCase(Const.DESCRIPTION)) {
                                offer.description = text;
                            } else if (tagname.equalsIgnoreCase(Const.COUNTRY_OF_ORIGIN)) {
                                offer.country_of_origin = text;
                            } else if (tagname.equalsIgnoreCase(Const.OFFERS)){
                                Collections.reverse(dataManager.men_jeans);
                                Collections.reverse(dataManager.women_jeans);

                            }
                        }
                        break;

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void addOfferByCategory(Offer offer) {
        String category_id = offer.categoryId;
        switch (category_id) {
            case Const.MEN_CATEGORY:
                dataManager.men_jeans.add(offer);
                break;
            case Const.WOMEN_CATEGORY:
                dataManager.women_jeans.add(offer);
                break;
            case Const.SHORTS_CATEGORY:
                dataManager.shortsCatalog.add(offer);
            break;
        default:
            break;
        }
    }
}


