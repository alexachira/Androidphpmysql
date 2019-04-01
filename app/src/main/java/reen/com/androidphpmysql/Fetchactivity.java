package reen.com.androidphpmysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.TruncatedChunkException;

public class Fetchactivity extends AppCompatActivity {
   @BindView(R.id.bookid)
   EditText bookid;

   @BindView(R.id.textViewbooktitle)
   TextView txttitle;

    @BindView(R.id.textViewauthor)
    TextView txtviewauthor;

    @BindView(R.id.textViewyear)
    TextView txtviewyear;

    @BindView(R.id.textViewcost)
    TextView txtviewcost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchactivity);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.buttonsearch})
   public void fetch() {
        if (!new Network().isInternetAvailable()) {
            Toast.makeText(this, "check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        String id =bookid.getText().toString().trim();
        if (id.isEmpty() )
           return;
        RequestParams params=new RequestParams();
        params.put("id",id);

            String url="http://f77a004c.ngrok.io/apis/fetch.php";

            AsyncHttpClient client=new AsyncHttpClient();

            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String responsestring, Throwable throwable) {
                    Toast.makeText(Fetchactivity.this, "could not communicate with the server please try again", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String responsestring) {
                    Log.d("DATA_BOOKS", "onSuccess: "+responsestring);
                    try {
                        JSONObject mainobject = new JSONObject(responsestring);
                        String status=mainobject.getString("status");
                        if (status.equalsIgnoreCase("success"))
                        {
                            JSONObject data=mainobject.getJSONObject("data");
                            String title =data.getString("title");
                            String author =data.getString("author");
                            String year =data.getString("year");
                            String cost =data.getString("cost");

                            txttitle.setText(title);
                            txtviewauthor.setText(author);
                            txtviewyear.setText(year);
                            txtviewcost.setText(cost);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            });
    }
}
