package com.echeck.echeck;

import java.util.List;

public class JsonBean {

    /**
     * errorCode : 40001
     * Message : 获取玩具信息成功
     * result : {"ageInfo":[{"id":"1","age":"0-2"},{"id":"2","age":"2-4"},{"id":"3","age":"4-6"},{"id":"4","age":"6-8"},{"id":"5","age":"8-10"},{"id":"6","age":"10-12"},{"id":"7","age":"12-14"},{"id":"8","age":"14-16"}],"toyInfo":[{"id":"1","type_name":"视觉与艺术类","son":[{"id":"2","name":"猪仔","type_id":"1","age_id":"2","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f080cd3cfe.jpg"]},{"id":"10","name":"猫猫","type_id":"1","age_id":"1","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0dc5a6b3a.jpg"]}]},{"id":"2","type_name":"角色与交流类","son":[{"id":"13","name":"蛇蛇","type_id":"2","age_id":"1","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0e7f560c9.jpg"]}]},{"id":"3","type_name":"构建与空间类","son":[{"id":"1","name":"阿虎","type_id":"3","age_id":"1","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0770c28c7.jpg"]}]},{"id":"4","type_name":"逻辑与数学类","son":[{"id":"3","name":"鼠仔","type_id":"4","age_id":"3","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0823348d2.jpg"]},{"id":"4","name":"龙仔","type_id":"4","age_id":"4","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f08318907a.jpg"]},{"id":"5","name":"蛇仔","type_id":"4","age_id":"5","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0842577d1.jpg"]},{"id":"6","name":"猫仔","type_id":"4","age_id":"6","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f085092868.jpg"]},{"id":"7","name":"工仔","type_id":"4","age_id":"8","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f08617aac0.jpg"]},{"id":"8","name":"ll仔","type_id":"4","age_id":"7","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f088c07a9a.jpg"]},{"id":"12","name":"龙龙","type_id":"4","age_id":"1","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0e607eb26.jpg"]}]},{"id":"5","type_name":"自然与科学类","son":[{"id":"11","name":"猪猪","type_id":"5","age_id":"1","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0e00416b7.jpg"]},{"id":"14","name":"猪猪","type_id":"5","age_id":"2","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f1eedee632.jpg"]}]}]}
     */

    private int errorCode;
    private String Message;
    private ResultBean result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<AgeInfoBean> ageInfo;
        private List<ToyInfoBean> toyInfo;

        public List<AgeInfoBean> getAgeInfo() {
            return ageInfo;
        }

        public void setAgeInfo(List<AgeInfoBean> ageInfo) {
            this.ageInfo = ageInfo;
        }

        public List<ToyInfoBean> getToyInfo() {
            return toyInfo;
        }

        public void setToyInfo(List<ToyInfoBean> toyInfo) {
            this.toyInfo = toyInfo;
        }

        public static class AgeInfoBean {
            /**
             * id : 1
             * age : 0-2
             */

            private String id;
            private String age;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }
        }

        public static class ToyInfoBean {
            /**
             * id : 1
             * type_name : 视觉与艺术类
             * son : [{"id":"2","name":"猪仔","type_id":"1","age_id":"2","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f080cd3cfe.jpg"]},{"id":"10","name":"猫猫","type_id":"1","age_id":"1","imgurl":["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f0dc5a6b3a.jpg"]}]
             */

            private String id;
            private String type_name;
            private List<SonBean> son;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public List<SonBean> getSon() {
                return son;
            }

            public void setSon(List<SonBean> son) {
                this.son = son;
            }

            public static class SonBean {
                /**
                 * id : 2
                 * name : 猪仔
                 * type_id : 1
                 * age_id : 2
                 * imgurl : ["http://xs.eqask.com:8060/Public./Uploads/Toy/2018-07-18/5b4f080cd3cfe.jpg"]
                 */

                private String id;
                private String name;
                private String type_id;
                private String age_id;
                private List<String> imgurl;

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

                public String getType_id() {
                    return type_id;
                }

                public void setType_id(String type_id) {
                    this.type_id = type_id;
                }

                public String getAge_id() {
                    return age_id;
                }

                public void setAge_id(String age_id) {
                    this.age_id = age_id;
                }

                public List<String> getImgurl() {
                    return imgurl;
                }

                public void setImgurl(List<String> imgurl) {
                    this.imgurl = imgurl;
                }
            }
        }
    }
}
