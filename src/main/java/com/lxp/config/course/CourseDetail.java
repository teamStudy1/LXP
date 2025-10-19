// domain/course/CourseDetail.java
package com.lxp.config.course;

import java.time.Instant;

/**
 * @param courseId      1. 식별자 (Course AR의 ID를 공유) PK 겸 FK 역할
 * @param content       2. 상세 내용
 * @param contentDetail content와 contentDetail이 DB 컬럼에 대응됨
 * @param createdAt     3. 메타데이터 (언제 생성/업데이트 되었는지)
 */
record CourseDetail(String sb, Long courseId, String content, String contentDetail, Instant createdAt, Instant updatedAt)

{
    // [규칙]: 필수 정보 검증은 Service/Constructor에서 처리 (생략)

    // 5. VO 필수: equals()와 hashCode() 구현 (생략)
}