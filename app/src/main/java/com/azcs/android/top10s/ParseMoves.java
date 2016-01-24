package com.azcs.android.top10s;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by 3zcs on 1/16/16.
 */
public class ParseMoves {
    private String date ;
    private ArrayList<moves> mMovies ;

    public ParseMoves(String date) {
        this.date = date;
        mMovies = new ArrayList<>();
    }

    public ArrayList<moves> getMovies() {
        return mMovies;
    }

    public boolean process (){
        boolean status = true ;
        moves movieRecord = null;
        boolean inEntry = false;
        String textValue = "" ;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.date));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG :
                        //Log.d("Parse" , "starting tag" + tagName);
                        if (tagName.equalsIgnoreCase("entry")) {
                            inEntry = true;
                            movieRecord = new moves();
                        }
                        break;

                    case XmlPullParser.TEXT :
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG :
                        //Log.d("Parse" , "Ending tag" + tagName);
                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                mMovies.add(movieRecord);
                                inEntry = false ;
                            }else if (tagName.equalsIgnoreCase("name")){
                                movieRecord.setName(textValue);
                            }else if (tagName.equalsIgnoreCase("artist")){
                                movieRecord.setArtist(textValue);
                            }else if (tagName.equalsIgnoreCase("releasedate")){
                                movieRecord.setReleaseDate(textValue);
                            }
                        }
                        break;
                    default:
                        //Do nothing
                }

                eventType = xpp.next();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        for (moves move : mMovies){
            Log.d("move", "name " + move.getName());
            Log.d("move", "Artist " + move.getArtist());
            Log.d("move", "Date " + move.getReleaseDate());

        }
        return true ;
    }
}
