/*
* WarehouseSheet.java 2012-09-03
*/
package ky.erp.model;

 import java.util.Date; 

/**
 * 
 * @description:	 TODO add description
 * @table warehouse_sheet   
 * @version  Ver 1.0
 * @Date	 2017-03-07
 *
 */
public class WarehouseSheet extends BaseModel{
 
 
	 /**
     * 描述:id     
     * 字段: id  INT(10)  
     */	
	private Integer id;
 
	 /**
     * 描述:申请部门     
     * 字段: department  VARCHAR(64)  
     */	
	private String department;
 
	 /**
     * 描述:申请编号（自动生成）     
     * 字段: No  VARCHAR(32)  
     */	
	private String no;
 
	 /**
     * 描述:申请时间     
     * 字段: apply_time  TIME(8)  
     */	
	private Date applyTime;
	//【非数据库字段，查询时使用】
	private Date applyTimeBegin;
	//【非数据库字段，查询时使用】
	private Date applyTimeEnd;
 
	 /**
     * 描述:是否有效     
     * 字段: yn  TINYINT(3)  
     */	
	private Integer yn;
 
	 /**
     * 描述:表单状态     
     * 字段: state  TINYINT(3)  
     */	
	private Integer state;
 
	 /**
     * 描述:创建时间     
     * 字段: create_time  TIME(8)  
     */	
	private Date createTime;
	//【非数据库字段，查询时使用】
	private Date createTimeBegin;
	//【非数据库字段，查询时使用】
	private Date createTimeEnd;
 
	 /**
     * 描述:更新时间     
     * 字段: update_time  TIME(8)  
     */	
	private Date updateTime;
	//【非数据库字段，查询时使用】
	private Date updateTimeBegin;
	//【非数据库字段，查询时使用】
	private Date updateTimeEnd;
 
	 /**
     * 描述:创建人     
     * 字段: create_user  VARCHAR(32)  
     */	
	private String createUser;
 
	 /**
     * 描述:更新人     
     * 字段: update_user  VARCHAR(32)  
     */	
	private String updateUser;
 

	public WarehouseSheet(){
	}

	public WarehouseSheet(
		Integer id
	){
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
    
    
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartment() {
		return this.department;
	}
	
    
    
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	
    
    
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Date getApplyTime() {
		return this.applyTime;
	}
	
    public void setApplyTimeBegin(Date applyTimeBegin) {
		this.applyTimeBegin = applyTimeBegin;
	}
	
	public Date getApplyTimeBegin() {
		return this.applyTimeBegin;
	}
	
	public void setApplyTimeEnd(Date applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	
	public Date getApplyTimeEnd() {
		return this.applyTimeEnd;
	}	
    
    
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
	public Integer getYn() {
		return this.yn;
	}
	
    
    
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getState() {
		return this.state;
	}
	
    
    
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
    public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	
	public Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	
	public Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}	
    
    
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
    public void setUpdateTimeBegin(Date updateTimeBegin) {
		this.updateTimeBegin = updateTimeBegin;
	}
	
	public Date getUpdateTimeBegin() {
		return this.updateTimeBegin;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	
	public Date getUpdateTimeEnd() {
		return this.updateTimeEnd;
	}	
    
    
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getCreateUser() {
		return this.createUser;
	}
	
    
    
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public String getUpdateUser() {
		return this.updateUser;
	}
	
    
    
 
}

