package com.co.ias.observatory.infrastructure.logSystem;

import java.time.LocalDate;

public class Logger {
    public static void log(LogStatus status, String message) {
        System.out.println(status.getLevel() + "\t " + LocalDate.now() + '\t' + message);
    }
}
