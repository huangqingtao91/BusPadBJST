package com.ltime.buspad.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YF001 on 2018/5/22.
 */

public class MusicResult  implements Serializable {

    /**
     * code : 0
     * msg : SUCCESS
     * data : [{"file":"Uploads/audio/B/6.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"6\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackArtist":"吴亦凡\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackAlbum":"6\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"},{"file":"Uploads/audio/B/JULY.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"JULY\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackArtist":"吴亦凡\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackAlbum":"JULY\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"},{"file":"Uploads/audio/B/.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Unknown","trackArtist":"Unknown","trackAlbum":"Unknown"},{"file":"Uploads/audio/B/B.M..mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"B.M.\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackArtist":"吴亦凡\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackAlbum":"B.M.\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"},{"file":"Uploads/audio/B/Deserve （feat. Travis Scott）.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Deserve （feat. Travis Scott）","trackArtist":"吴亦凡\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackAlbum":"Deserve （feat. Travis Scott）"},{"file":"Uploads/audio/B/.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Unknown","trackArtist":"Unknown","trackAlbum":"Unknown"},{"file":"Uploads/audio/B/.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Unknown","trackArtist":"Unknown","trackAlbum":"Unknown"},{"file":"Uploads/audio/B/Juice.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Juice\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackArtist":"吴亦凡\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackAlbum":"6\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"},{"file":"Uploads/audio/B/.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Unknown","trackArtist":"Unknown","trackAlbum":"Unknown"},{"file":"Uploads/audio/B/Bad Girl.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Bad Girl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackArtist":"吴亦凡\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackAlbum":"Bad Girl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"},{"file":"Uploads/audio/B/Lullaby.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Lullaby\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackArtist":"吴亦凡\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000","trackAlbum":"6\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"},{"file":"Uploads/audio/B/.mp3","thumb":"public/res/audio/thumbs/01.jpg","trackName":"Unknown","trackArtist":"Unknown","trackAlbum":"Unknown"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * file : Uploads/audio/B/6.mp3
         * thumb : public/res/audio/thumbs/01.jpg
         * trackName : 6
         * trackArtist : 吴亦凡
         * trackAlbum : 6
         */

        private String file;
        private String thumb;
        private String trackName;
        private String trackArtist;
        private String trackAlbum;

        public DataBean(String file, String thumb, String trackName, String trackArtist,String trackAlbum) {
            this.file = file;
            this.thumb = thumb;
            this.trackName = trackName;
            this.trackArtist = trackArtist;
            this.trackAlbum = trackAlbum;

        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTrackName() {
            return trackName;
        }

        public void setTrackName(String trackName) {
            this.trackName = trackName;
        }

        public String getTrackArtist() {
            return trackArtist;
        }

        public void setTrackArtist(String trackArtist) {
            this.trackArtist = trackArtist;
        }

        public String getTrackAlbum() {
            return trackAlbum;
        }

        public void setTrackAlbum(String trackAlbum) {
            this.trackAlbum = trackAlbum;
        }
    }
}
