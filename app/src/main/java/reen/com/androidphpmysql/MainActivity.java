package reen.com.androidphpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.inputtitle)
    EditText inputtitle;

    @BindView(R.id.inputauthor)
    EditText inputauthor;

    @BindView(R.id.inputyear)
    EditText inputyear;

    @BindView(R.id.inputcost)
    EditText inputcost;

ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progress=new ProgressDialog(this);
        progress.setMessage("saving......");
    }

    @OnClick(R.id.buttonsave)
    public void save() {

        if (! new Network().isInternetAvailable()){
            Toast.makeText(this, "check internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = inputtitle.getText().toString().trim();
        String author = inputauthor.getText().toString().trim();
        String cost = inputcost.getText().toString().trim();
        String year = inputyear.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || cost.isEmpty() || year.isEmpty())

        {
            Toast.makeText(this, "fill in all values", Toast.LENGTH_SHORT).show();
            return;

        }
        RequestParams params=new RequestParams();
        params.put("title",title);
        params.put("author",author);
        params.put("cost",cost);
        params.put("year",year);

        String url="http://f77a004c.ngrok.io/apis/save.php";

        AsyncHttpClient client=new AsyncHttpClient();

        progress.show();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "fail to send please check your connection", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "book saved succesfully", Toast.LENGTH_SHORT).show();
            inputtitle.setText("");
            inputauthor.setText("");
            inputyear.setText("");
            inputcost.setText("");
            }
        });

    }

    @OnClick(R.id.buttonfetch)
    public void fetch()
    {
        Intent X=new Intent(this,Fetchactivity.class);
        startActivity(X);
    }

}
