package com.robbe.lib.interfaces;


import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import javax.annotation.Nullable;

public interface ReadableCustomMap extends ReadableMap {
    Callback getCallback(@Nullable String name);
}