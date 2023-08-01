-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';


insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('贺昊强', '15927303856', 'www.wilson-langosh.name', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('余伟诚', '15586570787', 'www.tori-doyle.com', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('魏雨泽', '17830965054', 'www.keren-macejkovic.io', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('石金鑫', '18643268695', 'www.joey-heaney.io', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('徐涛', '17250071927', 'www.marcelo-lehner.com', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('林明哲', '15028607442', 'www.octavio-hickle.com', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('严浩然', '15730885599', 'www.cassaundra-zieme.info', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('田立诚', '17318073529', 'www.fred-gerhold.info', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('石浩然', '15875993481', 'www.regena-lemke.name', '111111', 0);
insert into `user` (`userName`, `userAccount`, `userAvatar`, `userPassword`, `isDelete`) values ('熊明杰', '15583982192', 'www.kelvin-beahan.info', '111111', 0);


-- 帖子表
create table if not exists post
(
    id            bigint auto_increment comment 'id' primary key,
    age           int comment '年龄',
    gender        tinyint  default 0                 not null comment '性别（0-男, 1-女）',
    education     varchar(512)                       null comment '学历',
    place         varchar(512)                       null comment '地点',
    job           varchar(512)                       null comment '职业',
    contact       varchar(512)                       null comment '联系方式',
    loveExp       varchar(512)                       null comment '感情经历',
    content       text                               null comment '内容（个人介绍）',
    photo         varchar(1024)                      null comment '照片地址',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    viewNum       int                                not null default 0 comment '浏览数',
    thumbNum      int                                not null default 0 comment '点赞数',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '帖子';


insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (15, 6, '专科', '河南', '学生', '15253820107', '暂无', '个人介绍。。。。。！！！', 'www.elwood-okon.info', 4);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (16, 6, '专科', '河南', '学生', '14507137672', '暂无', '个人介绍。。。。。！！！', 'www.belinda-wintheiser.org', 5);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (17, 6, '专科', '河南', '学生', '17613653366', '暂无', '个人介绍。。。。。！！！', 'www.kevin-dare.net', 6);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (18, 6, '专科', '河南', '学生', '17854133937', '暂无', '个人介绍。。。。。！！！', 'www.vito-gibson.name', 7);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (19, 6, '专科', '河南', '学生', '17730542322', '暂无', '个人介绍。。。。。！！！', 'www.isaiah-boyle.name', 8);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (20, 6, '专科', '河南', '学生', '17339721714', '暂无', '个人介绍。。。。。！！！', 'www.ronald-okeefe.biz', 9);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (21, 6, '专科', '河南', '学生', '17034938375', '暂无', '个人介绍。。。。。！！！', 'www.everette-haley.com', 10);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (22, 6, '专科', '河南', '学生', '17218636743', '暂无', '个人介绍。。。。。！！！', 'www.shirley-baumbach.info', 11);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (23, 6, '专科', '河南', '学生', '17195839264', '暂无', '个人介绍。。。。。！！！', 'www.viviana-emmerich.co', 12);
insert into `post` (`age`, `gender`, `education`, `place`, `job`, `contact`, `loveExp`, `content`, `photo`, `userId`) values (24, 6, '专科', '河南', '学生', '15734342900', '暂无', '个人介绍。。。。。！！！', 'www.tom-blick.info', 13);