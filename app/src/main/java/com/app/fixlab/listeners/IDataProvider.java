package com.app.fixlab.listeners;

import java.util.List;

public interface IDataProvider<T> {
    List<T> getData();
}
