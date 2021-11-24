package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import hk.hku.cs.comp3330project.databinding.ActivityMainBinding;

public class ArticleSearchResultsActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}