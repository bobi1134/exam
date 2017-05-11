package cn.mrx.exam.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-06
 */
@TableName("t_photo")
public class Photo extends Model<Photo> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 返回人脸检测结果
	 */
	@TableField(value="result_detectface")
	private String resultDetectface;
	/**
	 * 返回五官定位结果
	 */
	@TableField(value="result_faceshape")
	private String resultFaceshape;
	/**
	 * 返回人脸对比结果
	 */
	@TableField(value="result_facecompare")
	private String resultFacecompare;
	/**
	 * 照片名
	 */
	private String name;
	/**
	 * 创建时间
	 */
	@TableField(value="create_time")
	private Date createTime;
	/**
	 * 外键用户id
	 */
	@TableField(value="user_id")
	private Integer userId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResultDetectface() {
		return resultDetectface;
	}

	public void setResultDetectface(String resultDetectface) {
		this.resultDetectface = resultDetectface;
	}

	public String getResultFaceshape() {
		return resultFaceshape;
	}

	public void setResultFaceshape(String resultFaceshape) {
		this.resultFaceshape = resultFaceshape;
	}

	public String getResultFacecompare() {
		return resultFacecompare;
	}

	public void setResultFacecompare(String resultFacecompare) {
		this.resultFacecompare = resultFacecompare;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
