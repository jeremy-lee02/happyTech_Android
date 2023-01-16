package com.example.happytechhomepageui.repo;

import com.example.happytechhomepageui.Modals.Order;

import java.util.List;

public interface FireBaseCallbackOrder {
    void onCallback(List<Order> list);
}