package com.lxp.util;

public class TimeConverter {
    /**
     * 소수점 형태의 시간(예: 13.5 시간)을 "X시간 Y분" 형식으로 변환합니다.
     * @param totalHours 시간 단위의 소수점 값
     * @return "X시간 Y분" 형식의 문자열
     */
    public static String getFormattedDurationFromHours(double totalHours) {
        if (totalHours < 0) {
            return "0분";
        }

        // 정수 부분(시간)과 소수 부분(분으로 변환할 부분)을 분리
        int hours = (int) totalHours;
        double fractionalPart = totalHours - hours;

        // 소수 부분을 분으로 변환 (0.5시간 -> 30분)
        int minutes = (int) Math.round(fractionalPart * 60);

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append("시간 ");
        }
        if (minutes > 0 || hours == 0) { // 분이 있거나, 시간이 0일때는 0분이라도 표시
            sb.append(minutes).append("분");
        }

        return sb.toString().trim();
    }
}