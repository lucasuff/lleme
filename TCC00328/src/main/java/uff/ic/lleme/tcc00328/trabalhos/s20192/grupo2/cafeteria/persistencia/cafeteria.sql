-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 27-Jun-2018 Ã s 14:23
-- VersÃ£o do servidor: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafeteria`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(6) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `email` varchar(35) NOT NULL,
  `senha` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `compra`
--

CREATE TABLE `compra` (
  `id_compra` int(6) NOT NULL,
  `id_cliente` int(6) NOT NULL,
  `data_compra` varchar(15) NOT NULL,
  `valor` decimal(9,2) NOT NULL,
  `id_pedido` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario`
--

CREATE TABLE `funcionario` (
  `id_funcionario` int(6) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `email` varchar(35) NOT NULL,
  `senha` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `funcionario` (`id_funcionario`, `nome`, `email`, `senha`) VALUES
(1, 'Teste', 'admin', 'admin');


-- --------------------------------------------------------

--
-- Estrutura da tabela `pedido`
--

CREATE TABLE `pedido` (
  `id_pedido` int(6) NOT NULL,
  `valor` decimal(9,2) NOT NULL,
  `id_produto` int(6) NOT NULL,
  `quantidade` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `produto`
--

CREATE TABLE `produto` (
  `id_produto` int(6) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `descricao` varchar(55) NOT NULL,
  `categoria` varchar(30) NOT NULL,
  `valor` decimal(6,2) NOT NULL,
  `imagem` varchar(30) NOT NULL,
  `estoque` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `produto` (`id_produto`, `nome`, `descricao`, `categoria`, `valor`, `imagem`, `estoque`) VALUES
(2, 'Cafe Espresso', 'Graos selecionados e moidos na hora', 'cafe', '4.50', 'espresso.png', 10),
(3, 'Cafe Romano', 'Espresso com raspa de limao', 'cafe', '4.80', 'caferomano.jpg', 10),
(4, 'Cafe com Chantilly', ' Espresso com Chantilly', 'cafe', '5.80', 'espressochantilly.png', 10),
(5, 'Cafe Descafeinado', ' torrado e moido', 'cafe', '4.20', 'cafe.jpg', 10),
(6, ' Cafe Latte', '2/4 espresso com 2/4 leite cremoso', 'cafe', '5.50', 'cafelatte.png', 10),
(7, ' Mocha', 'Espresso com essencia de chocolate + Leite cremoso', 'cafe', '6.90', 'mocha.png', 10),
(8, 'Chocolate Quente', 'Com Chantilly e raspa de chocolate belga', 'cafe', '9.90', 'chocoquente.png', 10),
(9, 'Chocolate Gelado', ' Chantilly essencia macadania', 'cafe', '7.50', 'chocogelado.png', 10),
(10, 'Cafe Vanila', 'Espresso com essência de baunilha + Leite cremoso', 'cafe', '6.90', 'vanila.png', 10),
(11, 'Cappuccino  Italiano', ' 1/3  expresso , 1/3 leite , 1/3 de espuma de leite', 'cafe', '7.90', 'cappuccini.png', 10),
(12, 'Cha', 'Frutas Citricas', 'cafe', '4.50', 'cha.png', 10),
(13, 'Cookies ', 'como gotas de chocolate', 'paes', '3.90', 'cookies.png', 10),
(14, 'Croissant', 'Integral, Damasco', 'paes', '4.50', 'Croissant.png', 10),
(15, 'Pao  na chapa', 'Baguete com manteiga', 'paes', '2.90', 'paochapa.png', 10),
(16, 'Mil folhas', ' Doce de leite, creme de morango', 'paes', '6.90', '2milfolhas.png', 10),
(17, 'Brioche', ' Geleia de Damasco ou Amora', 'paes', '6.50', 'brioche.png', 10),
(18, 'Donut', 'Chocolate 50 % cacau', 'paes', '5.50', 'donut.png', 10),
(19, 'Pao sem gluten', 'Ciabatta', 'paes', '4.20', 'paosemgluten.png', 10),
(20, 'Folhado', ' Quatro queijo, palmito, ', 'paes', '4.50', 'folhado.png', 10),
(21, 'Pao de Queijo', 'simples, recheado  com salaminho', 'paes', '3.50', 'paodequeijo.png', 10),
(22, 'Torta', 'Chocolate, limao', 'paes', '6.50', 'tortachocolate.png', 10),
(23, 'Torta de Nozes Diet', 'Pão de lo de baunilha, nozes picadas, ovos moles e coco ralado', 'paes', '12.50', 'tortanozes.png', 10),
(24, 'Cup Cakes', 'chocolate com morango', 'paes', '6.90', 'cupcake.png', 10),
(25, 'Suco de frutas naturais  200ml,500ml', 'Laranja, Abacaxi, Melancia,Melao, Acai..', 'bebidas', '8.90', 'sucolaranja.png', 10),
(26, 'Agua de coco', 'amambi', 'bebidas', '12.90', 'aguacoco1.jpg', 10),
(27, 'Refrigerante', 'Coca-cola, Guarana', 'bebidas', '5.00', 'cocacola.png', 10),
(28, 'Agua', 'com e sem gas', 'bebidas', '3.50', 'agua.png', 10),
(29, 'Matte', 'Limao, pessego', 'bebidas', '4.00', 'matte.jpg', 10),
(30, 'Sanduiche Botafogo', '(Pao de sanduiche com pepino ,peito de peru com molho tartaro) + espresso + cockie', 'combo', '28.00', 'sandbotafogo.png', 10),
(31, 'Sanduiche Ciabatta', '( ciabatta, queijo provolone, presunto parma, tomate seco e azeitonas)  suco de laranja e cappuccini', 'combo', '32.00', 'ciabatta.png', 10),
(32, 'Combo 1', '(pao baguete, tomate,rucula , azeitona,queijo provolone) + Cafe Latte', 'combo', '12.00', 'combo1.jpg', 10),
(33, 'Combo 2', 'Pao na chapa mais cappuccini', 'combo', '6.00', 'combo2.jpg', 10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indexes for table `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id_compra`),
  ADD KEY `id_cliente` (`id_cliente`),
  ADD KEY `id_pedido` (`id_pedido`);

--
-- Indexes for table `funcionario`
--
ALTER TABLE `funcionario`
  ADD PRIMARY KEY (`id_funcionario`);

--
-- Indexes for table `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `id_produto` (`id_produto`);

--
-- Indexes for table `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`id_produto`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(6) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `compra`
--
ALTER TABLE `compra`
  MODIFY `id_compra` int(6) NOT NULL AUTO_INCREMENT;
  
--
-- AUTO_INCREMENT for table `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id_pedido` int(6) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `produto`
--
ALTER TABLE `produto`
  MODIFY `id_produto` int(6) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  ADD CONSTRAINT `compra_ibfk_2` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`);

--
-- Limitadores para a tabela `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id_produto`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
