/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.TransactionBean;
import entities.PaymentStatus;
import entities.PaymentTransaction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 */
@Named
@SessionScoped
public class TransactionJSFBean implements Serializable {
    
    @EJB
    private TransactionBean transactionBean;
    
    private String payer_email;
    private String payee_email;
    private String currency;
    private BigDecimal amount;
    private List<PaymentTransaction> transactions;
    private PaymentStatus pending = PaymentStatus.PENDING;
    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentStatus getPending() {
        return pending;
    }
    
    
    public List<PaymentTransaction> getTransactions() {        
        return transactions;
    }

    public String showTransaction(){
        transactions = transactionBean.showAllTransactions();
        return "transaction";
    }
    
    public List<PaymentTransaction> userTransactions(String user_email){
        return transactionBean.getUserTransactions(user_email);
    }
    public void setTransactions(List<PaymentTransaction> transactions) {
        this.transactions = transactions;
    }
    
    public String pay(String user_email){
        this.payer_email = user_email;
        transactionBean.submitPayment(this.payer_email, this.payee_email, amount, currency);
        //return "transfer_confirmation";
        return "show";
    }
    
    public String request(String user_email){
        this.payee_email = user_email;
        transactionBean.requestPayment(this.payer_email, this.payee_email, amount, currency);
        return "show";
    }
    
    public String accept(Long id){
        transactionBean.approvePendingTransaction(id);
        return "show";
    }
    
    public String reject(Long id){
        transactionBean.cancelTransaction(id);
        return "show";
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public String getPayee_email() {
        return payee_email;
    }

    public void setPayee_email(String payee_email) {
        this.payee_email = payee_email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    
}