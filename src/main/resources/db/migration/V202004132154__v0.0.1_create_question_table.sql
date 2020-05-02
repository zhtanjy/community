create table question
(
	id int auto_increment,
	title varchar(100) null,
	description text null,
	gmt_create bigint null,
	gmt_modified bigint null,
	creator int null comment '发布人id',
	comment_count int default 0 null comment '关注人数',
	view_count int default 0 null comment '阅读数',
	like_count int default 0 null comment '点赞数',
	tags varchar(256) null,
	constraint question_pk
		primary key (id)
);

