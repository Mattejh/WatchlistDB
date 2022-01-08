package org.matt.controllers;

import javafx.scene.Node;

public interface TableInterface {

    void createTable();
    Node createPage(int pageIndex);
    void handleRowSelect();

}
