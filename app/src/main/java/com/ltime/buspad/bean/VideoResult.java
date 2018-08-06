package com.ltime.buspad.bean;

import java.util.List;

/**
 * Created by YF001 on 2018/4/20.
 */

public class VideoResult {

    /**
     * code : 0
     * msg : SUCCESS
     * data : [{"dirname":"http://192.168.160.1/Uploads/video/D","basename":"[130914] KARA.mp4","extension":"mp4","filename":"[130914] KARA","fileimg":"http://192.168.160.1/Uploads/video_img/[130914] KARA.jpg","duration":"00:03:07","category":"D"},{"dirname":"http://192.168.160.1/Uploads/video/D","basename":"[www.52pdahd.com]-1080.mp4","extension":"mp4","filename":"[www.52pdahd.com]-1080","fileimg":"http://192.168.160.1/Uploads/video_img/[www.52pdahd.com]-1080.jpg","duration":"00:03:42","category":"D"},{"dirname":"http://192.168.160.1/Uploads/video/D","basename":"16ok.mp4","extension":"mp4","filename":"16ok","fileimg":"http://192.168.160.1/Uploads/video_img/16ok.jpg","duration":"00:04:23","category":"D"},{"dirname":"http://192.168.160.1/Uploads/video/A","basename":"ApinkPetal29.mp4","extension":"mp4","filename":"ApinkPetal29","fileimg":"http://192.168.160.1/Uploads/video_img/ApinkPetal29.jpg","duration":"00:03:40","category":"A"},{"dirname":"http://192.168.160.1/Uploads/video/A","basename":"Apink - Wishlist.mp4","extension":"mp4","filename":"Apink - Wishlist","fileimg":"http://192.168.160.1/Uploads/video_img/Apink - Wishlist.jpg","duration":"00:03:34","category":"A"},{"dirname":"http://192.168.160.1/Uploads/video/A","basename":"I am a bird.mp4","extension":"mp4","filename":"I am a bird","fileimg":"http://192.168.160.1/Uploads/video_img/I am a bird.jpg","duration":"00:01:33","category":"A"}]
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
         * dirname : http://192.168.160.1/Uploads/video/D
         * basename : [130914] KARA.mp4
         * extension : mp4
         * filename : [130914] KARA
         * fileimg : http://192.168.160.1/Uploads/video_img/[130914] KARA.jpg
         * duration : 00:03:07
         * category : D
         */

        private String dirname;
        private String basename;
        private String extension;
        private String filename;
        private String fileimg;
        private String duration;
        private String category;

        public String getDirname() {
            return dirname;
        }

        public void setDirname(String dirname) {
            this.dirname = dirname;
        }

        public String getBasename() {
            return basename;
        }

        public void setBasename(String basename) {
            this.basename = basename;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFileimg() {
            return fileimg;
        }

        public void setFileimg(String fileimg) {
            this.fileimg = fileimg;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
