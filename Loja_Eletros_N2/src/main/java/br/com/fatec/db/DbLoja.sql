CREATE DATABASE IF NOT EXISTS loja_eletro;

use loja_eletro;

CREATE TABLE IF NOT EXISTS loja(
    lojaID INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    telefone VARCHAR(11),
    email VARCHAR(20),
    endereco VARCHAR(100),
    cidade VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS produtos(
    produtoID INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    descricao VARCHAR(100),
    voltagem VARCHAR(3),
    preco DECIMAL
);

CREATE TABLE IF NOT EXISTS funcionarios(
    funcionarioId INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    cargo VARCHAR(50),
    salario DECIMAL,
    CPF VARCHAR(11),
    telefone VARCHAR(11),
    lojaID INT,
    FOREIGN KEY (lojaID) REFERENCES loja(lojaID)
);

CREATE TABLE IF NOT EXISTS estoque(
    produtoID INT,
    lojaID INT,
    quantidade INT,
    FOREIGN KEY (produtoID) REFERENCES produtos(produtoID),
    FOREIGN KEY (lojaID) REFERENCES loja(lojaID)
);

CREATE TABLE IF NOT EXISTS clientes(
    clienteID INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    CPF VARCHAR(11),
    email VARCHAR(50),
    endereco VARCHAR(100),
    lojaID INT,
    funcionarioID INT,
    FOREIGN KEY (lojaID) REFERENCES loja(lojaID),
    FOREIGN KEY (funcionarioID) REFERENCES funcionarios(funcionarioID)
);

INSERT INTO loja (nome, telefone, email, endereco, cidade)VALUES 
    ('Unidade São Caetano 1', 45454545, 'UnidadeSCS1@gmail.com', 'rua bel alience, 44', 'São Caetano'),
    ('Unidade São Caetano 2', 45454546, 'UnidadeSCS2@gmail.com', 'rua madeira, 15', 'São Caetano'),
    ('Unidade Mauá 1', 45454040, 'UnidadeMaua1@gmail.com', 'Avenida barão de mauá, 1.200', 'Mauá'),
    ('Unidade Mauá 2', 45454041, 'UnidadeMaua2@gmail.com', 'Avenida castelo branco, 125', 'Mauá'),
    ('Unidade Santo André 1', 45453030, 'UnidadeSantoAndre1@gmail.com', 'Rua das figueiras, 1.210', 'Santo André'),
    ('Unidade Santo André 2', 45453031, 'UnidadeSantoAndre2@gmail.com', 'Rua das monções, 145', 'Santo André');

INSERT INTO produtos (nome, descricao, voltagem, preco) VALUES
    ('Geladeira Consul', 'Geladeira Consul CRD37EB Cycle Defrost com Freezer Supercapacidade Branca - 334L', '110', 2184),
    ('Geladeira Brastemp ', 'Geladeira Brastemp BRE57AK Frost Free com Turbo Ice Inverse - 443L', '110', 4399),
    ('Fogão Consul', 'Fogão Consul 4 Bocas CFO4NAR com Mesa de Inox Acendimento Automático e Design Frente Única', '110', 1113),
    ('Fogão Brastemp', 'Fogão Brastemp 4 Bocas BFO4NBR Clean com Mesa de Inox, 2 Prateleiras Ajustáveis e Acendimento automático', 110, 1.405),
    ('Lavadoura de Roupas Consul', 'Lavadora de Roupas Consul 12kg CWH12BB com Dosagem Econômica e Ciclo Edredom ', 220, 1.779),
    ('Lavadoura de Roupas Electrolux', 'Lavadora de Roupas Electrolux LED14 Automática Essential Care com Cesto Inox, Jet&Clean e Ultra Filter 14kg - Branca', 220, 1.879),
    ('Forno Electrolux', 'Forno de Micro-ondas Electrolux MI41S com Painel Integrado - 31L', 110, 882),
    ('Forno Philco ', 'Forno Micro-ondas Philco PMO28 Espelhado - 28 L', 110, 799),
    ('Frigobar Brastemp', 'Frigobar Brastemp Retrô 76L Ice White', 110, 2.504),
    ('Frigobar Midea', 'Frigobar 67L preto Midea', 110, 1.299),
    ('Coifa Fogatti', 'Coifa de Parede Fogatti Vidro Curvo Slim Inox - 90cm', 110, 759),
    ('Coifa Electrolux', 'Coifa de Parede Electrolux 90cm com Luz de Led Efficient (CE9VX)', '110', 1174);

INSERT INTO funcionarios (nome, cargo, salario, cpf, telefone, lojaID) VALUES 
    ('Samanta', 'atendente', 1680, 12345678995, 123, 1),
    ('Thayla', 'Gerente', 4630, 21354687933, 123, 1),
    ('Gabriel', 'atendente', 1690, 87954622200, 123, 2),
    ('Pedro', 'Gerente', 4620, 54687922211, 123, 2),
    ('Luana', 'atendente', 1610, 22221354687900, 123, 3),
    ('Larissa', 'Gerente', 4630, 54633387911, 123, 3),
    ('André', 'atendente', 1620, 99974201366, 123, 5),
    ('Samuel', 'Gerente', 4600, 88874952100, 123, 5),
    ('Fabricio', 'atendente', 1600, 55564187900, 123, 6),
    ('Guilherme', 'Gerente', 4600, 22211130455, 123, 6);

INSERT INTO estoque (produtoID, lojaID, quantidade) VALUES 
    (1, 1, 25),
    (3, 1, 50),
    (4, 1, 43),
    (5, 1, 24),
    (6, 1, 7),
    (7, 1, 9),
    (8, 1, 33),
    (9, 1, 78),
    (10, 1, 44),
    (11, 1, 17),
    (12, 1, 16),
    (1, 2, 22),
    (2, 2, 9),
    (3, 2, 5),
    (4, 2, 23),
    (5, 2, 2),
    (7, 2, 9),
    (8, 2, 33),
    (9, 2, 28),
    (10, 2, 44),
    (11, 2, 17),
    (1, 3, 22),
    (2, 3, 9),
    (3, 3, 5),
    (4, 3, 23),
    (5, 3, 20),
    (6, 3, 7),
    (7, 3, 9),
    (8, 3, 33),
    (9, 3, 28),
    (10, 3, 44),
    (11, 3, 17),
    (12, 3, 16),
    (1, 4, 22),
    (2, 4, 90),
    (5, 4, 20),
    (6, 4, 7),
    (7, 4, 9),
    (9, 4, 28),
    (10, 4, 44),
    (11, 4, 17),
    (12, 4, 16),
    (1, 5, 22),
    (4, 5, 23),
    (5, 5, 2),
    (6, 5, 7),
    (7, 5, 9),
    (8, 5, 33),
    (9, 5, 28),
    (10, 5, 44),
    (11, 5, 17),
    (12, 5, 16),
    (1, 6, 22),
    (2, 6, 95),
    (3, 6, 5),
    (6, 6, 71),
    (7, 6, 9),
    (8, 6, 33),
    (9, 6, 28),
    (11, 6, 17),
    (12, 6, 16);

INSERT INTO clientes (nome, cpf, email, endereco, lojaID, funcionarioID) VALUES 
    ('Nicole Hirata', 12345678931, 'nicole@gmail.com','Estrada das lagrimas, 1.000', 1, 1),
    ('Matheus Amaral', 98765432101, 'matheus@gmail.com','Estrada do carneiro, 1.230', 1, 2),
    ('Jonathan Cicero', 65432198701, 'jonathan@gmail.com','Rua são josé do egito, 123', 2, 3),
    ('Eduarda Perez', 12365498700, 'eduarda@gmail.com','Rua fiker, 144', 2, 4),
    ('Amanda Diaz', 21354687900, 'amanda@gmai.com','Rua das palmeiras, 988', 3, 5),
    ('Rony Amorim', 78965412300, 'rony@gmail.com','Rua das flores, 166', 3, 6),
    ('Lucas Duarte', 12345678931, 'lucas@gmail.com','Estrada das lagrimas, 1.000', 4, 7),
    ('Marta Santos', 98765432101, 'marta@gmail.com','Estrada do carneiro, 1.230', 4, 8),
    ('Marcia Santos', 65432198701, 'marcia@gmail.com','Rua são josé do egito, 123', 5, 9),
    ('Lucia Souza', 12365498700, 'lucia@gmail.com','Rua fiker, 144', 5, 10),
    ('Tânia Maria', 21354687900, 'tania@gmail.com','Rua das palmeiras, 988', 6, 10);
