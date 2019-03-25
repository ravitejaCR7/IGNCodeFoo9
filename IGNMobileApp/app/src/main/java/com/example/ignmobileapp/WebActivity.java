package com.example.ignmobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ignmobileapp.FragmentsFile.ArticlesFragment;
import com.example.ignmobileapp.PojoClasses.ArticlesPojo;
import com.example.ignmobileapp.PojoClasses.MetadataArticles;
import com.example.ignmobileapp.PojoClasses.ThumbnailsArticles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WebActivity extends AppCompatActivity {

    private WebView browser;
    private StringBuilder resultHtml;
    private String sessionId;

    private RequestQueue requestQueue;
    private RequestQueue requestQueueComments;

    private List<ArticlesPojo> articlesPojos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueueComments = Volley.newRequestQueue(getApplicationContext());

        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");

        browser = findViewById(R.id.webview);

        resultHtml = new StringBuilder();
        resultHtml.append("<html><body>");

        jsonParse();
    }

    private void jsonParse() {
        String url = "https://ign-apis.herokuapp.com/content?startIndex=30\\u0026count=5";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObjectContentId = jsonArray.getJSONObject(i);

                        String type = jsonObjectContentId.getString("contentType");

                        if(type.equals("article"))
                        {
                            ArticlesPojo articlesPojo = new ArticlesPojo();
                            articlesPojo.setContentId(jsonObjectContentId.getString("contentId"));
                            Log.d("New Activity ",articlesPojo.getContentId());
                            //comments count dummy
                            articlesPojo.setNumberOfComments(0);


                            articlesPojo.setContentType(jsonObjectContentId.getString("contentType"));

                            List<ThumbnailsArticles> thumbnailsArticles = new ArrayList<>();

                            JSONArray jsonArrayThumbnails = jsonObjectContentId.getJSONArray("thumbnails");

                            for(int j=0; j<jsonArrayThumbnails.length(); j++)
                            {
                                JSONObject jsonThumnailObject = jsonArrayThumbnails.getJSONObject(j);

                                ThumbnailsArticles thumbnailsArticles1 = new ThumbnailsArticles();
                                thumbnailsArticles1.setUrl(jsonThumnailObject.getString("url"));
                                thumbnailsArticles1.setSize(jsonThumnailObject.getString("size"));
                                thumbnailsArticles1.setWidth(jsonThumnailObject.getInt("width"));
                                thumbnailsArticles1.setHeight(jsonThumnailObject.getInt("height"));

                                thumbnailsArticles.add(thumbnailsArticles1);
                            }

                            articlesPojo.setThumbnails(thumbnailsArticles);

                            MetadataArticles metadataArticles = new MetadataArticles();

                            JSONObject metadataJsonObject = jsonObjectContentId.getJSONObject("metadata");
                            metadataArticles.setHeadline(metadataJsonObject.getString("headline"));
                            metadataArticles.setDescription(metadataJsonObject.getString("description"));
                            metadataArticles.setPublishDate(metadataJsonObject.getString("publishDate"));
                            metadataArticles.setSlug(metadataJsonObject.getString("slug"));
                            metadataArticles.setState(metadataJsonObject.getString("state"));

                            articlesPojo.setMetadata(metadataArticles);

                            JSONArray tagsJsonArray = jsonObjectContentId.getJSONArray("tags");

                            List<String> tagsList = new ArrayList<>();

                            for(int k=0; k < tagsJsonArray.length() ; k++ )
                            {
                                tagsList.add((String) tagsJsonArray.get(k));
                            }

                            articlesPojo.setTags(tagsList);

                            JSONArray authorsJsonArray = jsonObjectContentId.getJSONArray("authors");

                            List<String> authorsList = new ArrayList<>();

                            for(int l=0; l < authorsJsonArray.length() ; l++ )
                            {
                                authorsList.add((String) authorsJsonArray.get(l));
                            }

                            articlesPojo.setAuthors(authorsList);

                            articlesPojos.add(articlesPojo);
                        }
                        else if(type.equals("video"))
                        {
                            ArticlesPojo articlesPojo = new ArticlesPojo();
                            articlesPojo.setContentId(jsonObjectContentId.getString("contentId"));
                            //comments count dummy
                            articlesPojo.setNumberOfComments(0);


                            articlesPojo.setContentType(jsonObjectContentId.getString("contentType"));

                            List<ThumbnailsArticles> thumbnailsArticles = new ArrayList<>();

                            JSONArray jsonArrayThumbnails = jsonObjectContentId.getJSONArray("thumbnails");

                            for(int j=0; j<jsonArrayThumbnails.length(); j++)
                            {
                                JSONObject jsonThumnailObject = jsonArrayThumbnails.getJSONObject(j);

                                ThumbnailsArticles thumbnailsArticles1 = new ThumbnailsArticles();
                                thumbnailsArticles1.setUrl(jsonThumnailObject.getString("url"));
                                thumbnailsArticles1.setSize(jsonThumnailObject.getString("size"));
                                thumbnailsArticles1.setWidth(jsonThumnailObject.getInt("width"));
                                thumbnailsArticles1.setHeight(jsonThumnailObject.getInt("height"));

                                thumbnailsArticles.add(thumbnailsArticles1);
                            }

                            articlesPojo.setThumbnails(thumbnailsArticles);

                            MetadataArticles metadataArticles = new MetadataArticles();

                            JSONObject metadataJsonObject = jsonObjectContentId.getJSONObject("metadata");
                            metadataArticles.setHeadline(metadataJsonObject.getString("title"));
                            metadataArticles.setDescription(metadataJsonObject.getString("description"));
                            metadataArticles.setPublishDate(metadataJsonObject.getString("publishDate"));
                            metadataArticles.setSlug(metadataJsonObject.getString("slug"));
                            metadataArticles.setState(metadataJsonObject.getString("state"));
                            metadataArticles.setDuration(metadataJsonObject.getInt("duration"));
                            metadataArticles.setVideoSeries(metadataJsonObject.getString("videoSeries"));

                            articlesPojo.setMetadata(metadataArticles);

                            JSONArray tagsJsonArray = jsonObjectContentId.getJSONArray("tags");

                            List<String> tagsList = new ArrayList<>();

                            for(int k=0; k < tagsJsonArray.length() ; k++ )
                            {
                                tagsList.add((String) tagsJsonArray.get(k));
                            }

                            articlesPojo.setTags(tagsList);

                            articlesPojos.add(articlesPojo);
                        }

                    }

                    jsonParseDataForComments(articlesPojos);
                    if(articlesPojos != null && articlesPojos.size() > 0)
                    {
                        Log.d("adapter","inside adapter");
                        Collections.reverse(articlesPojos);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        requestQueue.add(jsonObjectRequest);
    }

    public void jsonParseDataForComments(final List<ArticlesPojo> articlesPojos)
    {
        String str="";
        for(int i=0; i < articlesPojos.size(); i++)
        {
            if(i < articlesPojos.size()-1)
                str += articlesPojos.get(i).getContentId()+",";
            else
                str += articlesPojos.get(i).getContentId();
        }

        String url = "https://ign-apis.herokuapp.com/comments?ids="+str;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("content");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        articlesPojos.get(i).setNumberOfComments(jsonObject.getInt("count"));
                    }

                    loadCoorectDataToHtml();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        requestQueueComments.add(jsonObjectRequest);
    }

    private void loadCoorectDataToHtml() {
        for(int i=0; i < articlesPojos.size() ; i++)
        {
            if(sessionId.equals(articlesPojos.get(i).getContentId()))
            {
                MetadataArticles metadata = articlesPojos.get(i).getMetadata();
                String dateStr = metadata.getPublishDate();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String str = "";
                try {
                    Date date = dateFormat.parse(dateStr.substring(0,19));

                    Date currDate = new Date();

                    long diff = currDate.getTime() - date.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);

//                Log.d("times ","time is : "+date+" now "+currDate);
//                Log.d("times ","days : "+diffDays+" hours "+diffHours+" diffMinutes "+diffMinutes+" diffSeconds "+diffSeconds);

                    if(diffDays>0)
                    {
                        str += diffDays+" DAYS AGO";
                    }
                    else if(diffHours>0)
                    {
                        str += diffHours+" HR AGO";
                    }
                    else if(diffMinutes>0)
                    {
                        str += diffMinutes+" MIN AGO";
                    }
                    else if(diffSeconds>0)
                    {
                        str += diffSeconds+" SEC AGO";
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                resultHtml.append("<h4 style=\"color:BF1313;\" >"+str+"</h4>");
                resultHtml.append("<h2>"+metadata.getHeadline()+"</h2>");

                List<ThumbnailsArticles> thumbnails = articlesPojos.get(i).getThumbnails();

                resultHtml.append("<img src="+thumbnails.get(2).getUrl()+" alt=\"Content Image\">");
                resultHtml.append("<p style=\"color:A9A9A9;\" >"+metadata.getDescription()+"</p>");
                resultHtml.append("<p >"+"Publish Date: "+metadata.getPublishDate()+"</p>");
                resultHtml.append("<p >"+"Slug: "+metadata.getSlug()+"</p>");
                resultHtml.append("<p>Tags: </p><ul>");
                List<String> tags = articlesPojos.get(i).getTags();
                for (int j = 0 ; j< tags.size() ; j++)
                {
                    resultHtml.append("<li>"+tags.get(j)+"</li>");
                }
                resultHtml.append("</ul>");
                if(articlesPojos.get(i).getContentType().equals("article"))
                {
                    resultHtml.append("<p>Authors: </p><ol>");
                    List<String> authors = articlesPojos.get(i).getAuthors();
                    for (int j = 0 ; j< authors.size() ; j++)
                    {
                        resultHtml.append("<li>"+authors.get(j)+"</li>");
                    }
                    resultHtml.append("</ol>");
                }
            }
        }
        resultHtml.append("</body></html>");
        String encodedHtml = Base64.encodeToString(resultHtml.toString().getBytes(), Base64.NO_PADDING);
        browser.loadData(encodedHtml, "text/html", "base64");
    }
}
