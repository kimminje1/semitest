
//게시판정보
Drop Table Board_Info_Table;


Create Table Board_Info_Table(
	Board_Info_No Number(6) Not Null, -- 기본키설정 
	Board_Info_Name Varchar2(500) Not Null,
     Primary Key(Board_Info_No)
);

Select * From Board_Info_Table;

Insert Into Board_Info_Table(Board_Info_No, Board_Info_Name) Values (1, '공지게시판');
Insert Into Board_Info_Table(Board_Info_No, Board_Info_Name) Values (2, '자유게시판');

Select * From Board_Info_Table;

Commit;
//게시판테이블첨부파일은언젠간구현예정
Drop Table Content_Table;

Create Table Content_Table(
	Content_No Number(6) Not Null ,
	Content_Subject Varchar2(500) Not Null,
	Content_Text Long Not Null,
	Content_File Varchar2(500),
        
    Content_Writer_No Number(6),
    Content_Board_No Number(6),
    Content_Date Date,
    User_No Number(6) Not Null,
    Board_Info_No Number(6) Not Null,
    
	Foreign Key(User_No) References User_Info(User_No),
	Foreign Key(Board_Info_No) References Board_Info_Table(Board_Info_No)
);

-- 시퀀스 삭제
Drop Sequence Content_No_Seq;

-- 시퀀스 생성
Create Sequence Content_No_Seq
Increment By 1
Start With 1;

INSERT INTO Content_Table (
    Content_No, 
    Content_Subject, 
    Content_Text, 
    Content_File, 
    Content_Writer_No, 
    Content_Board_No, 
    Content_Date, 
    User_No, 
    Board_Info_No
) VALUES (
    Content_No_Seq.NEXTVAL, 
    'SQL 소개', 
    '이것은 SQL 소개에 대한 긴 텍스트입니다...', 
    'intro_to_sql.pdf', 
    100, 
    200, 
    TO_DATE('2024-08-05', 'YYYY-MM-DD'), 
    1, 
    1
);


Select * From Content_Table;
Commit;
