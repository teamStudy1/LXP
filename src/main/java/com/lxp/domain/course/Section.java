// domain/course/Section.java
package com.lxp.domain.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Section {

    // 1. 멤버 변수 영역: 필드는 클래스 블록의 맨 위에 모아둡니다.
    private final Long id;
    private String title;
    private final int sectionOrder;
    // Composition 관계: Lecture 목록을 소유
    private final List<Lecture> lectures = new ArrayList<>();

    // 2. 생성자
    public Section(Long id, String title, int sectionOrder) {
        this.id = id;
        this.title = title;
        this.sectionOrder = sectionOrder;
    }

    // --- 3. 비즈니스 기능 메서드 영역 ---

    // [섹션 수정 기능]: 이름 변경
    public void rename(String newTitle) {
        this.title = newTitle;
    }

    // [강의 추가]
    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    // [강의 삭제]
    public void removeLecture(Long lectureId) {
        this.lectures.removeIf(l -> lectureId.equals(l.getId()));
    }

    //  1. 순서 교환 기능 (Lecture 순서 변경 로직)
    public void swapLectureOrder(Long lectureId1, Long lectureId2) {
        // Lecture::getLectureOrder가 Lecture 클래스에 정의되어 있다고 가정합니다.

        // 1. 두 강의를 찾습니다.
        Lecture lec1 = findLectureById(lectureId1);
        Lecture lec2 = findLectureById(lectureId2);

        // 2. 순서를 서로 맞바꿉니다.
        int order1 = lec1.getLectureOrder();
        int order2 = lec2.getLectureOrder();

        lec1.changeOrder(order2); // Lecture는 자신의 값만 변경
        lec2.changeOrder(order1); // Section은 전체 순서 변경 로직을 책임
    }

    //  2. 특정 위치에 강의 삽입 (추가 생성 시 몇 번째에 넣을지)
    public void insertNewLecture(Lecture newLecture, int targetOrder) {
        // 1. targetOrder보다 크거나 같은 순서를 가진 모든 강의의 순서를 1씩 증가시킵니다.
        //    (뒤로 밀어내는 작업)
        for (Lecture lecture : lectures) {
            if (lecture.getLectureOrder() < targetOrder) {
                continue;
            }
            lecture.changeOrder(lecture.getLectureOrder() + 1);
        }

        // 2. 새 강의를 목표 위치에 삽입하고 목록에 추가합니다.
        newLecture.changeOrder(targetOrder);
        this.lectures.add(newLecture);

        // *참고: 목록 자체는 순서가 중요하지 않고, lectureOrder 필드를 사용해 정렬됩니다.
    }

    // 섹션 자체의 총 시간 계산
    public int calculateSectionDuration() {
        return this.lectures.stream().mapToInt(Lecture::getDuration).sum();
    }


    // --- 4. Getter 영역 ---

    public Long getId() {
        return id;
    }

    public int getSectionOrder() {
        return sectionOrder;
    }

    // 캡슐화: 컬렉션 불변 처리 후 반환 (외부에서 직접 add/remove 불가)
    public List<Lecture> getLectures() {
        return Collections.unmodifiableList(lectures);
    }

    // 헬퍼 메서드: 강의 ID로 Lecture 객체를 찾는 기능
    private Lecture findLectureById(Long lectureId) {
        return lectures.stream()
                .filter(l -> l.getId().equals(lectureId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다."));
    }
}