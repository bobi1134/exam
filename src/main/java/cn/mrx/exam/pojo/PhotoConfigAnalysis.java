package cn.mrx.exam.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-24
 */
@TableName("t_photo_config_analysis")
public class PhotoConfigAnalysis extends Model<PhotoConfigAnalysis> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 面部表情
	 */
	private String face;
	/**
	 * 转向问题
	 */
	@TableField(value="turn_around")
	private String turnAround;
	/**
	 * 中途换人
	 */
	@TableField(value="change_people")
	private String changePeople;
	/**
	 * 属于哪个采集配置外键
	 */
	@TableField(value="photoConfig_id")
	private Integer photoconfigId;
	/**
	 * 属于哪个学生外键
	 */
	@TableField(value="student_id")
	private Integer studentId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getTurnAround() {
		return turnAround;
	}

	public void setTurnAround(String turnAround) {
		this.turnAround = turnAround;
	}

	public String getChangePeople() {
		return changePeople;
	}

	public void setChangePeople(String changePeople) {
		this.changePeople = changePeople;
	}

	public Integer getPhotoconfigId() {
		return photoconfigId;
	}

	public void setPhotoconfigId(Integer photoconfigId) {
		this.photoconfigId = photoconfigId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
