package cc.lkme.hope.data.snssdk;

public class Token {
    /**
     * msg : success
     * data : {"access_token":"11153594728948918680671","expires_in":7776000}
     * ret : 0
     */

    private String msg;
    private DataBean data;
    private int ret;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public static class DataBean {
        /**
         * access_token : 11153594728948918680671
         * expires_in : 7776000
         */

        private String access_token;
        private int expires_in;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }
    }
}
