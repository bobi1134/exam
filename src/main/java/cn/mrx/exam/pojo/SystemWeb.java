package cn.mrx.exam.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-26
 */
@TableName("t_system_web")
public class SystemWeb extends Model<SystemWeb> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 关键字
	 */
	private String keywords;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 1:web, 2:admin
	 */
	private Integer category;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
