package data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePojo {
    public List<Result> result;
}

class Result {
    public int id;
    public String name;
    public String icon;
    @SerializedName("sub-categories")
    public List<SubCategory> subCategories;

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", subCategories=" + subCategories +
                '}';
    }
}

class SubCategory {
    public int id;

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                '}';
    }
}
