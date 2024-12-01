package com.app.fixlab.listeners;

import com.app.fixlab.models.persons.Person;

public interface IOnItemRepairClickListener<T> {
    void onItemClickRepair(T item);
}
