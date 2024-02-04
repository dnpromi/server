package com.dp.cointracker3.model;

import java.util.List;

public class Transaction {
    private String hash;
    private int ver;
    private int vin_sz;
    private int vout_sz;
    private String lock_time;
    private int size;
    private String relayed_by;
    private int block_height;
    private String tx_index;
    private List<TransactionInput> inputs;
    private List<TransactionOutput> out;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getVin_sz() {
        return vin_sz;
    }

    public void setVin_sz(int vin_sz) {
        this.vin_sz = vin_sz;
    }

    public int getVout_sz() {
        return vout_sz;
    }

    public void setVout_sz(int vout_sz) {
        this.vout_sz = vout_sz;
    }

    public String getLock_time() {
        return lock_time;
    }

    public void setLock_time(String lock_time) {
        this.lock_time = lock_time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRelayed_by() {
        return relayed_by;
    }

    public void setRelayed_by(String relayed_by) {
        this.relayed_by = relayed_by;
    }

    public int getBlock_height() {
        return block_height;
    }

    public void setBlock_height(int block_height) {
        this.block_height = block_height;
    }

    public String getTx_index() {
        return tx_index;
    }

    public void setTx_index(String tx_index) {
        this.tx_index = tx_index;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<TransactionInput> inputs) {
        this.inputs = inputs;
    }

    public List<TransactionOutput> getOut() {
        return out;
    }

    public void setOut(List<TransactionOutput> out) {
        this.out = out;
    }
}
