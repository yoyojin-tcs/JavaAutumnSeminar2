package ru.tinkoff.java.serminar;

import java.util.List;
import java.util.UUID;

public interface HistoryViewManager<T> {

    void addView(T uuid);

    List<T> getViewHistory();

}
