package cn.mrx.exam.youtu.pojo.detectface;

import java.util.Arrays;

/**
 * @ClassName: FaceShape
 * @Author: Mr.X
 * @Date: 2017/3/30 11:07
 * @Description:
 * @Version 1.0
 */
public class FaceShape {
    private String[] right_eye;
    private String[] nose;
    private String[] face_profile;
    private String[] mouth;
    private String[] left_eye;
    private String[] right_eyebrow;
    private String[] left_eyebrow;

    public String[] getRight_eye() {
        return right_eye;
    }

    public void setRight_eye(String[] right_eye) {
        this.right_eye = right_eye;
    }

    public String[] getNose() {
        return nose;
    }

    public void setNose(String[] nose) {
        this.nose = nose;
    }

    public String[] getFace_profile() {
        return face_profile;
    }

    public void setFace_profile(String[] face_profile) {
        this.face_profile = face_profile;
    }

    public String[] getMouth() {
        return mouth;
    }

    public void setMouth(String[] mouth) {
        this.mouth = mouth;
    }

    public String[] getLeft_eye() {
        return left_eye;
    }

    public void setLeft_eye(String[] left_eye) {
        this.left_eye = left_eye;
    }

    public String[] getRight_eyebrow() {
        return right_eyebrow;
    }

    public void setRight_eyebrow(String[] right_eyebrow) {
        this.right_eyebrow = right_eyebrow;
    }

    public String[] getLeft_eyebrow() {
        return left_eyebrow;
    }

    public void setLeft_eyebrow(String[] left_eyebrow) {
        this.left_eyebrow = left_eyebrow;
    }

    @Override
    public String toString() {
        return "FaceShape{" +
                "right_eye=" + Arrays.toString(right_eye) +
                ", nose=" + Arrays.toString(nose) +
                ", face_profile=" + Arrays.toString(face_profile) +
                ", mouth=" + Arrays.toString(mouth) +
                ", left_eye=" + Arrays.toString(left_eye) +
                ", right_eyebrow=" + Arrays.toString(right_eyebrow) +
                ", left_eyebrow=" + Arrays.toString(left_eyebrow) +
                '}';
    }
}
