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
	 * 返回结果
	 */
	@TableField(value="result_json")
	private String resultJson;
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
	@TableField(value="userId")
	private Integer userid;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
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

	@Override
	public String toString() {
		return "Photo{" +
				"id=" + id +
				", resultJson='" + resultJson + '\'' +
				", name='" + name + '\'' +
				", createTime=" + createTime +
				", userid=" + userid +
				'}';
	}
}
