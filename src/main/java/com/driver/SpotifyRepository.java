package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User user = new User(name,mobile);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        Album album = new Album(title,artistName);
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        Song song = new Song(title,albumName,length);
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {

        Playlist playlist = new Playlist(title);
        List<Song> list = new ArrayList<>();
        for(Song song : songs)
        {
            if(song.getLength()==length)
                list.add(song);
        }
        playlistSongMap.put(playlist,list);

        for(User user : users)
        {
            if(user.getMobile().equals(mobile)){
                creatorPlaylistMap.put(user,playlist);
                List<User> userList = new ArrayList<>();
                userList.add(user);
                playlistListenerMap.put(playlist,userList);
                List<Playlist> play = new ArrayList<>();
                play.add(playlist);
                userPlaylistMap.put(user,play);
            }

        }
        return playlist;

    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception
    {
        Playlist playlist = new Playlist(title);
        List<Song> list = new ArrayList<>();
        for(String songtitle :songTitles) {
            for (Song song : songs) {
                if (song.getTitle().equals(songtitle))
                    list.add(song);
            }
        }
        playlistSongMap.put(playlist,list);

        for(User user : users)
        {
            if(user.getMobile().equals(mobile)){
                creatorPlaylistMap.put(user,playlist);
                List<User> userList = new ArrayList<>();
                userList.add(user);
                playlistListenerMap.put(playlist,userList);
                List<Playlist> play = new ArrayList<>();
                play.add(playlist);
                userPlaylistMap.put(user,play);
            }

        }
        return playlist;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {

        for(Playlist pl : playlistSongMap.keySet()) {
            if (pl.getTitle().equals(playlistTitle)) {
                for (User user : users) {
                    if (user.getMobile().equals(mobile)) {
                        List<User> userList = playlistListenerMap.get(pl);
                        userList.add(user);
                        playlistListenerMap.put(pl, userList);

                        List<Playlist> play = userPlaylistMap.get(user);
                        play.add(pl);
                        userPlaylistMap.put(user, play);

                        if (!creatorPlaylistMap.get(user).equals(pl) || !creatorPlaylistMap.containsKey(user))
                            creatorPlaylistMap.put(user, pl);
                    }
                }
                return pl;
            }
        }
        return new Playlist();
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

        //The user likes the given song. The corresponding artist of the song gets auto-liked
        //A song can be liked by a user only once. If a user tried to like a song multiple times, do nothing
        //However, an artist can indirectly have multiple likes from a user, if the user has liked multiple songs of that artist.
        //If the user does not exist, throw "User does not exist" exception
        //If the song does not exist, throw "Song does not exist" exception
        //Return the song after updating
        //song,list users
        for(User user : users)
        {
            if(user.getMobile().equals(mobile)) {

                for (Song song : songs) {
                    if (song.getTitle().equals(songTitle) && !song.isIsliked()) {
                        song.setIsliked(true);

                        String albumname = song.getAlbumName();


                        for (Album album : albums) {
                            if (album.getTitle().equals(albumname)) {
                                String artistName = album.getArtistName();


                                for (Artist artist : artists) {
                                    if (artist.getName().equals(artistName)) {
                                        artist.setLikes(artist.getLikes() + 1);
                                    }
                                }
                            }
                        }
                        return song;
                    }
                   if(songLikeMap.containsKey(song))
                   {
                       List<User> list = songLikeMap.get(song);
                       if (!list.contains(user)) list.add(user);
                       songLikeMap.put(song,list);
                   }
                   else
                   {
                       List<User> list = new ArrayList<>();
                       list.add(user);
                       songLikeMap.put(song,list);
                   }
                }

            }}

        return new Song();

    }

    public String mostPopularArtist() {
        int max = Integer.MIN_VALUE;
        String popularArtist = "";
        for(Artist artist: artists){
            if(artist.getLikes()>=max){
                max = artist.getLikes();
                popularArtist = artist.getName();
            }
        }
        return popularArtist;
    }

    public String mostPopularSong() {
        int max = Integer.MIN_VALUE;
        String popularSong = "";
        for(Song song: songs){
            if(song.getLikes()>=max){
                max = song.getLikes();
                popularSong = song.getTitle();
            }
        }
        return popularSong;
    }
}
