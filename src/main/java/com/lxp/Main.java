package com.lxp;

import com.lxp.config.JDBCConnection;
import com.lxp.config.TransactionManager;
import com.lxp.util.CLIRouter;

public class Main {
    public static void main(String[] args) {
        TransactionManager.DatabaseInitializer.initialize();
        CLIRouter router = JDBCConnection.ApplicationContext.getRouter();
        router.start();
    }
}
