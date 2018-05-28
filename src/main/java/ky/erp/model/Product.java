/*
* Product.java 2012-09-03
*/
package ky.erp.model;

 import java.util.Date; 

/**
 * 
 * @description:	 TODO add description
 * @table product   
 * @version  Ver 1.0
 * @Date	 2017-03-07
 *
 */
public class Product extends BaseModel{
 
 
	 /**
     * 描述:id     
     * 字段: id  INT(10)  
     */	
	private Integer id;
 
	 /**
     * 描述:仓库sheet id     
     * 字段: sheet_id  INT(10)  
     */	
	private Integer sheetId;
 
	 /**
     * 描述:规格     
     * 字段: format  VARCHAR(64)  
     */	
	private String format;
 
	 /**
     * 描述:申请数量     
     * 字段: original_count  INT(10)  
     */	
	private Integer originalCount;
 
	 /**
     * 描述:实际数量     
     * 字段: real_count  INT(10)  
     */	
	private Integer realCount;
 
	 /**
     * 描述:单价     
     * 字段: price  FLOAT(12)  
     */	
	private Float price;
 
	 /**
     * 描述:备注     
     * 字段: remark  VARCHAR(200)  
     */	
	private String remark;
 
	 /**
     * 描述:创建时间     
     * 字段: create_time  TIME(8)  
     */	
	private Date createTime;
	//【非数据库字段，查询时使用】
	private Date createTimeBegin;
	//【非数据库字段，查询时使用】
	private Date createTimeEnd;
 

	public Product(){
	}

	public Product(
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
	
    
    
	public void setSheetId(Integer sheetId) {
		this.sheetId = sheetId;
	}
	
	public Integer getSheetId() {
		return this.sheetId;
	}
	
    
    
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getFormat() {
		return this.format;
	}
	
    
    
	public void setOriginalCount(Integer originalCount) {
		this.originalCount = originalCount;
	}
	
	public Integer getOriginalCount() {
		return this.originalCount;
	}
	
    
    
	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}
	
	public Integer getRealCount() {
		return this.realCount;
	}
	
    
    
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Float getPrice() {
		return this.price;
	}
	
    
    
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
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
    
    
 
}

