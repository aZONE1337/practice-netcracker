package ru.netcracker.practice;

import ru.netcracker.practice.buildings.dwelling.Flat;
import ru.netcracker.practice.buildings.dwelling.hotel.HotelFloor;
import ru.netcracker.practice.buildings.office.Office;
import ru.netcracker.practice.buildings.office.OfficeBuilding;
import ru.netcracker.practice.buildings.office.OfficeFloor;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        System.out.println(Buildings.inputBuilding(new FileInputStream(new File("src/main/resources/buildings.txt")),
                OfficeBuilding.class, HotelFloor.class, Flat.class));


//        HashSet<Track> tracks = new HashSet<>();
//        HashSet<Album> albums = new HashSet<>();
//        HashSet<Artist> artists = new HashSet<>();
//        for (int k = 0; k < 1000000; k++) {
//            artists.add(new Artist(albums));
//            for (int i = 0; i < 1000; i++) {
//                albums.add(new Album(tracks));
//                for (int j = 0; j < 100; j++) {
//                    tracks.add(new Track("1" + j));
//                }
//            }
//        }
//
//        long cur = System.currentTimeMillis();
        
    }

//    public static class Artist {
//        private HashSet<Album> albums;
//
//        public Artist(HashSet<Album> albums) {
//            this.albums = albums;
//        }
//
//        public HashSet<Album> getAlbums() {
//            return albums;
//        }
//
//        public void setAlbums(HashSet<Album> albums) {
//            this.albums = albums;
//        }
//    }
//
//    public static class Album {
//        private HashSet<Track> tracks;
//
//        public Album(HashSet<Track> tracks) {
//            this.tracks = tracks;
//        }
//
//        public HashSet<Track> getTracks() {
//            return tracks;
//        }
//
//        public void setTracks(HashSet<Track> tracks) {
//            this.tracks = tracks;
//        }
//    }
//
//    public static class Track {
//        private String name;
//
//        public Track(String name) {
//            this.name = name;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
}
