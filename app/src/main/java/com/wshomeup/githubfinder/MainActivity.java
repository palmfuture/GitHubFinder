package com.wshomeup.githubfinder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wshomeup.githubfinder.model.GitHubUser;
import com.wshomeup.githubfinder.service.GitHubService;
import com.wshomeup.githubfinder.util.HttpManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView tvName, tvCompany, tvBlog, tvGithub;
    private GitHubUser gitHubUser;
    private HttpManager httpManager;
    private EditText editText;
    private ImageView imageView;
    private String name, company, blog, image;

    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        picassoLoad();

        httpManager = HttpManager.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitLoadGitHubUser(httpManager.getService(), editText.getText().toString());
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("name", name);
        outState.putString("company", company);
        outState.putString("blog", blog);
        outState.putString("image", image);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getString("name", null) != null) {

            name = savedInstanceState.getString("name", null);
            company = savedInstanceState.getString("company", null);
            blog = savedInstanceState.getString("blog", null);
            image = savedInstanceState.getString("image", null);

            tvName.setText("Name : " + name);
            tvCompany.setText("Company : " + company);
            tvBlog.setText("Blog : " + blog);
            tvGithub.setText(null);

            if (MainActivity.this.getResources().getConfiguration().orientation == 2) {
                Picasso.with(getApplicationContext())
                        .load(image)
                        .resize(500, 500)
                        .centerCrop()
                        .into(imageView);
            } else {
                Picasso.with(getApplicationContext())
                        .load(image)
                        .resize(800, 800)
                        .centerCrop()
                        .into(imageView);
            }
        }
    }

    private void picassoLoad() {
        if (MainActivity.this.getResources().getConfiguration().orientation == 2) {
            Picasso.with(getApplicationContext())
                    .load("https://image.freepik.com/free-icon/github-logo-silhouette-in-a-square_318-54633.jpg")
                    .resize(500, 500)
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(getApplicationContext())
                    .load("https://image.freepik.com/free-icon/github-logo-silhouette-in-a-square_318-54633.jpg")
                    .resize(800, 800)
                    .centerCrop()
                    .into(imageView);
        }
    }

    private void retrofitLoadGitHubUser(GitHubService service, String user) {
        Call<GitHubUser> call = service.loadUser(user);

        call.enqueue(new Callback<GitHubUser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<GitHubUser> call, @NonNull Response<GitHubUser> response) {
                if (response.isSuccessful()) {
                    gitHubUser = response.body();
                    tvName.setText("Name : " + gitHubUser.getName());
                    tvCompany.setText("Company : " + gitHubUser.getCompany());
                    tvBlog.setText("Blog : " + gitHubUser.getBlog());
                    tvGithub.setText(null);

                    name = gitHubUser.getName();
                    company = gitHubUser.getCompany();
                    blog = gitHubUser.getCompany();
                    image = gitHubUser.getImage();

                    if (MainActivity.this.getResources().getConfiguration().orientation == 2) {
                        Picasso.with(getApplicationContext())
                                .load(gitHubUser.getImage())
                                .resize(500, 500)
                                .centerCrop()
                                .into(imageView);
                    } else {
                        Picasso.with(getApplicationContext())
                                .load(gitHubUser.getImage())
                                .resize(800, 800)
                                .centerCrop()
                                .into(imageView);
                    }

                } else {
                    try {
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GitHubUser> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void init() {
        button = (Button) findViewById(R.id.button);
        tvName = (TextView) findViewById(R.id.tvName);
        tvCompany = (TextView) findViewById(R.id.tvCompany);
        tvBlog = (TextView) findViewById(R.id.tvBlog);
        tvGithub = (TextView) findViewById(R.id.tvGithub);
        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
}
