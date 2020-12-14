package com.learn.favoriteapp.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.learn.favoriteapp.Database.DatabaseContract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.learn.favoriteapp.Database.DatabaseContract.GithubColumn.AVATAR;
import static com.learn.favoriteapp.Database.DatabaseContract.GithubColumn.ID;
import static com.learn.favoriteapp.Database.DatabaseContract.GithubColumn.URL;
import static com.learn.favoriteapp.Database.DatabaseContract.GithubColumn.USERNAME;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMain implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("login")
    private String login;

    @SerializedName("following_url")
    private String followingUrl;

    @SerializedName("followers_url")
    private String followersUrl;

    @SerializedName("repos_url")
    private String reposUrl;

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;

    public UserMain(Parcel in) {
        id = in.readInt();
        avatarUrl = in.readString();
        htmlUrl = in.readString();
        login = in.readString();
        followingUrl = in.readString();
        followersUrl = in.readString();
        reposUrl = in.readString();
        type = in.readString();
        url = in.readString();
    }

    public static final Creator<UserMain> CREATOR = new Creator<UserMain>() {
        @Override
        public UserMain createFromParcel(Parcel in) {
            return new UserMain(in);
        }

        @Override
        public UserMain[] newArray(int size) {
            return new UserMain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(avatarUrl);
        dest.writeString(htmlUrl);
        dest.writeString(login);
        dest.writeString(followingUrl);
        dest.writeString(followersUrl);
        dest.writeString(reposUrl);
        dest.writeString(type);
        dest.writeString(url);
    }

    public UserMain(Cursor cursor) {
        this.id = DatabaseContract.columnInt(cursor, ID);
        this.login = DatabaseContract.columnString(cursor, USERNAME);
        this.avatarUrl = DatabaseContract.columnString(cursor, AVATAR);
        this.htmlUrl = DatabaseContract.columnString(cursor, URL);
    }
}
