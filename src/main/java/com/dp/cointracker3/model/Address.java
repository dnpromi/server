package com.dp.cointracker3.model;

import java.util.List;

public class Address {
    private String hash160;
    private String address;
    private int n_tx;
    private int n_unredeemed;
    private long total_received;
    private long total_sent;
    private long final_balance;
    private List<Transaction> txs;

    public String getHash160() {
        return hash160;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getN_tx() {
        return n_tx;
    }

    public void setN_tx(int n_tx) {
        this.n_tx = n_tx;
    }

    public int getN_unredeemed() {
        return n_unredeemed;
    }

    public void setN_unredeemed(int n_unredeemed) {
        this.n_unredeemed = n_unredeemed;
    }

    public long getTotal_received() {
        return total_received;
    }

    public void setTotal_received(long total_received) {
        this.total_received = total_received;
    }

    public long getTotal_sent() {
        return total_sent;
    }

    public void setTotal_sent(long total_sent) {
        this.total_sent = total_sent;
    }

    public long getFinal_balance() {
        return final_balance;
    }

    public void setFinal_balance(long final_balance) {
        this.final_balance = final_balance;
    }

    public List<Transaction> getTxs() {
        return txs;
    }

    public void setTxs(List<Transaction> txs) {
        this.txs = txs;
    }
}
