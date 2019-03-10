-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2019-03-09 14:53:56
-- 服务器版本： 8.0.14
-- PHP 版本： 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `express`
--

-- --------------------------------------------------------

--
-- 表的结构 `Receive_Express`
--

CREATE TABLE `Receive_Express` (
  `task_id` int(11) NOT NULL,
  `stu_id` varchar(20) NOT NULL,
  `express_name` varchar(20) NOT NULL,
  `express_type` varchar(10) DEFAULT NULL,
  `express_weight` int(11) NOT NULL,
  `express_value` int(11) NOT NULL,
  `meeting_location` varchar(40) NOT NULL,
  `meeting_time` datetime DEFAULT NULL,
  `note` varchar(40) DEFAULT NULL,
  `access_code` varchar(20) NOT NULL,
  `sender_name` varchar(20) NOT NULL,
  `sender_tel` char(11) NOT NULL,
  `sender_location` varchar(40) NOT NULL,
  `receiver_name` varchar(20) NOT NULL,
  `receiver_tel` char(11) NOT NULL,
  `receiver_location` varchar(40) NOT NULL,
  `express_company` varchar(10) NOT NULL,
  `money` int(11) DEFAULT '0',
  `successful` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `Receive_Express`
--

INSERT INTO `Receive_Express` (`task_id`, `stu_id`, `express_name`, `express_type`, `express_weight`, `express_value`, `meeting_location`, `meeting_time`, `note`, `access_code`, `sender_name`, `sender_tel`, `sender_location`, `receiver_name`, `receiver_tel`, `receiver_location`, `express_company`, `money`, `successful`) VALUES
(1, '123456789', '华为matebook13', '电子产品', 3, 6000, '开水房超市', '2019-03-21 21:00:00', '轻拿轻放', 'XXX-49222', '', '', '', '测试收件人姓名', '98765432101', '中国某地', '顺丰快递', 20, 0),
(2, '123456789', '华为matebook13', '电子产品', 3, 6000, '开水房超市', '2019-03-21 21:00:00', '轻拿轻放', 'XXX-49222', '', '', '', '测试收件人姓名', '98765432101', '中国某地', '顺丰快递', 20, 0),
(3, '123456789', '华为matebook13', '电子产品', 3, 6000, '开水房超市', '2019-03-21 21:00:00', '轻拿轻放', 'XXX-49222', '', '', '', '测试收件人姓名', '98765432101', '中国某地', '顺丰快递', 20, 0),
(4, '123456789', '华为matebook13', '电子产品', 3, 6000, '开水房超市', '2019-03-21 21:00:00', '轻拿轻放', 'XXX-49222', '', '', '', '测试收件人姓名', '98765432101', '中国某地', '顺丰快递', 20, 0),
(5, '123456789', '华为matebook13', '电子产品', 3, 6000, '开水房超市', '2019-03-21 21:00:00', '轻拿轻放', 'XXX-49222', '', '', '', '测试收件人姓名', '98765432101', '中国某地', '顺丰快递', 20, 0),
(6, '123456789', '华为matebook13', '电子产品', 3, 6000, '开水房超市', '2019-03-21 21:00:00', '轻拿轻放', 'XXX-49222', '', '', '', '测试收件人姓名', '98765432101', '中国某地', '顺丰快递', 20, 0);

-- --------------------------------------------------------

--
-- 表的结构 `Send_Express`
--

CREATE TABLE `Send_Express` (
  `task_id` int(11) NOT NULL,
  `stu_id` varchar(20) NOT NULL,
  `express_name` varchar(20) NOT NULL,
  `express_type` varchar(10) DEFAULT NULL,
  `express_weight` int(11) NOT NULL,
  `express_value` int(11) NOT NULL,
  `meeting_location` varchar(40) NOT NULL,
  `meeting_time` datetime DEFAULT NULL,
  `note` varchar(40) DEFAULT NULL,
  `sender_name` varchar(20) NOT NULL,
  `sender_tel` char(11) NOT NULL,
  `sender_location` varchar(40) NOT NULL,
  `receiver_name` varchar(20) NOT NULL,
  `receiver_tel` char(11) NOT NULL,
  `receiver_location` varchar(40) NOT NULL,
  `express_company` varchar(10) NOT NULL,
  `money` int(11) DEFAULT '0',
  `successful` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `Send_Express`
--

INSERT INTO `Send_Express` (`task_id`, `stu_id`, `express_name`, `express_type`, `express_weight`, `express_value`, `meeting_location`, `meeting_time`, `note`, `sender_name`, `sender_tel`, `sender_location`, `receiver_name`, `receiver_tel`, `receiver_location`, `express_company`, `money`, `successful`) VALUES
(1, '123456789', '红领巾', '装饰', 2, 10, '五四广场', '2019-03-18 18:00:00', '暂无', '测试寄件人姓名', '17863113969', '测试地址', '测试接收人姓名', '12345678910', '中国某地', '申通快递', 10, 0),
(2, '123456789', '红领巾', '装饰', 2, 10, '五四广场', '2019-03-18 18:00:00', '暂无', '测试寄件人姓名', '17863113969', '测试地址', '测试接收人姓名', '12345678910', '中国某地', '申通快递', 10, 0),
(3, '123456789', '红领巾', '装饰', 2, 10, '五四广场', '2019-03-18 18:00:00', '暂无', '测试寄件人姓名', '17863113969', '测试地址', '测试接收人姓名', '12345678910', '中国某地', '申通快递', 10, 0),
(4, '123456789', '红领巾', '装饰', 2, 10, '五四广场', '2019-03-18 18:00:00', '暂无', '测试寄件人姓名', '17863113969', '测试地址', '测试接收人姓名', '12345678910', '中国某地', '申通快递', 10, 0),
(5, '123456789', '红领巾', '装饰', 2, 10, '五四广场', '2019-03-18 18:00:00', '暂无', '测试寄件人姓名', '17863113969', '测试地址', '测试接收人姓名', '12345678910', '中国某地', '申通快递', 10, 0),
(6, '123456789', '红领巾', '装饰', 2, 10, '五四广场', '2019-03-18 18:00:00', '暂无', '测试寄件人姓名', '17863113969', '测试地址', '测试接收人姓名', '12345678910', '中国某地', '申通快递', 10, 0),
(7, '123456789', '红领巾', '装饰', 2, 10, '五四广场', '2019-03-18 18:00:00', '暂无', '测试寄件人姓名', '17863113969', '测试地址', '测试接收人姓名', '12345678910', '中国某地', '申通快递', 10, 0);

-- --------------------------------------------------------

--
-- 表的结构 `Task_Manage`
--

CREATE TABLE `Task_Manage` (
  `task_id` int(11) NOT NULL,
  `sender_id` varchar(20) NOT NULL,
  `sender_tel` char(11) NOT NULL,
  `receiver_id` varchar(20) NOT NULL,
  `receiver_tel` char(11) NOT NULL,
  `praise` decimal(2,1) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `receive_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `express_number` varchar(20) DEFAULT NULL,
  `task_type` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Users`
--

CREATE TABLE `Users` (
  `tel` char(11) NOT NULL,
  `passwords` varchar(20) NOT NULL,
  `nickname` varchar(20) DEFAULT '懒懒用户',
  `avatar` varchar(20) DEFAULT NULL,
  `sex` char(4) NOT NULL,
  `name` varchar(20) NOT NULL,
  `school` varchar(20) NOT NULL,
  `stu_id` varchar(20) NOT NULL,
  `grade` decimal(2,1) DEFAULT NULL,
  `release_tasks` int(11) DEFAULT '0',
  `receive_tasks` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `Users`
--

INSERT INTO `Users` (`tel`, `passwords`, `nickname`, `avatar`, `sex`, `name`, `school`, `stu_id`, `grade`, `release_tasks`, `receive_tasks`) VALUES
('17863113969', '123456abc', '懒懒用户', NULL, '男', '测试用户', '测试用户学校', '123456789', '0.0', 0, 0);

--
-- 转储表的索引
--

--
-- 表的索引 `Receive_Express`
--
ALTER TABLE `Receive_Express`
  ADD PRIMARY KEY (`task_id`),
  ADD KEY `rstuid` (`stu_id`);

--
-- 表的索引 `Send_Express`
--
ALTER TABLE `Send_Express`
  ADD PRIMARY KEY (`task_id`),
  ADD KEY `sstuid` (`stu_id`);

--
-- 表的索引 `Task_Manage`
--
ALTER TABLE `Task_Manage`
  ADD PRIMARY KEY (`task_id`,`sender_id`,`receiver_id`),
  ADD KEY `sender_id` (`sender_id`),
  ADD KEY `receiver_id` (`receiver_id`);

--
-- 表的索引 `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`stu_id`);

--
-- 限制导出的表
--

--
-- 限制表 `Receive_Express`
--
ALTER TABLE `Receive_Express`
  ADD CONSTRAINT `rstuid` FOREIGN KEY (`stu_id`) REFERENCES `Users` (`stu_id`);

--
-- 限制表 `Send_Express`
--
ALTER TABLE `Send_Express`
  ADD CONSTRAINT `sstuid` FOREIGN KEY (`stu_id`) REFERENCES `Users` (`stu_id`);

--
-- 限制表 `Task_Manage`
--
ALTER TABLE `Task_Manage`
  ADD CONSTRAINT `Task_Manage_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `Users` (`stu_id`),
  ADD CONSTRAINT `Task_Manage_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `Users` (`stu_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
