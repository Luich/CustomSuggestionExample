package me.blog.dlstnry.customsuggestionexample;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by ${Bassnerd} on 2018-08-06.
 */
public class SuggestionProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //예제에 db가 없기 때문에 테이블이 없습니다.
        //테이블을 가지고 Cursor 객체를 만들어야하는데 그럴 때 쓸 수 있는 클래스가 MatrixCursor입니다.
        //생성자에 인자로 전달되는 String 배열은 각 column의 이름입니다.
        //여기서 중요한게 _ID와 SUGGEST_COLUMN_TEXT_1는 검색어 제안에있어 필수항목입니다.
        //_ID는 시스템에서 row를 특정하기 위한 고유 값입니다.
        //SUGGEST_COLUMN_TEXT_1는 제안 검색어 리스트에 표시될 텍스트입니다.
        //SUGGEST_COLUMN_TEXT_2는 필수 항목은 아니나 SUGGEST_COLUMN_TEXT_1 밑에 작은 글씨로 나오는
        //텍스트이며 여기선 해당 회사 홈페이지의 주소를 입력하려합니다.
        //SUGGEST_COLUMN_INTENT_EXTRA_DATA 또한 필수 항목은 아니나 Intent에 데이터를 추가하기에
        //회사 홈페이지의 주소를 전달해주려합니다.
        MatrixCursor cursor = new MatrixCursor(
                new String[]{
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_TEXT_2,
                        SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA
                });

        //SearchView에 입력된 텍스트는 uri의 마지막 인자로 옵니다.
        String query = uri.getLastPathSegment().toLowerCase();
        String[] companyNames = getContext().getResources().getStringArray(R.array.company_names);
        int columnId = 0;

        for (String companyName : companyNames) {
            if (companyName.toLowerCase().contains(query.toLowerCase())) {
                String companyUrl = getCompanyUrl(companyName);

                //ArrauList 객체를 만들어서 MatrixCursor 객체에 row로 추가합니다.
                //주의하실 점이 MatrixCursor 객체를 만들때 지정했던 column의 순서에 맞게 해당 값을 추가하셔야합니다.
                ArrayList<String> row = new ArrayList<>();
                row.add(String.valueOf(columnId));
                row.add(companyName);
                row.add(companyUrl);
                row.add(companyUrl);

                //만든 row를 MatrixCursor객체에 추가합니다.
                cursor.addRow(row);

                columnId++;
            }
        }

        return cursor;
    }

    private String getCompanyUrl(String companyName) {
        switch (companyName) {
            case "Google":
                return "https://www.google.co.kr/";
            case "Apple":
                return "https://www.apple.com/kr/";
            case "Microsoft":
                return "https://www.microsoft.com/ko-kr";
            case "Oracle":
                return "https://www.oracle.com/kr/index.html";
            case "Facebook":
                return "https://ko-kr.facebook.com/";
            case "Amazon":
                return "https://www.amazon.com/";
            case "Netflix":
                return "https://www.netflix.com/kr/";
            case "Twitter":
                return "https://twitter.com/?lang=ko";
            case "Kakao":
                return "https://www.kakaocorp.com/";
            case "Naver":
                return "https://www.naver.com/";
            case "AMD":
                return "https://www.amd.com/ko";
            case "Intel":
                return "https://www.intel.co.kr/content/www/kr/ko/homepage.html";
            case "Nvidia":
                return "www.nvidia.co.kr/Download/index.aspx?lang=kr";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
