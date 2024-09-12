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
 * @TableName order_info_package
 */
@TableName(value ="order_info_package")
@Data
public class OrderInfoPackage implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long orderId;

    /**
     * 
     */
    private Long packageId;

    /**
     * 
     */
    private String openId;

    /**
     * 
     */
    private Integer isPay;

    /**
     * 
     */
    private Integer audit;

    /**
     * 
     */
    private Float price;

    /**
     * 
     */
    private Date joinTime;

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
    private Integer downloadCount;

    /**
     * 
     */
    private String orderTradeNo;

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
        OrderInfoPackage other = (OrderInfoPackage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getPackageId() == null ? other.getPackageId() == null : this.getPackageId().equals(other.getPackageId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getIsPay() == null ? other.getIsPay() == null : this.getIsPay().equals(other.getIsPay()))
            && (this.getAudit() == null ? other.getAudit() == null : this.getAudit().equals(other.getAudit()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getJoinTime() == null ? other.getJoinTime() == null : this.getJoinTime().equals(other.getJoinTime()))
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
            && (this.getDownloadCount() == null ? other.getDownloadCount() == null : this.getDownloadCount().equals(other.getDownloadCount()))
            && (this.getOrderTradeNo() == null ? other.getOrderTradeNo() == null : this.getOrderTradeNo().equals(other.getOrderTradeNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getPackageId() == null) ? 0 : getPackageId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getIsPay() == null) ? 0 : getIsPay().hashCode());
        result = prime * result + ((getAudit() == null) ? 0 : getAudit().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getJoinTime() == null) ? 0 : getJoinTime().hashCode());
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
        result = prime * result + ((getDownloadCount() == null) ? 0 : getDownloadCount().hashCode());
        result = prime * result + ((getOrderTradeNo() == null) ? 0 : getOrderTradeNo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", packageId=").append(packageId);
        sb.append(", openId=").append(openId);
        sb.append(", isPay=").append(isPay);
        sb.append(", audit=").append(audit);
        sb.append(", price=").append(price);
        sb.append(", joinTime=").append(joinTime);
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
        sb.append(", downloadCount=").append(downloadCount);
        sb.append(", orderTradeNo=").append(orderTradeNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}