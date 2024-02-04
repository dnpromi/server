package com.dp.cointracker3.model;

public class TransactionInput {
    private TransactionInputPrevOut prev_out;
    private String script;

    public TransactionInputPrevOut getPrev_out() {
        return prev_out;
    }

    public void setPrev_out(TransactionInputPrevOut prev_out) {
        this.prev_out = prev_out;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
