package io.github.bilirecommand.ui.function;

import io.github.bilirecommand.api.SimpleCallback;
import io.github.bilirecommand.entity.enumeration.HandleType;

public interface ReadyTaskInfoFunction {

    void processSingleVideo(String id, HandleType handleType, SimpleCallback callback);
}
