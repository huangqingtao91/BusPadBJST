package com.ltime.buspad.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YF001 on 2018/7/16.
 */

public class AdResult2 {


    /**
     * code : 0
     * msg : SUCCESS
     * data : [{"id":"6","name":"10","addtime":"1531728915","extension":"mp4","dirname":"http://192.168.180.1/Uploads/ad/","basename":"10.mp4","status":"1"}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 6
         * name : 10
         * addtime : 1531728915
         * extension : mp4
         * dirname : http://192.168.180.1/Uploads/ad/
         * basename : 10.mp4
         * status : 1
         */

        private String id;
        private String name;
        private String addtime;
        private String extension;
        private String dirname;
        private String basename;
        private String status;

        public DataBean(String addtime, String basename, String dirname, String extension, String id, String name, String status) {
            this.addtime = addtime;
            this.basename = basename;
            this.dirname = dirname;
            this.extension = extension;
            this.id = id;
            this.name = name;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
