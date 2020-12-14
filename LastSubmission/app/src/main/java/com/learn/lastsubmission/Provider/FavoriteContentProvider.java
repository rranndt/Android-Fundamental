package com.learn.lastsubmission.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.learn.lastsubmission.Database.GithubHelper;

import static com.learn.lastsubmission.Database.DatabaseContract.AUTHORITY;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.CONTENT_URI;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.TABLE_NAME;

public class FavoriteContentProvider extends ContentProvider {

    private GithubHelper githubHelper;
    private static final int GITHUB = 0;
    private static final int GITHUB_ID = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, GITHUB);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                GITHUB_ID);
    }

    public FavoriteContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        githubHelper = GithubHelper.getInstance(getContext());
        githubHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case GITHUB:
                cursor = githubHelper.queryAll();
                break;
            case GITHUB_ID:
                cursor = githubHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long added;
        if (sUriMatcher.match(uri) == GITHUB) {
            added = githubHelper.insert(values);
        } else {
            added = 0;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int updated;
        switch (sUriMatcher.match(uri)) {
            case GITHUB_ID:
                updated = githubHelper.update(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return updated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case GITHUB_ID:
                deleted = githubHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }
}