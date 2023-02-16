package com.example.ode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author yilin
 * @email ${email}
 * @date 2023-02-16 19:42:51
 */
@Data
@TableName("t_flavor")
public class FlavorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自动生成的id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 *  用户id
	 */
	private Long userId;
	/**
	 * 辣度（0：不辣，1：微辣，：2：中辣，3：特辣）
	 */
	private Integer spicy;
	/**
	 * 口味（0：淡，1：中等，2：重口）
	 */
	private Integer salt;
	/**
	 * 食量（0：小，1：中等，2：大）
	 */
	private Integer size;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 修改时间
	 */
	private Date updTime;
	/**
	 * 备用
	 */
	private String extra;

}
