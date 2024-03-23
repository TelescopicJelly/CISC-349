package edu.harrisburgu.cisc349.dynamic_list_view;

import org.json.JSONException;
import org.json.JSONObject;

public class HolidaySongs {

    private String album_img;
    private String album_name;
    private String artist_name;
    private Double danceability;
    private Integer duration_ms;
    private String playlist_img;

    public HolidaySongs(JSONObject jsonObject) {
        try {
            this.album_img = jsonObject.getString("album_img");
            this.album_name = jsonObject.getString("album_name");
            this.artist_name = jsonObject.getString("artist_name");
            this.danceability = jsonObject.getDouble("danceability");
            this.duration_ms = jsonObject.getInt("duration_ms");
            this.playlist_img = jsonObject.getString("playlist_img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getAlbum_img() {return album_img;}

    public String getAlbum_name() {return album_name;}

    public String getArtist_name() {return artist_name;}

    public Double getDanceability() {return danceability;}

    public Integer getDuration_ms() {return duration_ms;}

    public String getPlaylist_img() {return playlist_img;}

}
