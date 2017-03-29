package cn.mrx.exam.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.beans.Transient;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-26
 */
@TableName("t_permission")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 权限uri
	 */
	private String uri;
	/**
	 * 权限名称
	 */
	@TableField(value="permission_name")
	private String permissionName;
	/**
	 * 现在id，1:父级菜单,2:二级菜单,3:普通菜单
	 */
	@TableField(value="now_id")
	private Integer nowId;
	/**
	 * 父菜单id，1:父级菜单,2:二级菜单,3:普通菜单
	 */
	@TableField(value="parent_id")
	private Integer parentId;

	/**
	 * 新增：选中已有的权限，不映射数据库
	 */
	@TableField(exist = false)
	private boolean flag;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public Integer getNowId() {
		return nowId;
	}

	public void setNowId(Integer nowId) {
		this.nowId = nowId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override

	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", uri='" + uri + '\'' +
				", permissionName='" + permissionName + '\'' +
				", nowId=" + nowId +
				", parentId=" + parentId +
				", flag=" + flag +
				'}';
	}
}
