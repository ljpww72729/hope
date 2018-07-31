package cc.lkme.hope.data;

public class EmptyData {
    // 标识该数据为无效数据
    private boolean isInvalid = false;

    public boolean isInvalid() {
        return isInvalid;
    }

    public void setInvalid(boolean invalid) {
        isInvalid = invalid;
    }
}
