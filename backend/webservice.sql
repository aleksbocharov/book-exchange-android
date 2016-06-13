-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 13, 2016 at 04:12 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `webservice`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_title` varchar(50) NOT NULL,
  `author` varchar(50) DEFAULT NULL,
  `isbn` varchar(50) DEFAULT NULL,
  `username` varchar(64) NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`book_id`, `book_title`, `author`, `isbn`, `username`) VALUES
(1, 'Java', 'JavaWiz', '123', 'Alex'),
(2, 'Android', NULL, '1233312', 'alex'),
(3, 'Lolo', NULL, 'asd', 'alex'),
(4, 'test', 'test', 'test', 'alex'),
(5, 'new', NULL, 'new', 'alex'),
(6, 'WAT', 'who', 'why', 'alex'),
(7, 'test1', 'Cooper', 'asdfadf23423', 'johndoe'),
(9, 'test123', 'asdads', 'asdad', 'asda'),
(11, 'gggg', 'gggg', 'gggg', 'gggggg'),
(12, 'gggg', 'gggg', 'gggg', 'gggggg');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `title` varchar(120) NOT NULL,
  `message` text NOT NULL,
  `receiver` varchar(64) NOT NULL,
  `status` varchar(15) NOT NULL,
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `post_id` (`post_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`post_id`, `username`, `title`, `message`, `receiver`, `status`) VALUES
(1, 'alex', 'Comment1', 'Testststststt', '', ''),
(2, 'alex', 'Android', 'Android', '', ''),
(3, 'test', 'Test2', 'Test2', '', ''),
(4, 'alex', 'No', 'Yes', '', ''),
(5, 'alex', 'Alex', 'loloshka', '', ''),
(7, 'alex', 'Hello', 'Hi', 'johndoe', ''),
(8, 'alex', 'derpherp', 'DErp', 'johndoe', ''),
(9, 'alex', 'ninetwelve', 'hello', 'johndoe', ''),
(10, 'alex', 'test21', 'asd', 'Alex', ''),
(11, 'johndoe', '2212test', 'lolo', 'alex', ''),
(15, 'johndoe', 'testafterfail', 'failfailaifailfa', 'alex', ''),
(17, 'alex', 'RE: testafterfail', 'test2312reply', 'johndoe', '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `id_2` (`id`),
  KEY `username` (`username`),
  KEY `id_3` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'alex', 'assa'),
(2, 'test', 'assa'),
(3, 'johndoe', 'assa');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
