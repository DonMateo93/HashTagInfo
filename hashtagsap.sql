-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 21 Maj 2016, 12:47
-- Wersja serwera: 10.1.13-MariaDB
-- Wersja PHP: 5.5.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `hashtagsap`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hashtag`
--

CREATE TABLE `hashtag` (
  `id` int(11) NOT NULL,
  `text` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `hashtag`
--

INSERT INTO `hashtag` (`id`, `text`) VALUES
(4, 'a'),
(13, 'dupa'),
(16, 'i'),
(5, 'jak'),
(14, 'kupa'),
(15, 'moc'),
(17, 'mocz'),
(6, 'moje'),
(11, 'nowe'),
(2, 'sobie'),
(12, 'stare'),
(7, 'tagi'),
(3, 'tak'),
(1, 'taki');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hashtag_hashtag`
--

CREATE TABLE `hashtag_hashtag` (
  `id` int(11) NOT NULL,
  `id1` int(11) DEFAULT NULL,
  `id2` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hashtag_info`
--

CREATE TABLE `hashtag_info` (
  `id` int(11) NOT NULL,
  `id_ht` int(11) DEFAULT NULL,
  `id_info` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `hashtag_info`
--

INSERT INTO `hashtag_info` (`id`, `id_ht`, `id_info`) VALUES
(1, 13, 8),
(2, 14, 8),
(3, 15, 8),
(4, 16, 8),
(5, 17, 8);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `info`
--

CREATE TABLE `info` (
  `id` int(11) NOT NULL,
  `information` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `info`
--

INSERT INTO `info` (`id`, `information`, `description`) VALUES
(1, 'takatam', 'taka tam'),
(2, 'moje', 'moje info'),
(3, 'indo', 'infor'),
(4, 'lakdjflksdfksf', 'to a'),
(5, 'sdlfjlsdkfs', 'lsdfkls'),
(6, 'jfjdjkdjdkd', 'slkjlkjal'),
(7, 'slkdjflskd', 'nowy proglek'),
(8, 'sdlfkjsdlkj', 'lkjsfd');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `hashtag`
--
ALTER TABLE `hashtag`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `text` (`text`);

--
-- Indexes for table `hashtag_hashtag`
--
ALTER TABLE `hashtag_hashtag`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hashtag_info`
--
ALTER TABLE `hashtag_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `info`
--
ALTER TABLE `info`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `hashtag`
--
ALTER TABLE `hashtag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT dla tabeli `hashtag_hashtag`
--
ALTER TABLE `hashtag_hashtag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `hashtag_info`
--
ALTER TABLE `hashtag_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT dla tabeli `info`
--
ALTER TABLE `info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
