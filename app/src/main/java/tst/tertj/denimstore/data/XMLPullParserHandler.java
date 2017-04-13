package tst.tertj.denimstore.data;

import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class XMLPullParserHandler {
    LinkedList<Offer> offersList;
    private Offer offer;
    private String text;
    int pictures_counter = 0;
    String zpt = ", ";
    boolean duplicateOffers = false;

    public XMLPullParserHandler() {
        offersList = new LinkedList<Offer>();
    }

    public LinkedList<Offer> getOffers() {
        return offersList;
    }

    public LinkedList<Offer> parse(String is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(new StringReader(is));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(Const.OFFER)) {
                            offer = new Offer();
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
                                    offer.setOffer_id(parser.getAttributeValue(i));
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
                                        if (lastListOffer.getName().equals(offer.getName())) {
                                            lastListOffer.setSizes(lastListOffer.getSizes() + zpt + cdata);
                                            duplicateOffers = true;
                                        } else {
                                            offer.setSizes(cdata);
                                        }
                                    } else {
                                        offer.setSizes(cdata);
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
                                    offersList.add(offer);
                                }
                                offer = null;
                                pictures_counter = 0;
                                Log.d(Const.TAG_PARSER, "offer was add to linked list");
                            } else if (tagname.equalsIgnoreCase(Const.PRICE)) {
                                offer.setPrice(text);
                                Log.d(Const.TAG_PARSER, "offer.price = " + offer.getPrice());
                            } else if (tagname.equalsIgnoreCase(Const.CURRENCY_ID)) {
                                offer.setCurrencyId(text);
                                Log.d(Const.TAG_PARSER, "offer.currencyId = " + offer.getCurrencyId());

                            } else if (tagname.equalsIgnoreCase(Const.CATEGORY_ID)) {
                                offer.setCategoryId(text);
                                Log.d(Const.TAG_PARSER, "offer.categoryId = " + offer.getCategoryId());
                                Log.d(Const.CATEGORY_PARSER, "offer.categoryId = " + offer.getCategoryId());

                            } else if (tagname.equalsIgnoreCase(Const.PICTURE)) {
                                pictures_counter++;
                                if (pictures_counter == 1) {
                                    offer.setPicture_1_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_1_URL = " + offer.getPicture_1_URL());

                                } else if (pictures_counter == 2) {
                                    offer.setPicture_2_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_2_URL = " + offer.getPicture_2_URL());

                                } else if (pictures_counter == 3) {
                                    offer.setPicture_3_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_3_URL = " + offer.getPicture_3_URL());

                                } else if (pictures_counter == 4) {
                                    offer.setPicture_4_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_4_URL = " + offer.getPicture_4_URL());

                                } else if (pictures_counter == 5) {
                                    offer.setPicture_5_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_5_URL = " + offer.getPicture_5_URL());

                                } else if (pictures_counter == 6) {
                                    offer.setPicture_6_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_6_URL = " + offer.getPicture_6_URL());

                                } else if (pictures_counter == 7) {
                                    offer.setPicture_7_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_7_URL = " + offer.getPicture_7_URL());

                                } else if (pictures_counter == 8) {
                                    offer.setPicture_8_URL(text);
                                    Log.d(Const.TAG_PARSER, "offer.picture_8_URL = " + offer.getPicture_8_URL());
                                }
                                offer.setHowMuchPhotos(pictures_counter);
                            } else if (tagname.equalsIgnoreCase(Const.NAME)) {
                                offer.setName(text);
                                Log.d(Const.TAG_PARSER, "offer.name = " + offer.getName());

                            } else if (tagname.equalsIgnoreCase(Const.DESCRIPTION)) {
                                offer.setDescription(text);
                                Log.d(Const.TAG_PARSER, "offer.description = " + offer.getDescription());

                            } else if (tagname.equalsIgnoreCase(Const.COUNTRY_OF_ORIGIN)) {
                                offer.setCountry_of_origin(text);
                                Log.d(Const.TAG_PARSER, "offer.country_of_origin = " + offer.getCountry_of_origin());
                            }
                        }
                        Log.d(Const.TAG_PARSER, "END_TAG: имя тега = " + tagname);
                        Log.d(Const.TAG_PARSER, "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                        break;

                    case XmlPullParser.START_DOCUMENT:
                        Log.d(Const.TAG_PARSER, "Начало документа");
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return offersList;
    }
}


