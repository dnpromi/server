package com.dp.cointracker3.model;

public class TransactionInputPrevOut {
    private String hash;
    private String value;
    private String tx_index;
    private String n;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTx_index() {
        return tx_index;
    }

    public void setTx_index(String tx_index) {
        this.tx_index = tx_index;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}
