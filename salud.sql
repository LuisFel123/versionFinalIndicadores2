-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-12-2024 a las 22:48:47
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `salud`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medida`
--

CREATE TABLE `medida` (
  `id_medida` int(11) NOT NULL,
  `id_persona` int(11) DEFAULT NULL,
  `peso` double DEFAULT NULL,
  `cintura` double DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `cadera` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `medida`
--

INSERT INTO `medida` (`id_medida`, `id_persona`, `peso`, `cintura`, `fecha`, `cadera`) VALUES
(29, 154, 78, 45, '2024-12-01', 0),
(30, 154, 78, 89, '2024-12-01', 0),
(31, 154, 78, 90, '2024-12-01', 0),
(32, 155, 80, 80, '2024-12-01', 0),
(33, 155, 86, 81, '2024-12-01', 0),
(34, 156, 89, 56, '2024-12-01', 0),
(35, 158, 67, 78, '2024-12-01', 0),
(36, 158, 89, 90, '2024-12-01', 0),
(37, 156, 78, 90, '2024-12-01', 0),
(38, 156, 89, 90, '2024-12-01', 0),
(39, 157, 89, 67, '2024-12-01', 0),
(40, 157, 78, 90, '2024-12-01', 0),
(41, 159, 55, 60, '2024-12-01', 0),
(42, 159, 40.5, 60.5, '2024-12-01', 0),
(43, 159, 67, 89, '2024-12-01', 90),
(44, 160, 50, 78, '2024-12-01', 90);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id_persona` int(11) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fecha_nac` date DEFAULT NULL,
  `estatura` double DEFAULT NULL,
  `genero` char(1) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `edad` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id_persona`, `usuario`, `password`, `fecha_nac`, `estatura`, `genero`, `nombre`, `edad`) VALUES
(150, 'fer90', '1234', '2001-06-09', 1.89, 'M', 'fernando', 23),
(152, 'yan1', '1234', '1999-08-09', 1.78, 'M', 'yani', 25),
(154, 'luis1', '1234', '2001-02-05', 1.56, 'M', 'luis', 23),
(155, 'alan1', '1234', '2001-06-09', 1.78, 'M', 'alan', 23),
(156, 'azu2', '1234', '1999-06-08', 1.67, 'F', 'azucena', 25),
(157, 'oscar1', '1234', '2004-08-09', 1.76, 'M', 'oscar', 20),
(158, 'julia1', '1234', '2001-08-09', 1.45, 'M', 'julia', 23),
(159, 'domin', '1234', '2003-02-09', 1.56, 'M', 'domingo', 21),
(160, 'chena1', '1234', '2001-08-09', 1.3, 'M', 'chenatrol', 23);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `medida`
--
ALTER TABLE `medida`
  ADD PRIMARY KEY (`id_medida`),
  ADD KEY `id_persona` (`id_persona`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id_persona`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `medida`
--
ALTER TABLE `medida`
  MODIFY `id_medida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `medida`
--
ALTER TABLE `medida`
  ADD CONSTRAINT `medida_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
