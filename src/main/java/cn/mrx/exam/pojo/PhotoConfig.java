package cn.mrx.exam.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

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
@TableName("t_photo_config")
public class PhotoConfig extends Model<PhotoConfig> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 开始时间
	 */
	@TableField(value="start_time")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@TableField(value="end_time")
	private Date endTime;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 是否为最新的采集,0表示false,1位true
	 */
	@TableField(value="isNew")
	private Integer isnew;
	/**
	 * 
	 */
	@TableField(value="userId")
	private Integer userid;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsnew() {
		return isnew;
	}

	public void setIsnew(Integer isnew) {
		this.isnew = isnew;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
