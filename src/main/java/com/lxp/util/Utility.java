
//Course 총 시간 계산 메서드

package com.lxp.util;

public class Utility {
    static public String getFormattedDuration(int totalSeconds) {
        // 1. totalSeconds 값을 초단위로.

        // 2. 시간 계산 (3600초 = 1시간)
        int hours = totalSeconds / 3600;

        // 3. 남은 초 계산 (나머지 연산)
        int remainingSeconds = totalSeconds % 3600;

        // 4. 분 계산 (60초 = 1분)
        int minutes = remainingSeconds / 60;

        // 5. 문자열로 조합하여 반환
        StringBuilder sb = new StringBuilder();

        if (hours > 0)
            sb.append(hours).append("시간");
        if (minutes > 0) { // 분이 표시될 때만 공백 추가
                sb.append(" ");
        }

        // 분이 0보다 크거나, 시간이 0이고 분이라도 1분 이상일 경우에만 분을 표시
        if (minutes > 0) {
            sb.append(minutes).append("분");
        }

        // 총 시간이 0초일 경우 (빈 강좌)
        if (sb.isEmpty()) {
            return "0";
        }

        return sb.toString().trim();
    }
}
