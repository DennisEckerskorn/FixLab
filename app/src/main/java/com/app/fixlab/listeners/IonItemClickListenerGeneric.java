package com.app.fixlab.listeners;

/**
 * Interface which can be used for all Clients, Technicians or Devices clicks
 * @param <T> Person or Device
 */
public interface IonItemClickListenerGeneric<T> {
    void onItemClick(T item);
}
