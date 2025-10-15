package com.lxp.util;

import java.time.LocalTime;

/**
 * 시간 형식 변환을 도와주는 유틸리티 클래스입니다.
 */
public final class TimeConverter {
        /**
         * 이 클래스는 인스턴스로 만들 필요가 없으므로 생성자를 private으로 막습니다.
         */
        private TimeConverter() {
        }

        /**
         * "HH:mm:ss" 형식의 시간 문자열을 받아서 총 초(int)로 변환합니다.
         * @param timeString 데이터베이스에서 가져온 시간 문자열 (예: "00:15:30")
         * @return 총 시간(초) (예: 930)
         */
        public static int timeToSeconds(String timeString) {
            // 만약 DB에서 가져온 값이 null이거나 비어있으면 0을 반환
            if (timeString == null || timeString.trim().isEmpty()) {
                return 0;
            }

            // "HH:mm:ss" 형식의 문자열을 LocalTime 객체로 변환합니다.
            LocalTime localTime = LocalTime.parse(timeString);

            // LocalTime 객체를 하루의 시작(0시)부터 몇 초가 지났는지로 변환합니다.
            return localTime.toSecondOfDay();
        }

        /**
         * 총 시간(초)를 받아서 "HH:mm:ss" 형식의 보기 좋은 문자열로 변환합니다.
         * @param totalSeconds 계산된 총 시간(초) (예: 5432)
         * @return 화면에 표시할 시간 문자열 (예: "01:30:32")
         */
        public static String secondsToTimeString(int totalSeconds) {
            // 음수 값이 들어올 경우 0으로 처리합니다.
            if (totalSeconds < 0) {
                totalSeconds = 0;
            }

            // 총 초를 3600으로 나눠서 '시간'을 구합니다.
            int hours = totalSeconds / 3600;
            // 남은 초를 60으로 나눠서 '분'을 구합니다.
            int minutes = (totalSeconds % 3600) / 60;
            // 마지막으로 남은 '초'를 구합니다.
            int seconds = totalSeconds % 60;

            // String.format을 사용해 "HH:mm:ss" 형태로 깔끔하게 만듭니다.
            // %02d는 숫자를 두 자리로 표현하고, 비어있으면 앞에 0을 붙이라는 의미입니다.
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }
