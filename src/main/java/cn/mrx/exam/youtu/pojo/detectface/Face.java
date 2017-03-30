package cn.mrx.exam.youtu.pojo.detectface;

/**
 * @ClassName: Face
 * @Author: Mr.X
 * @Date: 2017/3/30 10:54
 * @Description:
 * @Version 1.0
 */
public class Face {

    private Boolean glass;
    private int expression;
    private FaceShape faceShape;
    private int gender;
    private int beauty;
    private int roll;
    private int yaw;
    private int x;
    private int y;
    private int width;
    private String face_id;
    private int pitch;
    private int age;
    private int height;

    public Boolean getGlass() {
        return glass;
    }

    public void setGlass(Boolean glass) {
        this.glass = glass;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getBeauty() {
        return beauty;
    }

    public void setBeauty(int beauty) {
        this.beauty = beauty;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getYaw() {
        return yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public FaceShape getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(FaceShape faceShape) {
        this.faceShape = faceShape;
    }

    @Override
    public String toString() {
        return "Face{" +
                "glass=" + glass +
                ", expression=" + expression +
                ", faceShape=" + faceShape +
                ", gender=" + gender +
                ", beauty=" + beauty +
                ", roll=" + roll +
                ", yaw=" + yaw +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", face_id='" + face_id + '\'' +
                ", pitch=" + pitch +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}
