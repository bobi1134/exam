package cn.mrx.exam.utils;

/**
 * @Author Administrator
 * @Date 2016-08-25
 */
public class ParamEntity {

    private String name;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ParamEntity() {
    }

    public ParamEntity(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ParamEntity{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
