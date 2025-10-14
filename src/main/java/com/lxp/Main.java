package com.lxp;

import com.lxp.component.ApplicationContext;
import com.lxp.component.DatabaseInitializer;
import com.lxp.util.CLIRouter;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        CLIRouter router = ApplicationContext.getRouter();
        router.start();
    }
}
