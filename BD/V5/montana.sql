-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-08-2018 a las 01:21:58
-- Versión del servidor: 10.1.32-MariaDB
-- Versión de PHP: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `montana`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetNumerador` (IN `tipo` VARCHAR(12))  NO SQL
begin

declare secuencia integer;
START TRANSACTION;


set secuencia=(select min(A.secuencia) from numeradorespendientes A where rtrim(tipoNumerador)=rtrim(tipo) and estado='A');

if(secuencia is null or secuencia=0) then

UPDATE numeradores SET valor=(valor+Incremento) WHERE tipoNumerador=tipo;

insert into numeradorespendientes
SELECT tipoNumerador,valor,'P' FROM numeradores WHERE tipoNumerador=tipo;

SELECT valor FROM numeradores WHERE tipoNumerador=tipo;
else 
	update numeradorespendientes set estado='A' where 		tipoNumerador=tipo and estado='P' and 		    numeradorespendientes.secuencia=secuencia;
	select secuencia;
END if;





COMMIT;

end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajaproducto`
--

CREATE TABLE `cajaproducto` (
  `idCajaProducto` int(11) NOT NULL,
  `idCaja` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idTalla` int(10) NOT NULL,
  `idColor` int(10) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `Cantidad` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cajaproducto`
--

INSERT INTO `cajaproducto` (`idCajaProducto`, `idCaja`, `idProducto`, `idTalla`, `idColor`, `idCategoria`, `Cantidad`) VALUES
(13, 52, 6, 1, 2, 1, '35'),
(15, 69, 6, 1, 2, 1, '20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajas`
--

CREATE TABLE `cajas` (
  `idCaja` int(11) NOT NULL,
  `NumeroCaja` varchar(10) NOT NULL,
  `NombreCaja` varchar(30) NOT NULL,
  `cantidad` decimal(10,0) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cajas`
--

INSERT INTO `cajas` (`idCaja`, `NumeroCaja`, `NombreCaja`, `cantidad`, `estado`) VALUES
(52, '52', 'cajaM', '35', 'N'),
(69, '69', 'koko', '20', 'N');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `descripcion`) VALUES
(1, 'sombreros');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `idempresa` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `idPersona`, `idempresa`) VALUES
(1, 5, '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `despacho`
--

CREATE TABLE `despacho` (
  `idDespacho` int(11) NOT NULL,
  `idTurno` int(11) NOT NULL,
  `Usuario` varchar(12) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `idPedido` decimal(10,0) NOT NULL,
  `factura` varchar(15) NOT NULL,
  `FechaEntrega` date NOT NULL,
  `idCiudad` decimal(10,0) NOT NULL,
  `NumeroGuia` varchar(15) NOT NULL,
  `idTipoDespacho` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `despachoproducto`
--

CREATE TABLE `despachoproducto` (
  `idDespachoProducto` int(11) NOT NULL,
  `idDespacho` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idTalla` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `idEmpresa` int(11) NOT NULL,
  `Nit` varchar(15) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `Telefono1` varchar(12) NOT NULL,
  `Telefono2` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hormas`
--

CREATE TABLE `hormas` (
  `idHorma` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `hormas`
--

INSERT INTO `hormas` (`idHorma`, `descripcion`, `estado`) VALUES
(1, 'Volteado', 'A'),
(2, 'Redondo', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hormasprod`
--

CREATE TABLE `hormasprod` (
  `idHormaProd` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `idHorma` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `hormasprod`
--

INSERT INTO `hormasprod` (`idHormaProd`, `idProducto`, `idCategoria`, `idHorma`) VALUES
(3, 3, 1, 2),
(4, 4, 1, 2),
(12, 6, 1, 1),
(13, 68, 1, 2),
(14, 69, 1, 1),
(15, 5, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `insumoproducto`
--

CREATE TABLE `insumoproducto` (
  `idInsumoProducto` int(11) NOT NULL,
  `idInsumo` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `cantidad` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `insumoproducto`
--

INSERT INTO `insumoproducto` (`idInsumoProducto`, `idInsumo`, `idProducto`, `idCategoria`, `cantidad`) VALUES
(1, 1, 3, 1, '12'),
(2, 2, 3, 1, '12'),
(3, 1, 4, 1, '100'),
(4, 2, 4, 1, '150'),
(19, 1, 6, 1, '10'),
(20, 2, 6, 1, '50'),
(21, 1, 68, 1, '20'),
(22, 2, 68, 1, '10'),
(23, 1, 69, 1, '10'),
(24, 3, 69, 1, '15'),
(25, 4, 69, 1, '10'),
(26, 2, 69, 1, '5'),
(27, 5, 69, 1, '10'),
(28, 7, 69, 1, '5'),
(29, 8, 69, 1, '10'),
(30, 1, 5, 1, '0'),
(31, 3, 5, 1, '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `insumos`
--

CREATE TABLE `insumos` (
  `idInsumo` int(11) NOT NULL,
  `NombreInsumo` varchar(40) NOT NULL,
  `Cantidad` decimal(10,0) NOT NULL,
  `idUnidad` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`idInsumo`, `NombreInsumo`, `Cantidad`, `idUnidad`) VALUES
(1, 'Tela', '100', '1'),
(2, 'gtgg', '150', '1'),
(3, 'juan', '50', '1'),
(4, 'juan', '50', '1'),
(5, 'www', '12', '1'),
(6, 'www', '12', '1'),
(7, 'www', '12', '1'),
(8, 'www', '12', '1'),
(9, 'eee', '23', '2'),
(10, 'eee', '23', '2'),
(11, 'gtgg', '622', '2'),
(12, 'sss', '1', '1'),
(13, 'kiko', '85', '1'),
(14, 'lol', '1', '1'),
(15, 'lol', '1', '1'),
(16, 'lol2', '1', '1'),
(17, 'plp', '51', '1'),
(18, 'juan', '15', '1'),
(19, 'wwww', '150', '2'),
(20, 'juan', '333', '1'),
(21, 'rrrrr', '150', '1'),
(22, 'aaaaaaaaaaaaa', '1', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mciudades`
--

CREATE TABLE `mciudades` (
  `idCiudad` int(11) NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mcolores`
--

CREATE TABLE `mcolores` (
  `idColor` int(10) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `siglas` varchar(10) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mcolores`
--

INSERT INTO `mcolores` (`idColor`, `nombre`, `siglas`, `estado`) VALUES
(1, 'Azul', 'AZ', 'A'),
(2, 'Rojo', 'RJ', 'A'),
(3, 'Amarillo', 'AM', 'A'),
(4, 'Rosado', 'RS', 'A'),
(5, 'Blanco', 'BL', 'A'),
(6, 'Negro', 'NE', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mestados`
--

CREATE TABLE `mestados` (
  `idEstado` int(11) NOT NULL,
  `nombre` varchar(10) DEFAULT NULL,
  `descripcion` varchar(30) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mestados`
--

INSERT INTO `mestados` (`idEstado`, `nombre`, `descripcion`, `estado`) VALUES
(1, 'PP', 'Pedido Pendiente', 'A'),
(2, 'DP', 'Despacho Parcial', 'A'),
(3, 'EP', 'En Pie', 'A'),
(4, 'COM', 'Completado', 'A'),
(5, 'CAN', 'Cancelado', 'A'),
(6, 'DA', 'Despacho Adicional', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mtallas`
--

CREATE TABLE `mtallas` (
  `idTalla` int(11) NOT NULL,
  `med_in` varchar(10) DEFAULT NULL,
  `med_amer` varchar(10) DEFAULT NULL,
  `med_col` varchar(10) DEFAULT NULL,
  `med_cm` varchar(10) DEFAULT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mtallas`
--

INSERT INTO `mtallas` (`idTalla`, `med_in`, `med_amer`, `med_col`, `med_cm`, `estado`) VALUES
(1, NULL, NULL, '2', '51', 'A'),
(2, '6 1/2', NULL, '2 1/2', '52', 'A'),
(3, '6 5/8', 'S', '3', '53', 'A'),
(4, '6 3/4', NULL, '3 1/2', '54', 'A'),
(5, '6 7/8', 'M', '4', '55', 'A'),
(6, '7', NULL, '4 1/2', '56', 'A'),
(7, '7 1/8', 'L', '5', '57', 'A'),
(8, '7 1/4', NULL, '5 1/2', '58', 'A'),
(9, '7 3/8', 'XL', '6', '59', ''),
(10, '7 1/2', NULL, '6 1/2', '60', 'A'),
(11, '7 5/8', 'XXL', '7', '61', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mtipodocumento`
--

CREATE TABLE `mtipodocumento` (
  `idTipoDoc` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mtipodocumento`
--

INSERT INTO `mtipodocumento` (`idTipoDoc`, `descripcion`, `estado`) VALUES
(1, 'Cedula Ciudadania', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mvariables`
--

CREATE TABLE `mvariables` (
  `id_variables` int(11) NOT NULL,
  `Descripcion` varchar(30) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mvariables`
--

INSERT INTO `mvariables` (`id_variables`, `Descripcion`, `estado`) VALUES
(1, 'Pedido Pendiente', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `numeradores`
--

CREATE TABLE `numeradores` (
  `tipoNumerador` varchar(12) NOT NULL,
  `rango_inicial` int(11) NOT NULL,
  `rango_final` int(11) DEFAULT NULL,
  `valor` int(11) DEFAULT NULL,
  `incremento` int(11) DEFAULT NULL,
  `estado` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `numeradores`
--

INSERT INTO `numeradores` (`tipoNumerador`, `rango_inicial`, `rango_final`, `valor`, `incremento`, `estado`) VALUES
('Cajas', 1, 99999, 69, 1, 'A'),
('producto', 1, 1000, 72, 1, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `numeradorespendientes`
--

CREATE TABLE `numeradorespendientes` (
  `tipoNumerador` varchar(12) DEFAULT NULL,
  `secuencia` int(11) DEFAULT NULL,
  `estado` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `numeradorespendientes`
--

INSERT INTO `numeradorespendientes` (`tipoNumerador`, `secuencia`, `estado`) VALUES
('producto', 8, 'P'),
('producto', 9, 'P'),
('producto', 10, 'P'),
('producto', 11, 'P'),
('producto', 12, 'P'),
('producto', 13, 'P'),
('producto', 14, 'P'),
('producto', 15, 'P'),
('producto', 16, 'P'),
('producto', 17, 'P'),
('producto', 18, 'P'),
('producto', 19, 'P'),
('producto', 20, 'P'),
('producto', 21, 'P'),
('producto', 22, 'P'),
('producto', 23, 'P'),
('producto', 24, 'P'),
('producto', 25, 'P'),
('producto', 26, 'P'),
('producto', 27, 'P'),
('producto', 28, 'P'),
('producto', 29, 'P'),
('producto', 30, 'P'),
('producto', 31, 'P'),
('producto', 32, 'P'),
('producto', 33, 'P'),
('producto', 34, 'P'),
('producto', 35, 'P'),
('producto', 36, 'P'),
('producto', 37, 'P'),
('producto', 38, 'P'),
('producto', 39, 'P'),
('producto', 40, 'P'),
('producto', 41, 'P'),
('producto', 42, 'P'),
('producto', 43, 'P'),
('producto', 44, 'P'),
('producto', 45, 'P'),
('producto', 46, 'P'),
('producto', 47, 'P'),
('producto', 48, 'P'),
('producto', 49, 'P'),
('producto', 50, 'P'),
('Cajas', 2, 'P'),
('producto', 51, 'P'),
('Cajas', 3, 'P'),
('producto', 52, 'P'),
('Cajas', 4, 'P'),
('producto', 53, 'P'),
('Cajas', 5, 'P'),
('producto', 54, 'P'),
('Cajas', 6, 'P'),
('producto', 55, 'P'),
('Cajas', 7, 'P'),
('Cajas', 8, 'P'),
('Cajas', 9, 'P'),
('Cajas', 10, 'P'),
('Cajas', 11, 'P'),
('Cajas', 12, 'P'),
('Cajas', 13, 'P'),
('Cajas', 14, 'P'),
('Cajas', 15, 'P'),
('Cajas', 16, 'P'),
('Cajas', 17, 'P'),
('producto', 56, 'P'),
('Cajas', 18, 'P'),
('producto', 57, 'P'),
('Cajas', 19, 'P'),
('producto', 58, 'P'),
('Cajas', 20, 'P'),
('producto', 59, 'P'),
('Cajas', 21, 'P'),
('producto', 60, 'P'),
('Cajas', 22, 'P'),
('producto', 61, 'P'),
('Cajas', 23, 'P'),
('producto', 62, 'P'),
('Cajas', 24, 'P'),
('producto', 63, 'P'),
('producto', 64, 'P'),
('Cajas', 25, 'P'),
('Cajas', 26, 'P'),
('Cajas', 27, 'P'),
('Cajas', 28, 'P'),
('Cajas', 29, 'P'),
('Cajas', 30, 'P'),
('Cajas', 31, 'P'),
('Cajas', 32, 'P'),
('Cajas', 33, 'P'),
('Cajas', 34, 'P'),
('Cajas', 35, 'P'),
('Cajas', 36, 'P'),
('Cajas', 37, 'P'),
('Cajas', 38, 'P'),
('Cajas', 39, 'P'),
('Cajas', 40, 'P'),
('Cajas', 41, 'P'),
('Cajas', 42, 'P'),
('Cajas', 43, 'P'),
('Cajas', 44, 'P'),
('Cajas', 45, 'P'),
('Cajas', 46, 'P'),
('Cajas', 47, 'P'),
('Cajas', 48, 'P'),
('Cajas', 49, 'P'),
('Cajas', 50, 'P'),
('Cajas', 51, 'P'),
('Cajas', 52, 'P'),
('Cajas', 53, 'P'),
('Cajas', 54, 'P'),
('Cajas', 55, 'P'),
('Cajas', 56, 'P'),
('Cajas', 57, 'P'),
('Cajas', 58, 'P'),
('Cajas', 59, 'P'),
('Cajas', 60, 'P'),
('Cajas', 61, 'P'),
('Cajas', 62, 'P'),
('Cajas', 63, 'P'),
('Cajas', 64, 'P'),
('Cajas', 65, 'P'),
('Cajas', 66, 'P'),
('Cajas', 67, 'P'),
('Cajas', 68, 'P'),
('Cajas', 69, 'P'),
('producto', 65, 'P'),
('producto', 66, 'P'),
('producto', 67, 'P'),
('producto', 68, 'P'),
('producto', 69, 'P'),
('producto', 70, 'P'),
('producto', 71, 'P'),
('producto', 72, 'P');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `idPedido` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `FechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FechaEntrega` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `idTurno` int(11) NOT NULL,
  `Usuario` varchar(12) NOT NULL,
  `Observacion` varchar(200) NOT NULL,
  `total_pedido` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`idPedido`, `idCliente`, `idPersona`, `FechaRegistro`, `FechaEntrega`, `idTurno`, `Usuario`, `Observacion`, `total_pedido`) VALUES
(2, 1, 5, '2018-08-01 17:28:36', '2018-07-25 05:00:00', 1, 'admon', 'xxxx', 0),
(3, 1, 5, '2018-08-01 17:28:39', '2018-07-31 22:10:14', 33, 'admon', '', 0),
(4, 1, 5, '2018-08-01 17:28:41', '2018-07-31 22:10:14', 33, 'admon', '', 0),
(5, 1, 5, '2018-08-01 20:20:28', '2018-08-01 20:25:24', 33, 'admon', '', NULL),
(6, 1, 5, '2018-08-01 20:20:41', '2018-08-01 20:25:24', 33, 'admon', '', NULL),
(9, 1, 5, '2018-08-01 20:37:05', '2018-08-01 20:41:53', 33, 'admon', '', NULL),
(10, 1, 5, '2018-08-01 20:37:11', '2018-08-01 20:41:53', 33, 'admon', '', NULL),
(11, 1, 5, '2018-08-01 21:20:56', '2018-08-01 21:29:00', 33, 'admon', '', NULL),
(12, 1, 5, '2018-08-01 21:21:07', '2018-08-01 21:29:00', 33, 'admon', '', NULL),
(13, 1, 5, '2018-08-01 21:21:44', '2018-08-01 21:29:00', 33, 'admon', '', NULL),
(14, 1, 5, '2018-08-01 21:21:59', '2018-08-01 21:29:00', 33, 'admon', '', NULL),
(15, 1, 5, '2018-08-01 22:01:46', '2018-08-01 22:06:22', 0, 'admon', '', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidovariables`
--

CREATE TABLE `pedidovariables` (
  `idPedidoVariable` int(11) NOT NULL,
  `idPedido` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `id_variables` int(11) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `fecha` datetime NOT NULL,
  `idDespacho` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido_detalle`
--

CREATE TABLE `pedido_detalle` (
  `idPedido` int(11) NOT NULL,
  `idPedidoDespacho` int(11) NOT NULL,
  `idProducto` int(10) NOT NULL,
  `idTalla` int(11) NOT NULL,
  `idColor` int(10) NOT NULL,
  `cantidad` decimal(10,0) NOT NULL,
  `despachado` varchar(2) NOT NULL,
  `observacion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pedido_detalle`
--

INSERT INTO `pedido_detalle` (`idPedido`, `idPedidoDespacho`, `idProducto`, `idTalla`, `idColor`, `cantidad`, `despachado`, `observacion`) VALUES
(2, 1, 3, 1, 1, '10', 'N', ''),
(9, 2, 68, 2, 0, '10', 'N', ''),
(10, 3, 69, 7, 0, '10', 'N', ''),
(14, 4, 3, 8, 0, '10', 'N', ''),
(14, 5, 68, 3, 0, '10', 'N', ''),
(14, 6, 4, 2, 0, '150', 'N', ''),
(15, 7, 3, 9, 0, '150', 'N', ''),
(15, 8, 69, 2, 0, '10', 'N', ''),
(15, 9, 69, 6, 0, '11', 'N', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `idPersona` int(11) NOT NULL,
  `Nombre` varchar(40) NOT NULL,
  `Apellido` varchar(40) NOT NULL,
  `NombreCompleto` varchar(60) NOT NULL,
  `Documento` varchar(15) NOT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `sexo` varchar(2) DEFAULT NULL,
  `Telefono1` varchar(12) DEFAULT NULL,
  `Telefono2` varchar(12) DEFAULT NULL,
  `idTipoDoc` int(11) NOT NULL,
  `estado` varchar(2) NOT NULL DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`idPersona`, `Nombre`, `Apellido`, `NombreCompleto`, `Documento`, `fechaNacimiento`, `sexo`, `Telefono1`, `Telefono2`, `idTipoDoc`, `estado`) VALUES
(1, 'Admon', 'Admon', 'Admon', '1111111', NULL, 'M', '11', '', 1, 'A'),
(5, 'Mauricio Cecilio', 'Herrera', 'Mauricio Cecilio Herrera', '1113626301', '2018-06-04', '', '', '3156371573', 1, 'I');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL,
  `idTalla` int(11) NOT NULL,
  `idColor` int(10) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  `nombreProducto` varchar(80) NOT NULL,
  `Cantidad` int(8) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `idTalla`, `idColor`, `idCategoria`, `nombreProducto`, `Cantidad`, `estado`) VALUES
(3, 1, 0, 1, 'qq', 12, 'A'),
(4, 1, 0, 1, 'qqqq', 2, 'A'),
(5, 1, 1, 1, 'sombrero 10/2', 7, 'N'),
(6, 1, 2, 1, '----------', 140, 'A'),
(68, 1, 5, 1, 'kiak', 15, 'A'),
(69, 1, 6, 1, 'pepsi', 10, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL,
  `Descripcion` varchar(15) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`idRol`, `Descripcion`, `estado`) VALUES
(1, 'Cliente', 'A'),
(2, 'Admon', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolxuser`
--

CREATE TABLE `rolxuser` (
  `Usuario` varchar(50) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `idRol` int(11) NOT NULL,
  `idRolXUser` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rolxuser`
--

INSERT INTO `rolxuser` (`Usuario`, `idPersona`, `idRol`, `idRolXUser`) VALUES
('admon', 1, 1, 1),
('Mauricio870331', 5, 2, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipodespacho`
--

CREATE TABLE `tipodespacho` (
  `idTipoDespacho` int(11) NOT NULL,
  `Descripcion` varchar(40) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turno`
--

CREATE TABLE `turno` (
  `idTurno` int(11) NOT NULL,
  `Usuario` varchar(12) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `FechaTurno` datetime NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `turno`
--

INSERT INTO `turno` (`idTurno`, `Usuario`, `idPersona`, `FechaTurno`, `estado`) VALUES
(30, 'admon', 1, '2018-07-05 16:55:30', 'A'),
(31, 'admon', 1, '2018-07-05 16:59:45', 'A'),
(32, 'admon', 1, '2018-07-05 17:01:26', 'A'),
(33, 'admon', 1, '2018-07-15 23:07:01', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad`
--

CREATE TABLE `unidad` (
  `idUnidad` int(11) NOT NULL,
  `Descripcion` varchar(50) NOT NULL,
  `siglas` varchar(3) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `unidad`
--

INSERT INTO `unidad` (`idUnidad`, `Descripcion`, `siglas`, `estado`) VALUES
(1, 'Metro', 'M', 'A'),
(2, 'Kilometro', 'KM', 'A'),
(3, 'Centimetro', 'CM', 'A'),
(4, 'Gramo', 'G', 'A'),
(5, 'Litro', 'L', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Usuario` varchar(30) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `NickName` varchar(100) NOT NULL,
  `clave` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Usuario`, `idPersona`, `NickName`, `clave`) VALUES
('admon', 1, 'Admon', '1234'),
('Mauricio870331', 5, 'Mauricio Cecilio Herrera', '1113626301');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cajaproducto`
--
ALTER TABLE `cajaproducto`
  ADD PRIMARY KEY (`idCajaProducto`,`idCaja`,`idProducto`,`idCategoria`,`idTalla`,`idColor`) USING BTREE,
  ADD KEY `Cajas_CajaProducto_fk` (`idCaja`),
  ADD KEY `Producto_CajaProducto_fk` (`idProducto`,`idCategoria`);

--
-- Indices de la tabla `cajas`
--
ALTER TABLE `cajas`
  ADD PRIMARY KEY (`idCaja`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`,`idPersona`),
  ADD KEY `Persona_Cliente_fk` (`idPersona`);

--
-- Indices de la tabla `despacho`
--
ALTER TABLE `despacho`
  ADD PRIMARY KEY (`idDespacho`,`idTurno`,`Usuario`,`idPersona`),
  ADD KEY `TipoDespacho_Despacho_fk` (`idTipoDespacho`),
  ADD KEY `Turno_Despacho_fk` (`idTurno`,`Usuario`,`idPersona`);

--
-- Indices de la tabla `despachoproducto`
--
ALTER TABLE `despachoproducto`
  ADD PRIMARY KEY (`idDespachoProducto`,`idDespacho`,`idProducto`),
  ADD KEY `Cajas_DespachoProducto_fk` (`idProducto`),
  ADD KEY `Despacho_DespachoProducto_fk` (`idDespacho`),
  ADD KEY `idTalla` (`idTalla`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`idEmpresa`);

--
-- Indices de la tabla `hormas`
--
ALTER TABLE `hormas`
  ADD PRIMARY KEY (`idHorma`);

--
-- Indices de la tabla `hormasprod`
--
ALTER TABLE `hormasprod`
  ADD PRIMARY KEY (`idHormaProd`,`idProducto`,`idCategoria`,`idHorma`),
  ADD KEY `Hormas_HormasProd_fk` (`idHorma`),
  ADD KEY `Producto_HormasProd_fk` (`idProducto`,`idCategoria`);

--
-- Indices de la tabla `insumoproducto`
--
ALTER TABLE `insumoproducto`
  ADD PRIMARY KEY (`idInsumoProducto`,`idInsumo`,`idProducto`,`idCategoria`),
  ADD KEY `Insumos_InsumoProducto_fk` (`idInsumo`),
  ADD KEY `Producto_InsumoProducto_fk` (`idProducto`,`idCategoria`);

--
-- Indices de la tabla `insumos`
--
ALTER TABLE `insumos`
  ADD PRIMARY KEY (`idInsumo`);

--
-- Indices de la tabla `mciudades`
--
ALTER TABLE `mciudades`
  ADD PRIMARY KEY (`idCiudad`);

--
-- Indices de la tabla `mcolores`
--
ALTER TABLE `mcolores`
  ADD PRIMARY KEY (`idColor`);

--
-- Indices de la tabla `mestados`
--
ALTER TABLE `mestados`
  ADD PRIMARY KEY (`idEstado`);

--
-- Indices de la tabla `mtallas`
--
ALTER TABLE `mtallas`
  ADD PRIMARY KEY (`idTalla`);

--
-- Indices de la tabla `mtipodocumento`
--
ALTER TABLE `mtipodocumento`
  ADD PRIMARY KEY (`idTipoDoc`);

--
-- Indices de la tabla `mvariables`
--
ALTER TABLE `mvariables`
  ADD PRIMARY KEY (`id_variables`);

--
-- Indices de la tabla `numeradores`
--
ALTER TABLE `numeradores`
  ADD PRIMARY KEY (`tipoNumerador`,`rango_inicial`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`idPedido`,`idCliente`,`idPersona`),
  ADD KEY `Cliente_Pedido_fk` (`idCliente`,`idPersona`);

--
-- Indices de la tabla `pedidovariables`
--
ALTER TABLE `pedidovariables`
  ADD PRIMARY KEY (`idPedidoVariable`,`idPedido`,`idCliente`,`idPersona`,`id_variables`),
  ADD KEY `MVariables_PedidoVariables_fk` (`id_variables`),
  ADD KEY `Pedido_PedidoVariables_fk` (`idPedido`,`idCliente`,`idPersona`),
  ADD KEY `idDespacho` (`idDespacho`);

--
-- Indices de la tabla `pedido_detalle`
--
ALTER TABLE `pedido_detalle`
  ADD PRIMARY KEY (`idPedidoDespacho`,`idPedido`,`idProducto`,`idTalla`) USING BTREE,
  ADD KEY `Pedido_Pedido_Detalle_fk` (`idPedido`),
  ADD KEY `idTalla` (`idTalla`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`idPersona`),
  ADD UNIQUE KEY `Documento` (`Documento`),
  ADD KEY `MTipoDocumento_Persona_fk` (`idTipoDoc`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`,`idCategoria`,`idTalla`,`idColor`) USING BTREE,
  ADD KEY `Categoria_Producto_fk` (`idCategoria`) USING BTREE;

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`idRol`);

--
-- Indices de la tabla `rolxuser`
--
ALTER TABLE `rolxuser`
  ADD PRIMARY KEY (`Usuario`,`idPersona`,`idRol`),
  ADD KEY `Rol_RolXUser_fk` (`idRol`);

--
-- Indices de la tabla `tipodespacho`
--
ALTER TABLE `tipodespacho`
  ADD PRIMARY KEY (`idTipoDespacho`);

--
-- Indices de la tabla `turno`
--
ALTER TABLE `turno`
  ADD PRIMARY KEY (`idTurno`,`Usuario`,`idPersona`),
  ADD KEY `Usuario_Turno_fk` (`Usuario`,`idPersona`);

--
-- Indices de la tabla `unidad`
--
ALTER TABLE `unidad`
  ADD PRIMARY KEY (`idUnidad`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Usuario`,`idPersona`),
  ADD KEY `Persona_Usuario_fk` (`idPersona`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cajaproducto`
--
ALTER TABLE `cajaproducto`
  MODIFY `idCajaProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `cajas`
--
ALTER TABLE `cajas`
  MODIFY `idCaja` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `despacho`
--
ALTER TABLE `despacho`
  MODIFY `idDespacho` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `despachoproducto`
--
ALTER TABLE `despachoproducto`
  MODIFY `idDespachoProducto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `idEmpresa` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `hormas`
--
ALTER TABLE `hormas`
  MODIFY `idHorma` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `hormasprod`
--
ALTER TABLE `hormasprod`
  MODIFY `idHormaProd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `insumoproducto`
--
ALTER TABLE `insumoproducto`
  MODIFY `idInsumoProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `idInsumo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `mciudades`
--
ALTER TABLE `mciudades`
  MODIFY `idCiudad` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `mcolores`
--
ALTER TABLE `mcolores`
  MODIFY `idColor` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `mestados`
--
ALTER TABLE `mestados`
  MODIFY `idEstado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `mtipodocumento`
--
ALTER TABLE `mtipodocumento`
  MODIFY `idTipoDoc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `mvariables`
--
ALTER TABLE `mvariables`
  MODIFY `id_variables` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `idPedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `pedidovariables`
--
ALTER TABLE `pedidovariables`
  MODIFY `idPedidoVariable` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedido_detalle`
--
ALTER TABLE `pedido_detalle`
  MODIFY `idPedidoDespacho` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `idPersona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `idRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tipodespacho`
--
ALTER TABLE `tipodespacho`
  MODIFY `idTipoDespacho` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `turno`
--
ALTER TABLE `turno`
  MODIFY `idTurno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `unidad`
--
ALTER TABLE `unidad`
  MODIFY `idUnidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cajaproducto`
--
ALTER TABLE `cajaproducto`
  ADD CONSTRAINT `Cajas_CajaProducto_fk` FOREIGN KEY (`idCaja`) REFERENCES `cajas` (`idCaja`);

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `Persona_Cliente_fk` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`);

--
-- Filtros para la tabla `despacho`
--
ALTER TABLE `despacho`
  ADD CONSTRAINT `TipoDespacho_Despacho_fk` FOREIGN KEY (`idTipoDespacho`) REFERENCES `tipodespacho` (`idTipoDespacho`),
  ADD CONSTRAINT `Turno_Despacho_fk` FOREIGN KEY (`idTurno`,`Usuario`,`idPersona`) REFERENCES `turno` (`idTurno`, `Usuario`, `idPersona`);

--
-- Filtros para la tabla `despachoproducto`
--
ALTER TABLE `despachoproducto`
  ADD CONSTRAINT `Cajas_DespachoProducto_fk` FOREIGN KEY (`idProducto`) REFERENCES `cajas` (`idCaja`),
  ADD CONSTRAINT `despachoproducto_ibfk_1` FOREIGN KEY (`idTalla`) REFERENCES `mtallas` (`idTalla`);

--
-- Filtros para la tabla `hormasprod`
--
ALTER TABLE `hormasprod`
  ADD CONSTRAINT `Hormas_HormasProd_fk` FOREIGN KEY (`idHorma`) REFERENCES `hormas` (`idHorma`);

--
-- Filtros para la tabla `insumoproducto`
--
ALTER TABLE `insumoproducto`
  ADD CONSTRAINT `Insumos_InsumoProducto_fk` FOREIGN KEY (`idInsumo`) REFERENCES `insumos` (`idInsumo`);

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `Cliente_Pedido_fk` FOREIGN KEY (`idCliente`,`idPersona`) REFERENCES `cliente` (`idCliente`, `idPersona`);

--
-- Filtros para la tabla `pedidovariables`
--
ALTER TABLE `pedidovariables`
  ADD CONSTRAINT `MVariables_PedidoVariables_fk` FOREIGN KEY (`id_variables`) REFERENCES `mvariables` (`id_variables`),
  ADD CONSTRAINT `Pedido_PedidoVariables_fk` FOREIGN KEY (`idPedido`,`idCliente`,`idPersona`) REFERENCES `pedido` (`idPedido`, `idCliente`, `idPersona`);

--
-- Filtros para la tabla `pedido_detalle`
--
ALTER TABLE `pedido_detalle`
  ADD CONSTRAINT `Pedido_Pedido_Detalle_fk` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`),
  ADD CONSTRAINT `pedido_detalle_ibfk_1` FOREIGN KEY (`idTalla`) REFERENCES `mtallas` (`idTalla`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `MTipoDocumento_Persona_fk` FOREIGN KEY (`idTipoDoc`) REFERENCES `mtipodocumento` (`idTipoDoc`);

--
-- Filtros para la tabla `rolxuser`
--
ALTER TABLE `rolxuser`
  ADD CONSTRAINT `Rol_RolXUser_fk` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`),
  ADD CONSTRAINT `Usuario_RolXUser_fk` FOREIGN KEY (`Usuario`,`idPersona`) REFERENCES `usuario` (`Usuario`, `idPersona`);

--
-- Filtros para la tabla `turno`
--
ALTER TABLE `turno`
  ADD CONSTRAINT `Usuario_Turno_fk` FOREIGN KEY (`Usuario`,`idPersona`) REFERENCES `usuario` (`Usuario`, `idPersona`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `Persona_Usuario_fk` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
