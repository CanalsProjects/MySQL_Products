package canalsprojects.mysql_products;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by knals on 30/08/2014.
 */
public class MySuggestionProvider {

    public final static String AUTHORITY = "com.example.MySuggestionProvider";
    public final static int MODE = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES | SearchRecentSuggestionsProvider.DATABASE_MODE_2LINES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    private void setupSuggestions(String authority, int mode) {

    }


}
