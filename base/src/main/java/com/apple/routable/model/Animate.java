package com.apple.routable.model;

import com.market.R;

/**
 * Created by apple_hsp on 17/6/19.
 */

public enum Animate {
    NONE,
    PUSH(R.anim.wj_push_open, R.anim.wj_push_close),
    MODAL(R.anim.wj_modal_open, R.anim.wj_modal_close);

    private int openAnimValue;
    private int closeAnimValue;

    private Animate(int openAnimValue, int closeAnimValue) {
        this.openAnimValue = openAnimValue;
        this.closeAnimValue = closeAnimValue;
    }

    private Animate() {
    }

    public int getOpenAnimValue() {
        return this.openAnimValue;
    }

    public int getCloseAnimValue() {
        return this.closeAnimValue;
    }
}
