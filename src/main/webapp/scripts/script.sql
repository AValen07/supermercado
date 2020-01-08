-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.16 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para supermercado
DROP DATABASE IF EXISTS `supermercado`;
CREATE DATABASE IF NOT EXISTS `supermercado` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `supermercado`;

-- Volcando estructura para tabla supermercado.producto
DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `precio` float NOT NULL DEFAULT '0',
  `descuento` int(11) NOT NULL DEFAULT '0',
  `imagen` varchar(5000) DEFAULT 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9MJfiNi1BGC6eaEIDMp6H1tRv1BELm5Lrh-6Go7r3U-a51hh2LA&s',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_usuario` (`id_usuario`),
  CONSTRAINT `FK_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla supermercado.producto: ~6 rows (aproximadamente)
DELETE FROM `producto`;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id`, `nombre`, `id_usuario`, `descripcion`, `precio`, `descuento`, `imagen`) VALUES
	(1, 'leche', 1, 'leche entera', 0.6, 0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9MJfiNi1BGC6eaEIDMp6H1tRv1BELm5Lrh-6Go7r3U-a51hh2LA&s'),
	(2, 'cafe', 1, 'cafe descafeinado', 1.6, 10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9MJfiNi1BGC6eaEIDMp6H1tRv1BELm5Lrh-6Go7r3U-a51hh2LA&s'),
	(3, 'tortilla', 3, 'tortilla de patata', 3.2, 20, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9MJfiNi1BGC6eaEIDMp6H1tRv1BELm5Lrh-6Go7r3U-a51hh2LA&s'),
	(4, 'vodka rusp', 2, 'vodka ', 24, 25, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9MJfiNi1BGC6eaEIDMp6H1tRv1BELm5Lrh-6Go7r3U-a51hh2LA&s'),
	(5, 'alcohol', 1, 'baratico', 20, 5, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9MJfiNi1BGC6eaEIDMp6H1tRv1BELm5Lrh-6Go7r3U-a51hh2LA&s');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla supermercado.rol
DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '1: Usuario normal   2: Administrador',
  `nombre` varchar(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla supermercado.rol: ~2 rows (aproximadamente)
DELETE FROM `rol`;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`id`, `nombre`) VALUES
	(2, 'administrador'),
	(1, 'usuario');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

-- Volcando estructura para tabla supermercado.usuario
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `contrasena` varchar(50) NOT NULL DEFAULT '0',
  `id_rol` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla supermercado.usuario: ~3 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nombre`, `contrasena`, `id_rol`) VALUES
	(1, 'admin', '123456', 2),
	(2, 'pepe', '123456', 1),
	(3, 'Dolores', '56789', 1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
