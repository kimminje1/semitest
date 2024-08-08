-------------------------------------------------------------------------------
//AREA TABLE 

-- 테이블 삭제
DROP TABLE AREA;

-- 테이블 생성
CREATE TABLE AREA(
    AREA_NO NUMBER(2) NOT NULL,
    AREA_NAME VARCHAR2(20) NOT NULL  
);

-- 제약사항 수정
ALTER TABLE AREA
ADD CONSTRAINT AREA_NO_PK PRIMARY KEY(AREA_NO);

ALTER TABLE AREA
ADD CONSTRAINT AREA_NANE_UK UNIQUE(AREA_NAME);

-- 시퀀스 삭제
DROP SEQUENCE AREA_NO_SEQ;

-- 시퀀스 생성
CREATE SEQUENCE AREA_NO_SEQ
INCREMENT BY 1
START WITH 1;

-- 데이터 삽입
INSERT INTO AREA
(AREA_NO, AREA_NAME)
VALUES (AREA_NO_SEQ.NEXTVAL, '서울');

INSERT INTO AREA
(AREA_NO, AREA_NAME)
VALUES (AREA_NO_SEQ.NEXTVAL, '경기도');

-- 데이터 확인
SELECT *
FROM AREA;



-- 커밋
COMMIT;
-------------------------------------------------------------------------------