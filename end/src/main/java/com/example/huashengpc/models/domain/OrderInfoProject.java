package com.example.huashengpc.models.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName order_info_project
 */
@TableName(value ="order_info_project")
@Data
public class OrderInfoProject implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long projectId;

    /**
     * 
     */
    private String openId;

    /**
     * 
     */
    private Integer isToken;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private Integer audit;

    /**
     * 
     */
    private String addition;

    /**
     * 
     */
    private String invoiceTitle;

    /**
     * 
     */
    private String taxNumber;

    /**
     * 
     */
    private String bank;

    /**
     * 
     */
    private String bankNumber;

    /**
     * 
     */
    private String bankPhone;

    /**
     * 
     */
    private String companyAddress;

    /**
     * 
     */
    private String companyStatus;

    /**
     * 
     */
    private Float total;

    /**
     * 
     */
    private Integer downloadStatus;

    /**
     * ????
     */
    private String orderPicture;

    /**
     * 
     */
    private Date orderTime;

    /**
     * 
     */
    private String additionMessage;

    /**
     * 
     */
    private Date payTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderInfoProject other = (OrderInfoProject) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getIsToken() == null ? other.getIsToken() == null : this.getIsToken().equals(other.getIsToken()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getAudit() == null ? other.getAudit() == null : this.getAudit().equals(other.getAudit()))
            && (this.getAddition() == null ? other.getAddition() == null : this.getAddition().equals(other.getAddition()))
            && (this.getInvoiceTitle() == null ? other.getInvoiceTitle() == null : this.getInvoiceTitle().equals(other.getInvoiceTitle()))
            && (this.getTaxNumber() == null ? other.getTaxNumber() == null : this.getTaxNumber().equals(other.getTaxNumber()))
            && (this.getBank() == null ? other.getBank() == null : this.getBank().equals(other.getBank()))
            && (this.getBankNumber() == null ? other.getBankNumber() == null : this.getBankNumber().equals(other.getBankNumber()))
            && (this.getBankPhone() == null ? other.getBankPhone() == null : this.getBankPhone().equals(other.getBankPhone()))
            && (this.getCompanyAddress() == null ? other.getCompanyAddress() == null : this.getCompanyAddress().equals(other.getCompanyAddress()))
            && (this.getCompanyStatus() == null ? other.getCompanyStatus() == null : this.getCompanyStatus().equals(other.getCompanyStatus()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()))
            && (this.getDownloadStatus() == null ? other.getDownloadStatus() == null : this.getDownloadStatus().equals(other.getDownloadStatus()))
            && (this.getOrderPicture() == null ? other.getOrderPicture() == null : this.getOrderPicture().equals(other.getOrderPicture()))
            && (this.getOrderTime() == null ? other.getOrderTime() == null : this.getOrderTime().equals(other.getOrderTime()))
            && (this.getAdditionMessage() == null ? other.getAdditionMessage() == null : this.getAdditionMessage().equals(other.getAdditionMessage()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getIsToken() == null) ? 0 : getIsToken().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getAudit() == null) ? 0 : getAudit().hashCode());
        result = prime * result + ((getAddition() == null) ? 0 : getAddition().hashCode());
        result = prime * result + ((getInvoiceTitle() == null) ? 0 : getInvoiceTitle().hashCode());
        result = prime * result + ((getTaxNumber() == null) ? 0 : getTaxNumber().hashCode());
        result = prime * result + ((getBank() == null) ? 0 : getBank().hashCode());
        result = prime * result + ((getBankNumber() == null) ? 0 : getBankNumber().hashCode());
        result = prime * result + ((getBankPhone() == null) ? 0 : getBankPhone().hashCode());
        result = prime * result + ((getCompanyAddress() == null) ? 0 : getCompanyAddress().hashCode());
        result = prime * result + ((getCompanyStatus() == null) ? 0 : getCompanyStatus().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        result = prime * result + ((getDownloadStatus() == null) ? 0 : getDownloadStatus().hashCode());
        result = prime * result + ((getOrderPicture() == null) ? 0 : getOrderPicture().hashCode());
        result = prime * result + ((getOrderTime() == null) ? 0 : getOrderTime().hashCode());
        result = prime * result + ((getAdditionMessage() == null) ? 0 : getAdditionMessage().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", openId=").append(openId);
        sb.append(", isToken=").append(isToken);
        sb.append(", status=").append(status);
        sb.append(", audit=").append(audit);
        sb.append(", addition=").append(addition);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", taxNumber=").append(taxNumber);
        sb.append(", bank=").append(bank);
        sb.append(", bankNumber=").append(bankNumber);
        sb.append(", bankPhone=").append(bankPhone);
        sb.append(", companyAddress=").append(companyAddress);
        sb.append(", companyStatus=").append(companyStatus);
        sb.append(", total=").append(total);
        sb.append(", downloadStatus=").append(downloadStatus);
        sb.append(", orderPicture=").append(orderPicture);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", additionMessage=").append(additionMessage);
        sb.append(", payTime=").append(payTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}