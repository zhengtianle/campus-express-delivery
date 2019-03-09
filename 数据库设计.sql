#用户表
CREATE TABLE Users (
 	tel char(11) NOT NULL,
 	passwords varchar(20) NOT NULL,
 	nickname varchar(20) DEFAULT '懒懒用户',
 	avatar varchar(20) DEFAULT NULL,
	sex char(4) NOT NULL,
	name varchar(20) NOT NULL,
 	school varchar(20) NOT NULL,
	stu_id varchar(20) PRIMARY KEY,
	grade decimal(2,1) DEFAULT NULL,
	release_tasks int(11) DEFAULT '0',
	receive_tasks int(11) DEFAULT '0'
)
#代发快递信息
create table Send_Express(
    task_id int primary key,
    express_name varchar(20) not null,
    express_type varchar(10),
    express_weight int not null,
    express_value int not null,
    meeting_location varchar(40) not null,
    meeting_time datetime,
    note varchar(40),
    sender_name varchar(20) not null,
    sender_tel char(11) not null,
    sender_location varchar(40) not null,
    receiver_name varchar(20) not null,
    receiver_tel char(11) not null,
    receiver_location varchar(40) not null,
    express_company varchar(10) not null,
    money int default 0
)
#代收快递信息
create table Receive_Express(
    task_id int primary key,
    express_name varchar(20) not null,
    express_type varchar(10),
    express_weight int not null,
    express_value int not null,
    meeting_location varchar(40) not null,
    meeting_time datetime,
    note varchar(40),
    access_code varchar(20) not null,
    receiver_name varchar(20) not null,
    receiver_tel char(11) not null,
    receiver_location varchar(40) not null,
    express_company varchar(10) not null,
    money int default 0
)
#任务管理
create table Task_Manage(
    task_id int,
    sender_id varchar(20),
    sender_tel char(11) not null,
    receiver_id varchar(20),
    receiver_tel char(11) not null,
    praise numeric(2,1),
    send_time datetime,
    receive_time datetime,
    finish_time datetime,
    express_number varchar(20),
    task_type varchar(20),
    primary key(task_id,sender_id,receiver_id),
    foreign key (sender_id)references Users(stu_id),
    foreign key (receiver_id)references Users(stu_id)
)
