package com.onur_celdir.a3ocaktest.audio.core;

public interface Callback {
    void onBufferAvailable(byte[] buffer);
}