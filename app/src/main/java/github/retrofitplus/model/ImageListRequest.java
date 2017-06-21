package github.retrofitplus.model;

import java.util.List;

import github.library.utils.INoProguard;


public class ImageListRequest {


    public static class Res implements INoProguard {


        public boolean error;
        public List<Item> results;

        public static class Item implements INoProguard {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String source;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }


    }
}
