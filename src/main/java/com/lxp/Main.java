package com.lxp;

import com.lxp.config.ApplicationContext;
import com.lxp.config.DatabaseInitializer;
import com.lxp.util.CLIRouter;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        CLIRouter router = ApplicationContext.getRouter();
        router.start();
    }
}
