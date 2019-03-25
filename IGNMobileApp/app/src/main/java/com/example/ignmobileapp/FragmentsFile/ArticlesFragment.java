package com.example.ignmobileapp.FragmentsFile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ignmobileapp.OnClickListener.RecyclerItemClickListener;
import com.example.ignmobileapp.PojoClasses.ArticlesPojo;
import com.example.ignmobileapp.PojoClasses.MetadataArticles;
import com.example.ignmobileapp.PojoClasses.ThumbnailsArticles;
import com.example.ignmobileapp.R;
import com.example.ignmobileapp.WebActivity;

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

public class ArticlesFragment extends Fragment{

    private RequestQueue requestQueue;
    private RequestQueue requestQueueComments;

    private List<ArticlesPojo> articlesPojos = new ArrayList<>();

    private RecyclerView recyclerView;
    private ArticlesMyRecyclerViewAdapter adapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.articles_fragment_layout, container, false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueueComments = Volley.newRequestQueue(getActivity().getApplicationContext());


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // set up the RecyclerView
        recyclerView = getView().findViewById(R.id.articlesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
//                        TextView tv = view.findViewById(R.id.article_time);
//                        Toast.makeText(view.getContext(),"This is "+tv.getText()+" position "+position,Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(getActivity(), WebActivity.class);

                        intent.putExtra("EXTRA_SESSION_ID", articlesPojos.get(position).getContentId());
                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(view.getContext()," long position "+position,Toast.LENGTH_SHORT).show();
                    }
                })
        );
        jsonParse();

        Log.d("adapter","outside adapter");

        super.onActivityCreated(savedInstanceState);
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
                            //comments count dummy
                            articlesPojo.setNumberOfComments(0);
                            Log.d("comments waswas","key is "+type+"   "+articlesPojo.getContentId());

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

                    }

//                    Log.e("Json msg","type : "+articlesPojos.get(0).getContentType());
//                    Log.e("Json msg","auth : "+articlesPojos.get(0).getAuthors());
                    jsonParseDataForComments(articlesPojos);
                    if(articlesPojos != null && articlesPojos.size() > 0)
                    {
                        Log.d("adapter","inside adapter");
                        Collections.reverse(articlesPojos);
                        adapter = new ArticlesMyRecyclerViewAdapter(getActivity().getApplicationContext(), articlesPojos);
                        recyclerView.setAdapter(adapter);
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

    public class ArticlesMyRecyclerViewAdapter extends RecyclerView.Adapter<ArticlesMyRecyclerViewAdapter.ViewHolder>
    {
        private List<ArticlesPojo> articlesPojoMainData;
        private LayoutInflater articlesLayoutflater;
        private Context context;

        ArticlesMyRecyclerViewAdapter(Context context, List<ArticlesPojo> data)
        {
            this.context = context;
            this.articlesLayoutflater = LayoutInflater.from(context);
            this.articlesPojoMainData = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view;
            if(i == 1)
            {
                view = articlesLayoutflater.inflate(R.layout.articles_each_row_layout_first_row , viewGroup , false);
            }else
            {
                view = articlesLayoutflater.inflate(R.layout.articles_each_row_layout , viewGroup , false);
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            MetadataArticles metadata = articlesPojoMainData.get(i).getMetadata();

            String dateStr = metadata.getPublishDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
                    viewHolder.article_time.setText(diffDays+" DAYS AGO");
                }
                else if(diffHours>0)
                {
                    viewHolder.article_time.setText(diffHours+" HR AGO");
                }
                else if(diffMinutes>0)
                {
                    viewHolder.article_time.setText(diffMinutes+" MIN AGO");
                }
                else if(diffSeconds>0)
                {
                    viewHolder.article_time.setText(diffSeconds+" SEC AGO");
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

            viewHolder.article_heading.setText(metadata.getHeadline());
            viewHolder.article_description.setText(metadata.getDescription());
////
            Log.d("Recycler Mine",articlesPojoMainData.get(i).getContentId()+" : "+articlesPojoMainData.get(i).getNumberOfComments());
            viewHolder.article_comments_count_value.setText(""+articlesPojoMainData.get(i).getNumberOfComments());
     ////
//            Log.d("comments API out","key is "+commentsCount[0]);

            List<ThumbnailsArticles> thumbnails = articlesPojoMainData.get(i).getThumbnails();

            Glide.with(context.getApplicationContext())
                    .load(thumbnails.get(2).getUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                    .into(viewHolder.article_content_image);

        }

        @Override
        public int getItemCount() {
            return articlesPojoMainData.size();
        }

        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView article_time,article_heading,article_description,article_comments_count_value;
            ImageView article_content_image;

            ViewHolder(View itemView) {
                super(itemView);
                article_time = itemView.findViewById(R.id.article_time);
                article_heading = itemView.findViewById(R.id.article_heading);
                article_description = itemView.findViewById(R.id.article_description);
                article_comments_count_value = itemView.findViewById(R.id.article_comments_count_value);
                article_content_image = itemView.findViewById(R.id.article_content_image);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) return 1;
            else return 2;
        }
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
//                        articlesPojo.setNumberOfComments(jsonObject.getInt("count"));
                        Log.d("comments API ","key is "+jsonObject.getString("id")+" val is "+jsonObject.getInt("count"));
                    }

                    adapter.notifyDataSetChanged();

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

}
