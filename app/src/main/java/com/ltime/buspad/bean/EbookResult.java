package com.ltime.buspad.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YF001 on 2018/5/22.
 */

public class EbookResult implements Serializable {


    /**
     * code : 0
     * msg : SUCCESS
     * data : [{"name":"fz.txt","title":"fz","path":"http://192.168.180.1/Uploads/ebook/fz.txt","type":"txt","id":0},{"name":"f.txt","title":"f","path":"http://192.168.180.1/Uploads/ebook/f.txt","type":"txt","id":1},{"title":"Wnmp以及本地版ltime使用说明.pdf","name":"Wnmp以及本地版ltime使用说明","path":"http://192.168.180.1/Uploads/ebook/Wnmp以及本地版ltime使用说明","type":"pdf","id":2},{"name":"TheZenOfCat.txt","title":"TheZenOfCat","path":"http://192.168.180.1/Uploads/ebook/TheZenOfCat.txt","type":"txt","id":3},{"name":"TheGiftOfLove.txt","title":"TheGiftOfLove","path":"http://192.168.180.1/Uploads/ebook/TheGiftOfLove.txt","type":"txt","id":4},{"name":"TacticsForJob-huntSuccess.txt","title":"TacticsForJob-huntSuccess","path":"http://192.168.180.1/Uploads/ebook/TacticsForJob-huntSuccess.txt","type":"txt","id":5},{"name":"RelishTheMoment.txt","title":"RelishTheMoment","path":"http://192.168.180.1/Uploads/ebook/RelishTheMoment.txt","type":"txt","id":6},{"name":"PleaseDressMeInRed.txt","title":"PleaseDressMeInRed","path":"http://192.168.180.1/Uploads/ebook/PleaseDressMeInRed.txt","type":"txt","id":7},{"name":"MyFather.txt","title":"MyFather","path":"http://192.168.180.1/Uploads/ebook/MyFather.txt","type":"txt","id":8},{"name":"JustForToday.txt","title":"JustForToday","path":"http://192.168.180.1/Uploads/ebook/JustForToday.txt","type":"txt","id":9},{"name":"EyesCanSpeak.txt","title":"EyesCanSpeak","path":"http://192.168.180.1/Uploads/ebook/EyesCanSpeak.txt","type":"txt","id":10},{"name":"CompanionshipOfBooks2222222.txt","title":"CompanionshipOfBooks2222222","path":"http://192.168.180.1/Uploads/ebook/CompanionshipOfBooks2222222.txt","type":"txt","id":11},{"name":"AGoodbyeKiss11111111.txt","title":"AGoodbyeKiss11111111","path":"http://192.168.180.1/Uploads/ebook/AGoodbyeKiss11111111.txt","type":"txt","id":12}]
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
         * name : fz.txt
         * title : fz
         * path : http://192.168.180.1/Uploads/ebook/fz.txt
         * type : txt
         * id : 0
         */

        private String name;
        private String title;
        private String path;
        private String type;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
