package com.driver;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

    //Auto-wire will not work in this case, no need to change this and add autowire

    SpotifyRepository spotifyRepository = new SpotifyRepository();

    public User createUser(String name, String mobile){
       return spotifyRepository.createUser(name,mobile);
    }

    public Artist createArtist(String name) {
        return spotifyRepository.createArtist(name);
    }

    public Album createAlbum(String title, String artistName) {
        for(Artist artist : spotifyRepository.artistAlbumMap.keySet())
        {
            if(artist.getName().equals(artistName))
            {
                Album album =  spotifyRepository.createAlbum(title,artistName);
                List<Album> list = spotifyRepository.artistAlbumMap.get(artist);
                list.add(album);
                spotifyRepository.artistAlbumMap.put(artist,list);
                return album;
            }
        }
        Artist artist = spotifyRepository.createArtist(artistName);
        Album album = spotifyRepository.createAlbum(title,artistName);
        List<Album> list = new ArrayList<>();
        list.add(album);
        spotifyRepository.artistAlbumMap.put(artist,list);
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {

        for(Album album : spotifyRepository.albumSongMap.keySet()){
            if(album.getTitle().equals(albumName)) {
                List<Song> list = spotifyRepository.albumSongMap.get(album);
                Song song = spotifyRepository.createSong(title, albumName, length);
                list.add(song);
                spotifyRepository.albumSongMap.put(album,list);
                return song;
            }
        }

    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
      return   spotifyRepository.createPlaylistOnLength(mobile, title, length);
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {

        return spotifyRepository.createPlaylistOnName(mobile, title, songTitles);
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {

       return spotifyRepository.findPlaylist(mobile, playlistTitle);
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        return spotifyRepository.likeSong(mobile, songTitle);

    }

    public String mostPopularArtist() {
return spotifyRepository.mostPopularArtist();
    }

    public String mostPopularSong() {
return spotifyRepository.mostPopularSong();
    }

    public  boolean containsUser(String mobile){
        List<User> user = spotifyRepository.users;
        for(User u : user){
            if(u.getMobile().equals(mobile)) return  true;
        }
        return false;
    }
}
