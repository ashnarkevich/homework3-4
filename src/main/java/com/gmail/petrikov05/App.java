package com.gmail.petrikov05;

import com.gmail.petrikov05.service.HomeWorkService;
import com.gmail.petrikov05.service.impl.HomeWorkServiceImpl;

public class App {

    public static void main(String[] args) {
        HomeWorkService homeWorkService = new HomeWorkServiceImpl();
        homeWorkService.runTaskFirst();
        homeWorkService.runTaskSecond();

    }

}
