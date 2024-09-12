package com.example.huashengpc.models.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName users_audit
 */
@TableName(value ="users_audit")
@Data
public class UsersAudit implements Serializable {
    /**
     * 自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信自己的id
     */
    private String openId;

    /**
     * 
     */
    private String telephone;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String address;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 纳税人识别
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
     * 企业性质
     */
    private String companyStatus;

    /**
     * 
     */
    private String phoneNum;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String unitname;

    /**
     * 
     */
    private Long orderId;

    /**
     * 
     */
    private Integer isAudit;

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
        UsersAudit other = (UsersAudit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getInvoiceTitle() == null ? other.getInvoiceTitle() == null : this.getInvoiceTitle().equals(other.getInvoiceTitle()))
            && (this.getTaxNumber() == null ? other.getTaxNumber() == null : this.getTaxNumber().equals(other.getTaxNumber()))
            && (this.getBank() == null ? other.getBank() == null : this.getBank().equals(other.getBank()))
            && (this.getBankNumber() == null ? other.getBankNumber() == null : this.getBankNumber().equals(other.getBankNumber()))
            && (this.getBankPhone() == null ? other.getBankPhone() == null : this.getBankPhone().equals(other.getBankPhone()))
            && (this.getCompanyAddress() == null ? other.getCompanyAddress() == null : this.getCompanyAddress().equals(other.getCompanyAddress()))
            && (this.getCompanyStatus() == null ? other.getCompanyStatus() == null : this.getCompanyStatus().equals(other.getCompanyStatus()))
            && (this.getPhoneNum() == null ? other.getPhoneNum() == null : this.getPhoneNum().equals(other.getPhoneNum()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getUnitname() == null ? other.getUnitname() == null : this.getUnitname().equals(other.getUnitname()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getIsAudit() == null ? other.getIsAudit() == null : this.getIsAudit().equals(other.getIsAudit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getInvoiceTitle() == null) ? 0 : getInvoiceTitle().hashCode());
        result = prime * result + ((getTaxNumber() == null) ? 0 : getTaxNumber().hashCode());
        result = prime * result + ((getBank() == null) ? 0 : getBank().hashCode());
        result = prime * result + ((getBankNumber() == null) ? 0 : getBankNumber().hashCode());
        result = prime * result + ((getBankPhone() == null) ? 0 : getBankPhone().hashCode());
        result = prime * result + ((getCompanyAddress() == null) ? 0 : getCompanyAddress().hashCode());
        result = prime * result + ((getCompanyStatus() == null) ? 0 : getCompanyStatus().hashCode());
        result = prime * result + ((getPhoneNum() == null) ? 0 : getPhoneNum().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getUnitname() == null) ? 0 : getUnitname().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getIsAudit() == null) ? 0 : getIsAudit().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", openId=").append(openId);
        sb.append(", telephone=").append(telephone);
        sb.append(", email=").append(email);
        sb.append(", address=").append(address);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", taxNumber=").append(taxNumber);
        sb.append(", bank=").append(bank);
        sb.append(", bankNumber=").append(bankNumber);
        sb.append(", bankPhone=").append(bankPhone);
        sb.append(", companyAddress=").append(companyAddress);
        sb.append(", companyStatus=").append(companyStatus);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", name=").append(name);
        sb.append(", unitname=").append(unitname);
        sb.append(", orderId=").append(orderId);
        sb.append(", isAudit=").append(isAudit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}