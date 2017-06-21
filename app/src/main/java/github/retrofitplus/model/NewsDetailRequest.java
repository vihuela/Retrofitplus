package github.retrofitplus.model;

import java.util.ArrayList;

import github.library.utils.INoProguard;


public class NewsDetailRequest {


    public static class Res implements INoProguard {

        public String body;
        public String image_source;
        public String title;
        public String image;
        public String share_url;
        public ArrayList<String> js;
        public String ga_prefix;
        public ArrayList<String> images;
        public int type;
        public int id;
        public ArrayList<String> css;
    }
}
