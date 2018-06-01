package com.elad.reddit.service.repository.storge;

import android.content.Context;
import android.widget.Toast;

import com.elad.reddit.R;
import com.elad.reddit.service.model.RedditPost;
import com.google.gson.Gson;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Elad on 5/31/2018.
 */


    // handle adding posts to storage and search api via lucene
    // https://lucene.apache.org/core/
public class SavedPostsStorge {
    private static final int MAX_SEARCH_RESULTS = 100;
    final private String UNIQUE_ID = "UNIQUE_ID";
    final private Analyzer analyzer;
    final private Directory dir;
    final private IndexWriterConfig iwc;
    final private IndexWriter writer;
    private IndexReader reader;

    public SavedPostsStorge(Context context) throws IOException {
        File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + UNIQUE_ID);
        analyzer = new StandardAnalyzer(Version.LUCENE_36);
        dir = FSDirectory.open(file);
        iwc = new IndexWriterConfig(Version.LUCENE_36, analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        writer = new IndexWriter(dir, iwc);
    }
    // return RedditPost that contain the query text
    public List<RedditPost> search(String query) throws IOException {
        reader = IndexReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Set<RedditPost> result = new HashSet<>();
        // we loop all RedditPost members, since each query must focus on specific field.
        RedditPost.CLASS_MEMBERS_FILED.forEach(filed -> {
            Term term = new Term(filed, query);
            //  WildcardQuery support *, which matches any character sequence (including the empty one).
            WildcardQuery wildcardQuery = new WildcardQuery(term);
            try {
                TopDocs topDocs = searcher.search(wildcardQuery, MAX_SEARCH_RESULTS);
                // convert Docs to RedditPost
                Arrays.stream(topDocs.scoreDocs).forEach(scoreDoc -> {
                    try {
                        // get the doc from searcher by doc number
                        Document doc = searcher.doc(scoreDoc.doc);
                        RedditPost item = toObject(doc);
                        result.add(item);
                    } catch (IllegalAccessException | InstantiationException | IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        reader.close();
        List<RedditPost> resultAsList = new ArrayList<>(result.size());
        resultAsList.addAll(result);
        return resultAsList;
    }


    // adding RedditPost to storage.
    public void addItem(final RedditPost value, final Context context) throws JSONException, IOException {
        // we represent the RedditPost instance as json
        Gson gson = new Gson();
        JSONObject json = new JSONObject(gson.toJson(value));
        Document doc = new Document();
        json.keys().forEachRemaining((key) -> {
            try {
                // for each member in the class we create field, the fields contain name and value
                Field field = new Field(key, json.get(key).toString(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
                doc.add(field);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        // after create the doc store it.
        writer.addDocument(doc);
        writer.commit();
        Toast.makeText(context, context.getString(R.string.post_saved), Toast.LENGTH_LONG).show();
    }


    // convert the the Doc to RedditPost
    private RedditPost toObject(Document doc) throws IllegalAccessException, InstantiationException {
        // The doc first convert to json
        JSONObject jsonObject = new JSONObject();
        doc.getFields().forEach(field -> {
            try {
                jsonObject.put(field.name(), field.stringValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        // Convert the json object to RedditPost using Gson.
        Gson gson = new Gson();
        RedditPost result = gson.fromJson(jsonObject.toString(), RedditPost.class);
        return result;
    }

    public List<RedditPost> getSavedPosts() throws IOException {
        return search("*");
    }
}
