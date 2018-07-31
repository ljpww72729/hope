package cc.lkme.hope.data.snssdk;

import com.google.gson.annotations.SerializedName;

public class FilterWords {
    private String id;
    @SerializedName("is_selected")
    private String isSelected;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
