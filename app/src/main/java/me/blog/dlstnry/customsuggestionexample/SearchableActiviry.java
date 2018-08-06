package me.blog.dlstnry.customsuggestionexample;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchableActiviry extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable_activiry);

        mTextView = findViewById(R.id.text_view);

        //SearchableInfo 객체를 가져오기 위해 SearchManager 객체를 만듭니다.
        //SearchableInfo는 searchable.xml을 manifest에서 추가한 <meta-data>를 통해 가져옵니다.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.search_view);
        //SearchManager를 통해 SearchableInfo를 가져와 SearchView에 적용합니다.
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //검색어를 submit했을 시엔 회사명을 출력합니다.
        //제안 검색어 리스트에서 아이템을 선택했을 시엔 회사 웹페이지 주소를 출력합니다.

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //검색어는 SearchManater.QUERY라는 string extra로 보내집니다.
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            //SUGGEST_COLUMN_INTENT_EXTRA_DATA를 통해 보내진 데이터는
            //SearchManager.EXTRA_DATA_KEY로 조회할 수 있습니다.
            String url = intent.getStringExtra(SearchManager.EXTRA_DATA_KEY);
            mTextView.setText(url);
        }
    }

    void search(String query) {
        String[] companyNames = getResources().getStringArray(R.array.company_names);
        for (String companyName : companyNames) {
            if (companyName.toLowerCase().contains(query.toLowerCase())) {
                mTextView.setText(companyName);
                return;
            } else {
                mTextView.setText("Search failed.");
            }
        }
    }
}
