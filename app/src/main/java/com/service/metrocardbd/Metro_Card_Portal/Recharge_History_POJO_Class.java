package com.service.metrocardbd.Metro_Card_Portal;

public class Recharge_History_POJO_Class {

    String recharge_Amount,rechargeMethod,accountNo,recharge_date,txn_id;

    public Recharge_History_POJO_Class() {
    }

    public Recharge_History_POJO_Class(String recharge_Amount, String rechargeMethod, String accountNo, String recharge_date, String txn_id) {
        this.recharge_Amount = recharge_Amount;
        this.rechargeMethod = rechargeMethod;
        this.accountNo = accountNo;
        this.recharge_date = recharge_date;
        this.txn_id = txn_id;
    }

    public String getRecharge_Amount() {
        return recharge_Amount;
    }

    public void setRecharge_Amount(String recharge_Amount) {
        this.recharge_Amount = recharge_Amount;
    }

    public String getRechargeMethod() {
        return rechargeMethod;
    }

    public void setRechargeMethod(String rechargeMethod) {
        this.rechargeMethod = rechargeMethod;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getRecharge_date() {
        return recharge_date;
    }

    public void setRecharge_date(String recharge_date) {
        this.recharge_date = recharge_date;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }
}
